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

import java.beans.Expression;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import kiss.I;
import kiss.Signal;
import kiss.Variable;
import kiss.Ⅲ;
import reincarnation.JavaMethodDecompiler.TryCatchFinally;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.operator.BinaryOperator;
import reincarnation.structure.Break;
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

/**
 * @version 2018/11/05 15:07:53
 */
public class Node implements Code<Operand> {

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

    /** This node is switch starting node. */
    Switch switchy;

    /** The dominator node. */
    Node dominator;

    /** The flag whether this node has break expression or not. */
    private boolean breaker = false;

    /** The state. */
    private boolean whileFindingDominator;

    /** The flag whether this node has already written or not. */
    boolean analyzed = false;

    /** The number of additional write calls. */
    int additionalCalls = 0;

    /** The number of current write calls. */
    private int currentCalls = 0;

    /** The relationship with loop structure header. */
    public final Variable<Loopable> loopHeader = Variable.empty();

    /** The relationship with loop structure exit. */
    public final Variable<Loopable> loopExit = Variable.empty();

    /** The associated statement. */
    public Structure structure = Structure.Empty;

    /**
     */
    Node(int id) {
        this(String.valueOf(id));
    }

    /**
     */
    Node(String id) {
        this.id = id;
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
    final boolean isReturn() {
        return stack.peekFirst() instanceof OperandReturn;
    }

    /**
     * Detect node positioning.
     * 
     * @param target
     * @return
     */
    final boolean isBefore(Node target) {
        while (target != null) {
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
        while (target != null) {
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
     * Traverse forward sibling nodes.
     * 
     * @return
     */
    final Signal<Node> nexts() {
        return I.signal(this).recurse(n -> n.next).takeWhile(n -> n != null);
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
     * Traverse outgoing nodes recursively.
     * 
     * @return
     */
    final Signal<Node> outgoingRecursively() {
        return I.signal(this).recurseMap(n -> n.flatIterable(x -> x.outgoing)).takeWhile(n -> n != null);
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
    final void addOperand(Object operand) {
        if (operand instanceof Operand) {
            stack.add((Operand) operand);
        } else if (operand instanceof Number) {
            stack.add(new OperandNumber((Number) operand));
        } else {
            stack.add(new OperandExpression(operand));
        }
    }

    /**
     * Helper method to add new operand to the top of operands stack.
     * 
     * @param operand A new operand to add.
     */
    final void addOperand(Object operand, Class<?> type) {
        stack.add(new OperandExpression(operand, type));
    }

    /**
     * <p>
     * Helper method to add new operand to the top of operands stack.
     * </p>
     * 
     * @param operand A new operand to add.
     */
    final void addOperand(Object operand, InferredType type) {
        stack.add(new OperandExpression(operand, type));
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
     */
    final void addExpression(Expression operand, InferredType type) {
        stack.add(new OperandExpression(operand, type));
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
     * Helper method to check whether the specified node dominate this node or not.
     * 
     * @param dominator A dominator node.
     * @return A result.
     */
    final boolean hasDominator(Node dominator) {
        Node current = this;
        Set<Node> recorder = new HashSet<>();

        while (current != null && recorder.add(current)) {
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
        while (dom != null) {
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
        List<Node> exclusions = Arrays.asList(exclusionNodes);
        Set<Node> recorder = new HashSet<Node>();
        recorder.add(this);

        Deque<Node> queue = new ArrayDeque<Node>();
        queue.add(this);

        while (!queue.isEmpty()) {
            for (Node out : queue.pollFirst().outgoing) {
                if (out == node) {
                    return true;
                }

                if (!exclusions.contains(out) && recorder.add(out)) {
                    queue.addLast(out);
                }
            }
        }
        return false;
    }

    /**
     * Helper method to check whether the specified node is incoming.
     * 
     * @param node
     * @return
     */
    final boolean equalsAsIncoming(Node node) {
        return node == this || incoming.contains(node);
    }

    /**
     * Helper method to check whether the specified node is outgoing.
     * 
     * @param node
     * @return
     */
    final boolean equalsAsOutgoing(Node node) {
        return node == this || outgoing.contains(node);
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
        if (outgoing) this.outgoing.forEach(out -> out.disconnect(this));
    }

    /**
     * Disconnect from the previous node and connect to the next node on incoming nodes.
     * 
     * @param prev
     * @param next
     */
    final void switchIncoming(Node prev, Node next) {
        int indexPrev = incoming.indexOf(prev);
        int indexNext = incoming.indexOf(next);

        if (indexPrev != -1 && indexNext == -1) {
            incoming.set(indexPrev, next);

            disconnect(prev);
            connect(next);
        }
    }

    /**
     * Disconnect from the previous node and connect to the next node on incoming nodes.
     * 
     * @param prev
     * @param next
     */
    final void switchOutgoing(Node prev, Node next) {
        int indexPrev = outgoing.indexOf(prev);
        int indexNext = outgoing.indexOf(next);

        if (indexPrev != -1 && indexNext == -1) {
            outgoing.set(indexPrev, next);

            disconnect(prev);
            connect(next);
        }
    }

    /**
     * Create switch statement.
     * 
     * @param defaults A default node.
     * @param keys A case key values.
     * @param cases A list of case nodes.
     * @param isStringSwitch Whether this is string switch or not.
     */
    final void createSwitch(Node defaults, int[] keys, CopyOnWriteArrayList<Node> cases, boolean isStringSwitch) {
        switchy = new Switch(this, defaults, keys, cases, isStringSwitch);

        // connect enter node with each case node
        for (Node node : cases) {
            if (node != defaults) {
                connect(node);
            }
        }

        // connect enter node with default node
        connect(defaults);
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
                    removed.exit.additionalCalls++;
                }
                return new Try(this, removed.start, catches, removed.exit);
            }

            analyzed = true;

            // =============================================================
            // Switch Block
            // =============================================================
            // Switch block is independent from other blocks, so we must return at the end.
            if (switchy != null) {
                // // execute first to detect default node
                // Node exit = switchy.searchExit();
                //
                // // enter switch
                // buffer.write("switch", "(" + switchy.value + ")", "{");
                // breakables.add(switchy);
                //
                // // each cases
                // for (Node node : switchy.cases()) {
                // for (int value : switchy.values(node)) {
                // buffer.append("case ", value, ":").line();
                // }
                // process(node, buffer);
                // }
                //
                // // default case
                // if (!switchy.noDefault) {
                // buffer.append("default:").line();
                // process(switchy.defaults, buffer);
                // }
                //
                // breakables.pollLast();
                //
                // // exit switch
                // buffer.append("}").line();
                //
                // // write following node
                // process(exit, buffer);
                //
                // return; // must
            }

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
        currentCalls = incoming.size() - backedges.size() + additionalCalls + 1;

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
            return new While(this, this, nodes[0], nodes[1]);
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

            return new For(this, null, this, update, nodes[0], nodes[1]);
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
            if (one.getDominator() != this) {
                /**
                 * loop breaker <pre>
                 * loop-structure ( ~ ) {
                 *   if (condition) {
                 *     break; // to one
                 *   } else {
                 *     other
                 *   }
                 * }
                 * one // dominator is not if-condition but loop-condition
                 * </pre>
                 */
                condition.invert();

                then = other;
                elze = createConnectorNode(this, one);
                follow = one;
                follow.currentCalls--;
            } else {
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
            }
        } else if (other.getPureIncoming().size() != 1) {
            if (other.getDominator() != this) {
                /**
                 * loop breaker <pre>
                 * loop-structure ( ~ ) {
                 *   if (condition) {
                 *     break; // to other
                 *   } else {
                 *     one
                 *   }
                 * }
                 * other // dominator is not if-condition but loop-condition
                 * </pre>
                 */
                then = one;
                elze = createConnectorNode(this, other);
                follow = other;
                follow.currentCalls--;
            } else {
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
            }
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
            follow = dominators.stream().filter(node -> !outgoing.contains(node)).findFirst().orElse(null);

            if (follow != null) {
                follow.currentCalls--;
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
            int requiredCalls = next.incoming.size() - next.backedges.size() + next.additionalCalls;

            if (requiredCalls == next.currentCalls) {
                return Structure.Empty;
            }

            next.currentCalls++;

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
                Loopable loopable = next.loopExit.v;

                if (loopable.exit == next && !loopable.containsAsHeader(this) && hasDominator(loopable.entrance)) {
                    // check whether the current node connects to the exit node directly or not
                    if (loopable.exit.incoming.contains(this)) {
                        Break breaker = new Break(this, loopable);

                        if (Debugger.current().isEnable()) {
                            breaker.comment(id + " -> " + next.id + " break to " + loopable.entrance.id + "(" + next.currentCalls + " of " + requiredCalls + ") " + loopable);
                        }
                        return breaker;
                    }
                    return Structure.Empty;
                }
            }

            // normal process
            // System.out
            // .println("id:" + id + " req:" + requiredCalls + " add:" + next.additionalCalls + "
            // next.current:" + next.currentCalls + "[" + next.id + "]");
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
            // condition.invert();
        }
        return nodes;
    }

    /**
     * <p>
     * Create new connector node.
     * </p>
     * 
     * @param previous A previous node.
     * @param next A next node.
     * @return A new created node.
     */
    private Node createConnectorNode(Node previous, Node next) {
        // dislinkage
        previous.disconnect(next);

        // create and linkage
        Node node = new Node(previous.id + "*" + next.id);
        previous.connect(node);
        node.connect(next);

        node.next = previous.next;
        node.previous = previous;

        previous.next.previous = node;
        previous.next = node;

        if (previous.breaker) {
            previous.breaker = false;
            node.addExpression("break");
            throw new Error("IMPLEMENT!");
        }

        // API definition
        return node;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();

        for (Operand operand : stack) {
            builder.append(operand.disclose());
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

    /**
     * @version 2014/07/02 23:19:37
     */
    private abstract static class Breakable {

        /** The first processing node of this block structure. */
        protected final Node first;

        /**
         * @param first
         */
        protected Breakable(Node first) {
            this.first = first;
        }
    }

    /**
     * @version 2013/01/23 9:25:08
     */
    static class Switch extends Breakable {

        /** Normal switch or String switch. */
        final boolean isStringSwitch;

        /** The entering node. */
        private final Node enter;

        /** The evaluated value. */
        private final Operand value;

        /** The default node of this switch statement. */
        final Node defaults;

        /** The case nodes of this switch statement. */
        final CopyOnWriteArrayList<Node> cases;

        /** The case value of this switch statement. */
        private final List<Integer> keys = new ArrayList<Integer>();

        /** Whether this switch has default node or not. */
        private boolean noDefault = false;

        /**
         * <p>
         * Creat switch block infomation holder.
         * </p>
         * 
         * @param enter
         * @param defaults
         * @param keys
         * @param cases
         * @param isStringSwitch
         */
        private Switch(Node enter, Node defaults, int[] keys, CopyOnWriteArrayList<Node> cases, boolean isStringSwitch) {
            super(enter);

            this.enter = enter;
            this.enter.disposable = false;
            this.value = enter.remove(0);
            this.defaults = defaults;
            this.cases = cases;
            this.isStringSwitch = isStringSwitch;

            enter.disposable = defaults.disposable = false;
            for (Node node : cases) {
                node.disposable = false;
            }

            for (int key : keys) {
                this.keys.add(key);
            }
        }

        /**
         * <p>
         * Find all case values for the specified node.
         * </p>
         * 
         * @param node A target node.
         * @return A collected case values.
         */
        private List<Integer> values(Node node) {
            CopyOnWriteArrayList<Integer> values = new CopyOnWriteArrayList<Integer>();

            for (int i = 0; i < cases.size(); i++) {
                if (cases.get(i) == node) {
                    values.addIfAbsent(keys.get(i));
                }
            }
            return values;
        }

        /**
         * <p>
         * Find all unique cases for the specified node.
         * </p>
         * 
         * @param node A target node.
         * @return A collected case values.
         */
        private List<Node> cases() {
            CopyOnWriteArrayList<Node> nodes = new CopyOnWriteArrayList<Node>();

            for (int i = 0; i < cases.size(); i++) {
                if (cases.get(i) != defaults) {
                    nodes.addIfAbsent(cases.get(i));
                }
            }
            return nodes;
        }

        /**
         * <p>
         * Search exit node of this switch block.
         * </p>
         * 
         * @return Null or exit node.
         */
        private Node searchExit() {
            // The end node is not default node.
            if (defaults.incoming.size() != 1 && defaults.incoming.contains(enter)) {
                noDefault = true; // default node does not exist
            }

            for (Node node : defaults.incoming) {
                if (node.hasDominator(enter)) {
                    if (node.outgoing.size() == 1) {
                        node.addExpression("break");
                    } else {
                        node.breaker = true;
                    }
                }
            }

            if (!noDefault) {
                Set<Node> record = new HashSet<Node>();
                record.addAll(defaults.outgoing);

                List<Node> nodes = new LinkedList<Node>();
                nodes.addAll(defaults.outgoing);

                while (!nodes.isEmpty()) {
                    Node node = nodes.remove(0);

                    // PATTERN 1
                    // The exit node accepts only from case nodes.
                    if (node.getDominator() == enter) {
                        for (Node incoming : node.incoming) {
                            incoming.addExpression("break");
                        }
                        return node;
                    }

                    // PATTERN 2
                    // The exit node accepts both case nodes and other flow nodes.
                    if (!node.hasDominator(enter)) {
                        for (Node incoming : node.incoming) {
                            if (incoming.hasDominator(enter)) {
                                incoming.addExpression("break");
                            }
                        }
                        return node;
                    }

                    for (Node out : node.outgoing) {
                        if (record.add(out)) {
                            nodes.add(out);
                        }
                    }
                }
            }
            return defaults;
        }

        /**
         * <p>
         * Preprocess.
         * </p>
         */
        List<Node> process() {
            List<Node> disposables = new ArrayList<Node>();

            if (isStringSwitch) {
                // skip the value equivalent node like the following:
                //
                // case 97:
                // if (value.equals("a")) {
                // goto actual case node
                // } else {
                // goto default or exit node
                // }
                // break;
                for (int i = 0; i < cases.size(); i++) {
                    Node equivalent = cases.get(i);
                    Node actualCase = equivalent.outgoing.get(equivalent.outgoing.get(0) == defaults ? 1 : 0);

                    // replace to actual case node
                    cases.set(i, actualCase);

                    // dispose equivalent node
                    disposables.add(equivalent);
                }
            }
            return disposables;
        }

        /**
         * <p>
         * Helper method to detect special enum method.
         * </p>
         * 
         * @param name
         * @param description
         * @return
         */
        static boolean isEnumSwitchTable(String name, String description) {
            // For Eclipse JDT compiler.
            if (name.startsWith("$SWITCH_TABLE$")) {
                return true;
            }

            // For JDK compiler.
            if (name.startsWith("$SwitchMap$")) {
                return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "Switch [enter=" + enter.id + "]";
        }
    }
}