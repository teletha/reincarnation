/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package js.lang;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the Java implementation of a JavaScript native API.
 * <p>
 * The Java body is used when the code runs on the JVM, while the annotation value is used when the
 * code is transpiled into JavaScript. The template accepts {@code $this} for the receiver and
 * {@code $0}, {@code $1}, ... for the arguments.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
public @interface Native {

    /**
     * The JavaScript expression template. An empty value means that the element only marks a
     * native type.
     * 
     * @return A JavaScript expression.
     */
    String value() default "";
}
