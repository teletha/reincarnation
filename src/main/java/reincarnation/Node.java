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
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

import kiss.I;
import kiss.Signal;
import kiss.Variable;
import kiss.Ⅱ;
import kiss.Ⅲ;
import reincarnation.JavaMethodDecompiler.TryCatchFinally;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.operator.BinaryOperator;
import reincarnation.structure.Break;
import reincarnation.structure.Breakable;
import reincarnation.structure.Continue;
import reincarnation.structure.DoWhile;
import reincarnation.structure.For;
import reincarnation.structure.Fragment;
import reincarnation.structure.If;
import reincarnation.structure.InfiniteLoop;
import reincarnation.structure.Loopable;
import reincarnation.structure.Structure;
import reincarnation.structure.Try;
import reincarnation.structure.While;
import reincarnation.util.Classes;

public class Node implements Code<Operand>, Comparable<Node> {

    /** The representation of node termination. */
    static final Node Termination = new Node("T");

    /** The identified label for this node. */
    public final String id;

    /** The actual operand stack. */
    final LinkedList<Operand> stack = new LinkedList<>();

    /** The node list. */
    final CopyOnWriteArrayList<Node> incoming = new CopyOnWriteArrayList<>();

    /** The node list. */
    final CopyOnWriteArrayList<Node> outgoing = new CopyOnWriteArrayList<>();

    /** The node list. */
    final CopyOnWriteArrayList<Node> dominators = new CopyOnWriteArrayList<>();

    /** The node list. */
    final CopyOnWriteArrayList<Node> backedges = new CopyOnWriteArrayList<>();

    /** The try-catch-finally starting node list. */
    final List<TryCatchFinally> tries = new CopyOnWriteArrayList<>();

    /** The line number. */
    int lineNumber = -1;

    /** The flag whether we can dispose this node or not. */
    boolean disposable = true;

    /** The previous node by order of appearance. */
    Node previous;

    /** The next node by order of appearance. */
    Node next;

    /**
     * The next node by jump destination. If this node has return or throw operand, this property
     * will be {@link #Termination}.
     */
    Node destination;

    /** The dominator node. */
    Node dominator;

    /** The state. */
    private boolean whileFindingDominator;

    /** The flag whether this node has already written or not. */
    boolean analyzed = false;

    /** The number of additional write calls. */
    int additionalCall = 0;

    /** The number of current write calls. */
    private int currentCalls = 0;

    /** The relationship with loop structure header. */
    public final Variable<Loopable> loopHeader = Variable.empty();

    /** The relationship with loop structure exit. */
    public final Variable<Breakable> loopExit = Variable.empty();

    /** The associated statement. */
    public Structure structure = Structure.Empty;

    /**
     * Create node with integral id.
     */
    Node(int id) {
        this(String.valueOf(id));
    }

    /**
     * Create node with textual id.
     */
    Node(String id) {
        this.id = id;
    }

    final boolean isValue() {
        return !stack.isEmpty() && stack.peekLast().isValue();
    }

    /**
     * Check node type.
     * 
     * @return
     */
    final boolean isThrow() {
        return stack.peekFirst() instanceof OperandThrow;
    }

    /**
     * Check node type.
     * 
     * @return
     */
    final boolean isAssign() {
        return stack.peekFirst() instanceof OperandAssign;
    }

    /**
     * Check node type.
     * 
     * @return
     */
    final boolean isReturnable() {
        Operand o = stack.peekFirst();
        return o instanceof OperandReturn || o instanceof OperandYield;
    }

    /**
     * Check node type.
     * 
     * @return
     */
    final boolean isSwitchStatement() {
        return child(OperandSwitch.class).map(OperandSwitch::isStatement).or(Boolean.FALSE);
    }

    /**
     * Check node type.
     * 
     * @return
     */
    final boolean isSwitchExpression() {
        return child(OperandSwitch.class).map(OperandSwitch::isExpression).or(Boolean.FALSE);
    }

    /**
     * Detect node positioning.
     * 
     * @param target
     * @return
     */
    final boolean isBefore(Node target) {
        return isBefore(target, Collections.EMPTY_SET);
    }

    /**
     * Detect node positioning.
     * 
     * @param target
     * @return
     */
    final boolean isBefore(Node target, Collection<Node> stopper) {
        while (target != null && !stopper.contains(target)) {
            if (target == this) {
                return true;
            }
            target = target.previous;
        }
        return false;
    }

    /**
     * Detect node positioning.
     * 
     * @param target
     * @return
     */
    final boolean isAfter(Node target) {
        return isAfter(target, Collections.EMPTY_SET);
    }

    /**
     * Detect node positioning.
     * 
     * @param target
     * @return
     */
    final boolean isAfter(Node target, Collection<Node> stopper) {
        while (target != null && !stopper.contains(target)) {
            if (target == this) {
                return true;
            }
            target = target.next;
        }
        return false;
    }

    /**
     * Traverse the chained tail node.
     * 
     * @return
     */
    final Signal<Node> tails() {
        return I.signal(outgoing).recurseMap(n -> n.flatIterable(x -> x.outgoing)).take(n -> n.outgoing.isEmpty());
    }

    /**
     * Traverse backward sibling node.
     * 
     * @return
     */
    final Signal<Node> prev() {
        return I.signal(previous);
    }

    /**
     * Traverse forward sibling node.
     * 
     * @return
     */
    final Signal<Node> next() {
        return I.signal(next);
    }

    /**
     * Traverse the unique outgoing node.
     * 
     * @return
     */
    final Signal<Node> uniqueOutgoing() {
        if (outgoing.size() == 1) {
            Node out = outgoing.get(0);
            if (out.incoming.size() == 1) {
                return I.signal(out);
            }
        }
        return I.signal();
    }

    /**
     * Traverse the unique incoming node.
     * 
     * @return
     */
    final Signal<Node> uniqueIncoming() {
        if (incoming.size() == 1) {
            Node in = incoming.get(0);
            if (in.outgoing.size() == 1) {
                return I.signal(in);
            }
        }
        return I.signal();
    }

    /**
     * Traverse incoming nodes recursively.
     * 
     * @return
     */
    final Signal<Node> incomingRecursively() {
        return I.signal(this).recurseMap(n -> n.flatIterable(x -> x.incoming)).takeWhile(n -> n != null);
    }

    /**
     * Traverse incoming nodes recursively.
     * 
     * @return
     */
    final Signal<Node> incomingRecursively(Predicate<Node> exclude) {
        Set<Node> recorder = new HashSet();

        return I.signal(this)
                .recurseMap(n -> n.flatIterable(x -> x.incoming).skip(x -> !recorder.add(x) || exclude.test(x)))
                .takeWhile(n -> n != null && n.backedges.isEmpty());
    }

    /**
     * Traverse outgoing nodes recursively.
     * 
     * @return
     */
    final Signal<Node> outgoingRecursively() {
        return I.signal(this).recurseMap(n -> n.flatIterable(x -> x.outgoing)).takeWhile(n -> n != null && n.backedges.isEmpty());
    }

    /**
     * Traverse outgoing nodes recursively.
     * 
     * @return
     */
    final Signal<Node> outgoingRecursively(Predicate<Node> exclude) {
        Set<Node> recorder = new HashSet();

        return I.signal(this)
                .recurseMap(n -> n.flatIterable(x -> x.outgoing).skip(x -> !recorder.add(x) || exclude.test(x)))
                .takeWhile(n -> n != null && n.backedges.isEmpty());
    }

    /**
     * Traverse the first outgoing junction point.
     * 
     * @return
     */
    final Signal<Node> junction() {
        return outgoingRecursively().take(n -> n.incoming.size() > 1 && getLowestCommonDominator(n.incoming) != this);
    }

    /**
     * Helper method to add new operand to the top of operands stack.
     * 
     * @param operand A new operand to add.
     */
    final Node addOperand(Object operand) {
        if (operand instanceof Operand) {
            stack.add((Operand) operand);
        } else if (operand instanceof Number) {
            stack.add(new OperandNumber((Number) operand));
        } else {
            stack.add(new OperandExpression(operand));
        }
        return this;
    }

    /**
     * Helper method to add new operand to the top of operands stack.
     * 
     * @param operand A new operand to add.
     */
    final Node addOperand(Object operand, Class<?> type) {
        stack.add(new OperandExpression(operand, type));

        return this;
    }

    /**
     * @param operands
     */
    final void addExpression(Object... operands) {
        for (Object operand : operands) {
            addOperand(operand);
        }
    }

    /**
     * <p>
     * Helper method to chech whether this node has the specified operand or not.
     * </p>
     * 
     * @param test
     * @return
     */
    final boolean has(Operand test) {
        for (Operand operand : stack) {
            if (operand == test) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Helper method to remove the operand which is stored in the specified index from the operands
     * stack.
     * </p>
     * 
     * @param index An index that you want to remove from the operands stack.
     * @return A removed operand.
     */
    final Operand remove(int index) {
        return remove(index, true);
    }

    /**
     * <p>
     * Helper method to remove the operand which is stored in the specified index from the operands
     * stack.
     * </p>
     * 
     * @param index An index that you want to remove from the operands stack.
     * @return A removed operand.
     */
    final Operand remove(int index, boolean processDuplication) {
        // Calculate index
        index = stack.size() - 1 - index;

        if (index < 0) {
            // Remove operand from the previous node if we can.
            //
            // calculated = stack.size() - 1 - index
            // index - stack.size() = -calculated - 1;
            return previous == null || incoming.isEmpty() ? null : previous.remove(-index - 1, processDuplication);
        }

        // Retrieve and remove it
        Operand operand = stack.remove(index);

        if (processDuplication && operand.duplicated) {
            operand.duplicated = false;

            // Duplicate pointer
            stack.add(index, operand);
        }

        // API definition
        return operand;
    }

    /**
     * Helper method to peek the operand which is stored in the specified index from the operands
     * stack.
     * 
     * @param index An index that you want to peek from the operands stack.
     * @return A target operand.
     */
    final Operand peek(int index) {
        // Calculate index
        index = stack.size() - 1 - index;

        if (index < 0) {
            // Peek operand from the previous node if we can.
            //
            // calculated = stack.size() - 1 - index
            // index - stack.size() = -calculated - 1;
            return previous == null || incoming.isEmpty() ? null : previous.peek(-index - 1);
        }

        // Retrieve it
        Operand operand = stack.get(index);

        // API definition
        return operand;
    }

    /**
     * <p>
     * Helper method to set the operand at the specified index from the operands stack.
     * </p>
     * 
     * @param index An index that you want to peek from the operands stack.
     * @return A target operand.
     */
    final Operand set(int index, Operand operand) {
        // Calculate index
        index = stack.size() - 1 - index;

        if (index < 0) {
            // Set operand to the previous node if we can.
            //
            // calculated = stack.size() - 1 - index
            // index - stack.size() = -calculated - 1;
            return previous == null || incoming.isEmpty() ? null : previous.set(-index - 1, operand);
        }

        // Retrieve and remove it
        operand = stack.set(index, operand);

        if (operand.duplicated) {
            operand.duplicated = false;

            // Duplicate pointer
            stack.add(index, operand);
        }

        // API definition
        return operand;
    }

    /**
     * Swaps the given operand with another operand.
     * 
     * @param target
     * @param replacer
     */
    final void swap(Operand target, Operand replacer) {
        if (target != null && replacer != null) {
            int index = stack.indexOf(target);
            if (index != -1) {
                stack.set(index, replacer);
            }
        }
    }

    /**
     * Clear all operands from this node.
     * 
     * @return
     */
    final Node clear() {
        stack.clear();
        return this;
    }

    /**
     * Transfer all operands to the given node.
     * 
     * @param node
     */
    final void transferTo(Node node) {
        if (node != null) {
            node.stack.addAll(stack);
            stack.clear();
        }
    }

    /**
     * <p>
     * Helper method to add new conditional operand on the top of this stack.
     * </p>
     * 
     * @param left A left operand.
     * @param operator A condition operator.
     * @param right A right operand.
     * @param transition A transition node.
     */
    final void condition(Operand left, int operator, Operand right, Node transition) {
        stack.add(new OperandCondition(left, operator, right, transition));
    }

    /**
     * Helper method to join latest two operands.
     * 
     * @param operator
     * @return Chainable API.
     */
    final Node join(BinaryOperator operator, int opecode) {
        Operand right = remove(0);
        Operand left = remove(0);
        addOperand(new OperandBinary(left, operator, right).fix(OperandUtil.load(opecode)));

        // API definition
        return this;
    }

    /**
     * <p>
     * Helper method to enclose current operand.
     * </p>
     * 
     * @return Chainable API.
     */
    final Node enclose() {
        stack.add(remove(0).encolose());

        // API definition
        return this;
    }

    /**
     * Helper method to search all backedge nodes using depth-first search.
     */
    final void searchBackEdge() {
        searchBackEdge(this, new ArrayDeque());
    }

    /**
     * Helper method to search all backedge nodes using depth-first search.
     * 
     * @param node A target node to check.
     * @param recorder All passed nodes.
     */
    private static void searchBackEdge(Node node, Deque<Node> recorder) {
        // Store the current processing node.
        recorder.add(node);

        // Step into outgoing nodes.
        for (Node out : node.outgoing) {
            if (recorder.contains(out)) {
                out.backedges.addIfAbsent(node);
            } else {
                searchBackEdge(out, recorder);
            }
        }

        // Remove the current processing node.
        recorder.pollLast();
    }

    /**
     * Helper method to check whether the specified node dominate this node or not.
     * 
     * @param dominator A dominator node.
     * @return A result.
     */
    final boolean hasDominator(Node dominator) {
        return hasDominator(dominator, Collections.EMPTY_SET);
    }

    /**
     * Helper method to check whether the specified node dominate this node or not.
     * 
     * @param dominator A dominator node.
     * @return A result.
     */
    final boolean hasDominator(Node dominator, Collection<Node> stoppers) {
        Node current = this;
        Set<Node> recorder = new HashSet<>();

        while (current != null && recorder.add(current) && !stoppers.contains(current)) {
            if (current == dominator) {
                return true;
            }
            current = current.getDominator();
        }

        // Not Found
        return false;
    }

    /**
     * Compute the immediate dominator of this node.
     * 
     * @return A dominator node. If this node is root, <code>null</code>.
     */
    final Node getDominator() {
        // check cache
        if (dominator == null && !whileFindingDominator) {
            whileFindingDominator = true;

            // We must search a immediate dominator.
            //
            // At first, we can ignore the older incoming nodes.
            List<Node> candidates = new CopyOnWriteArrayList<Node>(incoming);

            // compute backedges
            for (Node node : candidates) {
                if (backedges.contains(node)) {
                    candidates.remove(node);
                }
            }

            int size = candidates.size();

            switch (size) {
            case 0: // this is root node
                dominator = null;
                break;

            case 1: // only one incoming node
                dominator = candidates.get(0);
                break;

            default: // multiple incoming nodes
                Node candidate = candidates.get(0);

                search: while (candidate != null) {
                    for (int i = 1; i < size; i++) {
                        if (!candidates.get(i).hasDominator(candidate)) {
                            candidate = candidate.getDominator();
                            continue search;
                        }
                    }
                    dominator = candidate;
                    break;
                }
                break;
            }
            whileFindingDominator = false;
        }

        // API definition
        return dominator;
    }

    /**
     * Get all dominator nodes for this node. The first element is the nearest parent dominator
     * node.
     */
    final List<Node> getDominators() {
        List<Node> nodes = new ArrayList();
        Node dom = getDominator();
        while (dom != null && !nodes.contains(dom)) {
            nodes.add(dom);
            dom = dom.getDominator();
        }
        return nodes;
    }

    /**
     * Find the lowest common dominator node with the specified node.
     * 
     * @param target
     * @return
     */
    final Node getLowestCommonDominator(Node target) {
        List<Node> doms = getDominators();
        doms.add(0, this);

        while (target != null && !doms.contains(target)) {
            target = target.getDominator();
        }
        return target;
    }

    /**
     * Find the lowest common dominator node with the specified node.
     * 
     * @return
     */
    static Node getLowestCommonDominator(Collection<Node> targets) {
        int size = targets.size();

        if (size == 0) {
            return null;
        } else if (size == 1) {
            return targets.iterator().next();
        } else {
            Iterator<Node> iterator = targets.iterator();
            Node base = iterator.next();
            while (base != null && iterator.hasNext()) {
                base = base.getLowestCommonDominator(iterator.next());
            }
            return base;
        }
    }

    /**
     * Retrieve the valid destination node.
     * 
     * @return An actual destination node of this node.
     */
    Node getDestination() {
        return destination == Termination ? next : destination;
    }

    /**
     * Collect pure incoming nodes which is not backedge.
     * 
     * @return
     */
    Set<Node> getPureIncoming() {
        Set<Node> nodes = new HashSet<Node>(incoming);
        nodes.removeAll(backedges);

        return nodes;
    }

    /**
     * Detect whether the specified node is traversable from this node.
     * 
     * @param node A target node.
     * @return A result.
     */
    final boolean canReachTo(Node node, Node... exclusionNodes) {
        return canReachTo(node, I.set(exclusionNodes));
    }

    /**
     * Detect whether the specified node is traversable from this node.
     * 
     * @param node A target node.
     * @return A result.
     */
    final boolean canReachTo(Node node, Collection<Node> exclusionNodes) {
        return canReachTo(node, exclusionNodes, false);
    }

    /**
     * Detect whether the specified node is traversable from this node.
     * 
     * @param node A target node.
     * @return A result.
     */
    final boolean canReachTo(Node node, Collection<Node> exclusionNodes, boolean acceptThrow) {
        Set<Node> tails = new HashSet();

        Set<Node> recorder = new HashSet();
        recorder.add(this);

        Deque<Node> queue = new ArrayDeque();
        queue.add(this);

        while (!queue.isEmpty()) {
            for (Node out : queue.pollFirst().outgoing) {
                if (out == node) {
                    return true;
                }

                if (!exclusionNodes.contains(out) && recorder.add(out)) {
                    queue.addLast(out);

                    if (out.outgoing.isEmpty()) {
                        tails.add(out);
                    }
                }
            }
        }

        if (acceptThrow) {
            return tails.stream().allMatch(Node::isThrow);
        }

        return false;
    }

    /**
     * Helper method to connect nodes each other.
     * 
     * @param node A target node.
     */
    final void connect(Node node) {
        if (node != null && node != Termination) {
            outgoing.addIfAbsent(node);
            node.incoming.addIfAbsent(this);
        }
    }

    /**
     * Helper method to disconnect nodes each other.
     * 
     * @param node A target node.
     */
    final void disconnect(Node node) {
        if (node != null && node != Termination) {
            outgoing.remove(node);
            node.incoming.remove(this);
        }
    }

    /**
     * Disconnect node from incomings or outgoings.
     * 
     * @param incoming
     * @param outgoing
     */
    final void disconnect(boolean incoming, boolean outgoing) {
        if (incoming) this.incoming.forEach(in -> in.disconnect(this));
        if (outgoing) this.outgoing.forEach(out -> disconnect(out));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Node o) {
        return this == o ? 0 : isBefore(o) ? -1 : 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Operand> children() {
        return I.signal(stack);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        for (Operand operand : stack) {
            if (operand.isStatement()) {
                coder.writeStatement(operand);
            } else {
                operand.write(coder);
            }
        }
    }

    public Structure analyze() {
        if (!analyzed) {
            // =============================================================
            // Try-Catch-Finally Block
            // =============================================================
            if (!tries.isEmpty()) {
                TryCatchFinally removed = tries.remove(0);
                List<Ⅲ<Class, String, Structure>> catches = I.signal(removed.blocks)
                        .map(catchOrFinally -> I
                                .pair(catchOrFinally.exception, catchOrFinally.variable.toString(), process(catchOrFinally.node)))
                        .toList();
                if (removed.exit != null) {
                    removed.exit.additionalCall++;
                }
                return new Try(this, removed.start, catches, removed.exit);
            }

            if (isSwitchStatement()) {
                return child(OperandSwitch.class).v.structurize();
            } else if (isSwitchExpression()) {
                return Structure.Empty;
            }

            analyzed = true;

            // =============================================================
            // Other Block
            // =============================================================
            int outs = outgoing.size();
            int backs = backedges.size();

            if (outs == 0) {
                // end node
                return new Fragment(this);
            } else if (outs == 1) {
                // do while or normal
                if (backs == 0) {
                    // normal node with follower
                    return new Fragment(this, process(outgoing.get(0)));
                } else if (backs == 1) {
                    // do while or infinite loop
                    BackedgeGroup group = new BackedgeGroup(this);

                    if (backedges.get(0).outgoing.size() == 2) {
                        if (group.exit == null) {
                            // do while
                            return writeDoWhile();
                        } else {
                            // infinit loop
                            return writeInfiniteLoop(group);
                        }
                    } else {
                        // infinit loop
                        return writeInfiniteLoop(group);
                    }
                } else {
                    // infinit loop
                    return writeInfiniteLoop(new BackedgeGroup(this));
                }
            } else if (outs == 2) {
                // while, for or if
                if (backs == 0) {
                    return writeIf();
                } else if (backs == 1 && backedges.get(0).outgoing.size() == 1) {
                    return writeFor();
                } else {
                    return writeWhile();
                }
            }
        }
        return Structure.Empty;
    }

    /**
     * Write infinite loop structure.
     */
    private Structure writeInfiniteLoop(BackedgeGroup group) {
        if (group.exit != null) group.exit.currentCalls--;

        // make reanalyzable this node
        analyzed = false;

        // clear all backedge nodes of infinite loop
        incoming.removeAll(group);
        backedges.removeAll(group);
        currentCalls = incoming.size() - backedges.size() + additionalCall + 1;

        return new InfiniteLoop(this, this, group.exit);
    }

    /**
     * Write while structure.
     */
    private Structure writeWhile() {
        Node[] nodes = detectProcessAndExit();

        if (nodes == null) {
            return writeInfiniteLoop(new BackedgeGroup(this));
        } else {
            return new While(this, this, nodes[0], nodes[1], detectEnhancedForLoop(this, nodes[0]));
        }
    }

    /**
     * Write do-while structure.
     */
    private Structure writeDoWhile() {
        // setup condition expression node
        Node condition = backedges.remove(0);

        // make rewritable this node
        analyzed = false;

        Node exit;
        Node inner;

        if (condition.outgoing.get(0) == this) {
            exit = condition.outgoing.get(1);
            inner = condition.outgoing.get(0);
        } else {
            exit = condition.outgoing.get(0);
            inner = condition.outgoing.get(1);
        }
        return new DoWhile(this, condition, inner, exit);
    }

    /**
     * Write for structure.
     */
    private Structure writeFor() {
        Node[] nodes = detectProcessAndExit();

        if (nodes == null) {
            return writeInfiniteLoop(new BackedgeGroup(this));
        } else {
            // setup update expression node
            Node update = backedges.get(0);

            // search initializer
            // Node initializer = new Node(id + "-for-init");
            // I.signal(getPureIncoming())
            // .flatMap(n -> n.incomingRecursively())
            // .skip(Node::isEmpty)
            // .takeWhile(Node::isAssign)
            // .first()
            // .to(n -> {
            // while (n.isNotEmpty()) {
            // Operand removed = n.remove(0);
            // initializer.addOperand(new OperandExpression(removed));
            // }
            // });
            return new For(this, null, this, update, nodes[0], nodes[1], detectEnhancedForLoop(this, nodes[0]));
        }
    }

    /**
     * Build if statement.
     */
    private Structure writeIf() {
        OperandCondition condition = (OperandCondition) stack.peekLast().disclose();

        Node then = null;
        Node elze = null;
        Node follow = null;
        Node one = outgoing.get(0);
        Node other = outgoing.get(1);

        if (condition.then == other) {
            condition.invert();
        }

        if (one.getPureIncoming().size() != 1) {
            /**
             * no else <pre>
             * if (condition) {
             *      other
             * }
             * one
             * </pre>
             */
            condition.invert();

            then = other;
            follow = one;
        } else if (other.getPureIncoming().size() != 1) {
            /**
             * no else <pre>
             * if (condition) {
             *      one
             * }
             * other
             * </pre>
             */
            then = one;
            follow = other;
        } else {
            if (one.hasDominator(other)) {
                /**
                 * no else <pre>
                 * if (condition) {
                 *      one
                 * }
                 * </pre>
                 */
                then = one;
                follow = other;
            } else if (other.hasDominator(one)) {
                /**
                 * no else <pre>
                 * if (!condition) {
                 *      other
                 * }
                 * </pre>
                 */
                condition = condition.invert();
                then = other;
                follow = one;
            } else {
                /**
                 * with else <pre>
                 * if (condition) {
                 *      one
                 * } else {
                 *      other
                 * }
                 * </pre>
                 */
                then = one;
                elze = other;

                List<Node> candidates = I.signal(one, other)
                        .flatMap(node -> node.outgoingRecursively().take(n -> !n.hasDominator(node) && n.hasDominator(this)).first())
                        .distinct()
                        .toList();

                if (!candidates.isEmpty()) {
                    follow = candidates.get(0);
                    follow.additionalCall++;
                }
            }
        }
        return new If(this, condition, then, elze, follow);
    }

    /**
     * Detect a node relationship between this node and the next node.
     * 
     * @param next A next node to write.
     * @return An analyzed {@link Structure}.
     */
    public Structure process(Node next) {
        if (next != null) {
            // count a number of required write call
            int requiredCalls = next.incoming.size() - next.backedges.size() + next.additionalCall;

            if (requiredCalls == next.currentCalls) {
                return Structure.Empty;
            }

            next.currentCalls++;

            if (!isReturnable()) {
                // continue
                if (next.loopHeader.isPresent()) {
                    Loopable loopable = next.loopHeader.v;

                    if (loopable.containsAsHeader(next) && hasDominator(loopable.entrance)) {
                        Continue continuer = new Continue(this, loopable);

                        if (Debugger.current().isEnable()) {
                            continuer
                                    .comment(id + " -> " + next.id + " continue to " + loopable.entrance.id + " (" + next.currentCalls + " of " + requiredCalls + ") " + loopable);
                        }
                        return continuer;
                    }
                }

                // break
                if (next.loopExit.isPresent()) {
                    Breakable breakable = next.loopExit.v;

                    if (breakable instanceof Loopable loopable) {
                        if (loopable.exit == next && !loopable.containsAsHeader(this) && hasDominator(loopable.entrance)) {
                            // check whether the current node connects to the exit node directly or
                            // not
                            if (loopable.exit.incoming.contains(this)) {
                                Break breaker = new Break(this, loopable);

                                if (Debugger.current().isEnable()) {
                                    breaker.comment(id + " -> " + next.id + " break to " + loopable.entrance.id + "(" + next.currentCalls + " of " + requiredCalls + ") " + loopable);
                                }
                                return breaker;
                            }
                            return Structure.Empty;
                        }
                    } else if (requiredCalls != next.currentCalls && outgoing.contains(next)) {
                        Break breaker = new Break(this, breakable);
                        if (Debugger.current().isEnable()) {
                            breaker.comment(id + " -> " + next.id + " break" + "(" + next.currentCalls + " of " + requiredCalls + ") ");
                        }
                        return breaker;
                    }
                }
            }

            // normal process
            if (requiredCalls <= next.currentCalls) {
                return next.analyze();
            }
        }
        return Structure.Empty;
    }

    /**
     * <p>
     * Detect the follower node.
     * </p>
     * 
     * @return A non-follower node.
     */
    private Node[] detectProcessAndExit() {
        Node first = outgoing.get(0);
        Node last = outgoing.get(1);
        Node back = backedges.get(backedges.size() - 1);

        if (first.canReachTo(back, getDominator()) && last.canReachTo(back, getDominator())) {
            return null;
        }

        Node[] nodes;

        if (backedges.get(0).hasDominator(first)) {
            nodes = new Node[] {first, last};
        } else {
            nodes = new Node[] {last, first};
        }

        /**
         * condition's destination node must be the process node <pre>
         * loop (condition) {
         *      process-node
         * }
         * exit-node
         * </pre>
         */
        OperandCondition condition = (OperandCondition) stack.peekLast().disclose();

        if (condition.then != nodes[0]) {
            condition.invert();
        }
        return nodes;
    }

    /**
     * Detects code groups that are recognizable as enhanced for loop.
     * 
     * @param current
     * @param entrance
     * @return
     */
    private static Variable<Ⅱ<Operand, Operand>> detectEnhancedForLoop(Node current, Node entrance) {
        return detectEnhancedForIterableLoop(current, entrance).or(detectEnhancedForArrayLoop(current, entrance)).to();
    }

    /**
     * Detects code groups that are recognizable as enhanced for loop.
     * 
     * @param current
     * @param entrance
     * @return
     */
    private static Signal<Ⅱ<Operand, Operand>> detectEnhancedForArrayLoop(Node current, Node entrance) {
        return I.signal(current.getPureIncoming())
                // Check whether the only incoming node to the current is array's length
                .flatMap(in -> in.children(OperandAssign.class))
                .flatMap(length -> length.children(OperandArrayLength.class)
                        .map(m -> m.owner)
                        .combine(length.children(OperandLocalVariable.class)))

                // Check whether the current node compare index to array's length
                .takeIf(x -> current.children(OperandCondition.class).take(c -> c.right == x.ⅱ))

                // Check whether the entrance node access to the indexed item
                .takeIf(x -> entrance.children(OperandAssign.class, OperandArrayAccess.class).take(access -> access.array == x.ⅰ))

                // Summarize only information that will be used later.
                .combine(entrance.children(OperandAssign.class, OperandLocalVariable.class).as(Operand.class), (a, b) -> I.pair(a.ⅰ, b))

                // The contents of the entrance and incoming nodes should be deleted as they
                // are no longer needed.
                .effect(() -> {
                    // remove 'var item = array[index]'
                    entrance.clear();

                    // remove 'int length = array.length'
                    // remove 'index++'
                    current.incoming.forEach(Node::clear);
                });
    }

    /**
     * Detects code groups that are recognizable as enhanced for loop.
     * 
     * @param current
     * @param entrance
     * @return
     */
    private static Signal<Ⅱ<Operand, Operand>> detectEnhancedForIterableLoop(Node current, Node entrance) {
        return I.signal(current.getPureIncoming())
                // Check whether the only incoming node to the current is generating iterators.
                .flatMap(in -> in.children(OperandAssign.class))
                .flatMap(iterator -> iterator.children(OperandMethodCall.class)
                        .take(m -> m.checkMethod(Iterable.class, "iterator"))
                        .map(m -> m.owner)
                        .combine(iterator.children(OperandLocalVariable.class)))

                // Check whether the current node calls the iterator's hasNext method.
                .takeIf(x -> current.children(OperandCondition.class, OperandMethodCall.class)
                        .take(m -> m.checkCaller(x.ⅱ) && m.checkMethod(Iterator.class, "hasNext")))

                // Check whether the entrance node calls the iterator's next method.
                .takeIf(x -> entrance.children(OperandAssign.class, OperandMethodCall.class)
                        .flatMap(Node::throughUnwrapper)
                        .take(m -> m.checkCaller(x.ⅱ) && m.checkMethod(Iterator.class, "next")))

                // Summarize only information that will be used later.
                .combine(entrance.children(OperandAssign.class, OperandLocalVariable.class).as(Operand.class), (a, b) -> I.pair(a.ⅰ, b))

                // The contents of the entrance and incoming nodes should be deleted as they are no
                // longer needed.
                .effect(() -> {
                    entrance.clear();
                    current.getPureIncoming().forEach(Node::clear);
                });
    }

    /**
     * If the specified method call is a primitive wrapper method, it is ignored and its internal
     * method calls are searched further.
     * 
     * @param call
     * @return
     */
    static Signal<OperandMethodCall> throughUnwrapper(OperandMethodCall call) {
        if (Classes.isUnwrapper(call.method)) {
            return call.owner.children(OperandMethodCall.class);
        } else {
            return I.signal(call);
        }
    }

    /**
     * Ignore the empty connector node.
     * 
     * @param node
     * @return
     */
    static Signal<Node> throughEmpty(Node node) {
        if (node.isEmpty() && node.outgoing.size() == 1) {
            Node next = node.outgoing.get(0);
            if (next.incoming.size() == 1 && next == node.next) {
                return I.signal(next);
            }
        }
        return I.signal(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();

        for (Operand operand : stack) {
            builder.append(operand);
        }
        return builder.toString();
    }

    /**
     * @version 2018/11/05 13:17:16
     */
    @SuppressWarnings("serial")
    private static class BackedgeGroup extends ArrayDeque<Node> {

        /** The loop exit node. */
        private Node exit;

        /**
         * @param entrance
         */
        private BackedgeGroup(Node entrance) {
            // collect all backedges which share same infinite loop structure
            Node base = null;

            // the descendant order in incoming nodes is important
            for (int i = entrance.incoming.size() - 1; 0 <= i; i--) {
                Node node = entrance.incoming.get(i);

                // we needs backedge nodes only
                if (entrance.backedges.contains(node)) {
                    // track back to branch node
                    Node branch = node;

                    while (branch.outgoing.size() == 1 && branch.incoming.size() == 1) {
                        branch = branch.incoming.get(0);
                    }

                    if (base == null) {
                        // first backedge

                        // store as base node to collect all other backedges which share same
                        // infinite loop structure
                        base = branch;
                        add(node);
                    } else if (base.incoming.contains(branch) || base.hasDominator(branch)) {
                        // following backedges which is dominated by base node
                        add(node);
                    } else {
                        // stop collecting
                        break;
                    }
                }
            }

            // search exit node of this infinite loop structure (depth-first search)
            //
            // start from base node
            if (base != null) {
                Deque<Node> candidates = new ArrayDeque<Node>(base.outgoing);

                // record accessed nodes to avoid second access
                Set<Node> recorder = new HashSet<Node>(entrance.incoming);
                recorder.add(entrance);

                while (!candidates.isEmpty()) {
                    Node node = candidates.pollFirst();

                    if (recorder.add(node)) {
                        if (!node.hasDominator(base)) {
                            exit = node;
                            break;
                        } else {
                            candidates.addAll(node.outgoing);
                        }
                    }
                }
            }
        }
    }
}