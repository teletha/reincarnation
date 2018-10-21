/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.coder.java;

import java.util.HashSet;
import java.util.Set;

import kiss.model.Model;

/**
 * @version 2018/10/19 14:24:55
 */
class Imports {

    /** The core packege classes. */
    private static final Set<Class> cores = new HashSet();

    static {
        // interfaces
        cores.add(Appendable.class);
        cores.add(AutoCloseable.class);
        cores.add(CharSequence.class);
        cores.add(Cloneable.class);
        cores.add(Comparable.class);
        cores.add(Iterable.class);
        cores.add(ProcessHandle.class);
        cores.add(Readable.class);
        cores.add(Runnable.class);

        // classes
        cores.add(Boolean.class);
        cores.add(Byte.class);
        cores.add(Class.class);
        cores.add(ClassLoader.class);
        cores.add(ClassValue.class);
        cores.add(Double.class);
        cores.add(Enum.class);
        cores.add(Float.class);
        cores.add(InheritableThreadLocal.class);
        cores.add(Integer.class);
        cores.add(Long.class);
        cores.add(Math.class);
        cores.add(Module.class);
        cores.add(ModuleLayer.class);
        cores.add(Number.class);
        cores.add(Object.class);
        cores.add(Package.class);
        cores.add(Process.class);
        cores.add(ProcessBuilder.class);
        cores.add(Runtime.class);
        cores.add(RuntimePermission.class);
        cores.add(SecurityManager.class);
        cores.add(Short.class);
        cores.add(StackTraceElement.class);
        cores.add(StrictMath.class);
        cores.add(String.class);
        cores.add(StringBuffer.class);
        cores.add(StringBuilder.class);
        cores.add(System.class);
        cores.add(Thread.class);
        cores.add(ThreadGroup.class);
        cores.add(ThreadLocal.class);
        cores.add(Throwable.class);
        cores.add(Void.class);

        // Exceptions
        cores.add(ArithmeticException.class);
        cores.add(ArrayIndexOutOfBoundsException.class);
        cores.add(ArrayStoreException.class);
        cores.add(ClassCastException.class);
        cores.add(ClassNotFoundException.class);
        cores.add(CloneNotSupportedException.class);
        cores.add(EnumConstantNotPresentException.class);
        cores.add(Exception.class);
        cores.add(IllegalAccessError.class);
        cores.add(IllegalArgumentException.class);
        cores.add(IllegalCallerException.class);
        cores.add(IllegalMonitorStateException.class);
        cores.add(IllegalThreadStateException.class);
        cores.add(IndexOutOfBoundsException.class);
        cores.add(InstantiationException.class);
        cores.add(InterruptedException.class);
        cores.add(LayerInstantiationException.class);
        cores.add(NegativeArraySizeException.class);
        cores.add(NoSuchFieldException.class);
        cores.add(NoSuchMethodException.class);
        cores.add(NullPointerException.class);
        cores.add(NumberFormatException.class);
        cores.add(ReflectiveOperationException.class);
        cores.add(RuntimeException.class);
        cores.add(SecurityException.class);
        cores.add(StringIndexOutOfBoundsException.class);
        cores.add(TypeNotPresentException.class);
        cores.add(UnsupportedOperationException.class);

        // Errors
        cores.add(AbstractMethodError.class);
        cores.add(AssertionError.class);
        cores.add(BootstrapMethodError.class);
        cores.add(ClassCircularityError.class);
        cores.add(ClassFormatError.class);
        cores.add(Error.class);
        cores.add(ExceptionInInitializerError.class);
        cores.add(IllegalAccessError.class);
        cores.add(IncompatibleClassChangeError.class);
        cores.add(InstantiationError.class);
        cores.add(InternalError.class);
        cores.add(LinkageError.class);
        cores.add(NoClassDefFoundError.class);
        cores.add(NoSuchFieldError.class);
        cores.add(NoSuchMethodError.class);
        cores.add(OutOfMemoryError.class);
        cores.add(StackOverflowError.class);
        cores.add(ThreadDeath.class);
        cores.add(UnknownError.class);
        cores.add(UnsatisfiedLinkError.class);
        cores.add(UnsupportedClassVersionError.class);
        cores.add(VerifyError.class);
        cores.add(VirtualMachineError.class);

        // Annotatios
        cores.add(Deprecated.class);
        cores.add(FunctionalInterface.class);
        cores.add(Override.class);
        cores.add(SafeVarargs.class);
        cores.add(SuppressWarnings.class);
    }

    /** The imported class. */
    final Set<Class> imported = new HashSet();

    /** The imported simple class name. */
    private final Set<String> importedName = new HashSet();

    /** The Implicitly imported class. */
    final Set<Class> importedImplicitly = new HashSet();

    /** The Implicitly imported class. */
    private final Set<String> importedNameImplicitly = new HashSet();

    Set<Class> hierarchy = new HashSet();

    /** The base class. */
    private Class base;

    /** The base package. */
    private Package basePackage;

    /**
     * Set base class.
     * 
     * @param base
     */
    void setBase(Class base) {
        this.base = base;
        this.basePackage = base.getPackage();

        addImplicitly(base);

        hierarchy = Model.collectTypes(base);

        for (Class hierarchy : hierarchy) {
            for (Class member : hierarchy.getDeclaredClasses()) {
                addImplicitly(member);
            }
        }

        for (Class core : cores) {
            addImplicitly(core);
        }
    }

    private void addImplicitly(Class clazz) {
        String simple = clazz.getSimpleName();

        if (importedNameImplicitly.add(simple)) {
            importedImplicitly.add(clazz);
        }
    }

    /**
     * Try to import class.
     * 
     * @param clazz A class to import.
     */
    void add(Class clazz) {
        if (clazz.isAnonymousClass() || clazz.getPackage().getName().equals("java.lang")) {
            return;
        }

        String simple = clazz.getSimpleName();

        if (importedName.add(simple)) {
            imported.add(clazz);
        }
    }

    /**
     * Try to import class.
     * 
     * @param classes Classes to import.
     */
    void add(Set<Class> classes) {
        for (Class clazz : classes) {
            add(clazz);
        }
    }

    /**
     * Check whether the specified class is imported or not.
     * 
     * @param clazz
     * @return
     */
    boolean contains(Class clazz) {
        return imported.contains(clazz);
    }

    /**
     * Compute fully qualified class name.
     * 
     * @param clazz A target class.
     * @return A class name.
     */
    String name(Class clazz) {
        if (clazz.isPrimitive()) {
            return clazz.getName();
        }

        if (imported.contains(clazz) || importedImplicitly.contains(clazz)) {
            return clazz.getSimpleName();
        }

        return clazz.getCanonicalName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Imported \t").append(imported).append("\r\n");
        builder.append("Names    \t").append(importedName).append("\r\n");

        return builder.toString();
    }
}
