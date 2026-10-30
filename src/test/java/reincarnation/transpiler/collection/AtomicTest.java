/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.transpiler.collection;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the atomic classes.
 */
class AtomicTest extends JavascriptCodeVerifier {

    @Test
    void atomicBoolean() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                AtomicBoolean value = new AtomicBoolean();
                boolean first = value.compareAndSet(false, true);
                boolean second = value.getAndSet(false);
                boolean third = value.compareAndSet(true, false);
                return (first ? "true" : "false") + "|" + (second ? "true" : "false") + "|" + (third ? "true" : "false") + "|" + value.get() + "|" + value;
            }
        });
    }

    @Test
    void atomicInteger() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                AtomicInteger value = new AtomicInteger(10);
                int increment = value.incrementAndGet();
                int add = value.addAndGet(5);
                int update = value.updateAndGet(x -> x * 2);
                int accumulate = value.accumulateAndGet(3, (left, right) -> left + right);
                int getAnd = value.getAndAdd(1);
                return increment + "|" + add + "|" + update + "|" + accumulate + "|" + getAnd + "|" + value.get() + "|" + value.longValue();
            }
        });
    }

    @Test
    void atomicLong() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                AtomicLong value = new AtomicLong(10L);
                long increment = value.incrementAndGet();
                long add = value.addAndGet(5L);
                long update = value.updateAndGet(x -> x * 2L);
                long accumulate = value.accumulateAndGet(3L, (left, right) -> left + right);
                long getAnd = value.getAndAdd(1L);
                return increment + "|" + add + "|" + update + "|" + accumulate + "|" + getAnd + "|" + value.get() + "|" + value.longValue();
            }
        });
    }

    @Test
    void atomicReference() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                String initial = "a";
                AtomicReference<String> value = new AtomicReference<>(initial);
                boolean first = value.compareAndSet(initial, "b");
                String update = value.updateAndGet(x -> x + "c");
                String accumulate = value.accumulateAndGet("d", (left, right) -> left + right);
                String getAnd = value.getAndSet("e");
                return (first ? "true" : "false") + "|" + update + "|" + accumulate + "|" + getAnd + "|" + value.get();
            }
        });
    }

    @Test
    void atomicReferenceArray() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                AtomicReferenceArray<Integer> array = new AtomicReferenceArray<>(3);
                array.set(0, 1);
                array.set(1, 2);
                array.set(2, 1);
                boolean compare = array.compareAndSet(0, 1, 3);
                Integer update = array.updateAndGet(1, x -> x + 10);
                Integer accumulate = array.accumulateAndGet(2, 4, (left, right) -> left + right);
                Integer getAnd = array.getAndSet(0, 5);
                return (compare ? "true" : "false") + "|" + update + "|" + accumulate + "|" + getAnd + "|" + array.get(0) + "|" + array.length() + "|" + array;
            }
        });
    }
}
