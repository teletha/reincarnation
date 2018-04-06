/*
 * Copyright (C) 2016 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @version 2014/07/05 19:06:00
 */
class Node {

    /** The representation of node termination. */
    static final Node Termination = new Node("T");

    /** The frequently used operand for cache. */
    static final OperandExpression END = new OperandExpression(";");

    /** The frequently used operand for cache. */
    static final OperandExpression Return = new OperandExpression("return ");

    /** The stack of labeled blocks. */
    private static final Deque<Breakable> breakables = new ArrayDeque();

    /** The identified label for this node. */
    final String id;

    /** The actual operand stack. */
    final LinkedList<Operand> stack = new LinkedList();

    /** The node list. */
    final CopyOnWriteArrayList<Node> incoming = new CopyOnWriteArrayList();

    /** The node list. */
    final CopyOnWriteArrayList<Node> outgoing = new CopyOnWriteArrayList();

    /** The node list. */
    final CopyOnWriteArrayList<Node> dominators = new CopyOnWriteArrayList();

    /** The node list. */
    final CopyOnWriteArrayList<Node> backedges = new CopyOnWriteArrayList();

    /** The try-catch-finally starting node list. */
    final List<TryCatchFinally> tries = new CopyOnWriteArrayList();

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
    private boolean written = false;

    /** The flag whether this node can omit continue statement safely or not. */
    private Boolean continueOmittable;

    /** The flag whether this node can omit return statement safely or not. */
    private boolean returnOmittable = true;

    /** The number of additional write calls. */
    private int additionalCalls = 0;

    /** The number of current write calls. */
    private int currentCalls = 0;

    /** The associated loop structure. */
    private Deque<LoopStructure> loops = new ArrayDeque();

    /**
     * @param label
     */
    Node(int id) {
        this(String.valueOf(id));
    }

    /**
     * @param label
     */
    Node(String id) {
        this.id = id;
    }

    /**
     * <p>
     * Helper method to add new operand to the top of operands stack.
     * </p>
     * 
     * @param operand A new operand to add.
     */
    final void addOperand(Object operand) {
        if (operand instanceof Operand) {
            stack.add((Operand) operand);
        } else if (operand instanceof Integer) {
            stack.add(new OperandNumber((Integer) operand));
        } else {
            stack.add(new OperandExpression(operand));
        }
    }

    /**
     * <p>
     * Helper method to add new operand to the top of operands stack.
     * </p>
     * 
     * @param operand A new operand to add.
     */
    final void addOperand(Object operand, Class type) {
        stack.add(new OperandExpression(operand, type));
    }

    /**
     * @param operands
     */
    final void addExpression(Object... operands) {
        for (Object operand : operands) {
            addOperand(operand);
        }
        stack.add(END);
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
     * <p>
     * Helper method to peek the operand which is stored in the specified index from the operands
     * stack.
     * </p>
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
     * @param separator
     * @return Chainable API.
     */
    final Node join(String separator) {
        Operand left = remove(0);
        Operand right = remove(0);

        if (left.infer().type() == char.class) {
            left = new OperandExpression(Javascript.writeMethodCode(String.class, "codePointAt", left, int.class, 0), int.class);
        }

        if (right.infer().type() == char.class) {
            right = new OperandExpression(Javascript.writeMethodCode(String.class, "codePointAt", right, int.class, 0), int.class);
        }

        stack.add(new OperandExpression(right + separator + left, right.infer()));

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
        stack.add(new OperandEnclose(remove(0)));

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
        Set<Node> recorder = new HashSet();

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
            List<Node> candidates = new CopyOnWriteArrayList(incoming);

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
     * <p>
     * Retrieve the valid destination node.
     * </p>
     * 
     * @return An actual destination node of this node.
     */
    Node getDestination() {
        return destination == Termination ? next : destination;
    }

    /**
     * <p>
     * Collect pure incoming nodes which is not backedge.
     * </p>
     * 
     * @return
     */
    Set<Node> getPureIncoming() {
        Set<Node> nodes = new HashSet(incoming);
        nodes.removeAll(backedges);

        return nodes;
    }

    /**
     * <p>
     * Detect whether the specified node is traversable from this node.
     * </p>
     * 
     * @param node A target node.
     * @return A result.
     */
    final boolean canReachTo(Node node, Node... exclusionNodes) {
        List<Node> exclusions = Arrays.asList(exclusionNodes);
        Set<Node> recorder = new HashSet();
        recorder.add(this);

        Deque<Node> queue = new ArrayDeque();
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
     * <p>
     * Helper method to check whether the specified node is incoming.
     * </p>
     * 
     * @param node
     * @return
     */
    final boolean equalsAsIncoming(Node node) {
        return node == this || incoming.contains(node);
    }

    /**
     * <p>
     * Helper method to check whether the specified node is outgoing.
     * </p>
     * 
     * @param node
     * @return
     */
    final boolean equalsAsOutgoing(Node node) {
        return node == this || outgoing.contains(node);
    }

    /**
     * <p>
     * Helper method to connect nodes each other.
     * </p>
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
     * <p>
     * Helper method to disconnect nodes each other.
     * </p>
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
     * <p>
     * Create switch statement.
     * </p>
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
     * <p>
     * Write script fragment.
     * </p>
     * 
     * @param buffer
     */
    final void write(ScriptWriter buffer) {
        if (!written) {
            written = true;

            if (lineNumber != -1) {
                buffer.comment(lineNumber);
            }

            // =============================================================
            // Switch Block
            // =============================================================
            // Switch block is independent from other blocks, so we must return at the end.
            if (switchy != null) {
                // execute first to detect default node
                Node exit = switchy.searchExit();

                // enter switch
                buffer.write("switch", "(" + switchy.value + ")", "{");
                breakables.add(switchy);

                // each cases
                for (Node node : switchy.cases()) {
                    for (int value : switchy.values(node)) {
                        buffer.append("case ", value, ":").line();
                    }
                    process(node, buffer);
                }

                // default case
                if (!switchy.noDefault) {
                    buffer.append("default:").line();
                    process(switchy.defaults, buffer);
                }

                breakables.pollLast();

                // exit switch
                buffer.append("}").line();

                // write following node
                process(exit, buffer);

                return; // must
            }

            // =============================================================
            // Try-Catch-Finally Block
            // =============================================================
            for (int i = 0; i < tries.size(); i++) {
                buffer.write("try", "{");
            }

            // =============================================================
            // Other Block
            // =============================================================
            int outs = outgoing.size();
            int backs = backedges.size();

            if (outs == 0) {
                // end node
                if (!returnOmittable || stack.size() != 2 || stack.get(0) != Return || stack.get(1) != END) {
                    buffer.append(this);
                }
            } else if (outs == 1) {
                // do while or normal
                if (backs == 0) {
                    // normal node with follower
                    buffer.append(this);
                    process(outgoing.get(0), buffer);
                } else if (backs == 1) {
                    // do while or infinite loop
                    BackedgeGroup group = new BackedgeGroup(this);

                    if (backedges.get(0).outgoing.size() == 2) {
                        if (group.exit == null) {
                            // do while
                            writeDoWhile(buffer);
                        } else {
                            // infinit loop
                            writeInfiniteLoop1(group, buffer);
                        }
                    } else {
                        // infinit loop
                        writeInfiniteLoop1(group, buffer);
                    }
                } else {
                    // infinit loop
                    writeInfiniteLoop1(new BackedgeGroup(this), buffer);
                }
            } else if (outs == 2) {
                // while, for or if
                if (backs == 0) {
                    writeIf(buffer);
                } else if (backs == 1 && backedges.get(0).outgoing.size() == 1) {
                    writeFor(buffer);
                } else {
                    writeWhile(buffer);
                }
            }

            // =============================================================
            // Try-Catch-Finally Block
            // =============================================================
            for (TryCatchFinally block : tries) {
                buffer.write("}", "catch", "($)", "{");
                buffer.write("$", "=", Javascript.writeMethodCode(Throwable.class, "wrap", Object.class, "$"), ";").line();

                for (int i = 0; i < block.catches.size(); i++) {
                    Catch current = block.catches.get(i);
                    String variable = current.variable;

                    if (current.exception == null) {
                        // finally block
                        buffer.write(variable, "=", "$;").line();
                        process(current.node, buffer);
                    } else {
                        String condition = current.exception == Throwable.class ? "(true)"
                                : "($ instanceof " + Javascript.computeClassName(current.exception) + ")";
                        buffer.write("if", condition, "{");
                        buffer.write(variable, "=", "$;").line();
                        process(current.node, buffer);
                        buffer.write("}", "else");

                        if (i + 1 == block.catches.size()) {
                            buffer.write("", "{");
                            buffer.write("throw $;");
                            buffer.write("}");
                        } else {
                            buffer.write(" ");
                        }
                    }
                }
                buffer.write("}"); // close try statement

                Node exit = block.exit;

                if (exit != null) {
                    if (Debugger.isEnable()) {
                        buffer.comment("Start " + block.start.id + "  End " + block.end.id + "   Catcher " + block.catcher.id);
                    }
                    buffer.comment("ext block " + exit.id);
                    process(exit, buffer);
                }
            }
        }
    }

    /**
     * <p>
     * Write infinite loop structure.
     * </p>
     * 
     * @param buffer
     */
    private void writeInfiniteLoop(ScriptWriter buffer) {
        // make rewritable this node
        written = false;

        LoopStructure loop = new LoopStructure(this, this, null, null, buffer);

        // clear all backedge nodes of infinite loop
        backedges.clear();

        // re-write script fragment
        buffer.write("for", "(;;)", "{");
        breakables.add(loop);
        write(buffer);
        breakables.removeLast();
        buffer.write("}");
        loop.writeRequiredLabel();
    }

    /**
     * <p>
     * Write infinite loop structure.
     * </p>
     * 
     * @param buffer
     */
    private void writeInfiniteLoop1(BackedgeGroup group, ScriptWriter buffer) {
        LoopStructure loop = new LoopStructure(this, this, group.exit, null, buffer);

        if (group.exit != null) group.exit.currentCalls--;

        // make rewritable this node
        written = false;

        // clear all backedge nodes of infinite loop
        backedges.removeAll(group);

        // re-write script fragment
        buffer.write("for", "(;;)", "{");
        breakables.add(loop);
        write(buffer);
        breakables.removeLast();
        buffer.write("}");
        loop.writeRequiredLabel();
        process(group.exit, buffer);
    }

    /**
     * <p>
     * Write while structure.
     * </p>
     * 
     * @param buffer
     */
    private void writeWhile(ScriptWriter buffer) {
        Node[] nodes = detectProcessAndExit();

        if (nodes == null) {
            writeInfiniteLoop(buffer);
        } else {
            LoopStructure loop = new LoopStructure(this, nodes[0], nodes[1], this, buffer);

            // write script fragment
            buffer.write("while", "(" + this + ")", "{");
            breakables.add(loop);
            process(nodes[0], buffer);
            breakables.removeLast();
            buffer.write("}").line();
            loop.writeRequiredLabel();
            process(nodes[1], buffer);
        }
    }

    /**
     * <p>
     * Write do-while structure.
     * </p>
     * 
     * @param buffer
     */
    private void writeDoWhile(ScriptWriter buffer) {
        // setup condition expression node
        Node condition = backedges.remove(0);

        condition.written = true;

        Node exit;

        if (condition.outgoing.get(0) == this) {
            exit = condition.outgoing.get(1);
        } else {
            exit = condition.outgoing.get(0);
        }

        LoopStructure loop = new LoopStructure(this, outgoing.get(0), exit, condition, buffer);

        // write script fragment
        buffer.write("do", "{");
        breakables.add(loop);
        buffer.append(this);
        process(outgoing.get(0), buffer);
        breakables.removeLast();
        buffer.write("}", "while", "(" + condition + ")");
        loop.writeRequiredLabel();
        condition.process(exit, buffer);
    }

    /**
     * <p>
     * Write for structure.
     * </p>
     * 
     * @param buffer
     */
    private void writeFor(ScriptWriter buffer) {
        Node[] nodes = detectProcessAndExit();

        if (nodes == null) {
            writeInfiniteLoop(buffer);
        } else {
            // setup update expression node
            Node update = backedges.get(0);
            update.written = true;

            // At first, remove tail semicolon.
            if (update.stack.peekLast() == END) {
                update.remove(0);
            }

            // Then, replace all semicolons with comma from update expression node.
            update.stack.replaceAll(operand -> {
                return operand != END ? operand : new OperandExpression(",");
            });

            LoopStructure loop = new LoopStructure(this, nodes[0], nodes[1], update, buffer);

            // write script fragment
            buffer.write("for", "(;", this + ";", update + ")", "{");
            breakables.add(loop);
            process(nodes[0], buffer);
            breakables.removeLast();
            buffer.write("}").line();
            loop.writeRequiredLabel();
            process(nodes[1], buffer);
        }
    }

    /**
     * <p>
     * Write if structure.
     * </p>
     * 
     * @param buffer
     */
    private void writeIf(ScriptWriter buffer) {
        Debugger.print(this);
        OperandCondition condition = (OperandCondition) stack.peekLast();

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

        // check whether all following nodes can omit continue statement or not
        if (follow != null && follow.loops.isEmpty()) {
            then.continueOmittable = false;
            then.returnOmittable = false;
            if (elze != null) {
                elze.continueOmittable = false;
                elze.returnOmittable = false;
            }
        }

        // write script fragment
        buffer.write("if", "(" + this + ")", "{");
        process(then, buffer);
        if (elze != null) {
            buffer.write("}", "else", "{");
            process(elze, buffer);
        }
        buffer.write("}").line();
        process(follow, buffer);
    }

    /**
     * <p>
     * Detect a node relationship between this node and the next node.
     * </p>
     * 
     * @param next A next node to write.
     * @param buffer A script code buffer.
     */
    private void process(Node next, ScriptWriter buffer) {
        if (next != null) {
            next.currentCalls++;

            // count a number of required write call
            int requiredCalls = next.incoming.size() - next.backedges.size() + next.additionalCalls;

            LoopStructure loop = next.loops.peekLast();

            if (loop != null) {
                // continue
                if (loop.hasHeader(next) && hasDominator(loop.entrance)) {
                    if (Debugger.isEnable()) {
                        buffer.comment(id + " -> " + next.id + " continue to " + loop.entrance.id + " (" + next.currentCalls + " of " + requiredCalls + ")  " + loop);
                    }

                    String label = loop.computeLabelFor(next);

                    if (label.length() != 0 || continueOmittable == null || !continueOmittable) {
                        buffer.append("continue", label, ";").line();
                    }
                    return;
                }

                // break
                if (!loop.hasHeader(this) && loop.hasExit(next) && hasDominator(loop.entrance)) {
                    // check whether the current node connects to the exit node directly or not
                    if (loop.exit.incoming.contains(this)) {
                        if (Debugger.isEnable()) {
                            buffer.comment(id + " -> " + next.id + " break to " + loop.entrance.id + "(" + next.currentCalls + " of " + requiredCalls + ")  " + loop);
                        }
                        buffer.append("break", loop.computeLabelFor(next), ";").line();
                    }
                    return;
                }
            }

            if (Debugger.isEnable()) {
                buffer.comment(id + " -> " + next.id + " (" + next.currentCalls + " of " + requiredCalls + ")   " + (loop != null ? loop
                        : ""));
            }

            // normal process
            if (requiredCalls <= next.currentCalls) {
                Node dominator = next.getDominator();

                if (dominator == null || dominator == this || (loop != null && loop.exit == next)) {
                    // next node inherits the mode of dominator
                    if (next.continueOmittable == null) next.continueOmittable = continueOmittable;
                    if (!returnOmittable) next.returnOmittable = false;

                    // process next node
                    next.write(buffer);
                }
            }
        }
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
        OperandCondition condition = (OperandCondition) stack.peekLast();

        if (condition.then != nodes[0]) {
            condition.invert();
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

        previous.next = node;
        node.next = next;

        next.previous = node;
        node.previous = previous;

        if (previous.breaker) {
            previous.breaker = false;
            node.addExpression("break");
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
     * @version 2014/07/03 11:36:56
     */
    @SuppressWarnings("serial")
    private static class BackedgeGroup extends ArrayDeque {

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
            // startfrom base node
            if (base != null) {
                Deque<Node> candidates = new ArrayDeque(base.outgoing);

                // record accessed nodes to avoid second access
                Set<Node> recorder = new HashSet(entrance.incoming);
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
     * @version 2013/11/27 14:57:53
     */
    private class LoopStructure extends Breakable {

        /** The super dominator for all nodes in this loop structure. */
        private final Node entrance;

        /** The exit node of this loop structure if present. */
        private final Node exit;

        /** The checkpoint node (i.e. condition or update) of this loop structure if present. */
        private final Node checkpoint;

        /** The script buffer. */
        private final ScriptWriter buffer;

        /** The label insertion position. */
        private final int position;

        /** The flag. */
        private boolean requireLabel;

        /**
         * @param entrance The super dominator for all nodes in this loop structure.
         * @param first The first processing node of this loop structure.
         * @param exit The exit node of this loop structure if present.
         * @param checkpoint The checkpoint node (i.e. condition or update) of this loop structure
         *            if present.
         */
        private LoopStructure(Node entrance, Node first, Node exit, Node checkpoint, ScriptWriter buffer) {
            super(first == checkpoint ? entrance : first);

            this.entrance = entrance;
            this.exit = exit;
            this.checkpoint = checkpoint;
            this.buffer = buffer;
            this.position = buffer.length();

            // The first node must be the header of breakable structure and
            // be able to omit continue statement.
            this.first.continueOmittable = true;
            this.first.returnOmittable = false;

            // associate this structure with exit and checkpoint nodes
            loops.add(this);
            if (exit != null) exit.loops.add(this);
            if (checkpoint != null) checkpoint.loops.add(this);
        }

        /**
         * <p>
         * Compute label name for the specified node.
         * </p>
         * 
         * @param node
         * @return
         */
        private String computeLabelFor(Node node) {
            if (node.loops.contains(breakables.peekLast())) {
                return "";
            } else {
                requireLabel = true;
                return " l" + entrance.id;
            }
        }

        /**
         * <p>
         * Write loop label if necessary.
         * </p>
         */
        private void writeRequiredLabel() {
            if (requireLabel) {
                buffer.insertAt(position, "l" + entrance.id + ":");
            }
        }

        /**
         * <p>
         * Detect whether the specified node is the header of this loop or not.
         * </p>
         * 
         * @param node A target node.
         * @return A result.
         */
        private boolean hasHeader(Node node) {
            return node == entrance || node == checkpoint || node == first;
        }

        /**
         * <p>
         * Detect whether the specified node is the exit of this loop or not.
         * </p>
         * 
         * @param node A target node.
         * @return A result.
         */
        private boolean hasExit(Node node) {
            return node == exit;
        }

        private String id(Node node) {
            return node == null ? "null" : String.valueOf(node.id);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "Loop[entrance=" + id(entrance) + ", first=" + id(first) + ", exit=" + id(exit) + ", check=" + id(checkpoint) + "]";
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
        private final List<Integer> keys = new ArrayList();

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
            CopyOnWriteArrayList<Integer> values = new CopyOnWriteArrayList();

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
            CopyOnWriteArrayList<Node> nodes = new CopyOnWriteArrayList();

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
                Set<Node> record = new HashSet();
                record.addAll(defaults.outgoing);

                List<Node> nodes = new LinkedList();
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
            List<Node> disposables = new ArrayList();

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

    /**
     * @version 2013/11/24 23:28:29
     */
    static class TryCatchFinallyBlocks {

        /** The managed try-catch-finally blocks. */
        private final List<TryCatchFinally> blocks = new ArrayList();

        /**
         * <p>
         * Manage block.
         * </p>
         * 
         * @param start
         * @param end
         * @param catcher
         * @param exception
         */
        void addTryCatchFinallyBlock(Node start, Node end, Node catcher, Class exception) {
            for (TryCatchFinally block : blocks) {
                // The try-catch-finally block which indicates the same start node
                // without error class means finally block.
                // But this translator ignores finally block to use compiler duplicated codes.
                if (exception == null && block.start == start) {
                    // block.finalizer = end;
                    return;
                }

                // The try-catch-finally block which indicates the same start and end nodes
                // means multiple catches.
                if (block.start == start && block.end == end) {
                    block.addCatchBlock(exception, catcher);
                    return;
                }

                // In Java 6 and later, the old jsr and ret instructions are effectively deprecated.
                // These instructions were used to build mini-subroutines inside methods.
                //
                // The try-catch block which indicates the same catch node is copied by compiler,
                // so we must ignore it.
                if (block.catcher == catcher) {
                    return;
                }
            }
            blocks.add(new TryCatchFinally(start, end, catcher, exception));
        }

        /**
         * <p>
         * Preprocess.
         * </p>
         */
        void process() {
            // To analyze try-catch-finally statement tree, we must connect each nodes.
            // But these connections disturb the analysis of other statements (e.g. if, for).
            // So we must disconnect them immediately after analysis of try-catch-finally statement.

            // At first, do connecting only.
            for (TryCatchFinally block : blocks) {
                block.start.connect(block.catcher);

                for (Catch catchBlock : block.catches) {
                    block.start.connect(catchBlock.node);
                }
            }

            // Then, we can analyze.
            for (TryCatchFinally block : blocks) {
                // Associate node with block.
                block.start.tries.add(block);
                block.searchExit();
            }

            // At last, disconnect immediately after analysis.
            for (TryCatchFinally block : blocks) {
                block.start.disconnect(block.catcher);

                for (Catch catchBlock : block.catches) {
                    block.start.disconnect(catchBlock.node);
                }
            }

            // Purge the catch block which is inside loop structure directly.
            for (TryCatchFinally block : blocks) {
                for (Catch catchBlock : block.catches) {
                    Set<Node> recorder = new HashSet();
                    recorder.add(catchBlock.node);

                    Deque<Node> queue = new ArrayDeque();
                    queue.add(catchBlock.node);

                    while (!queue.isEmpty()) {
                        Node node = queue.pollFirst();

                        for (Node out : node.outgoing) {
                            if (out.hasDominator(catchBlock.node)) {
                                if (recorder.add(out)) {
                                    // test next node
                                    queue.add(out);
                                }
                            } else {
                                if (!out.backedges.isEmpty()) {
                                    // purge the catch block from the loop structure
                                    node.disconnect(out);
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * <p>
         * Set exception variable name.
         * </p>
         * 
         * @param current A target node.
         * @param variable A variable name.
         */
        void assignVariableName(Node current, String variable) {
            for (TryCatchFinally block : blocks) {
                for (Catch catchBlock : block.catches) {
                    if (catchBlock.node == current) {
                        catchBlock.variable = variable;
                    }
                }
            }
        }
    }

    /**
     * @version 2013/04/11 19:45:29
     */
    static class TryCatchFinally {

        /** The start node. */
        final Node start;

        /** The end node. */
        final Node end;

        /** The catcher node. */
        final Node catcher;

        /** The catch blocks. */
        final List<Catch> catches = new ArrayList();

        /** The exit node. */
        Node exit;

        /**
         * @param start
         * @param end
         * @param catcher
         * @param exception
         */
        private TryCatchFinally(Node start, Node end, Node catcher, Class exception) {
            this.start = start;
            this.end = end;
            this.catcher = catcher;
            start.disposable = end.disposable = catcher.disposable = false;

            addCatchBlock(exception, catcher);
        }

        /**
         * <p>
         * Add catch block.
         * </p>
         * 
         * @param exception
         * @param catcher
         */
        private void addCatchBlock(Class exception, Node catcher) {
            for (Catch block : catches) {
                if (block.exception == exception) {
                    return;
                }
            }
            catcher.disposable = false;
            catches.add(new Catch(exception, catcher));
        }

        /**
         * <p>
         * Search exit node of this try-catch-finally block.
         * </p>
         */
        private void searchExit() {
            Deque<Node> nodes = new ArrayDeque();
            nodes.addAll(catcher.outgoing); // catcher node must be first
            nodes.addAll(end.outgoing); // then end node

            Set<Node> recorder = new HashSet(nodes);

            while (!nodes.isEmpty()) {
                Node node = nodes.pollFirst();

                if (node.getDominator() == start) {
                    exit = node;
                    return;
                }

                for (Node n : node.outgoing) {
                    if (recorder.add(n)) {
                        nodes.add(n);
                    }
                }
            }
        }
    }

    /**
     * @version 2013/04/11 11:32:44
     */
    private static class Catch {

        /** The Throwable class, may be null for finally statmenet. */
        private final Class exception;

        /** The associated node. */
        private final Node node;

        /** The exception variable name. */
        private String variable;

        /**
         * @param exception
         * @param node
         */
        private Catch(Class exception, Node node) {
            this.exception = exception;
            this.node = node;
            this.node.additionalCalls++;
        }
    }
}
