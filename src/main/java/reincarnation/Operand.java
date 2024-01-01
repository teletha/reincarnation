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

import java.lang.reflect.Type;
import java.util.Optional;

import kiss.I;
import kiss.Signal;
import kiss.Variable;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.coder.java.JavaCoder;

public abstract class Operand implements Code<Operand> {

    /** The reusable operand for null literal. */
    public static final Operand Null = new OperandExpression(null).fix(Object.class);

    /** The infered type. */
    protected Variable<Type> type = Variable.of(Object.class);

    /** The flag for operand duplication. */
    protected boolean duplicated = false;

    /** The comment. */
    private String comment;

    /** The mark. */
    private boolean statement;

    /** The state. */
    private boolean enclosed;

    /**
     * Display the human-readable operand info.
     * 
     * @return
     */
    protected String info() {
        return getClass().getSimpleName().substring("Operand".length()) + type
                .map(v -> v == Object.class ? "" : v instanceof Class c ? "#" + c.getSimpleName() : "#" + v.getTypeName());
    }

    /**
     * Fix as the current type.
     * 
     * @return Chainable API.
     */
    protected final Operand fix() {
        return fix(type.v);
    }

    /**
     * Fix as the specified type.
     * 
     * @param type A type to fix.
     * @return Chainable API.
     */
    protected final Operand fix(Type type) {
        this.type.set(type);
        this.type.fix();

        return this;
    }

    /**
     * Check type inference state.
     * 
     * @return
     */
    protected final boolean isFixed() {
        return this.type.isFixed();
    }

    /**
     * Bind infered type.
     * 
     * @param other
     */
    protected final Operand bindTo(Operand other) {
        if (other != null && other != this) {
            if (isFixed()) {
                if (other.isFixed()) {
                    // do nothing
                } else {
                    other.fix(type.v);
                }
            } else {
                if (other.isFixed()) {
                    fix(other.type.v);
                } else {
                    type.observing().skip(Object.class).to(other.type::set);
                }
            }
        }
        return this;
    }

    protected boolean isTrue() {
        return false;
    }

    protected boolean isFalse() {
        return false;
    }

    /**
     * Check whether this operand is negatable or not.
     * 
     * @return
     */
    protected boolean isNegatable() {
        return false;
    }

    /**
     * Check whether this operand means any value or not.
     * 
     * @return
     */
    protected boolean isValue() {
        return enclosed;
    }

    protected final boolean isEnclosed() {
        return enclosed;
    }

    /**
     * Get the associated class type.
     * 
     * @return
     */
    public final Class type() {
        return type.v instanceof Class clazz ? clazz : Object.class;
    }

    /**
     * Enclose myself.
     * 
     * @return Chainable API.
     */
    public final Operand encolose() {
        enclosed = true;
        return this;
    }

    /**
     * Disclose myself.
     * 
     * @return Chainable API.
     */
    public final Operand disclose() {
        enclosed = false;
        return this;
    }

    /**
     * Check {@link Operand} type.
     * 
     * @return A result.
     */
    public boolean isStatement() {
        return statement;
    }

    /**
     * Check {@link Operand} type.
     * 
     * @return A result.
     */
    public final boolean isExpression() {
        return !isStatement();
    }

    /**
     * Mark this {@link Operand} as statement.
     */
    public final void markAsStatement() {
        statement = true;
    }

    /**
     * Mark this {@link Operand} as expression.
     */
    public final void markAsExpression() {
        statement = false;
    }

    public <T extends Operand> Variable<T> as(Class<T> type) {
        return I.signal(this).as(type).to();
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
     * @return
     */
    boolean isLarge() {
        return type.is(long.class) || type.is(double.class);
    }

    /**
     * Set commnet for this {@link Operand}.
     * 
     * @param comment
     * @return
     */
    Operand comment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> comment() {
        return Optional.ofNullable(comment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void write(Coder coder) {
        if (enclosed) {
            coder.writeEnclose(() -> {
                writeCode(coder);
            });
        } else {
            writeCode(coder);
        }
    }

    /**
     * Write code actually.
     * 
     * @param coder
     */
    protected abstract void writeCode(Coder coder);

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        JavaCoder coder = new JavaCoder();
        write(coder);
        return coder.toString();
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
            return children().skipNull().flatMap(o -> o.find(type));
        }
    }
}