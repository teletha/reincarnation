/*
 * Copyright (C) 2024 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * This annotation is provided for Reicarnation developers. Normally, it should be included in the
 * test code, but due to unavoidable circumstances, it has been exposed in the implementation code.
 * </p>
 * <p>
 * If any strange behavior is found when decompiling an external class, this annotation is expected
 * to help visualize the bytecode conversion process and facilitate problem solving.
 * </p>
 */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Debuggable {
    boolean fernflower() default false;
}