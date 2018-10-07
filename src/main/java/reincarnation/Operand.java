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

import com.github.javaparser.ast.expr.Expression;

import kiss.I;
import kiss.Signal;

/**
 * @version 2018/10/05 19:36:25
 */
abstract class Operand {

    /** The reusable boolean. */
    public static final OperandBoolean True = new OperandBoolean(true);

    /** The reusable boolean. */
    public static final OperandBoolean False = new OperandBoolean(false);

    protected Class type;

    /** The flag for operand duplication. */
    boolean duplicated = false;

    /**
     * Process if this {@link Operand} is instance of the specified type.
     * 
     * @param type
     * @return
     */
    public final <T extends Operand> Signal<T> as(Class<T> type) {
        return I.signal(this).as(type);
    }

    /**
     * @param type
     * @return
     */
    final Operand cast(Class type) {
        Operand operand = castActual(type);
        operand.duplicated = duplicated;

        return operand;
    }

    /**
     * @param type
     * @return
     */
    final Operand cast(InferredType type) {
        return cast(type.type());
    }

    /**
     * <p>
     * Internal API.
     * </p>
     * 
     * @param type
     * @return
     */
    Operand castActual(Class type) {
        return this;
    }

    /**
     * <p>
     * Invert operand value if we can.
     * </p>
     * 
     * @return A inverted operand.
     */
    Operand invert() {
        return this;
    }

    int computeMultiplicity() {
        return 1;
    }

    /**
     * <p>
     * Enclose this operand.
     * </p>
     * 
     * @return A disclosed operand.
     */
    Operand encolose() {
        return new OperandEnclose(this);
    }

    /**
     * <p>
     * Disclose the outmost parenthesis if we can.
     * </p>
     * 
     * @return A disclosed operand.
     */
    Operand disclose() {
        return this;
    }

    /**
     * <p>
     * Infer the type of this {@link Operand}.
     * </p>
     * 
     * @return
     */
    InferredType infer() {
        return new InferredType(type);
    }

    /**
     * @return
     */
    boolean isLarge() {
        return infer().type() == long.class || infer().type() == double.class;
    }

    Expression build() {
        // If this exception will be thrown, it is bug of this program. So we must rethrow the
        // wrapped error in here.
        throw new Error();
    }

    /**
     * Find all typed {@link Operand}s.
     * 
     * @param type
     * @return
     */
    public final <T extends Operand> Signal<T> find(Class<T> type) {
        if (type == getClass()) {
            return I.signal((T) this);
        } else {
            return children().flatMap(o -> o.find(type));
        }
    }

    /**
     * Collect all child {@link Operand}s.
     * 
     * @return
     */
    protected Signal<Operand> children() {
        // If this exception will be thrown, it is bug of this program. So we must rethrow the
        // wrapped error in here.
        throw new Error("Implemetn #children at " + getClass().getName());
    }
}
