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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import kiss.I;
import kiss.Model;
import kiss.Signal;
import kiss.Variable;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.coder.CodingOption;
import reincarnation.coder.DelegatableCoder;
import reincarnation.operator.AccessMode;
import reincarnation.util.GeneratedCodes;

class OperandMethodCall extends Operand {

    /** The special method. */
    private static final Method StringBuilderToString;

    static {
        try {
            StringBuilderToString = StringBuilder.class.getDeclaredMethod("toString");
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /** The call mode. */
    private final AccessMode mode;

    /** The method. */
    final Method method;

    /** The context. */
    final Operand owner;

    /** The method parameters. */
    final List<Operand> params;

    /**
     * @param ownerType
     * @param methodName
     * @param parameters
     */
    OperandMethodCall(AccessMode mode, Class ownerType, String methodName, Class[] parameterTypes, Operand owner, List<Operand> parameters) {
        this.mode = mode;
        this.method = find(ownerType, methodName, parameterTypes);
        this.owner = owner;
        this.params = parameters;

        fix(Inference.specialize(method, owner.type.v, parameters));
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters.get(i).fix(parameterTypes[i]);
        }
    }

    /**
     * Find the suitable {@link Method}.
     * 
     * @param owner A method owner.
     * @param name A method name.
     * @return
     */
    private Method find(Class owner, String name, Class[] types) {
        Set<Class> collections = Model.collectTypes(owner);

        // In Javac, when the interface method reference invokes the Object class method,
        // it is compiled as a call to the interface, not to the object. However, this would fail to
        // find methods of the Object class, so the Object class is added unconditionally.
        collections.add(Object.class);

        for (Class type : collections) {
            try {
                Method method = type.getDeclaredMethod(name, types);
                int mod = method.getModifiers();
                if (Modifier.isPrivate(mod)) {
                    if (type != owner) continue;
                } else if (Modifier.isProtected(mod)) {
                    if (!type.isAssignableFrom(owner)) continue;
                } else if (Modifier.isPublic(mod)) {
                    // accept all
                } else /* if package private */ {
                    if (type.getPackage() != owner.getPackage()) continue;
                }
                return method;
            } catch (NoSuchMethodException e) {
                // ignore
            }
        }
        throw new NoSuchMethodError(owner + "#" + name + Stream.of(types)
                .map(Class::getSimpleName)
                .collect(Collectors.joining(", ", "(", ")")));
    }

    /**
     * Check method caller.
     * 
     * @param caller
     * @return
     */
    boolean checkCaller(Operand caller) {
        return owner.equals(caller);
    }

    /**
     * Check method caller.
     * 
     * @param caller
     * @return
     */
    boolean checkCaller(Signal<Operand> caller) {
        return caller.to().is(owner);
    }

    /**
     * Check method signature.
     * 
     * @param owner
     * @param name
     * @param params
     * @return
     */
    boolean checkMethod(Class owner, String name, Class... params) {
        if (!owner.isAssignableFrom(method.getDeclaringClass())) {
            return false;
        }

        if (!name.equals(method.getName())) {
            return false;
        }

        Parameter[] parameters = method.getParameters();
        if (parameters.length != params.length) {
            return false;
        }

        for (int i = 0; i < params.length; i++) {
            if (parameters[i].getType() != params[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isValue() {
        return type.isNot(void.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isNegatable() {
        return type.is(boolean.class) || type.is(Boolean.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        // if (!method.isSynthetic()) {
        if (method.equals(StringBuilderToString)) {
            Iterator<Code> concat = detectStringConcantenation(owner, new LinkedList());

            if (concat != null) {
                coder.writeStringConcatenation(concat);
                return;
            }
        } else if (method.equals(JavaMethodDecompiler.MakeConcatWithConstants)) {
            coder.writeStringConcatenation(params.iterator());
            return;
        } else if (GeneratedCodes.isEnumSwitchMethod(method)) {
            System.out.println(method + "   ");
        }
        coder.writeMethodCall(method, owner, params, mode);
        // } else {
        // Code code = Reincarnation.exhume(method.getDeclaringClass()).methods.get(method);
        // code.write(new SyntheticMethodInliner(coder));
        // }
    }

    /**
     * Detect the pattern of string concantenation.
     * 
     * @param operand The current operand.
     * @param values A list of concantenatable values.
     * @return
     */
    private Iterator<Code> detectStringConcantenation(Operand operand, LinkedList<Code> values) {
        if (operand instanceof OperandMethodCall method) {
            if (isBuilder(method.method)) {
                values.addAll(method.params);
                return detectStringConcantenation(method.owner, values);
            } else {
                return null;
            }
        } else if (operand instanceof OperandConstructorCall constructor) {
            constructor.children().to(param -> {
                if (param instanceof OperandMethodCall method && isFactory(method.method)) {
                    values.addAll(method.params);
                } else {
                    values.add(param);
                }
            });
            return values.descendingIterator();
        } else {
            return null;
        }
    }

    /**
     * Check whether the specified method is builder or not.
     * 
     * @param method
     * @return
     */
    private boolean isBuilder(Method method) {
        Class<?> clazz = method.getDeclaringClass();
        return (clazz == StringBuilder.class || clazz == StringBuffer.class) && method.getName().equals("append");
    }

    /**
     * Check whether the specified method is factory or not.
     * 
     * @param method
     * @return
     */
    private boolean isFactory(Method method) {
        return method.getDeclaringClass() == String.class && method.getName().equals("valueOf");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(params).startWith(owner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return owner.view() + "." + method.getName() + params.stream().map(Operand::view).collect(Collectors.joining(",", "(", ")"));
    }

    /**
     * @version 2018/10/23 14:03:05
     */
    private class SyntheticMethodInliner extends DelegatableCoder<CodingOption> {

        /**
         * Delegate {@link Coder}
         */
        private SyntheticMethodInliner(Coder coder) {
            super(coder);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeReturn(Variable<Code> code) {
            code.to(c -> c.write(this));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeAccessField(Field field, Code context, AccessMode mode) {
            coder.writeAccessField(field, params.get(0), mode);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeStatement(Code code) {
            code.write(this);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void writeMethodCall(Method method, Code context, List<Code> parameters, AccessMode mode) {
            LinkedList<Code> copy = new LinkedList(params);
            Code delegate = context;

            if (!Modifier.isStatic(method.getModifiers())) {
                delegate = copy.removeFirst();
            }
            coder.writeMethodCall(method, delegate, copy, mode);
        }
    }
}