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

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
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
        } else if (target instanceof TypeVariable variable) {
            for (Type bound : variable.getBounds()) {
                if (instanceOf(bound, required)) {
                    return true;
                }
            }
            return false;
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

    /**
     * Specialized the return type of the specified method.
     * 
     * @param method
     * @return
     */
    static Type specialize(Method method, Type owner, List<Operand> parameters) {
        Type definedReturnType = method.getGenericReturnType();

        if (definedReturnType instanceof Class) {
            return definedReturnType;
        }

        if (definedReturnType instanceof TypeVariable variable && owner instanceof ParameterizedType parameterized) {
            GenericDeclaration declaration = variable.getGenericDeclaration();
            TypeVariable<?>[] declaredParameters = declaration.getTypeParameters();

            for (int i = 0; i < declaredParameters.length; i++) {
                if (declaredParameters[i].equals(variable)) {
                    return parameterized.getActualTypeArguments()[i];
                }
            }
        }

        if (definedReturnType instanceof ParameterizedType parameterized) {
            // Type[] args = parameterized.getActualTypeArguments();
            // for (int i = 0; i < args.length; i++) {
            // if (args[i] instanceof TypeVariable variable) {
            // GenericDeclaration declaration = variable.getGenericDeclaration();
            // System.out.println(i + " " + args[i] + " " + parameters
            // .size() + " " + method + " " + declaration + " " + declaration.getClass());
            // args[i] = parameters.get(i).type.v;
            // }
            // }
            // return parameterized;
        }

        return method.getReturnType();
    }
}