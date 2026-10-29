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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import reincarnation.TestCode;
import reincarnation.transpiler.JavascriptCodeVerifier;

/**
 * Test the JavaScript output for the {@link List} API.
 * 
 * @version 2026/10/11 12:00:00
 */
class ListTest extends JavascriptCodeVerifier {

    @Test
    void size() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                return list.size();
            }
        });
    }

    @Test
    void isEmpty() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List<String> list = new ArrayList<>();
                return list.isEmpty();
            }
        });
    }

    @Test
    void get() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(10);
                list.add(20);
                list.add(30);
                return list.get(1);
            }
        });
    }

    @Test
    void set() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(10);
                list.add(20);
                list.set(0, 99);
                return list.get(0);
            }
        });
    }

    @Test
    void addReturnsBoolean() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List<String> list = new ArrayList<>();
                return list.add("a");
            }
        });
    }

    @Test
    void addAt() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("c");
                list.add(1, "b");
                return list.toString();
            }
        });
    }

    @Test
    void contains() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                return list.contains("b");
            }
        });
    }

    @Test
    void indexOf() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                return list.indexOf("b");
            }
        });
    }

    @Test
    void removeByObject() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                return list.remove("a");
            }
        });
    }

    @Test
    void removeByIndex() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                list.add("c");
                String removed = list.remove(1);
                return removed + list;
            }
        });
    }

    @Test
    void toStringValue() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                return list.toString();
            }
        });
    }

    @Test
    void equalsValue() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List<String> left = new ArrayList<>();
                left.add("a");
                List<String> right = new ArrayList<>();
                right.add("a");
                return left.equals(right);
            }
        });
    }

    @Test
    void enhancedFor() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                int sum = 0;
                for (int value : list) {
                    sum += value;
                }
                return sum;
            }
        });
    }

    @Test
    void linkedList() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<String> list = new LinkedList<>();
                list.add("a");
                list.add("b");
                return list.toString();
            }
        });
    }

    @Test
    void factory() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<Integer> list = List.of(1, 2, 3);
                return list.toString();
            }
        });
    }

    @Test
    void addAll() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.addAll(List.of(2, 3));
                return list.size();
            }
        });
    }

    @Test
    void sort() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(3);
                list.add(1);
                list.add(2);
                list.sort(null);
                return list.get(0) * 100 + list.get(1) * 10 + list.get(2);
            }
        });
    }

    @Test
    void replaceAll() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                list.replaceAll(value -> value * 2);
                return list.get(2);
            }
        });
    }

    @Test
    void removeIf() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                list.add(4);
                list.removeIf(value -> value % 2 == 0);
                return list.size() * 10 + list.get(1);
            }
        });
    }

    @Test
    void subList() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                list.add(4);
                List<Integer> sub = list.subList(1, 3);
                return sub.size() * 10 + sub.get(0);
            }
        });
    }

    @Test
    void iterator() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                Iterator<Integer> iterator = list.iterator();
                int sum = 0;
                while (iterator.hasNext()) {
                    sum += iterator.next();
                }
                return sum;
            }
        });
    }

    @Test
    void iteratorRemove() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                list.add(4);
                Iterator<Integer> iterator = list.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() % 2 == 0) {
                        iterator.remove();
                    }
                }
                return list.toString();
            }
        });
    }

    @Test
    void addAllAt() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(4);
                list.addAll(1, List.of(2, 3));
                return list.toString();
            }
        });
    }

    @Test
    void removeAll() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                list.removeAll(List.of(2, 3));
                return list.toString();
            }
        });
    }

    @Test
    void retainAll() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                list.retainAll(List.of(2, 3));
                return list.toString();
            }
        });
    }

    @Test
    void containsAll() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                return list.containsAll(List.of(1, 3));
            }
        });
    }

    @Test
    void toArray() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                List<String> list = new ArrayList<>();
                list.add("a");
                list.add("b");
                String[] array = list.toArray(new String[0]);
                return array[1];
            }
        });
    }

    @Test
    void factoryEmptyAndSingle() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                return List.of().size() + List.of(9).get(0);
            }
        });
    }

    @Test
    void factoryMany() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = List.of(1, 2, 3, 4, 5);
                return list.size() * 10 + list.get(4);
            }
        });
    }

    @Test
    void copyOf() throws Exception {
        verify(new TestCode.Text() {

            @Override
            public String run() {
                return List.copyOf(List.of(1, 2)).toString();
            }
        });
    }

    @Test
    void instanceOfList() throws Exception {
        verify(new TestCode.Boolean() {

            @Override
            public boolean run() {
                java.lang.Object value = new ArrayList<Integer>();
                return value instanceof List;
            }
        });
    }

    @Test
    void listIterator() throws Exception {
        verify(new TestCode.Int() {

            @Override
            public int run() {
                List<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                java.util.ListIterator<Integer> iterator = list.listIterator();
                int sum = 0;
                while (iterator.hasNext()) {
                    sum += iterator.next();
                }
                while (iterator.hasPrevious()) {
                    sum += iterator.previous();
                }
                return sum;
            }
        });
    }
}
