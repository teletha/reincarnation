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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the concurrent collections.
 */
class ConcurrentCollectionTest extends JavascriptCodeVerifier {

    @Test
    void concurrentHashMap() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Map<String, Integer> map = new ConcurrentHashMap<>();
                map.put("a", 1);
                map.put("b", 2);
                return map.size() * 10 + map.get("b");
            }
        });
    }

    @Test
    void concurrentSkipListSet() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Set<Integer> set = new ConcurrentSkipListSet<>();
                set.add(3);
                set.add(1);
                set.add(2);
                int value = 0;
                for (int element : set) {
                    value = value * 10 + element;
                }
                return value;
            }
        });
    }

    @Test
    void copyOnWriteArrayList() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<String> list = new CopyOnWriteArrayList<>();
                list.add("a");
                list.add("b");
                list.add("c");
                int sum = 0;
                for (String value : list) {
                    sum += value.length();
                }
                return list.size() * 10 + sum;
            }
        });
    }

    @Test
    void copyOnWriteArraySet() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Set<String> set = new CopyOnWriteArraySet<>();
                set.add("a");
                set.add("b");
                set.add("a");
                return set.size();
            }
        });
    }

    @Test
    void concurrentLinkedQueue() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Queue<Integer> queue = new ConcurrentLinkedQueue<>();
                queue.add(1);
                queue.offer(2);
                return queue.poll() * 10 + queue.peek();
            }
        });
    }

    @Test
    void concurrentLinkedDeque() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
                deque.addFirst(1);
                deque.addLast(2);
                deque.offerFirst(3);
                return deque.pollFirst() * 10 + deque.pollLast();
            }
        });
    }

    @Test
    void arrayBlockingQueue() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(4);
                try {
                    queue.put(1);
                    queue.put(2);
                    return queue.take() * 10 + queue.peek();
                } catch (InterruptedException e) {
                    return -1;
                }
            }
        });
    }

    @Test
    void linkedBlockingQueue() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
                queue.add(1);
                queue.add(2);
                return queue.poll() * 10 + queue.size();
            }
        });
    }

    @Test
    void priorityBlockingQueue() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                Queue<Integer> queue = new PriorityBlockingQueue<>();
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
}
