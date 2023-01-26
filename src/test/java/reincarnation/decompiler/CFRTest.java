/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.decompiler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.benf.cfr.reader.Main;
import org.junit.jupiter.api.Test;

import kiss.I;
import kiss.Signal;
import kiss.Signaling;
import kiss.WiseBiFunction;

public class CFRTest {

    @Test
    void testName() {
        // Options option = new OptionsImpl(new HashMap());
        // ClassFileSource2 source = new ClassFileSourceImpl(option);
        //
        // DCCommonState state = new DCCommonState(option, source);

        Main.main(new String[] {"kiss.Variable"});

        Variable<String> a = Variable.of("10");
        System.out.println(a);
        a.set("change");
        System.out.println(a);
        a.fix();
        a.set("NOOO");
        System.out.println(a);
    }

    public static class Variable<V> implements Consumer<V>, Supplier<V> {
        private static final MethodHandle set;

        public final transient V v;

        private boolean fix;

        Signaling<V> signaling;

        private volatile WiseBiFunction<V, V, V> interceptor;

        protected Variable(V v) {
            this.v = v;
        }

        @Override
        public void accept(V v) {
            this.set(v);
        }

        public final V acquire() {
            return this.or(this::next);
        }

        public final V exact() {
            return this.or(() -> {
                throw new NullPointerException();
            });
        }

        @Override
        public final V get() {
            return this.v;
        }

        public final Variable<V> intercept(WiseBiFunction<V, V, V> wiseBiFunction) {
            this.interceptor = wiseBiFunction;
            return this;
        }

        public final boolean is(V v) {
            return Objects.equals(this.v, v);
        }

        public final boolean is(Predicate<V> predicate) {
            return predicate == null ? false : predicate.test(this.v);
        }

        public final boolean isNot(V v) {
            return !this.is(v);
        }

        public final boolean isNot(Predicate<V> predicate) {
            return !this.is(predicate);
        }

        public final boolean isAbsent() {
            return this.is(Objects::isNull);
        }

        public final boolean isPresent() {
            return this.is(Objects::nonNull);
        }

        public final boolean isFixed() {
            return this.fix;
        }

        public final Variable<V> fix() {
            this.fix = true;
            return this;
        }

        public final <R> Variable<R> flatMap(Function<V, Variable<R>> function) {
            return this.v == null || function == null ? new Variable(null) : function.apply(this.v);
        }

        public final <R> Variable<R> map(Function<? super V, ? extends R> function) {
            if (this.v != null && function != null) {
                try {
                    return Variable.of(function.apply(this.v));
                } catch (Throwable throwable) {
                    // empty catch block
                }
            }
            return Variable.empty();
        }

        public final V next() {
            try {
                CompletableFuture completableFuture = new CompletableFuture();
                this.observe().first().to(completableFuture::complete);
                return (V) completableFuture.get();
            } catch (Exception exception) {
                throw I.quiet(exception);
            }
        }

        public final synchronized Signal<V> observe() {
            if (this.signaling == null) {
                this.signaling = new Signaling();
            }
            return this.signaling.expose;
        }

        public final Signal<V> observing() {
            return this.observe().startWith(this::get);
        }

        public final V or(V v) {
            return this.v != null ? this.v : v;
        }

        public final V or(Supplier<V> supplier) {
            return this.v != null ? this.v : (supplier == null ? null : supplier.get());
        }

        public final synchronized V set(V v) {
            V v2 = this.v;
            if (!this.fix) {
                try {
                    if (this.interceptor != null) {
                        v = this.interceptor.apply(this.v, v);
                    }
                    set.invoke(this, v);
                    if (this.signaling != null) {
                        this.signaling.accept(this.v);
                    }
                } catch (RuntimeException runtimeException) {
                } catch (Throwable throwable) {
                    throw I.quiet(throwable);
                }
            }
            return v2;
        }

        public final V set(Supplier<V> supplier) {
            return this.set(supplier == null ? null : (V) supplier.get());
        }

        public final V set(UnaryOperator<V> unaryOperator) {
            return this.set(unaryOperator == null ? null : (V) unaryOperator.apply(this.v));
        }

        public final void to(Consumer<V> consumer) {
            this.to(consumer, null);
        }

        public final void to(Consumer<V> consumer, Runnable runnable) {
            if (this.v != null) {
                if (consumer != null) {
                    consumer.accept(this.v);
                }
            } else if (runnable != null) {
                runnable.run();
            }
        }

        @Override
        public final int hashCode() {
            return Objects.hash(this.v);
        }

        @Override
        public final boolean equals(Object object) {
            if (!(object instanceof Variable)) {
                return false;
            }
            return ((Variable) object).is(this.v);
        }

        @Override
        public String toString() {
            return String.valueOf(this.v);
        }

        public static <T> Variable<T> of(T t) {
            return new Variable<T>(t);
        }

        public static <T> Variable<T> empty() {
            return new Variable(null);
        }

        static {
            try {
                Field field = Variable.class.getField("v");
                field.setAccessible(true);
                set = MethodHandles.lookup().unreflectSetter(field);
            } catch (Exception exception) {
                throw I.quiet(exception);
            }
        }
    }

}