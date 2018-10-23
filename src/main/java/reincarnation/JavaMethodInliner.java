/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import static org.objectweb.asm.Opcodes.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import antibug.bytecode.Agent.Translator;
import kiss.I;

/**
 * @version 2018/10/23 12:24:42
 */
class JavaMethodInliner {

    /** The code manager. */
    private static final Map<String, BiFunction<List<Operand>, Node, String>> inlines = new HashMap();

    /**
     * @param name
     * @param desc
     * @return
     */
    public static boolean isInlinable(String name, Class type) {
        return name.startsWith("access$") && type == void.class;
    }

    /**
     * <p>
     * Create inline translator.
     * </p>
     * 
     * @param owner
     * @param name
     * @param desc
     * @return
     */
    public static BiFunction<List<Operand>, Node, String> inline(Class owner, String name, String desc) {
        String id = id(owner, name, desc);
        BiFunction<List<Operand>, Node, String> translator = inlines.get(id);

        if (translator == null) {
            try {
                new ClassReader(owner.getName()).accept(new InlineClassParser(owner), 0);
            } catch (IOException e) {
                throw I.quiet(e);
            }
            translator = inlines.get(id);
        }
        return translator;
    }

    /**
     * <p>
     * Create the identical key for the specified method.
     * </p>
     * 
     * @param owner A method owner class.
     * @param method A method name.
     * @param desc A method description.
     * @return A created key.
     */
    private static String id(Class owner, String method, String desc) {
        return owner + "#" + method + desc;
    }

    /**
     * @version 2015/01/22 12:22:21
     */
    private static class InlineClassParser extends ClassVisitor {

        /** The method name. */
        private final Class owner;

        /**
         * @param owner
         */
        private InlineClassParser(Class owner) throws IOException {
            super(ASM5);

            this.owner = owner;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if ((access & ACC_SYNTHETIC) != 0 && name.startsWith("access$") && desc.endsWith("V")) {
                return new InlineMethodParser(id(owner, name, desc));
            }
            return null;
        }

        /**
         * @version 2015/01/22 12:22:21
         */
        private class InlineMethodParser extends MethodVisitor {

            /** The method identifier. */
            private final String id;

            /**
             * <p>
             * Parse method to write inline code.
             * </p>
             * 
             * @param id A method identifier.
             */
            private InlineMethodParser(String id) {
                super(ASM5);

                this.id = id;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void visitFieldInsn(int opcode, String ownerClassName, String name, String desc) {
                Class owner = JavaMethodCompiler.convert(ownerClassName);

                switch (opcode) {
                case PUTSTATIC:
                    inlines.put(id, (contexts, current) -> {
                        return translator.translateStaticField(owner, name) + "=" + contexts.get(0);
                    });
                    break;

                case PUTFIELD:
                    inlines.put(id, (contexts, current) -> {
                        return translator.translateField(owner, name, contexts.remove(0)) + "=" + contexts.remove(0);
                    });
                    break;

                default:
                    break;
                }
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void visitMethodInsn(int opcode, String className, String methodName, String desc, boolean access) {
                Class owner = JavaMethodCompiler.convert(className);

                // compute parameter types
                Type[] types = Type.getArgumentTypes(desc);
                Class[] parameters = new Class[types.length];

                for (int i = 0; i < types.length; i++) {
                    parameters[i] = JavaMethodCompiler.convert(types[i]);
                }

                // retrieve translator for this method owner
                Translator translator = TranslatorManager.getTranslator(owner);

                switch (opcode) {
                case INVOKESTATIC:
                    inlines.put(id, (contexts, current) -> {
                        return translator.translateStaticMethod(owner, methodName, desc, parameters, contexts);
                    });
                    break;

                case INVOKESPECIAL:
                case INVOKEVIRTUAL:
                    inlines.put(id, (contexts, current) -> {
                        return translator.translateMethod(owner, methodName, desc, parameters, contexts);
                    });
                    break;

                default:
                    break;
                }
            }
        }
    }
}
