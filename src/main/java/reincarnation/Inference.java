/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.List;

class Inference {

    /**
     * Test whether the specified type is instance of the required class or not.
     * 
     * @param target A target type to test.
     * @param required A required type.
     * @return
     */
    static boolean instanceOf(Type target, Class required) {
        if (target == null || required == null) {
            return false;
        }

        if (target instanceof Class clazz) {
            return required.isAssignableFrom(clazz);
        } else if (target instanceof ParameterizedType parameterized) {
            return instanceOf(parameterized.getRawType(), required);
        } else if (target instanceof WildcardType wild) {
            for (Type upper : wild.getUpperBounds()) {
                if (!instanceOf(upper, required)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static ParameterizedType embody(ParameterizedType parameterized, List<Operand> params) {
        return new ParameterizedType() {

            @Override
            public Type getRawType() {
                return parameterized.getRawType();
            }

            @Override
            public Type getOwnerType() {
                return parameterized.getOwnerType();
            }

            @Override
            public Type[] getActualTypeArguments() {
                return params.stream().map(x -> x.type.v).toArray(Type[]::new);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                return getRawType() + "(" + Arrays.toString(getActualTypeArguments()) + ")";
            }
        };
    }
}
