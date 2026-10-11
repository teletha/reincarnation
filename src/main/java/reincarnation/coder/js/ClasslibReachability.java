/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Computes the method level reachability for the TeaVM class library based JavaScript program. The
 * whole class based closure pulls almost every class through {@code java.lang.Object}, so the
 * reachable methods (and the classes which declare them) are computed here to prune the output.
 * <p>
 * The virtual dispatch is taken into account by propagating the reachable method signatures to the
 * overriding methods of every reachable class.
 * </p>
 */
public final class ClasslibReachability {

    /** The reachability result. */
    public static final class Result {

        /** The reachable classes. */
        public final Set<Class> classes = new LinkedHashSet();

        /** The reachable method signatures ({@code name#desc}). */
        public final Set<String> signatures = new HashSet();

        /** The class dependency edges (referencer to referenced). */
        public final Map<Class, Set<Class>> dependencies = new java.util.HashMap();
    }

    /** The per method references. */
    private static final class MethodRefs {

        /** The referenced method keys. */
        private final Set<String> methods = new HashSet();

        /** The referenced type internal names. */
        private final Set<String> types = new HashSet();
    }

    /** The per class scan result. */
    private static final class ClassRefs {

        /** The references by method signature ({@code name#desc}). */
        private final Map<String, MethodRefs> methods = new HashMap();
    }

    /**
     * Hide constructor.
     */
    private ClasslibReachability() {
    }

    /**
     * Analyze the method level reachability from the specified root class.
     * 
     * @param root A root class.
     * @return A reachability result.
     */
    public static Result analyze(Class<?> root) {
        return new Analyzer(root).run();
    }

    /**
     * The reachability analyzer.
     */
    private static final class Analyzer {

        /** The result. */
        private final Result result = new Result();

        /** The class scan cache. */
        private final Map<Class, ClassRefs> cache = new HashMap();

        /** The pending methods. */
        private final Deque<String[]> queue = new ArrayDeque();

        /** The scanned method keys. */
        private final Set<String> scanned = new HashSet();

        /**
         * @param root A root class.
         */
        private Analyzer(Class<?> root) {
            ClassRefs refs = scan(root);
            if (refs != null) {
                addClass(root);
                for (String signature : refs.methods.keySet()) {
                    addSignature(signature);
                    addMethod(root, signature);
                }
            }
        }

        /**
         * Run the analysis.
         * 
         * @return A result.
         */
        private Result run() {
            while (!queue.isEmpty()) {
                String[] member = queue.poll();
                Class owner = load(member[0]);
                if (owner == null) {
                    continue;
                }
                ClassRefs refs = cache.computeIfAbsent(owner, ClasslibReachability::scan);
                if (refs == null) {
                    continue;
                }
                MethodRefs reference = refs.methods.get(member[1] + "#" + member[2]);
                if (reference == null) {
                    continue;
                }

                boolean definitionTime = member[1].equals("<clinit>");
                for (String type : reference.types) {
                    Class dependency = load(type);
                    addClass(dependency);
                    if (definitionTime) {
                        edge(owner, dependency);
                    }
                }
                for (String method : reference.methods) {
                    int first = method.indexOf('#');
                    int second = method.indexOf('#', first + 1);
                    String ownerName = method.substring(0, first);
                    if (isJdk(ownerName) || isUnsupported(ownerName)) {
                        continue;
                    }
                    String name = method.substring(first + 1, second);
                    String descriptor = method.substring(second + 1);
                    Class dependency = load(ownerName);
                    addClass(dependency);
                    if (definitionTime) {
                        edge(owner, dependency);
                    }
                    addSignature(name + "#" + descriptor);
                    addMethod(ownerName, name, descriptor);
                }
            }

            // Include the super classes and the interfaces of the reachable classes.
            Deque<Class> expand = new ArrayDeque(result.classes);
            while (!expand.isEmpty()) {
                Class clazz = expand.poll();
                Class parent = sub(clazz.getSuperclass());
                if (parent != null && result.classes.add(parent)) {
                    expand.add(parent);
                }
                for (Class inf : clazz.getInterfaces()) {
                    Class substituted = sub(inf);
                    if (substituted != null && result.classes.add(substituted)) {
                        expand.add(substituted);
                    }
                }
            }
            return result;
        }

        /**
         * Substitute the class and skip the standard classes which have no class library.
         * 
         * @param clazz A target class.
         * @return A substituted class or null.
         */
        private static Class sub(Class clazz) {
            Class substituted = Classlib.substitute(clazz);
            return substituted == null || isJdk(substituted.getName()) || isUnsupported(substituted.getName()) ? null
                    : substituted;
        }

        /**
         * Check whether the class is not supported in the JavaScript runtime (native methods such as
         * the string injection, the thread or platform infrastructure).
         * 
         * @param name A class name (dot or slash separated).
         * @return A result.
         */
        private static boolean isUnsupported(String name) {
            String normalized = name.replace('/', '.');
            return normalized.startsWith("org.teavm.classlib.java.lang.TThread") //
                    || normalized.startsWith("org.teavm.classlib.java.lang.TRunnable") //
                    || normalized.contains("UncaughtExceptionHandler") //
                    || normalized.endsWith("TObject$Monitor") //
                    || normalized.startsWith("org.teavm.backend") //
                    || normalized.startsWith("org.teavm.platform") //
                    || normalized.startsWith("org.teavm.runtime") //
                    || normalized.startsWith("org.teavm.classlib.java.lang.reflect") //
                    || normalized.equals("org.teavm.classlib.java.lang.TClass") //
                    || normalized.equals("org.teavm.classlib.java.lang.TString") //
                    || normalized.equals("org.teavm.classlib.java.lang.TAbstractStringBuilder") //
                    || normalized.equals("org.teavm.classlib.java.lang.TStringBuilder") //
                    || normalized.equals("org.teavm.classlib.java.lang.TStringBuffer") //
                    || normalized.equals("org.teavm.classlib.java.lang.TAppendable");
        }

        /**
         * Check whether the class name belongs to the JDK namespace.
         * 
         * @param name A class name (dot or slash separated).
         * @return A result.
         */
        private static boolean isJdk(String name) {
            String normalized = name.replace('/', '.');
            return normalized.startsWith("java.") || normalized.startsWith("javax.") //
                    || normalized.startsWith("jdk.") || normalized.startsWith("sun.") //
                    || normalized.startsWith("com.sun.");
        }

        /**
         * Add the class and propagate the reachable signatures to its declared methods.
         * 
         * @param clazz A target class.
         */
        private void addClass(Class clazz) {
            if (clazz == null || isJdk(clazz.getName()) || isUnsupported(clazz.getName())) {
                return;
            }
            if (result.classes.add(clazz)) {
                // The static initializer is always executed, so it must be included.
                addDeclared(clazz, "<clinit>#()");
                for (String signature : new ArrayList<>(result.signatures)) {
                    addDeclared(clazz, signature);
                }
            }
        }

        /**
         * Add the class dependency edge.
         * 
         * @param from A referencing class.
         * @param to A referenced class.
         */
        private void edge(Class from, Class to) {
            if (from != null && to != null && from != to && !isJdk(to.getName())) {
                result.dependencies.computeIfAbsent(from, key -> new HashSet()).add(to);
            }
        }

        /**
         * Add the method signature and propagate it to the overriding methods of the reachable
         * classes.
         * 
         * @param signature A method signature.
         */
        private void addSignature(String signature) {
            if (result.signatures.add(signature)) {
                for (Class clazz : new ArrayList<>(result.classes)) {
                    addDeclared(clazz, signature);
                }
            }
        }

        /**
         * Add the method if the class declares the specified signature.
         * 
         * @param clazz A target class.
         * @param signature A method signature.
         */
        private void addDeclared(Class clazz, String signature) {
            int index = signature.indexOf('#');
            String name = signature.substring(0, index);
            String descriptor = signature.substring(index + 1);
            ClassRefs refs = cache.computeIfAbsent(clazz, ClasslibReachability::scan);
            if (refs != null && refs.methods.containsKey(name + "#" + descriptor)) {
                addMethod(clazz.getName().replace('.', '/'), name, descriptor);
            }
        }

        /**
         * Add the declared method.
         * 
         * @param clazz A target class.
         * @param signature A method signature.
         */
        private void addMethod(Class clazz, String signature) {
            int index = signature.indexOf('#');
            addMethod(clazz.getName().replace('.', '/'), signature.substring(0, index), signature.substring(index + 1));
        }

        /**
         * Add the method by the owner name.
         * 
         * @param owner An internal owner name.
         * @param name A method name.
         * @param descriptor A method descriptor.
         */
        private void addMethod(String owner, String name, String descriptor) {
            if (scanned.add(owner + "#" + name + "#" + descriptor)) {
                queue.add(new String[] { owner, name, descriptor });
            }
        }
    }

    /**
     * Load the class by its name.
     * 
     * @param name A class name.
     * @return A class or null.
     */
    private static Class load(String name) {
        try {
            return Classlib.substitute(Class.forName(name.replace('/', '.'), false, ClasslibReachability.class
                    .getClassLoader()));
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * Normalize the referenced method to the class which actually declares it, so that the coder
     * (which resolves the declaring class) can match the signature.
     * 
     * @param owner An internal owner name.
     * @param name A method name.
     * @param descriptor A method descriptor.
     * @return A normalized method key.
     */
    private static String normalize(String owner, String name, String descriptor) {
        String params = Classlib.params(descriptor);
        Class<?> substituted = load(owner);
        String found = findDeclared(substituted, name, params, new HashSet());
        if (found != null) {
            return found;
        }
        String ownerName = substituted != null ? substituted.getName().replace('.', '/') : owner;
        return ownerName + "#" + name + "#" + params;
    }

    /**
     * Find the method which actually declares the specified signature.
     * 
     * @param clazz A target class.
     * @param name A method name.
     * @param params A parameter descriptor.
     * @param visited A visited classes.
     * @return A method key or null.
     */
    private static String findDeclared(Class<?> clazz, String name, String params, Set<Class> visited) {
        if (clazz == null || !visited.add(clazz)) {
            return null;
        }

        if (name.equals("<init>")) {
            for (java.lang.reflect.Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                if (Classlib.params(Type.getConstructorDescriptor(constructor)).equals(params)) {
                    return clazz.getName().replace('.', '/') + "#<init>#" + params;
                }
            }
        } else {
            for (java.lang.reflect.Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(name) && Classlib.params(Type.getMethodDescriptor(method)).equals(params)) {
                    return clazz.getName().replace('.', '/') + "#" + name + "#" + params;
                }
            }
        }

        String found = findDeclared(clazz.getSuperclass(), name, params, visited);
        if (found != null) {
            return found;
        }
        for (Class<?> inf : clazz.getInterfaces()) {
            found = findDeclared(inf, name, params, visited);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * Scan the specified class.
     * 
     * @param clazz A target class.
     * @return A scan result or null if the bytecode is not available.
     */
    private static ClassRefs scan(Class<?> clazz) {
        try (InputStream input = clazz.getResourceAsStream("/" + clazz.getName().replace('.', '/') + ".class")) {
            if (input == null) {
                return null;
            }
            ClassRefs refs = new ClassRefs();
            new ClassReader(input).accept(new ClassVisitor(Opcodes.ASM9) {

                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    MethodRefs method = refs.methods.computeIfAbsent(name + "#" + Classlib.params(descriptor), key -> new MethodRefs());
                    return new MethodVisitor(Opcodes.ASM9) {

                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                            method.types.add(owner);
                            method.methods.add(normalize(owner, name, descriptor));
                        }

                        @Override
                        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                            method.types.add(owner);
                            method.types.add(Type.getType(descriptor).getInternalName());
                        }

                        @Override
                        public void visitTypeInsn(int opcode, String type) {
                            method.types.add(type);
                        }

                        @Override
                        public void visitLdcInsn(Object value) {
                            // The assertion setup in the static initializer references the enclosing
                            // class only for Class#desiredAssertionStatus, so it must be ignored.
                            if (value instanceof Type && name.equals("<clinit>")) {
                                return;
                            }
                            collect(method, value);
                        }

                        @Override
                        public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrap, Object... args) {
                            if (bootstrap != null) {
                                method.types.add(bootstrap.getOwner());
                            }
                            for (Object arg : args) {
                                collect(method, arg);
                            }
                        }

                        @Override
                        public void visitTryCatchBlock(org.objectweb.asm.Label start, org.objectweb.asm.Label end, org.objectweb.asm.Label handler, String type) {
                            if (type != null) {
                                method.types.add(type);
                            }
                        }
                    };
                }

                @Override
                public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                    if (value instanceof Type type) {
                        refs.methods.computeIfAbsent("<clinit>#()", key -> new MethodRefs()).types.add(type
                                .getInternalName());
                    }
                    return null;
                }
            }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            return refs;
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * Collect the referenced type from the constant.
     * 
     * @param method A target method references.
     * @param value A constant value.
     */
    private static void collect(MethodRefs method, Object value) {
        if (value instanceof Type type) {
            if (type.getSort() == Type.OBJECT || type.getSort() == Type.ARRAY) {
                method.types.add(type.getInternalName());
            }
        } else if (value instanceof Handle handle) {
            method.types.add(handle.getOwner());
            method.methods.add(normalize(handle.getOwner(), handle.getName(), handle.getDesc()));
        } else if (value instanceof ConstantDynamic dynamic) {
            method.types.add(dynamic.getDescriptor());
            for (int i = 0; i < dynamic.getBootstrapMethodArgumentCount(); i++) {
                collect(method, dynamic.getBootstrapMethodArgument(i));
            }
        }
    }
}
