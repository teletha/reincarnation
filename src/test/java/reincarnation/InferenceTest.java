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

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

class InferenceTest {
    @Test
    void instanceOfClass() {
        Type type = String.class;

        assert Inference.instanceOf(type, String.class);
        assert Inference.instanceOf(type, Object.class);
        assert Inference.instanceOf(type, CharSequence.class);
        assert Inference.instanceOf(type, Serializable.class);

        assert Inference.instanceOf(type, Date.class) == false;
        assert Inference.instanceOf(type, AutoCloseable.class) == false;
    }

    @Test
    void instanceOfParameterizedType() {
        Type type = LinkedList.class.getGenericSuperclass();

        assert type instanceof ParameterizedType;
        assert Inference.instanceOf(type, AbstractList.class);
        assert Inference.instanceOf(type, Object.class);
        assert Inference.instanceOf(type, List.class);

        assert Inference.instanceOf(type, Date.class) == false;
        assert Inference.instanceOf(type, AutoCloseable.class) == false;
    }

    @Test
    void instanceOfExtends() {
        class X<A extends List<? extends String>> {
        }

        TypeVariable variable = X.class.getTypeParameters()[0];
        ParameterizedType list = (ParameterizedType) variable.getBounds()[0];
        WildcardType type = (WildcardType) list.getActualTypeArguments()[0];

        assert type instanceof WildcardType;
        assert Inference.instanceOf(type, String.class);
        assert Inference.instanceOf(type, CharSequence.class);

        assert Inference.instanceOf(type, Date.class) == false;
        assert Inference.instanceOf(type, AutoCloseable.class) == false;
    }

    @Test
    void instanceOfSuper() {
        class X<A extends List<? super ArrayList>> {
        }

        TypeVariable variable = X.class.getTypeParameters()[0];
        ParameterizedType list = (ParameterizedType) variable.getBounds()[0];
        WildcardType type = (WildcardType) list.getActualTypeArguments()[0];

        assert type instanceof WildcardType;
        assert Inference.instanceOf(type, ArrayList.class) == false;
        assert Inference.instanceOf(type, AbstractList.class) == false;

        assert Inference.instanceOf(type, Date.class) == false;
        assert Inference.instanceOf(type, AutoCloseable.class) == false;
    }

    @Test
    void instanceOfTypeVariable() {
        class X<A extends CharSequence & Serializable> {
        }

        TypeVariable type = X.class.getTypeParameters()[0];
        assert type instanceof TypeVariable;
        assert Inference.instanceOf(type, String.class) == false;
        assert Inference.instanceOf(type, CharSequence.class);
        assert Inference.instanceOf(type, Serializable.class);

        assert Inference.instanceOf(type, Date.class) == false;
        assert Inference.instanceOf(type, AutoCloseable.class) == false;
    }
}