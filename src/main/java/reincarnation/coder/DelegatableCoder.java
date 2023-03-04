/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import kiss.Variable;
import kiss.Ⅲ;
import reincarnation.Reincarnation;
import reincarnation.operator.AccessMode;
import reincarnation.operator.AssignOperator;
import reincarnation.operator.BinaryOperator;
import reincarnation.operator.UnaryOperator;

public abstract class DelegatableCoder<O extends CodingOption> extends Coder<O> {

    /** The delegator. */
    protected final Coder<O> coder;

    /**
     * Default coder.
     * 
     * @param coder A delegator.
     */
    protected DelegatableCoder(Coder<O> coder) {
        super(coder);
        this.coder = Objects.requireNonNull(coder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Reincarnation reincarnation) {
        coder.write(reincarnation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writePackage(Package info) {
        coder.writePackage(info);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeType(Class type, Runnable inner) {
        coder.writeType(type, inner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeField(Field field) {
        coder.writeField(field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStaticField(Field field) {
        coder.writeStaticField(field);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInitializer(Code code) {
        coder.writeInitializer(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStaticInitializer(Code code) {
        coder.writeStaticInitializer(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeConstructor(Constructor constructor, Code code) {
        coder.writeConstructor(constructor, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethod(Method method, Code code) {
        coder.writeMethod(method, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLambda(Method method, Code code) {
        coder.writeLambda(method, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStatement(Code code) {
        coder.writeStatement(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeReturn(Variable<Code> code) {
        coder.writeReturn(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeBoolean(boolean code) {
        coder.writeBoolean(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeChar(char code) {
        coder.writeChar(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInt(int code) {
        coder.writeInt(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLong(long code) {
        coder.writeLong(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeFloat(float code) {
        coder.writeFloat(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeDouble(double code) {
        coder.writeDouble(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeString(String code) {
        coder.writeString(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeStringConcatenation(Iterator<Code> code) {
        coder.writeStringConcatenation(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeClass(Class code) {
        coder.writeClass(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThis() {
        coder.writeThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeNull() {
        coder.writeNull();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeEnclose(Runnable code) {
        coder.writeEnclose(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAssignOperation(Code left, AssignOperator operator, Code right) {
        coder.writeAssignOperation(left, operator, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeBinaryOperation(Code left, BinaryOperator operator, Code right) {
        coder.writeBinaryOperation(left, operator, right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writePositiveOperation(Code code) {
        coder.writePositiveOperation(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeNegativeOperation(Code code) {
        coder.writeNegativeOperation(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeUnaryOperation(Code code, UnaryOperator operator) {
        coder.writeUnaryOperation(code, operator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInstanceof(Code code, Class type, Code cast) {
        coder.writeInstanceof(code, type, cast);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLocalVariable(Type type, String name) {
        coder.writeLocalVariable(type, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessField(Field field, Code context, AccessMode mode) {
        coder.writeAccessField(field, context, mode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessType(Class type) {
        coder.writeAccessType(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessArray(Code array, Code index) {
        coder.writeAccessArray(array, index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccessArrayLength(Code array) {
        coder.writeAccessArrayLength(array);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeConstructorCall(Constructor constructor, List<? extends Code> params) {
        coder.writeConstructorCall(constructor, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeSuperConstructorCall(Constructor constructor, List<? extends Code> params) {
        coder.writeSuperConstructorCall(constructor, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThisConstructorCall(Constructor constructor, List<? extends Code> params) {
        coder.writeThisConstructorCall(constructor, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeMethodCall(Method method, Code context, List<? extends Code> params, AccessMode mode) {
        coder.writeMethodCall(method, context, params, mode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCreateArray(Class type, List<Code> dimensions) {
        coder.writeCreateArray(type, dimensions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCreateArray(Class type, List<Code> dimensions, List<Code> initialValues) {
        coder.writeCreateArray(type, dimensions, initialValues);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTernary(Code condition, Code then, Code elze) {
        coder.writeTernary(condition, then, elze);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeCast(Class type, Code code) {
        coder.writeCast(type, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLineComment(Object comment) {
        coder.writeLineComment(comment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAssert(Code code, Variable<Code> message) {
        coder.writeAssert(code, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeThrow(Code code) {
        coder.writeThrow(code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeBreak(Optional<String> label) {
        coder.writeBreak(label);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeContinue(Optional<String> label, boolean omitLabel) {
        coder.writeContinue(label, omitLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeIf(Code condition, Code then, Code elze, Code follow) {
        coder.writeIf(condition, then, elze, follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeFor(Optional<String> label, Code initialize, Code condition, Code updater, Runnable inner, Code follow) {
        coder.writeFor(label, initialize, condition, updater, inner, follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeIterableFor(Optional<String> label, Code variable, Code iterable, Runnable inner, Code follow) {
        coder.writeIterableFor(label, variable, iterable, inner, follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeWhile(Optional<String> label, Code condition, Runnable inner, Code follow) {
        coder.writeWhile(label, condition, inner, follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeDoWhile(Optional<String> label, Code condition, Runnable inner, Code follow) {
        coder.writeDoWhile(label, condition, inner, follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInfinitLoop(Optional<String> label, Runnable inner, Code follow) {
        coder.writeInfinitLoop(label, inner, follow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeTryCatchFinally(Code tryBlock, List<Ⅲ<Class, String, Code>> catchBlocks, Code follow) {
        coder.writeTryCatchFinally(tryBlock, catchBlocks, follow);
    }
}