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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;

import kiss.I;
import kiss.Signal;
import kiss.Variable;
import reincarnation.coder.Coder;import reincarnation.structure.Structure;
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
    final Variable<Operand> condition = Variable.empty();

    /** The special case manager. */
    private MultiMap<Node, Object> cases = new MultiMap(true);

    /** The default case. */
    private Node defaultNode;

    /** The end node. */
    private Node follower;

    /** The post processor for case blocks. */
    private UnaryOperator<MultiMap<Node, Object>> caseConverter = UnaryOperator.identity();

    /** The post processor for default block. */
    private UnaryOperator<Node> defaultConverter = UnaryOperator.identity();

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
        this.condition.set(condition);
        this.defaultNode = defaultNode;

        for (int i = 0; i < keys.length; i++) {
            cases.put(caseNodes.get(i), keys[i]);
        }
        cases.remove(defaultNode);

        // connect from entrance to each cases and default
        nodes().to(entrance::connect);

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

            coder.writeSwitch(false, Optional.empty(), condition.get(), condition.get().type(), caseBlocks, null);
        }
    }

    /**
     * @return
     */
    public Switch structurize() {
        return new Switch(entrance, condition.get(), cases, follower);
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

    private boolean analyzed;

    /** Whether this switch is a type pattern switch or not. */
    private boolean typeSwitch;

    /** The label variable that drives the typeSwitch dispatch. */
    private Operand patternLabel;

    /** The flag whether this pattern switch is converted or not. */
    private boolean patternConverted;

    /**
     * Mark this switch as a type pattern switch (JDK 21). The dispatch index labels will be
     * converted to the actual type patterns by {@link #convertToTypeSwitch()}.
     * 
     * @param labelVariable The label variable that drives the typeSwitch dispatch.
     */
    final void markTypeSwitch(Operand labelVariable) {
        this.typeSwitch = true;
        this.patternLabel = labelVariable;
    }

    /**
     * Analyze following node.
     */
    void analyze(NodeManipulator manipulator) {
        if (analyzed) {
            return;
        }
        analyzed = true;

        Debugger.current().print(entrance.id + "    " + cases.keys().map(x -> x.id).toList() + "   " + this);

        // Dismantle the pattern structure before any further analysis so that the switch expression
        // conversion below (and the one in the visitFrame phase) sees a clean control flow.
        convertToTypeSwitch();

        this.cases = caseConverter.apply(cases);
        cases.sort();
        this.defaultNode = defaultConverter.apply(defaultNode);

        // ===============================================
        // Detect the really default node
        // ===============================================
        if (isReallyDefaultNode(defaultNode)) {
            follower = searchFollower();

            if (follower == defaultNode) {
                if (typeSwitch && !isExpression()) {
                    // A type pattern switch statement must be exhaustive (or have a default), so the
                    // default must be kept as a case inside the switch instead of the trailing follow.
                    cases.put(defaultNode);
                    follower = null;
                } else {
                    defaultNode = null;
                }
            } else {
                cases.put(defaultNode);
            }
        } else {
            follower = defaultNode;
            defaultNode = null;
        }

        if (isExpression() && follower != null) {
            collectYieldables(follower).forEach(in -> {
                if (in.peek(0) instanceof OperandYield == false) {
                    OperandYield yield = new OperandYield(in.remove(0));
                    in.addOperand(yield);

                    // type inference
                    bindTo(yield);
                }
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
            return;
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
            Set<Node> incomings = group.values().map(nodes -> manipulator.createSplitterNodeBefore(follower, nodes)).toSet();

            // create splitter node for this switch
            follower = manipulator.createSplitterNodeBefore(follower, incomings);
            follower.additionalCall++;
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

    private boolean hasFallThrough(Node node) {
        return nodes().skip(n -> n == node || n.isBefore(node)).any(n -> node.canReachTo(n)).to().get();
    }

    /**
     * Traverse all case nodes.
     * 
     * @return
     */
    public Signal<Node> cases() {
        return cases.keys().skipNull();
    }

    /**
     * Traverse all case and default nodes.
     * 
     * @return
     */
    public Signal<Node> nodes() {
        return cases.keys().startWith(defaultNode).skipNull();
    }

    Signal<Node> tails(Node stop) {
        return nodes().flatMap(n -> n.outgoingRecursively(x -> x == stop));
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

        Set<Node> yieldables = collectYieldables(follow);
        for (Node node : yieldables) {
            if (!node.isValue()) {
                return false;
            }
        }

        // The default case must yield into the follow through only yielded values. If it reaches
        // the follow through other statements, the switch is a statement whose default falls
        // through to the code after the switch (not a switch expression).
        if (defaultNode != null && !reachesThroughYields(defaultNode, follow, yieldables)) {
            return false;
        }
        return true;
    }

    /**
     * Test whether the specified node reaches the follow node through only empty nodes and yielded
     * value nodes.
     * 
     * @param start A start node.
     * @param follow A follow node.
     * @param yieldables The yielded value nodes.
     * @return A result.
     */
    private boolean reachesThroughYields(Node start, Node follow, Set<Node> yieldables) {
        Set<Node> recorder = new HashSet();
        recorder.add(start);

        Deque<Node> queue = new ArrayDeque();
        queue.add(start);

        while (!queue.isEmpty()) {
            for (Node out : queue.pollFirst().outgoing) {
                if (out == follow || !recorder.add(out)) {
                    continue;
                }

                if (!out.isEmpty() && !yieldables.contains(out)) {
                    return false;
                }
                queue.addLast(out);
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
    final void convertToStringSwitch(UnaryOperator<Node> defaultConverter, UnaryOperator<MultiMap<Node, Object>> caseConverter) {
        this.defaultConverter = defaultConverter;
        this.caseConverter = caseConverter;

        List<OperandLocalVariable> variables = entrance.children(OperandLocalVariable.class).toList();
        entrance.stack.removeAll(variables);

        condition.get().as(OperandMethodCall.class).to(call -> {
            if (call.owner instanceof OperandAssign assign) {
                this.condition.set(assign.right);
            } else {
                this.condition.set(call.owner);

                // The caller may be a temporary copy of the actual switch argument generated by the
                // compiler (e.g. `local2 = arg0`), so resolve it to the original argument.
                if (call.owner instanceof OperandLocalVariable local) {
                    I.signal(entrance.stack).take(OperandAssign.class::isInstance).to(o -> {
                        OperandAssign assign = (OperandAssign) o;
                        if (assign.left instanceof OperandLocalVariable left && left.equals(local)) {
                            this.condition.set(assign.right);
                        }
                    });
                }
            }
        });
    }

    /**
     * Utility method to optimize for string-switch.
     * 
     */
    final void convertToStringSwitch(Node defaultNode) {
        this.defaultConverter = node -> defaultNode;

        List<OperandLocalVariable> variables = entrance.children(OperandLocalVariable.class).toList();
        entrance.stack.removeAll(variables);

        condition.get().as(OperandMethodCall.class).to(call -> {
            if (call.owner instanceof OperandAssign assign) {
                this.condition.set(assign.right);
            } else {
                this.condition.set(call.owner);
            }
        });
    }

    /**
     * Convert the dispatch index labels of a type pattern switch to the actual case patterns. Each
     * type pattern case starts with the binding {@code localX = (Type) value}, optionally followed by
     * a guard condition and the label assignment for the re-dispatch on the guard failure. The
     * binding, the guard and the label assignment are stripped from the case body, the re-dispatch
     * edge to the switch header is removed, and the case label becomes the bound type, the pattern
     * variable and the guard (or the null pattern for a case without a binding).
     * 
     * <p>
     * This conversion must be done before the switch expression analysis ({@code canBeExpression}),
     * because the re-dispatch structure of a guarded pattern confuses the expression conversion. The
     * conversion is all-or-nothing and idempotent.
     * </p>
     */
    final void convertToTypeSwitch() {
        if (!typeSwitch || patternConverted) {
            return;
        }

        List<Node> caseNodes = cases.keys().toList();

        // The switch value may be a temporary copy of the actual argument (e.g. `local2 = param`),
        // so resolve the condition to the original argument.
        if (condition.get() instanceof OperandLocalVariable local) {
            I.signal(entrance.stack).take(OperandAssign.class::isInstance).to(o -> {
                OperandAssign assign = (OperandAssign) o;
                if (assign.left instanceof OperandLocalVariable left && left.equals(local)) {
                    this.condition.set(assign.right);
                }
            });
        }

        // Classify all cases first; the conversion is all-or-nothing.
        // The guard layout differs per compiler: Javac may keep the guard condition (and the label
        // assignment for the re-dispatch) on the same node as the pattern binding, while ECJ places
        // the condition on the node forwarded from the binding node.
        Map<Node, GuardInfo> guards = new HashMap();
        for (Node node : caseNodes) {
            List<Object> labels = cases.get(node);
            if (labels.size() != 1 || !(labels.get(0) instanceof Integer label)) {
                return;
            }

            boolean binding = !node.stack.isEmpty() && node.stack.get(0) instanceof OperandAssign assign && assign.left instanceof OperandLocalVariable
                    && assign.right instanceof OperandCast;

            if (binding) {
                if (node.stack.size() > 1 && node.stack.get(1) instanceof OperandCondition condition) {
                    // The guard condition shares the node with the pattern binding (Javac).
                    GuardInfo info = guardInfo(node, condition);
                    if (info == null) {
                        return;
                    }
                    guards.put(node, info);
                } else if (node.stack.size() == 1) {
                    // The guard condition may be placed on the forwarded node (ECJ), or the case
                    // is unguarded.
                    guards.put(node, forwardedGuardInfo(node));
                }
            } else if (label != -1) {
                // A case without a pattern binding must be the null case (dispatch label -1).
                return;
            }
        }

        // Apply the conversion.
        patternConverted = true;
        for (Node node : caseNodes) {
            cases.remove(node);

            if (node.stack.isEmpty() || !(node.stack.get(0) instanceof OperandAssign binding)) {
                // The null case has no pattern binding.
                cases.put(node, PatternCase.NULL);
                continue;
            }

            OperandLocalVariable variable = (OperandLocalVariable) binding.left;
            OperandCast cast = (OperandCast) binding.right;
            node.stack.remove(0);

            Operand guard = null;
            GuardInfo info = guards.get(node);
            if (info != null) {
                info.host.stack.remove(0); // the guard condition
                if (info.host == node && !node.stack.isEmpty() && node.stack.get(0) instanceof OperandAssign) {
                    node.stack.remove(0); // the label assignment for the re-dispatch
                }
                info.host.disconnect(info.redispatch);
                if (info.redispatch != entrance) {
                    info.redispatch.disconnect(entrance); // remove the re-dispatch edge to the switch header
                }
                // The re-dispatch happens when the condition matches, so the guard is inverted.
                guard = (info.redispatch == info.condition.then) ? info.condition.invert() : info.condition;
            }

            cases.put(node, new PatternCase(cast.type, resolvePatternVariable(variable, guard, node), guard));
        }
    }

    /**
     * Build the guard info for a condition that shares the same node as the pattern binding.
     * 
     * @param host The node holding the condition.
     * @param condition The guard condition.
     * @return The guard info, or null when no branch re-dispatches the switch dispatch.
     */
    private GuardInfo guardInfo(Node host, OperandCondition condition) {
        Node redispatch = reachesHeader(condition.then) ? condition.then : (reachesHeader(condition.elze) ? condition.elze : null);
        if (redispatch == null) {
            return null;
        }
        return new GuardInfo(condition, host, redispatch);
    }

    /**
     * Find the guard condition placed on the node forwarded from the pattern binding node
     * (compiled by ECJ).
     * 
     * @param node The pattern binding node.
     * @return The guard info, or null when the forwarded node has no guard condition.
     */
    private GuardInfo forwardedGuardInfo(Node node) {
        if (node.outgoing.size() != 1) {
            return null;
        }

        Node host = node.outgoing.get(0);
        if (host == entrance || host.stack.isEmpty() || !(host.stack.get(0) instanceof OperandCondition condition)) {
            return null;
        }

        boolean then = reachesHeader(condition.then);
        boolean elze = reachesHeader(condition.elze);
        if (then == elze) {
            // Not a clear guard (both or neither branch re-dispatches), treat it as unguarded.
            return null;
        }
        return new GuardInfo(condition, host, then ? condition.then : condition.elze);
    }

    /**
     * Test whether the branch node leads back to the switch header (the re-dispatch) or not.
     */
    private boolean reachesHeader(Node node) {
        return node == entrance || (node != null && node.outgoing.contains(entrance));
    }

    /**
     * The guard condition layout of a pattern case.
     */
    private final class GuardInfo {

        /** The condition operand of the guard. */
        final OperandCondition condition;

        /** The node that holds the guard condition. */
        final Node host;

        /** The branch node of the condition that re-dispatches the switch dispatch. */
        final Node redispatch;

        GuardInfo(OperandCondition condition, Node host, Node redispatch) {
            this.condition = condition;
            this.host = host;
            this.redispatch = redispatch;
        }
    }

    /**
     * Resolve the pattern variable that the guard and the case body actually reference. The compiler
     * may name the binding and the references differently through the local variable table (e.g. the
     * binding is local4 while the guard or the body refers to i), so the case label uses the name
     * referenced in the guard or the body.
     * 
     * @param variable The pattern variable of the stripped binding.
     * @param guard The guard condition of the pattern, or null.
     * @param node The case body node.
     * @return The referenced pattern variable.
     */
    private OperandLocalVariable resolvePatternVariable(OperandLocalVariable variable, Operand guard, Node node) {
        List<Signal<OperandLocalVariable>> references = new ArrayList();
        if (guard != null) {
            references.add(guard.children(OperandLocalVariable.class));
        }
        references.add(node.children(OperandLocalVariable.class));
        node.outgoing.forEach(out -> references.add(out.children(OperandLocalVariable.class)));

        for (Signal<OperandLocalVariable> refs : references) {
            for (OperandLocalVariable v : refs.toList()) {
                if (v.index.get().equals(variable.index.get())) {
                    return v;
                }
            }
        }
        return variable;
    }

    final void updateCase(Node oldNode, Node newNode) {
        if (defaultNode == oldNode) {
            defaultNode = newNode;
        }

        if (cases.containsKey(oldNode)) {
            cases.putAll(newNode, cases.remove(oldNode));
        }
    }

    /**
     * @param oldNode
     * @param newNode
     */
    public void replaceCase(Node oldNode, Node newNode, OperandString value) {
        if (defaultNode == oldNode) {
            defaultNode = newNode;
        }

        List<Object> removed = cases.remove(oldNode);
        if (removed != null) {
            cases.put(newNode, value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String view() {
        return "switch" + (isExpression() ? "-expression" : "") + " (" + condition.get().view() + ")";
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
