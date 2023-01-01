/*
 * Copyright (C) 2023 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import static org.objectweb.asm.Opcodes.ASM7;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

import org.objectweb.asm.AnnotationVisitor;

import kiss.I;
import reincarnation.Node.TryCatchFinally;

/**
 * @version 2018/10/31 11:34:57
 */
public class Debugger extends AnnotationVisitor {

    /** The processing environment. */
    static boolean whileDebug = false;

    /** The processing environment. */
    private static final boolean whileTest;

    /** The list for debug patterns. */
    private static final List<Pattern[]> patterns = new ArrayList();

    /** The current debugger. */
    private static Debugger debugger = new Debugger();

    // initialization
    static {
        // enable(".+\\$OffsetIdPrinterParser", "parse");

        boolean flag = false;

        for (StackTraceElement element : new Error().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                flag = true;
                break;
            }
        }
        whileTest = flag;
    }

    /** The use flag. */
    private boolean enable = false;

    /** The use flag. */
    private boolean firstTime = true;

    /** Can i use getDominator safely? */
    private boolean dominatorSafe = false;

    /**
     * 
     */
    private Debugger() {
        super(ASM7);

        // update
        debugger = this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(String methodName, Object type) {
        if (!patterns.isEmpty()) {
            Class clazz = (Class) type;

            Iterator<Pattern[]> iterator = patterns.iterator();

            while (iterator.hasNext()) {
                Pattern[] patterns = iterator.next();

                if (patterns[0].matcher(clazz.getName()).matches() && patterns[1].matcher(methodName).matches()) {
                    enable = true;
                    return;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitEnd() {
        enable = true;
    }

    /**
     * Register current processing target.
     */
    public static void enable() {
        debugger.enable = true;
    }

    /**
     * Register debug target.
     * 
     * @param className A class name regex.
     * @param methodName A method name regex.
     */
    public static void enable(String className, String methodName) {
        patterns.add(new Pattern[] {Pattern.compile(className), Pattern.compile(methodName)});
    }

    /**
     * @return
     */
    public static boolean isEnable() {
        if (debugger.enable && debugger.firstTime) {
            debugger.firstTime = false;
            printInfo(false);
        }
        return debugger.enable;
    }

    /**
     * <p>
     * Print debug message.
     * </p>
     * 
     * @param values
     */
    public static void info(Object... values) {
        StringBuilder text = new StringBuilder();
        CopyOnWriteArrayList<Node> nodes = new CopyOnWriteArrayList();

        for (Object value : values) {
            if (value instanceof Node) {
                Node node = (Node) value;
                nodes.addIfAbsent(node);
                text.append("n").append(node.id);
            } else if (value instanceof List) {
                List<Node> list = (List<Node>) value;

                for (Node node : list) {
                    nodes.addIfAbsent(node);
                }
            } else if (value instanceof Set) {
                Set<Node> list = (Set<Node>) value;

                for (Node node : list) {
                    nodes.addIfAbsent(node);
                }
            } else {
                text.append(value);
            }
        }
        text.append("   ").append(linkableMethodInfo(false));

        System.out.println(text);
        System.out.println(format(nodes));
    }

    /**
     * <p>
     * Print debug message.
     * </p>
     * 
     * @param values
     */
    public static void print(Object... values) {
        if (isEnable()) {
            info(values);
        }
    }

    /**
     * Print method info as header like.
     */
    private static void printInfo(boolean safe) {
        debugger.dominatorSafe = safe;

        if (isEnable()) {
            String text = linkableMethodInfo(true);
            int base = (120 - text.length()) / 2;

            System.out.println("//" + "-".repeat(base) + " " + linkableMethodInfo(true) + " " + "-".repeat(base) + "//");
        }
    }

    /**
     * Show linkable method info in console.
     * 
     * @return
     */
    private static String linkableMethodInfo(boolean head) {
        String methodName;

        if (whileTest) {
            String testClassName = computeTestClassName(getScript());
            String testMethodName = computeTestMethodName(testClassName);

            methodName = testMethodName == null ? getMethodName() : testMethodName;
        } else {
            methodName = getMethodName();
        }

        if (!methodName.startsWith("<")) {
            methodName = "#".concat(methodName);
        }

        Class clazz = getScript();

        if (clazz.isAnonymousClass()) {
            clazz = clazz.getEnclosingClass();
        }
        return clazz.getSimpleName() + methodName + " (" + clazz.getSimpleName() + ".java:" + (head ? getMethodLine() : getLine()) + ")";
    }

    /**
     * Dump all node tree.
     */
    public static void print(LinkedList<Node> nodes, Object... messages) {
        print(messages);
        print(nodes.peekFirst().signal().toList());
    }

    /**
     * @param message
     */
    public static void print(Object message) {
        if (isEnable()) {
            System.out.println(message);
        }
    }

    /**
     * <p>
     * Dump node tree.
     * </p>
     * 
     * @param node
     */
    public static void print(Node node) {
        if (node != null) {
            print(Collections.singletonList(node));
        }
    }

    /**
     * <p>
     * Dump node tree.
     * </p>
     * 
     * @param nodes
     */
    public static void print(List<Node> nodes) {
        if (isEnable()) {
            System.out.println(format(nodes));
        }
    }

    /**
     * <p>
     * Compute actual test class name.
     * </p>
     * 
     * @param clazz
     * @return
     */
    private static String computeTestClassName(Class clazz) {
        String name = clazz.getName();

        int index = name.indexOf('$');

        if (index != -1) {
            name = name.substring(0, index);
        }
        return name;
    }

    /**
     * <p>
     * Compute actual test method name.
     * </p>
     * 
     * @param testClassName
     * @return
     */
    private static String computeTestMethodName(String testClassName) {
        for (StackTraceElement element : new Error().getStackTrace()) {
            if (element.getClassName().equals(testClassName)) {
                return element.getMethodName();
            }
        }
        return null;
    }

    /**
     * <p>
     * Helper method to format node tree.
     * </p>
     * 
     * @param nodes
     * @return
     */
    private static String format(List<Node> nodes) {
        Set<TryCatchFinally> tries = new LinkedHashSet();

        for (Node node : nodes) {
            if (node.tries != null) {
                tries.addAll(node.tries);
            }
        }

        // compute max id length
        int max = 1;

        for (Node node : nodes) {
            max = Math.max(max, String.valueOf(node.id).length());
        }

        int incoming = 0;
        int outgoing = 0;
        int backedge = 0;

        for (Node node : nodes) {
            incoming = Math.max(incoming, node.incoming.size() * max + (node.incoming.size() - 1) * 2);
            outgoing = Math.max(outgoing, node.outgoing.size() * max + (node.outgoing.size() - 1) * 2);
            backedge = Math.max(backedge, node.backedges.size() * max + (node.backedges.size() - 1) * 2);
        }

        Formatter format = new Formatter();

        for (Node node : nodes) {
            StringBuilder tryFlow = new StringBuilder();

            for (TryCatchFinally block : tries) {
                if (block.start == node) {
                    tryFlow.append("s");
                } else if (block.end == node) {
                    tryFlow.append("e");
                } else if (block.catcher == node) {
                    tryFlow.append("c");
                } else if (block.exit == node) {
                    tryFlow.append("x");
                } else {
                    tryFlow.append("  ");
                }
            }

            format.write(String.valueOf(node.id), max);
            format.write("  in : ");
            format.formatNode(node.incoming, incoming);
            format.write("out : ");
            format.formatNode(node.outgoing, outgoing);
            format.write("dom : ");
            format.formatNode(list(getDominator(node)), 1);
            format.write("doms : ");
            format.formatNode(node.dominators, node.dominators.size());
            format.write("prev : ");
            format.formatNode(list(node.previous), 1);
            format.write("next : ");
            format.formatNode(list(node.next), 1);
            format.write("dest : ");
            format.formatNode(list(node.destination), 1);

            if (backedge != 0) {
                format.write("back : ");
                format.formatNode(node.backedges, backedge);
            }
            if (!tries.isEmpty()) {
                format.write("try : ");
                format.write(tryFlow.toString(), tries.size() * 2 + 2);
            }
            format.write("code : ");
            format.formatCodeFragment(node.stack);
            format.write("\r\n");
        }
        return format.toString();
    }

    /**
     * <p>
     * Create single item list.
     * </p>
     * 
     * @param node
     * @return
     */
    private static List<Node> list(Node node) {
        if (node == null) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(node);
    }

    /**
     * <p>
     * Helper method to compute dominator node.
     * </p>
     * 
     * @param target
     * @return
     */
    private static Node getDominator(Node target) {
        if (debugger.dominatorSafe) {
            return target.getDominator();
        } else {
            return getDominator(target, new HashSet());
        }
    }

    /**
     * Compute the immediate dominator of this node.
     * 
     * @return A dominator node. If this node is root, <code>null</code>.
     */
    private static Node getDominator(Node target, Set<Node> nodes) {
        if (!nodes.add(target)) {
            return null;
        }

        // check cache
        // We must search a immediate dominator.
        //
        // At first, we can ignore the older incoming nodes.
        List<Node> candidates = new CopyOnWriteArrayList(target.incoming);

        // compute backedges
        for (Node node : candidates) {
            if (target.backedges.contains(node)) {
                candidates.remove(node);
            }
        }

        int size = candidates.size();

        switch (size) {
        case 0: // this is root node
            return null;

        case 1: // only one incoming node
            return candidates.get(0);

        default: // multiple incoming nodes
            Node candidate = candidates.get(0);

            search: while (candidate != null) {
                for (int i = 1; i < size; i++) {
                    if (!hasDominator(candidates.get(i), candidate, nodes)) {
                        candidate = getDominator(candidate, nodes);
                        continue search;
                    }
                }
                return candidate;
            }
            return null;
        }
    }

    /**
     * Helper method to check whether the specified node dominate this node or not.
     * 
     * @param dominator A dominator node.
     * @return A result.
     */
    private static boolean hasDominator(Node target, Node dominator, Set<Node> nodes) {
        Node current = target;

        while (current != null) {
            if (current == dominator) {
                return true;
            }
            current = getDominator(current, nodes);
        }

        // Not Found
        return false;
    }

    /**
     * @version 2018/10/31 11:35:02
     */
    private static final class Formatter {

        /** The actual buffer. */
        private final StringBuilder builder = new StringBuilder();

        /** The tab length. */
        private final int tab = 8;

        /**
         * Helper method to write message.
         * 
         * @param message
         */
        private void write(String message) {
            builder.append(message);
        }

        /**
         * Helper method to write message.
         * 
         * @param message
         */
        private void write(String message, int max) {
            builder.append(message);

            // calcurate required tab
            int requireTab = 1;

            while (requireTab * tab <= max) {
                requireTab++;
            }

            // calcurate remaining spaces
            int remaining = requireTab * tab - message.length();
            int actualTab = 0;

            while (0 < remaining - tab * actualTab) {
                actualTab++;
            }

            for (int i = 0; i < actualTab; i++) {
                builder.append('\t');
            }
        }

        /**
         * Helper method to write message.
         * 
         * @param message
         */
        private void formatNode(List<Node> nodes, int max) {
            StringBuilder builder = new StringBuilder("[");

            for (int i = 0; i < nodes.size(); i++) {
                builder.append(String.valueOf(nodes.get(i).id));

                if (i != nodes.size() - 1) {
                    builder.append(",");
                }
            }
            builder.append("]");

            write(builder.toString(), max + 2);
        }

        /**
         * Helper method to format code fragment.
         * 
         * @param operands
         */
        private void formatCodeFragment(List<Operand> operands) {
            whileDebug = true;
            for (int i = 0; i < operands.size(); i++) {
                Operand operand = operands.get(i);

                builder.append(operand.toString().strip()).append(" [").append(operand.info()).append("]");

                if (i != operands.size() - 1) {
                    builder.append(" ");
                }
            }
            whileDebug = false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return builder.toString();
        }
    }

    /** The compiling route. */
    private static Deque<DebugContext> route = new ArrayDeque();

    /**
     * <p>
     * Record starting class compiling.
     * </p>
     * 
     * @param clazz A target class.
     */
    static void start(Class clazz) {
        DebugContext context = route.peekFirst();

        if (context == null || context.clazz != clazz) {
            route.addFirst(new DebugContext(clazz));
        }
    }

    /**
     * <p>
     * Record finishing class compiling.
     * </p>
     * 
     * @param clazz A target class.
     */
    static void finish(Class clazz) {
        route.pollFirst();
    }

    /**
     * <p>
     * Record starting method compiling.
     * </p>
     * 
     * @param method A target method name.
     */
    static void recordMethodName(String method) {
        DebugContext context = route.peekFirst();
        context.method = method;
        context.line = 1;
        I.make(Debugger.class).visit(method, context.clazz);
    }

    /**
     * <p>
     * Record line number.
     * </p>
     * 
     * @param line
     */
    static void recordMethodLineNumber(int line) {
        DebugContext context = route.peekFirst();

        if (context.line == 1) {
            context.line = line - 1;
        }
        context.lineNow = line;
    }

    /**
     * <p>
     * Retrieve the current compiling class info.
     * </p>
     * 
     * @return The current compiling class.
     */
    static Class getScript() {
        return route.peekFirst().clazz;
    }

    /**
     * <p>
     * Retrieve the current compiling class info.
     * </p>
     * 
     * @return The current compiling class.
     */
    static String getMethodName() {
        return route.peekFirst().method;
    }

    /**
     * <p>
     * Retrieve the current compiling class info.
     * </p>
     * 
     * @return The current compiling class.
     */
    static int getMethodLine() {
        return route.peekFirst().line;
    }

    /**
     * <p>
     * Retrieve the current compiling class info.
     * </p>
     * 
     * @return The current compiling class.
     */
    static int getLine() {
        return route.peekFirst().lineNow;
    }

    /**
     * @version 2018/10/31 11:35:08
     */
    private static class DebugContext {

        /** The current compiling class. */
        private final Class clazz;

        /** The current compiling method. */
        private String method;

        /** The current compiling method position. */
        private int line = 1;

        /** The current compiling line position. */
        private int lineNow = 1;

        /**
         * @param clazz An associated clazz.
         */
        private DebugContext(Class clazz) {
            this.clazz = clazz;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder(" at ").append(clazz.getName())
                    .append('.')
                    .append(method)
                    .append('(')
                    .append(clazz.getSimpleName())
                    .append(".java:")
                    .append(lineNow)
                    .append(')');

            return builder.toString();
        }
    }
}