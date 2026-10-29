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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the {@link Queue} and {@link Deque} API.
 */
class QueueTest extends JavascriptCodeVerifier {

    @Test
    void queue() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Queue<Integer> queue = new ArrayDeque<>();
                queue.add(1);
                queue.offer(2);
                return queue.poll() * 10 + queue.peek();
            }
        });
    }

    @Test
    void deque() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Deque<Integer> deque = new ArrayDeque<>();
                deque.addFirst(1);
                deque.addLast(2);
                deque.offerFirst(3);
                deque.offerLast(4);
                return deque.pollFirst() * 1000 + deque.pollLast() * 100 + deque.pollFirst() * 10 + deque.pollLast();
            }
        });
    }

    @Test
    void stack() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Deque<Integer> deque = new ArrayDeque<>();
                deque.push(1);
                deque.push(2);
                deque.push(3);
                return deque.pop() * 10 + deque.pop();
            }
        });
    }

    @Test
    void iterate() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Queue<Integer> queue = new ArrayDeque<>();
                queue.add(1);
                queue.add(2);
                queue.add(3);

                int sum = 0;
                for (int value : queue) {
                    sum += value;
                }
                return sum;
            }
        });
    }

    @Test
    void priority() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Queue<Integer> queue = new PriorityQueue<>();
                queue.add(3);
                queue.add(1);
                queue.add(2);

                int value = 0;
                while (!queue.isEmpty()) {
                    value = value * 10 + queue.poll();
                }
                return value;
            }
        });
    }

    @Test
    void sizeAndContains() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Queue<Integer> queue = new ArrayDeque<>();
                queue.add(1);
                queue.add(2);
                return queue.size() + (queue.contains(2) ? 10 : 0) + (queue.contains(3) ? 100 : 0);
            }
        });
    }
}
