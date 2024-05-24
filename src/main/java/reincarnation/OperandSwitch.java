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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;

import kiss.I;
import kiss.Signal;
import reincarnation.coder.Coder;
import reincarnation.structure.Breakable;
import reincarnation.structure.Structure;
import reincarnation.structure.Switch;
import reincarnation.util.GeneratedCodes;
import reincarnation.util.MultiMap;

/**
 * Operand representing a switch statement.
 * 
 * ====================================================================
 * For Javac
 *
 * A static array is added to the bytecode compiled by Javac. At runtime, this array
 * dynamically stores the ordinal numbers of the enumerated constants and the
 * mapping data for the case labels. The array is created large enough to store all
 * enumeration constants, but the mapping data is stored only for the constants used
 * for the case labels.
 *
 * The following is Java code that is roughly equivalent to byte code
 * {@snippet :
 * public class Main {
 *     // The compiler adds an array for mapping from ordinals to case labels for enumerated constants.
 *     static final int[] $SwitchMap$;
 * 
 *     static {
 *         // Instantiate an array for mapping. size is the number of all enumeration constants.
 *         $SwitchMap$ = new int[ENUM.values().length];
 * 
 *         // The data for mapping are numbered from 1 in the order indicated by the labels in the switch statement.
 *         try {
 *             $SwitchMap$[ENUM.A.ordinal()] = 1;
 *         } catch (NoSuchFieldError e) {
 *             // ignore error
 *         }
 *         try {
 *             $SwitchMap$[ENUM.B.ordinal()] = 2;
 *         } catch (NoSuchFieldError e) {
 *             // ignore error
 *         }
 *         try {
 *             $SwitchMap$[ENUM.C.ordinal()] = 3;
 *         } catch (NoSuchFieldError e) {
 *             // ignore error
 *         }
 *     }
 * 
 *     public static void main(String[] args) {
 *         switch ($SwitchMap$[ENUM.B.ordinal()]) {
 *         case 1:
 *             System.out.println("A");
 *             break;
 *         case 2:
 *             System.out.println("B");
 *             break;
 *         case 3:
 *             System.out.println("C");
 *             break;
 *         }
 *     }
 * }
 * }
 * 
 * If the enumeration constant used for the case label fails to be retrieved, the exception is
 * ignored. This allows the switch statement to work without problems even after the enumeration
 * constant in question has been deleted in the enumeration class.
 * 
 * @SeeAlso https://happynow.hateblo.jp/entry/20120505/1336227066
 * @SeeAlso http://www.ne.jp/asahi/hishidama/home/tech/java/enum.html#h2_switch
 */
class OperandSwitch extends Operand {

    /** The entrance node. */
    final Node entrance;

    /** The condition. */
    private Operand condition;

    /** The special case manager. */
    private MultiMap<Node, Object> cases = new MultiMap(true);

    /** The default case. */
    private Node defaultNode;

    /** The end node. */
    Node follower;

    /** The post processor for case blocks. */
    UnaryOperator<MultiMap<Node, Object>> caseConverter = UnaryOperator.identity();

    /** The post processor for default block. */
    UnaryOperator<Node> defaultConverter = UnaryOperator.identity();

    /**
     * Creates a new {@code OperandSwitch} instance.
     *
     * @param entrance the entrance node
     * @param condition the condition operand
     * @param keys the keys for each case
     * @param caseNodes the nodes corresponding to each case
     * @param defaultNode the default node
     */
    OperandSwitch(Node entrance, Operand condition, int[] keys, List<Node> caseNodes, Node defaultNode) {
        // enum switch generates special code, we should strip them
        if (condition instanceof OperandArrayAccess access) {
            // For ECJ
            if (access.array instanceof OperandMethodCall ordinals && GeneratedCodes.isEnumSwitchMethod(ordinals.method)) {
                if (access.index instanceof OperandMethodCall call && call.method.getName()
                        .equals("ordinal") && call.method.getDeclaringClass() == Enum.class) {
                    condition = call.owner;
                }
            } else {
                // For Javac
                if (access.array instanceof OperandFieldAccess field && GeneratedCodes.isEnumSwitchField(field.field)) {
                    if (access.index instanceof OperandMethodCall call && call.method.getName()
                            .equals("ordinal") && call.method.getDeclaringClass() == Enum.class) {
                        condition = call.owner;

                        // See header document for details
                        //
                        // In Javac's implementation, there is a discrepancy between the ordinal
                        // value of the Enum and the value of the retained index, which should be
                        // corrected.
                        for (int i = 0; i < keys.length; i++) {
                            keys[i]++;
                        }
                    }
                }
            }
        }

        this.entrance = entrance;
        this.condition = condition;
        this.defaultNode = defaultNode;

        for (int i = 0; i < keys.length; i++) {
            cases.put(caseNodes.get(i), keys[i]);
        }

        markAsStatement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(condition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "switch(" + condition + "){ case:" + nodes().skipNull()
                .map(x -> x == defaultNode ? "#" + x.id : x.id)
                .toList() + " follow:" + (follower == null ? "none" : follower.id) + "}";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeCode(Coder coder) {
        if (Debugger.whileDebug) {
            coder.write(toString());
        } else {
            MultiMap<Structure, Object> caseBlocks = cases.convertKeys(entrance::process);

            coder.writeSwitch(false, Optional.empty(), condition, condition.type(), caseBlocks, null);
        }
    }

    private Switch cache;

    /**
     * @return
     */
    public Switch structurize() {
        if (cache == null) {
            cache = new Switch(entrance, condition, cases, follower);
        }
        return cache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isValue() {
        return isExpression();
    }

    /**
     * Test the given node is any case or not.
     * 
     * @param node
     * @return
     */
    boolean isCase(Node node) {
        return cases.containsKey(node);
    }

    /**
     * Test the given node is default case or not.
     * 
     * @param node
     * @return
     */
    boolean isDefault(Node node) {
        return defaultNode == node;
    }

    /**
     * Tests whether a given node is unrelated to this switch.
     * 
     * @param node
     * @return
     */
    boolean isOther(Node node) {
        return !isCase(node) && !isDefault(node) && entrance != node;
    }

    /**
     * Analyze following node.
     */
    void analyze(NodeManipulator manipulator) {
        cases.sort();
        cases.remove(defaultNode);

        this.cases = caseConverter.apply(cases);
        this.defaultNode = defaultConverter.apply(defaultNode);

        // ===============================================
        // Detect the really default node
        // ===============================================
        if (isReallyDefaultNode(defaultNode)) {
            follower = searchFollower();

            if (follower == defaultNode) {
                defaultNode = null;
            } else {
                cases.put(defaultNode);
            }
        } else {
            follower = defaultNode;
            defaultNode = null;
        }

        if (isExpression() && follower != null) {
            collectYieldables(follower).forEach(in -> {
                OperandYield yield = new OperandYield(in.remove(0));
                in.addOperand(yield);

                // type inference
                bindTo(yield);
            });

            List<Node> incomings = new ArrayList(entrance.incoming);
            entrance.disconnect(true, false);
            // follow.disconnect(true, false);
            for (Node in : follower.incoming) {
                if (in.hasDominator(entrance)) {
                    in.disconnect(follower);
                }
            }

            incomings.forEach(in -> in.connect(follower));
        }

        if (defaultNode != null && follower != null) {
            List<Node> cases = nodes().toList();

            // group incomings by cases
            MultiMap<Node, Node> group = new MultiMap(true);
            for (Node in : follower.getPureIncoming()) {
                for (Node node : cases) {
                    if (in.hasDominator(node)) {
                        group.put(node, in);
                    }
                }
            }

            // create splitter node for each cases
            Set<Node> incomings = group.values()
                    .map(nodes -> manipulator.createSplitterNodeBefore(follower, nodes))
                    .effect(node -> node.loopExit.set(new DelayedBreak(node)))
                    .toSet();

            // create splitter node for this switch
            follower = manipulator.createSplitterNodeBefore(follower, incomings);
            follower.additionalCall++;
        }
    }

    private class DelayedBreak extends Breakable {

        /**
         * @param that
         */
        public DelayedBreak(Node that) {
            super(that, that);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void writeCode(Coder coder) {
            OperandSwitch.this.structurize().write(coder);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Optional<String> label() {
            return OperandSwitch.this.structurize().label();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object id() {
            return condition;
        }
    }

    /**
     * Determine if the default node is really the default node or actually an exit node.
     * 
     * @return
     */
    private boolean isReallyDefaultNode(Node node) {
        // In searching for follow nodes in switches, there are several things to consider.
        //
        // First, we need to determine if the default node is indeed a default node or if it is
        // actually a follow node without a default node. What conditions must be met to determine
        // that a node is a default node?
        //
        // The default node has zero or one other directly (without going through other case nodes)
        // connected case nodes.
        List<Node> directlyConnectedCases = cases.keys()
                .take(caseNode -> caseNode.canReachTo(node, cases.keys().skip(caseNode).toSet()))
                .toList();

        switch (directlyConnectedCases.size()) {
        // It is an independent default node that is not connected to any case node.
        case 0:
            return true;

        // It is necessary to determine whether the node is a default node that has been
        // fall-through from the previous case or a follow node that is connected by break only from
        // one case.
        case 1:
            Node directly = directlyConnectedCases.get(0);
            // If the connected case node is declared immediately before def, it is the default
            // node. Conversely, if the connected case node has not been declared immediately
            // before, it is an exit node.
            return directly.isBefore(node, cases.keys().skip(directly).toSet());

        // It is a follow node because it is connected to two or more case nodes
        default:
            return false;
        }
    }

    private Node searchFollower() {
        List<Node> maybeFollowers = nodes()
                .flatMap(node -> node.outgoingRecursively(n -> isCase(n) || isDefault(n)).take(n -> !n.hasDominator(node)).first())
                .toList();

        if (maybeFollowers.isEmpty()) {
            if (hasFallThrough(defaultNode)) {
                return null;
            } else {
                return defaultNode;
            }
        } else {
            return maybeFollowers.get(0);
        }
    }

    private boolean acceptFallThrough(Node node) {
        return nodes().skip(node).any(node::hasDominator).to().v;
    }

    private boolean hasFallThrough(Node node) {
        return nodes().skip(n -> n == node || n.isBefore(node)).any(n -> node.canReachTo(n)).to().v;
    }

    /**
     * Traverse all cases.
     * 
     * @return
     */
    private Signal<Node> nodes() {
        return cases.keys().startWith(defaultNode).skipNull();
    }

    boolean canBeExpression(Node follow) {
        if (isExpression()) {
            return false;
        }

        if (!isOther(follow)) {
            return false;
        }

        for (Node node : nodes().toList()) {
            if (!node.isBefore(follow)) {
                return false;
            }

            if (!node.canReachTo(follow, nodes().skip(node).toSet(), true)) {
                return false;
            }
        }

        for (Node node : collectYieldables(follow)) {
            if (!node.isValue()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Collect pure incoming nodes which is not backedge.
     * 
     * @return
     */
    private Set<Node> collectYieldables(Node follow) {
        Set<Node> nodes = new HashSet();
        Set<Node> recorder = new HashSet();
        Deque<Node> queue = new ArrayDeque(follow.getPureIncoming());
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (recorder.add(node)) {
                if (node == entrance) {
                    // ignore
                } else if (node.isEmpty()) {
                    queue.addAll(node.getPureIncoming());
                } else {
                    nodes.add(node);
                }
            }
        }
        return nodes;
    }

    /**
     * Utility method to optimize for string-switch.
     * 
     */
    final void convertToStringSwitch() {
        replaceCondition(x -> x.as(OperandMethodCall.class).exact().owner);
    }

    /**
     * Replcase the condition on switch.
     * 
     * @param condition
     */
    final void replaceCondition(Operand condition) {
        this.condition = Objects.requireNonNull(condition);
    }

    /**
     * Replcase the condition on switch.
     * 
     * @param converter
     */
    final void replaceCondition(UnaryOperator<Operand> converter) {
        replaceCondition(converter.apply(condition));
    }

    void replaceCase(Node oldNode, Node newNode) {
        if (defaultNode == oldNode) {
            defaultNode = newNode;
        }

        if (cases.containsKey(oldNode)) {
            cases.putAll(newNode, cases.remove(oldNode));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return "switch" + (isExpression() ? "-expression" : "") + " (" + condition.view() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperandSwitch other) {
            return Objects.equals(condition, other.condition);
        } else {
            return false;
        }
    }
}