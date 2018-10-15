/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import reincarnation.coder.JavaCoder;

/**
 * @version 2018/10/15 10:01:59
 */
public class JavaReincarnation extends Reincarnation<JavaReincarnation> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void reborn(JavaSourceCode code, Appendable output) {
        JavaCoder coder = new JavaCoder(output);
        JavaSourceCode root = code.enclosingRoot();

        if (root == code) {
            root.write(coder);
        } else {
            root.write(coder, code.clazz);
        }
    }

    /**
     * Compute the fully qualified class name of the specified class.
     * 
     * @param clazz A target class.
     * @return A fully qualified class name.
     */
    public static final String name(Class clazz) {
        if (clazz.isAnonymousClass() || clazz.isLocalClass()) {
            String name = clazz.getName();
            return clazz.getEnclosingClass().getName() + "$" + name.substring(name.lastIndexOf(".") + 1);
        } else {
            return clazz.getName();
        }
    }

    /**
     * Compute the simple class name of the specified class.
     * 
     * @param clazz A target class.
     * @return A simple class name.
     */
    public static final String simpleName(Class clazz) {
        if (clazz.isAnonymousClass()) {
            String name = clazz.getName();
            return name.substring(name.lastIndexOf(".") + 1);
        } else {
            return clazz.getSimpleName();
        }
    }
}
