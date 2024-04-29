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

import static org.objectweb.asm.Opcodes.*;
import static reincarnation.Node.*;
import static reincarnation.OperandCondition.*;
import static reincarnation.OperandUtil.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import kiss.I;
import kiss.Signal;
import reincarnation.Debugger.Printable;
import reincarnation.coder.Code;
import reincarnation.coder.Coder;
import reincarnation.coder.Naming;
import reincarnation.operator.AccessMode;
import reincarnation.operator.AssignOperator;
import reincarnation.operator.BinaryOperator;
import reincarnation.operator.UnaryOperator;
import reincarnation.structure.Structure;
import reincarnation.util.Classes;
import reincarnation.util.GeneratedCodes;
import reincarnation.util.MultiMap;

class JavaMethodDecompiler extends MethodVisitor implements Code, Naming, NodeManipulator {

    /** The reusable reference to record method. */
    private static final Method RecordToString;

    /** The reusable reference to record method. */
    private static final Method RecordEquals;

    /** The reusable reference to record method. */
    private static final Method RecordHashCode;

    /** The reusable reference to record method. */
    static final Method MakeConcatWithConstants;

    static {
        try {
            RecordToString = GeneratedCodes.class.getMethod("recordToString", Object.class);
            RecordEquals = GeneratedCodes.class.getMethod("recordEquals", Object.class, Object.class);
            RecordHashCode = GeneratedCodes.class.getMethod("recordHashCode", Object.class);
            MakeConcatWithConstants = GeneratedCodes.class.getMethod("makeConcatWithConstants");
        } catch (Exception e) {
            throw I.quiet(e);
        }
    }

    /** The description of {@link Debugger}. */
    private static final String DEBUGGER = Type.getType(Debuggable.class).getDescriptor();

    /**
     * Represents an expanded frame. See {@link ClassReader#EXPAND_FRAMES}.
     */
    private static final int FRAME_NEW = 400;

    /**
     * Represents a compressed frame with complete frame data.
     */
    private static final int FRAME_FULL = 401;

    /**
     * Represents a compressed frame where locals are the same as the locals in the previous frame,
     * except that additional 1-3 locals are defined, and with an empty stack.
     */
    private static final int FRAME_APPEND = 402;

    /**
     * Represents a compressed frame where locals are the same as the locals in the previous frame,
     * except that the last 1-3 locals are absent and with an empty stack.
     */
    private static final int FRAME_CHOP = 403;

    /**
     * Represents a compressed frame with exactly the same locals as the previous frame and with an
     * empty stack.
     */
    private static final int FRAME_SAME = 404;

    /**
     * Represents a compressed frame with exactly the same locals as the previous frame and with a
     * single value on the stack.
     */
    private static final int FRAME_SAME1 = 405;

    /**
     * Represents an expanded frame. See {@link ClassReader#EXPAND_FRAMES}.
     */
    private static final int FRAME = 406;

    /**
     * Represents an expanded frame. See {@link ClassReader#EXPAND_FRAMES}.
     */
    private static final int FRAME_ANY_SAME = 407;

    /**
     * Represents a jump instruction. A jump instruction is an instruction that may jump to another
     * instruction.
     */
    private static final int JUMP = 410;

    /**
     * Represents a primitive addtion instruction. IADD, LADD, FADD and DADD.
     */
    private static final int ADD = 420;

    /**
     * Represents a primitive substruction instruction. ISUB, LSUB, FSUB and DSUB.
     */
    private static final int SUB = 421;

    /**
     * Represents a constant 0 instruction. ICONST_0, LCONST_0, FCONST_0 and DCONST_0.
     */
    private static final int CONSTANT_0 = 430;

    /**
     * Represents a constant 1 instruction. ICONST_1, LCONST_1, FCONST_1 and DCONST_1.
     */
    private static final int CONSTANT_1 = 431;

    /**
     * Represents a duplicate instruction. DUP and DUP2.
     */
    private static final int DUPLICATE = 440;

    /**
     * Represents a duplicate instruction. DUP_X1 and DUP2_X2.
     */
    private static final int DUPLICATE_AWAY = 441;

    /**
     * Represents a return instruction.
     */
    private static final int RETURNS = 450;

    /**
     * Represents a increment instruction.
     */
    private static final int INCREMENT = 460;

    /**
     * Represents a compare instruction. FCMPL and FCMPG
     */
    private static final int FCMP = 470;

    /**
     * Represents a compare instruction. DCMPL and DCMPG
     */
    private static final int DCMP = 471;

    /**
     * Represents a compare instruction. DCMPL and DCMPG
     */
    private static final int CMP = 472;

    /**
     * Represents a invoke method instruction. INVOKESTATIC, INVOKEVIRTUAL etc
     */
    private static final int INVOKE = 480;

    /**
     * Represents a store instruction. ISTORE, ASTORE etc
     */
    private static final int STORE = 490;

    /**
     * Represents a store instruction. ILOAD, ALOAD etc
     */
    private static final int LOAD = 500;

    /**
     * Represents a switch instruction. TABLESWITCH, LOOKUPSWITCH etc
     */
    private static final int SWITCH = 510;

    /** The extra opcode for byte code parsing. */
    private static final int LABEL = 300;

    /** The extra opcode for byte code parsing. */
    private static final int OptionalLABEL = 301;

    /** The current processing source. */
    private final Reincarnation source;

    /** The method return type. */
    private final Class returnType;

    /** The local variable manager. */
    private final LocalVariables locals;

    /** The current processing method or constructor. */
    private final Executable executable;

    /** The pool of try-catch-finally blocks. */
    private final TryCatchFinallyManager tries = new TryCatchFinallyManager();

    private final LinkedList<OperandSwitch> switches = new LinkedList();

    /** The root statement. */
    private Structure root;

    /** The current processing node. */
    private Node current = null;

    /** The all node list for this method. */
    private LinkedList<Node> nodes = new LinkedList();

    /** The counter for the current processing node identifier. */
    private int counter = 0;

    /** The counter for the generated node identifier. */
    private int counterAlpha = 1;

    /** The counter for construction of the object initialization. */
    private int countInitialization = 0;

    /** The record of recent instructions. */
    private int[] records = new int[10];

    /** The current start position of instruction records. */
    private int recordIndex = 0;

    /** The record of recent local variable reference position. */
    private int[] localVarialbeAccess = new int[10];

    /** The record of recent local variable reference position. */
    private int localVarialbeAccessIndex = 0;

    /**
     * {@link Enum#values} produces special bytecode, so we must handle it by special way.
     */
    private Operand[] enumValues = new Operand[2];

    /** The synchronized block related nodes. */
    private Set<Node> synchronizer = new HashSet();

    /** The flag whether the next jump instruction is used for assert statement or not. */
    private boolean assertJump = false;

    /** The default debugger. */
    private final Debugger debugger = Debugger.current();

    /**
     * @param source
     * @param locals
     * @param returns
     * @param executable
     */
    JavaMethodDecompiler(Reincarnation source, LocalVariables locals, Type returns, Executable executable) {
        super(ASM9);

        this.source = source;
        this.returnType = OperandUtil.load(returns);
        this.locals = locals;
        this.executable = executable;

        debugger.startMethod(executable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Signal<Node> children() {
        return I.signal(nodes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(Coder coder) {
        if (root != null) root.write(coder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String name(String name) {
        return locals.name(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (desc.equals(DEBUGGER)) {
            debugger.enable = true;
        }
        return null; // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        return null; // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitAttribute(Attribute attr) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitCode() {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitEnd() {
        if (Classes.isAbstract(executable)) {
            return;
        }

        analyze(nodes);

        // ============================================
        // Reset debugger state
        // ============================================
        debugger.finishMethod();
    }

    /**
     * Analyze the given sequencial nodes.
     * 
     * @param nodes
     */
    private void analyze(List<Node> nodes) {
        // ============================================
        // Print debug info
        // ============================================
        debugger.printMethod();
        debugger.print(nodes);

        // Dispose all nodes which contains synchronized block.
        for (Node node : synchronizer) {
            dispose(node, true, false);
        }

        tries.disposeCopiedFinallyBlock();
        tries.disposeEmptyTryCatchFinallyBlock();

        // Separate conditional operands and dispose empty node.
        for (Node node : nodes.toArray(new Node[nodes.size()])) {
            if (node.disposable && node.stack.isEmpty()) {
                dispose(node, false, false);
            } else {
                new SequentialConditionInfo(node).split();
            }
        }

        // Search all backedge nodes.
        nodes.get(0).searchBackEdge();

        // ============================================
        // Analyze variable declaration
        // ============================================
        try (Printable diff = debugger.diff(nodes, "Analyze variable declaration")) {
            // insert variable declaration at the header of common dominator node
            locals.analyzeVariableDeclarationNode(this);
        }

        // ============================================
        // Build dominator tree
        // ============================================
        for (Node node : nodes) {
            Node dominator = node.getDominator();
            if (dominator != null) dominator.dominators.addIfAbsent(node);
        }

        // ============================================
        // Analyze all switch block
        // ============================================
        try (Printable diff = debugger.diff(nodes, "Analyze switch")) {
            new ArrayList<>(nodes).forEach(n -> n.child(OperandSwitch.class).to(op -> {
                op.analyze(this);
            }));
        }

        // ============================================
        // Analyze all try-catch-finally blocks.
        // ============================================
        try (Printable diff = debugger.diff(nodes, "Analyze try-catch-finally")) {
            tries.process();
        }

        // ============================================
        // Code Optimization
        // ============================================
        try (Printable diff = debugger.diff(nodes, "Delete tail empty return")) {
            optimizeTailEmptyReturn();
        }
        try (Printable diff = debugger.diff(nodes, "Merge immediate return")) {
            optimizeImmediateReturn(nodes);
        }
        try (Printable diff = debugger.diff(nodes, "Build shorthand assign")) {
            optimizeShorthandAssign();
        }

        // ============================================
        // Analyze node relation
        // ============================================
        try (Printable diff = debugger.diff(nodes, "Analyze nodes")) {
            root = nodes.get(0).analyze();
        }

        // ============================================
        // Build code structure
        // ============================================
        root.structurize();

        // ============================================
        // Print debug info
        // ============================================
        debugger.print(nodes);
    }

    /**
     * Remove {@link OperandReturn} if it is empty at last.
     */
    private void optimizeTailEmptyReturn() {
        Node lastNode = nodes.peekLast();
        Operand lastOperand = lastNode.peek(0);

        if (lastOperand == OperandReturn.Empty) {
            lastNode.remove(0);
        }
    }

    /**
     * Merge into one node if the specified nodes mean the immediate return (and yield).
     */
    private void optimizeImmediateReturn(List<Node> nodes) {
        // copy all nodes to avoid concurrent modification exception
        List<Node> copied = new ArrayList(nodes);

        for (Node node : copied) {
            node.uniqueOutgoing().flatMap(Node::throughEmpty).to(out -> {
                out.children(OperandReturn.class, OperandLocalVariable.class).to(local -> {
                    node.children(OperandAssign.class).flatMap(o -> o.assignedTo(local)).to(value -> {
                        node.clear().addOperand(new OperandReturn(value));

                        dispose(out, true, false);
                    });
                });

                out.children(OperandYield.class, OperandLocalVariable.class).to(local -> {
                    node.children(OperandAssign.class).flatMap(o -> o.assignedTo(local)).to(value -> {
                        node.clear().addOperand(new OperandYield(value));

                        dispose(out, true, false);
                    });
                });
            });
        }
    }

    /**
     * Shorthand the binary assign from {a = a + 1} to {a += 1}.
     */
    private void optimizeShorthandAssign() {
        children().flatMap(node -> node.children(OperandAssign.class)).to(OperandAssign::shorten);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitFieldInsn(int opcode, String ownerClassName, String name, String desc) {
        // If this field access instruction is used for assertion, we should skip it to erase
        // compiler generated extra code.
        if (opcode == GETSTATIC && name.equals("$assertionsDisabled")) {
            assertJump = true;
            return;
        }

        // recode current instruction
        record(opcode);

        // compute owner class
        Class owner = load(ownerClassName);

        // Field type
        Class type = load(Type.getType(desc));

        switch (opcode) {
        case PUTFIELD:
            Operand filed = new OperandFieldAccess(owner, name, current.remove(1));

            // Increment (decrement) of field doesn't use increment instruction, so we must
            // distinguish increment (decrement) from addition by pattern matching.
            if (match(DUP, GETFIELD, DUPLICATE_AWAY, CONSTANT_1, ADD, PUTFIELD)) {
                // The pattenr of post-increment field is like above.
                current.remove(0);

                current.addOperand(increment(filed, type, true, true));
            } else if (match(DUP, GETFIELD, DUPLICATE_AWAY, CONSTANT_1, SUB, PUTFIELD)) {
                // The pattenr of post-decrement field is like above.
                current.remove(0);

                current.addOperand(increment(filed, type, false, true));
            } else if (match(DUP, GETFIELD, CONSTANT_1, ADD, DUPLICATE_AWAY, PUTFIELD)) {
                // The pattenr of pre-increment field is like above.
                current.remove(0);

                current.addOperand(increment(filed, type, true, false));
            } else if (match(DUP, GETFIELD, CONSTANT_1, SUB, DUPLICATE_AWAY, PUTFIELD)) {
                // The pattenr of pre-decrement field is like above.
                current.remove(0);

                current.addOperand(increment(filed, type, false, false));
            } else {
                Operand value = current.remove(0).fix(type);
                OperandAssign assign = new OperandAssign(filed, AssignOperator.ASSIGN, value);

                if (match(DUPLICATE_AWAY, PUTFIELD)) {
                    // multiple assignment (i.e. this.a = this.b = 0;)
                    current.addOperand(assign.encolose());
                } else {
                    // normal assignment
                    current.addExpression(assign);
                }
            }
            break;

        case GETFIELD:
            current.addOperand(new OperandFieldAccess(owner, name, current.remove(0)).fix(type));
            break;

        case PUTSTATIC:
            /**
             * On Javac, enum switch generates the special byte code like the following
             * {@snippet :
             * // The compiler adds an array for mapping from ordinals to case labels for enumerated constants.
             * static final int[] $SwitchMap$;
             * 
             * static {
             *     // Instantiate an array for mapping. size is the number of all enumeration constants.
             *     $SwitchMap$ = new int[ENUM.values().length];
             * }
             * }
             * We must remove it completely. See {@link OperandSwitch} header document for details.
             */
            if (GeneratedCodes.isEnumSwitchName(name)) {
                current.remove(0);
                break;
            }

            // On Javac, $assertionDisabled flag field is assigined in static initializer, but that
            // field will be remove to avoid other compile error, so we must remove this assign
            // statement also.
            if (match(INVOKEVIRTUAL, JUMP, ICONST_1, GOTO, LABEL, FRAME, ICONST_0, LABEL, FRAME, PUTSTATIC)) {
                current.remove(0);
                break;
            }

            if (match(GETSTATIC, DUPLICATE, CONSTANT_1, ADD, PUTSTATIC)) {
                // The pattenr of post-increment field is like above.
                current.remove(0);

                current.addOperand(increment(current.remove(0), type, true, true));
            } else if (match(GETSTATIC, DUPLICATE, CONSTANT_1, SUB, PUTSTATIC)) {
                // The pattenr of post-decrement field is like above.
                current.remove(0);

                current.addOperand(increment(current.remove(0), type, false, true));
            } else if (match(GETSTATIC, CONSTANT_1, ADD, DUPLICATE, PUTSTATIC)) {
                current.remove(0);
                current.remove(0);

                current.addOperand(increment(accessClassField(owner, name), type, true, false));
            } else if (match(GETSTATIC, CONSTANT_1, SUB, DUPLICATE, PUTSTATIC)) {
                // The pattenr of pre-decrement field is like above.
                current.remove(0);
                current.remove(0);

                current.addOperand(increment(accessClassField(owner, name), type, false, false));
            } else {
                OperandAssign assign = new OperandAssign(accessClassField(owner, name), AssignOperator.ASSIGN, current.remove(0).fix(type));

                if (match(DUPLICATE, PUTSTATIC)) {
                    // The pattern of static field assignment in method parameter.
                    current.remove(0);
                    // current.addOperand(assign);
                } else {
                    current.addExpression(assign);
                }
            }
            break;

        case GETSTATIC:
            // In Javac, accessing static fields via instances generates extra rubbish, which should
            // be removed.
            if (match(ALOAD, POP, GETSTATIC)) {
                current.remove(0);
                current.remove(0);
            }

            // Primitive class literals are converted to special field accesses. So it is restored
            // to its original code.
            if (name.equals("TYPE")) {
                if (owner == Boolean.class) {
                    current.addOperand(new OperandClass(boolean.class));
                    break;
                } else if (owner == Integer.class) {
                    current.addOperand(new OperandClass(int.class));
                    break;
                } else if (owner == Long.class) {
                    current.addOperand(new OperandClass(long.class));
                    break;
                } else if (owner == Float.class) {
                    current.addOperand(new OperandClass(float.class));
                    break;
                } else if (owner == Double.class) {
                    current.addOperand(new OperandClass(double.class));
                    break;
                } else if (owner == Character.class) {
                    current.addOperand(new OperandClass(char.class));
                    break;
                } else if (owner == Short.class) {
                    current.addOperand(new OperandClass(short.class));
                    break;
                } else if (owner == Byte.class) {
                    current.addOperand(new OperandClass(byte.class));
                    break;
                }
            }
            current.addOperand(accessClassField(owner, name));
            break;
        }

    }

    private Operand accessClassField(Class owner, String name) {
        source.require(owner);
        return new OperandFieldAccess(owner, name, new OperandType(owner));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        if (!tries.isCatch(current) && !tries.isFinally(current)) {
            for (OperandSwitch op : switches) {
                if (op.canBeExpression(current)) {
                    try (Printable diff = debugger.diff(nodes, "Process switch expression")) {
                        op.markAsExpression();

                        List<Node> sub = new ArrayList(nodes.subList(nodes.indexOf(op.entrance), nodes.indexOf(current)));
                        analyze(sub);

                        Node created = createNodeBefore(op.entrance, true);
                        link(created, current);
                        op.entrance.transferTo(created);

                        this.nodes.removeAll(sub);
                    }
                }
            }
        }

        switch (type) {
        case F_NEW:
            record(FRAME_NEW);
            break;

        case F_FULL:
            record(FRAME_FULL);

            processTernaryOperator();
            break;

        case F_APPEND:
            record(FRAME_APPEND);
            break;

        case F_CHOP:
            record(FRAME_CHOP);
            break;

        case F_SAME:
            record(FRAME_SAME);

            if (nLocal == 0 && nStack == 0) {
                processTernaryOperator();
                merge(current.previous);
            }
            break;

        case F_SAME1:
            record(FRAME_SAME1);

            if (nLocal == 0 && nStack == 1) {
                processTernaryOperator();
            }
            break;
        }
    }

    /**
     * <p>
     * Helper method to resolve ternary operator.
     * </p>
     */
    private void processTernaryOperator() {
        Operand third = current.peek(2);

        if (third instanceof OperandCondition) {
            OperandCondition condition = (OperandCondition) third;

            Operand first = current.peek(0);
            if (first.isStatement()) {
                return;
            }

            Operand second = current.peek(1);
            if (second.isStatement()) {
                return;
            }

            Node rightNode = findNodeBy(first);
            Node leftNode = findNodeBy(second);
            Node conditionNode = findNodeBy(third);

            if (rightNode == leftNode) {
                return;
            }

            // The condition's transition node must be right node. (not left node)
            // The bytecode order is:
            // [jump to RIGHT value]
            // [label]?
            // [LEFT value]
            // [label]
            // [RIGHT value]
            boolean conditionTransition = collectSingleNodePath(condition.then).contains(rightNode);

            // In ternary operator, the left node's outgoing node must not contain right node.
            // But the outgoing nodes contains right node, this sequence will be logical expression
            // or if statement.
            boolean leftTransition = conditionNode == leftNode || !leftNode.outgoing.contains(rightNode);

            // The condition node must be dominator of the left and right nodes.
            boolean dominator = leftNode.hasDominator(conditionNode) && rightNode.hasDominator(conditionNode);

            // The left node must not be dominator of the right node except when condition and left
            // value are in same node.
            boolean values = conditionNode != leftNode && rightNode.hasDominator(leftNode);

            if (leftTransition && conditionTransition && dominator && !values) {
                try (Printable diff = debugger.diff(nodes, "Handle ternary operator")) {
                    if (first.isTrue() && second.isFalse()) {
                        current.remove(0);
                        current.remove(0);
                        conditionNode.addOperand(new OperandAmbiguousZeroOneTernary(current.remove(0)));
                    } else if (first.isFalse() && second.isTrue()) {
                        current.remove(0);
                        current.remove(0);
                        conditionNode.addOperand(new OperandAmbiguousZeroOneTernary(current.remove(0).invert()));
                    } else {
                        current.remove(0);
                        current.remove(0);
                        current.remove(0);

                        OperandCondition con = (OperandCondition) third;

                        if (con.then == rightNode) {
                            conditionNode.addOperand(new OperandTernary(con, first, second).encolose());
                        } else if (con.then == leftNode) {
                            conditionNode.addOperand(new OperandTernary(con, second, first).encolose());
                        }
                    }

                    // dispose empty nodes
                    if (rightNode.stack.isEmpty()) {
                        dispose(rightNode);
                    }

                    if (leftNode.stack.isEmpty()) {
                        dispose(leftNode);
                    }

                    // process recursively
                    processTernaryOperator();
                }
            }
        }
    }

    private Set<Node> collectSingleNodePath(Node node) {
        Set<Node> nodes = new HashSet();
        nodes.add(node);

        while (node.stack.isEmpty() && node.outgoing.size() == 1 && node.incoming.size() == 1) {
            node = node.outgoing.get(0);

            nodes.add(node);
        }
        return nodes;
    }

    /**
     * <p>
     * Search the node which has the specified operand.
     * </p>
     * 
     * @param operand
     * @return
     */
    private Node findNodeBy(Operand operand) {
        for (int i = nodes.size() - 1; 0 <= i; i--) {
            Node node = nodes.get(i);

            if (node.has(operand)) {
                return node;
            }
        }
        throw new IllegalArgumentException("The operand [" + operand + "] is not found in the current context.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitIincInsn(int position, int increment) {
        // recode current instruction
        record(INCREMENT);

        // retrieve the local variable name
        Operand variable = locals.find(position, ILOAD, current);

        if (increment == 1) {
            // increment
            if (match(ILOAD, INCREMENT) && current.peek(0) instanceof OperandUnary == false) {
                // post increment
                current.addOperand(new OperandUnary(current.remove(0), UnaryOperator.POST_INCREMENT));
            } else {
                // pre increment
                current.addOperand(new OperandUnary(variable, UnaryOperator.PRE_INCREMENT));
            }
        } else if (increment == -1) {
            // decrement
            if (match(ILOAD, INCREMENT) && current.peek(0) instanceof OperandUnary == false) {
                // post decrement
                current.addOperand(new OperandUnary(current.remove(0), UnaryOperator.POST_DECREMENT));
            } else {
                // pre decrement
                current.addOperand(new OperandUnary(variable, UnaryOperator.PRE_DECREMENT));
            }
        } else {
            current.addOperand(new OperandAssign(variable, AssignOperator.PLUS, new OperandNumber(increment)));

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitInsn(int opcode) {
        // recode current instruction
        record(opcode);

        switch (opcode) {
        case DUP:
            if (!match(NEW, DUP) && !match(NEW, DUP2)) {
                // mark as duplicated operand
                current.peek(0).duplicated = true;
            }
            break;

        case DUP2:
            if (!match(NEW, DUP) && !match(NEW, DUP2)) {
                // mark as duplicated operand
                Operand first = current.peek(0);
                first.duplicated = true;

                if (!first.isLarge()) {
                    current.peek(1).duplicated = true;
                }
            }
            break;

        case DUP_X1:
            // These instructions are used for field increment mainly, see visitFieldInsn(PUTFIELD).
            // Skip this instruction to simplify compiler.
            break;

        case DUP_X2:
            // mark as duplicated operand
            current.peek(0).duplicated = true;
            break;

        case DUP2_X1:
            // These instructions are used for field increment mainly, see visitFieldInsn(PUTFIELD).
            // Skip this instruction to simplify compiler.
            break;

        case DUP2_X2:
            // mark as duplicated operand
            current.peek(0).duplicated = true;
            break;

        case POP:
            // When the compiler compiles the code including "instance method reference", it
            // generates the byte code expressed in following ASM codes.
            //
            // In Javac
            // visitInsn(DUP);
            // visitMethodInsn(INVOKESTATIC, "java/util/Objects","requireNonNull",SIGNATURE, false);
            // visitInsn(POP);
            //
            // In ECJ
            // visitInsn(DUP);
            // visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object","getClass","()Ljava/lang/Class;");
            // visitInsn(POP);
            //
            // I guess that it is NULL checker and initialize code for the class to which the lambda
            // method belongs. It is a completely unnecessary code, so we should remove them
            // unconditionally.
            if (match(DUP, INVOKESTATIC, POP) || match(DUP, INVOKEVIRTUAL, POP)) {
                current.remove(0);
                break;
            }

            // Accessing the interface static field from the instance, compiler generates the
            // byte code expressed in following ASM codes.
            //
            // visitFieldInsn(GETFIELD, "ConcreateClass", "FieldName", "signature");
            // visitTypeInsn(CHECKCAST, "FieldType");
            // visitInsn(POP);
            //
            // In Javascript runtime, it is a completely unnecessary code,
            // so we should delete them unconditionally.
            if (match(GETFIELD, CHECKCAST, POP)) {
                current.remove(0);
                break;
            }

        case POP2:
            // One sequence of expressions was finished, so we must write out one remaining
            // operand. (e.g. Method invocation which returns some operands but it is not used ever)
            current.addExpression(current.remove(0));
            break;

        // 0
        case ICONST_0:
            current.addOperand(0);
            break;
        case LCONST_0:
            current.addOperand(0L);
            break;
        case FCONST_0:
            current.addOperand(0F);
            break;
        case DCONST_0:
            current.addOperand(0D);
            break;

        // 1
        case ICONST_1:
            current.addOperand(1);
            break;
        case LCONST_1:
            current.addOperand(1L);
            break;
        case FCONST_1:
            current.addOperand(1F);
            break;
        case DCONST_1:
            current.addOperand(1D);
            break;

        // 2
        case ICONST_2:
            current.addOperand(2);
            break;
        case FCONST_2:
            current.addOperand(2F);
            break;

        // 3
        case ICONST_3:
            current.addOperand(3);
            break;

        // 4
        case ICONST_4:
            current.addOperand(4);
            break;

        // 5
        case ICONST_5:
            current.addOperand(5);
            break;

        // -1
        case ICONST_M1:
            current.addOperand(-1);
            break;

        // null
        case ACONST_NULL:
            current.addOperand(Operand.Null); // not "null"
            break;

        // + operand
        case IADD:
        case FADD:
        case DADD:
        case LADD:
            current.join(BinaryOperator.PLUS, opcode).enclose();
            break;

        // - operand
        case ISUB:
        case FSUB:
        case DSUB:
        case LSUB:
            current.join(BinaryOperator.MINUS, opcode).enclose();
            break;

        // * operand
        case IMUL:
        case FMUL:
        case DMUL:
        case LMUL:
            current.join(BinaryOperator.MULTIPLY, opcode);
            break;

        // / operand
        case IDIV:
        case FDIV:
        case DDIV:
        case LDIV:
            current.join(BinaryOperator.DIVIDE, opcode);
            break;

        // % operand
        case IREM:
        case FREM:
        case DREM:
        case LREM:
            current.join(BinaryOperator.REMAINDER, opcode);
            break;

        // & operand
        case IAND:
        case LAND:
            current.join(BinaryOperator.BINARY_AND, opcode).enclose();
            break;

        // | operand
        case IOR:
        case LOR:
            current.join(BinaryOperator.BINARY_OR, opcode).enclose();
            break;

        // ^ operand
        case IXOR:
        case LXOR:
            current.join(BinaryOperator.XOR, opcode);
            break;

        // << operand
        case ISHL:
        case LSHL:
            current.join(BinaryOperator.LEFT_SHIFT, opcode).enclose();
            break;

        // >> operand
        case ISHR:
        case LSHR:
            current.join(BinaryOperator.RIGHT_SHIFT, opcode).enclose();
            break;

        // >>> operand
        case IUSHR:
        case LUSHR:
            current.join(BinaryOperator.UNSIGNED_RIGHT_SHIFT, opcode).enclose();
            break;

        // negative operand
        case INEG:
        case FNEG:
        case DNEG:
        case LNEG:;
            current.addOperand(new OperandUnary(current.remove(0), UnaryOperator.MINUS));
            break;

        case RETURN:
            current.addExpression(OperandReturn.Empty);
            current.destination = Termination;
            break;

        case IRETURN:
            // // Java bytecode represents boolean value as integer value (0 or 1).
            // if (match(JUMP, ICONST_0, IRETURN, LABEL, FRAME_ANY_SAME, ICONST_1, IRETURN) ||
            // match(JUMP, LABEL, FRAME, ICONST_0, IRETURN, LABEL, FRAME_ANY_SAME, ICONST_1,
            // IRETURN)) {
            // // Current operands is like the following, so we must remove four operands to
            // // represent boolean value.
            // //
            // // JUMP [Condition] return false [Expression] 1 [Number]
            // current.remove(0); // remove "1"
            // current.remove(0); // remove "return false"
            //
            // // remove empty node if needed
            // if (current.previous.stack.isEmpty()) dispose(current.previous);
            // } else if (match(JUMP, ICONST_1, IRETURN, LABEL, FRAME_ANY_SAME, ICONST_0, IRETURN)
            // || match(JUMP, LABEL, FRAME, ICONST_1, IRETURN, LABEL, FRAME_ANY_SAME, ICONST_0,
            // IRETURN)) {
            // // Current operands is like the following, so we must remove four operands to
            // // represent boolean value.
            // //
            // // JUMP [Condition] return true [Expression] 0 [Number]
            // current.remove(0); // remove "0"
            // current.remove(0); // remove "return true"
            //
            // // remove empty node if needed
            // if (current.previous.stack.isEmpty()) dispose(current.previous);
            //
            // // invert the latest condition
            // current.peek(0).invert();
            // }
            Operand operand = current.remove(0);
            operand.fix(returnType);

            current.addOperand(new OperandReturn(operand).fix(returnType));
            current.destination = Termination;
            break;

        case ARETURN:
        case LRETURN:
        case FRETURN:
        case DRETURN:
            current.addOperand(new OperandReturn(current.remove(match(DUP, JUMP, ARETURN) ? 1 : 0).fix(returnType)));
            current.destination = Termination;
            break;

        // write array value by index
        case AALOAD:
        case IALOAD:
        case BALOAD:
        case LALOAD:
        case FALOAD:
        case DALOAD:
        case CALOAD:
        case SALOAD:
            current.addOperand(new OperandArrayAccess(current.remove(1), current.remove(0)));
            break;

        // read array value by index
        case IASTORE:
            /**
             * On Javac, enum switch generates the special byte code like the following
             * {@snippet :
             * // The compiler adds an array for mapping from ordinals to case labels for enumerated constants.
             * static final int[] $SwitchMap$;
             * 
             * static {
             *     // The data for mapping are numbered from 1 in the order indicated by the labels in the switch statement.
             *     try {
             *         $SwitchMap$[ENUM.A.ordinal()] = 1;
             *     } catch (NoSuchFieldError e) {
             *         // ignore error
             *     }
             *     try {
             *         $SwitchMap$[ENUM.B.ordinal()] = 2;
             *     } catch (NoSuchFieldError e) {
             *         // ignore error
             *     }
             *     try {
             *         $SwitchMap$[ENUM.C.ordinal()] = 3;
             *     } catch (NoSuchFieldError e) {
             *         // ignore error
             *     }
             * }
             * }
             * We must remove it completely. See {@link OperandSwitch} header document for details.
             */
            if (current.peek(2) instanceof OperandFieldAccess field && GeneratedCodes.isEnumSwitchField(field.field)) {
                current.remove(0);
                current.remove(0);
                current.remove(0);
                break;
            }

        case BASTORE:
        case LASTORE:
        case FASTORE:
        case DASTORE:
        case CASTORE:
        case SASTORE:
        case AASTORE:
            Operand contextMaybeArray = current.remove(2);
            Operand value = current.remove(0, false);

            if (opcode == CASTORE) {
                // convert assign value (int -> char)
                value = value.fix(char.class);
            }

            if (contextMaybeArray instanceof OperandArray) {
                // initialization of syntax sugar
                ((OperandArray) contextMaybeArray).set(current.remove(0), value);
            } else {
                // write by index
                OperandArrayAccess array = new OperandArrayAccess(contextMaybeArray, current.remove(0));
                OperandAssign assign = new OperandAssign(array, AssignOperator.ASSIGN, value);

                if (!value.duplicated) {
                    current.addExpression(assign);
                } else {
                    value.duplicated = false;

                    // duplicate pointer
                    current.addOperand(assign.encolose());
                }
            }
            break;

        // read array length
        case ARRAYLENGTH:
            if (match(ALOAD, ASTORE, ALOAD, ARRAYLENGTH)) {
                Operand prev = current.peek(1);
                Operand next = current.peek(0);
                if (prev instanceof OperandAssign assign && next instanceof OperandLocalVariable local) {
                    assign.assignedTo(local).as(OperandLocalVariable.class).to(original -> {
                        locals.register(local.index, original);
                    });
                }
            }
            current.addOperand(new OperandArrayLength(current.remove(0)));
            break;

        // throw
        case ATHROW:
            if (match(INVOKESPECIAL, ATHROW)) {
                Operand o = current.peek(0);
                if (o instanceof OperandConstructorCall con && con.type.is(AssertionError.class)) {
                    debugger.print(nodes);
                    current.remove(0); // remove new AssertionError()
                    merge(current);
                    current.addOperand(new OperandAssert(current.remove(0), con.children().to()));
                    break;
                }
            }

            current.addOperand(new OperandThrow(current.remove(0)));
            current.destination = Termination;
            break;

        // numerical comparison operator for long, float and double primitives
        case LCMP:
        case DCMPL:
        case DCMPG:
        case FCMPL:
        case FCMPG:
            break; // ignore, because we should handle it in visitJumpInsn method

        case MONITORENTER:
            current.remove(0);
            synchronizer.add(current);
            break;

        case MONITOREXIT:
            synchronizer.add(current);
            break;

        case I2C:
            // cast int to char
            current.addOperand(new OperandCast(current.remove(0).fix(int.class), char.class));
            break;

        case L2I:
            // cast long to int
            current.addOperand(new OperandCast(current.remove(0).fix(long.class), int.class));
            break;

        case I2L:
            // cast int to long
            current.addOperand(new OperandCast(current.remove(0).fix(int.class), long.class));
            break;

        case L2D:
            // cast long to double
            current.addOperand(new OperandCast(current.remove(0).fix(long.class), double.class));
            break;

        case D2L:
            // cast double to long
            current.addOperand(new OperandCast(current.remove(0).fix(double.class), long.class));
            break;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitIntInsn(int opcode, int operand) {
        // recode current instruction
        record(opcode);

        // The opcode of the instruction to be visited. This opcode is either BIPUSH, SIPUSH or
        // NEWARRAY.
        switch (opcode) {
        // When opcode is BIPUSH, operand value should be between Byte.MIN_VALUE and
        // Byte.MAX_VALUE.
        case BIPUSH:
            current.addOperand(operand);
            break;

        // When opcode is SIPUSH, operand value should be between Short.MIN_VALUE and
        // Short.MAX_VALUE.
        case SIPUSH:
            current.addOperand(operand);
            break;

        // When opcode is NEWARRAY, operand value should be one of Opcodes.T_BOOLEAN,
        // Opcodes.T_CHAR, Opcodes.T_FLOAT, Opcodes.T_DOUBLE, Opcodes.T_BYTE, Opcodes.T_SHORT,
        // Opcodes.T_INT or Opcodes.T_LONG.
        case NEWARRAY:
            Class type = null;

            switch (operand) {
            case T_INT:
                type = int[].class;
                break;

            case T_LONG:
                type = long[].class;
                break;

            case T_FLOAT:
                type = float[].class;
                break;

            case T_DOUBLE:
                type = double[].class;
                break;

            case T_BOOLEAN:
                type = boolean[].class;
                break;

            case T_BYTE:
                type = byte[].class;
                break;

            case T_CHAR:
                type = char[].class;
                break;

            case T_SHORT:
                type = short[].class;
                break;

            default:
                // If this exception will be thrown, it is bug of this program. So we must rethrow
                // the wrapped error in here.
                throw new Error();
            }

            current.addOperand(new OperandArray(current.remove(0), type));
            break;
        }
    }

    /**
     * {@inheritDoc} bootstrapMethodHandle
     */
    @Override
    public void visitInvokeDynamicInsn(String name, String description, Handle bootstrap, Object... bootstrapMethodArguments) {
        // recode current instruction
        record(INVOKEDYNAMIC);

        if (bootstrap.getOwner().equals("java/lang/runtime/ObjectMethods") && bootstrap.getName().equals("bootstrap")) {
            // Bootstrap methods for state-driven implementations of core methods, including
            // Object.equals(Object), Object.hashCode(), and Object.toString(). These methods may be
            // used, for example, by Java compiler implementations to implement the bodies of Object
            // methods for record classes.
            switch (name) {
            case "toString" -> current.addOperand(OperandUtil.convertMethod(RecordToString, current.remove(0)));
            case "hashCode" -> current.addOperand(OperandUtil.convertMethod(RecordHashCode, current.remove(0)));
            case "equals" -> current.addOperand(OperandUtil.convertMethod(RecordEquals, current.remove(0), current.remove(0)));
            default -> throw new IllegalArgumentException("Unexpected value: " + name);
            }
        } else if (bootstrap.getOwner().equals("java/lang/invoke/StringConcatFactory") && bootstrap.getName()
                .equals("makeConcatWithConstants")) {
            current.addOperand(OperandUtil.convertMethod(MakeConcatWithConstants, parseConcatString(bootstrapMethodArguments)));
        } else {
            Handle handle = (Handle) bootstrapMethodArguments[1];
            Type callerType = Type.getMethodType(description);

            // detect functional interface
            Class interfaceClass = load(callerType.getReturnType());

            try {
                // detect lambda method
                Class lambdaClass = load(handle.getOwner());
                String lambdaName = handle.getName();
                Class[] lambdaParameterTypes = load(Type.getArgumentTypes(handle.getDesc()));

                source.require(lambdaClass);

                switch (handle.getTag()) {
                case H_INVOKESTATIC:
                case H_INVOKEVIRTUAL:
                case H_INVOKEINTERFACE:
                case H_INVOKESPECIAL:
                    Method lambdaMethod = lambdaClass.getDeclaredMethod(lambdaName, lambdaParameterTypes);
                    if (lambdaMethod.isSynthetic()) {
                        // ==================================
                        // Lambda
                        // ==================================
                        // build parameter from local environment
                        int consumableStackSize = callerType.getArgumentTypes().length;
                        List<Operand> params = new ArrayList();

                        for (int i = consumableStackSize - 1; 0 <= i; i--) {
                            Operand removed = current.remove(i);
                            if (removed instanceof OperandLocalVariable local && local.index == 0) {
                                // ignore "this" variable
                            } else {
                                params.add(removed);
                            }
                        }
                        current.addOperand(new OperandLambda(interfaceClass, lambdaMethod, params, source));
                    } else {
                        // ==================================
                        // Method Reference
                        // ==================================
                        SpecializedType specialized = new SpecializedType(interfaceClass)
                                .specializeByReturnAndParamTypes((Type) bootstrapMethodArguments[2])
                                .specializeBySAM(lambdaMethod);

                        boolean needInstance = callerType.getArgumentTypes().length != 0;
                        current.addOperand(new OperandMethodReference(lambdaMethod, needInstance ? current.remove(0) : null)
                                .fix(specialized));
                    }
                    break;

                case H_NEWINVOKESPECIAL:
                    // ==================================
                    // Constructor Reference in Javac
                    // ==================================
                    Constructor lambdaConstructor = lambdaClass.getDeclaredConstructor(lambdaParameterTypes);
                    SpecializedType specialized = new SpecializedType(interfaceClass).specializeByReturnType(lambdaClass)
                            .specializeByParamTypes(lambdaParameterTypes);

                    current.addOperand(new OperandConstructorReference(lambdaConstructor).fix(specialized));
                    break;

                default:
                    // If this exception will be thrown, it is bug of this program. So we
                    // must
                    // rethrow
                    // the wrapped error in here.
                    throw new Error();
                }
            } catch (Exception e) {
                throw I.quiet(e);
            }
        }
    }

    private Object[] parseConcatString(Object... recipes) {
        String recipe = (String) recipes[0];

        int escaper = recipes.length - 1;
        int parsed = recipe.length();
        LinkedList params = new LinkedList();

        for (int i = parsed - 1; 0 <= i; i--) {
            char c = recipe.charAt(i);
            switch (c) {
            case '\u0001':
                if (parsed != i + 1) {
                    params.addFirst(recipe.substring(i + 1, parsed));
                }
                params.addFirst(current.remove(0));
                parsed = i;
                break;

            case '\u0002':
                if (parsed != i + 1) {
                    params.addFirst(recipe.substring(i + 1, parsed));
                }
                params.addFirst(recipes[escaper--]);
                parsed = i;
                break;

            default: // skip others
                break;
            }
        }

        // add remaining if needed
        if (0 < parsed) {
            params.addFirst(recipe.substring(0, parsed));
        }

        return params.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitJumpInsn(int opcode, Label label) {
        // If this jump instruction is used for assertion, we should skip it to erase compiler
        // generated extra code.
        if (assertJump) {
            assertJump = false; // reset flag
            return;
        }

        // Consecutive jump instructions, such as ternary operators and if statements that break
        // into loop structures, can be stored in separate nodes to normalize and simplify later
        // processing.
        if (match(JUMP)) {
            visitLabel(new Label());
        }

        // recode current instruction
        record(opcode);

        // search node
        Node node = getNode(label);

        switch (opcode) {
        case GOTO:
            current.disposable = false;
            current.connect(node);
            current.destination = node;

            if (match(JUMP, LABEL, GOTO) && current.previous.outgoing.size() == 3) {
                dispose(current);
            }
            return;

        case IFEQ: // == 0
            if (match(LCMP, JUMP) || match(DCMP, JUMP) || match(FCMP, JUMP)) {
                // for long, float and double
                current.condition(current.remove(1), EQ, current.remove(0), node);
            } else {
                // others
                Operand left = current.remove(0);
                current.condition(left, EQ, new OperandNumber(0).fix(left.type.v), node);
            }
            break;
        case IFNE: // != 0
            if (match(LCMP, JUMP) || match(DCMP, JUMP) || match(FCMP, JUMP)) {
                // for long, float and double
                current.condition(current.remove(1), NE, current.remove(0), node);
            } else {
                // others
                Operand left = current.remove(0);
                current.condition(left, NE, new OperandNumber(0).fix(left.type.v), node);
            }
            break;

        case IFGE: // => 0
            if (match(LCMP, JUMP) || match(DCMP, JUMP) || match(FCMP, JUMP)) {
                // for long, float and double
                current.condition(current.remove(1), GE, current.remove(0), node);
            } else {
                // others
                current.condition(current.remove(0), GE, new OperandNumber(0), node);
            }
            break;

        case IFGT: // > 0
            if (match(LCMP, JUMP) || match(DCMP, JUMP) || match(FCMP, JUMP)) {
                // for long, float and double
                current.condition(current.remove(1), GT, current.remove(0), node);
            } else {
                // others
                current.condition(current.remove(0), GT, new OperandNumber(0), node);
            }
            break;

        case IFLE: // <= 0
            if (match(LCMP, JUMP) || match(DCMP, JUMP) || match(FCMP, JUMP)) {
                // for long, float and double
                current.condition(current.remove(1), LE, current.remove(0), node);
            } else {
                // others
                current.condition(current.remove(0), LE, new OperandNumber(0), node);
            }
            break;

        case IFLT: // < 0
            if (match(LCMP, JUMP) || match(DCMP, JUMP) || match(FCMP, JUMP)) {
                // for long, float and double
                current.condition(current.remove(1), LT, current.remove(0), node);
            } else {
                // others
                current.condition(current.remove(0), LT, new OperandNumber(0), node);
            }
            break;

        case IFNULL: // object == null
            current.condition(current.remove(0), EQ, Operand.Null, node);
            break;

        case IFNONNULL: // object != null
            current.condition(current.remove(0), NE, Operand.Null, node);
            break;

        // ==
        case IF_ACMPEQ:
        case IF_ICMPEQ:
            current.condition(current.remove(1), EQ, current.remove(0), node);
            break;

        // !=
        case IF_ACMPNE:
        case IF_ICMPNE:
            current.condition(current.remove(1), NE, current.remove(0), node);
            break;

        case IF_ICMPGE: // int => int
            current.condition(current.remove(1), GE, current.remove(0), node);
            break;

        case IF_ICMPGT: // int > int
            current.condition(current.remove(1), GT, current.remove(0), node);
            break;

        case IF_ICMPLE: // int <= int
            current.condition(current.remove(1), LE, current.remove(0), node);
            break;

        case IF_ICMPLT: // int < int
            current.condition(current.remove(1), LT, current.remove(0), node);
            break;
        }
        current.connect(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitLabel(Label label) {
        // recode current instruction
        record(LABEL);

        // build next node
        Node next = getNode(label);

        if (current != null && current.destination == null) {
            current.connect(next);
            current.destination = next;
        }
        current = link(current, next);

        // store the node in appearing order
        nodes.add(current);

        if (1 < nodes.size()) {
            merge(current.previous);
        }
    }

    /**
     * This parameter must be a non null Integer, a Float, a Long, a Double a String (or a Type for
     * .class constants, for classes whose version is 49.0 or more).
     */
    @Override
    public void visitLdcInsn(Object constant) {
        record(LDC);

        if (constant instanceof String) {
            current.stack.add(new OperandString((String) constant));
        } else if (constant instanceof Type) {
            Class clazz = OperandUtil.load((Type) constant);
            source.require(clazz);
            current.addOperand(new OperandClass(clazz));
        } else {
            current.addOperand(constant);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitLineNumber(int line, Label start) {
        getNode(start).lineNumber = line;

        debugger.recordLine(line);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        // If no debugging options are specified, this information is not added. So only the name
        // will be used if available.
        locals.registerName(index, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitMethodInsn(int opcode, String className, String method, String desc, boolean access) {
        // recode current instruction
        record(opcode);

        // compute owner class
        Class owner = load(className);
        source.require(owner);

        // compute parameter types
        Type[] types = Type.getArgumentTypes(desc);
        Class[] parameters = new Class[types.length];

        for (int i = 0; i < types.length; i++) {
            parameters[i] = load(types[i]);
        }

        // copy latest operands for this method invocation
        ArrayList<Operand> contexts = new ArrayList(parameters.length + 1);

        for (int i = 0; i < parameters.length; i++) {
            contexts.add(0, current.remove(0));
        }

        // write mode
        Class returnType = load(Type.getReturnType(desc));
        boolean immediately = returnType == void.class;

        switch (opcode) {
        // Invoke instance method; special handling for superclass constructor, private method,
        // and instance initialization method invocations
        case INVOKESPECIAL:
            // Analyze method argument
            if (!method.equals("<init>")) {
                // push "this" operand
                contexts.add(0, current.remove(0));

                if (owner == source.clazz) {
                    // private method invocation
                    current.addOperand(new OperandMethodCall(AccessMode.THIS, owner, method, parameters, contexts.remove(0), contexts));
                } else {
                    // super method invocation
                    current.addOperand(new OperandMethodCall(AccessMode.SUPER, owner, method, parameters, contexts.remove(0), contexts));
                }
            } else {
                // remove type operand
                current.remove(0);

                // constructor
                if (countInitialization != 0) {
                    // instance initialization method invocation
                    current.addOperand(new OperandConstructorCall(null, owner, parameters, contexts));

                    countInitialization--;

                    // don't write
                    immediately = false;
                } else {
                    String kind = owner == source.clazz ? "this" : "super";
                    current.addOperand(new OperandConstructorCall(kind, owner, parameters, contexts));
                }
            }
            break;

        case INVOKEVIRTUAL: // method call
        case INVOKEINTERFACE: // interface method call
            current.addOperand(new OperandMethodCall(AccessMode.THIS, owner, method, parameters, current.remove(0), contexts));
            break;

        case INVOKESTATIC: // static method call
            // Non-private static method which is called from child class have parent
            // class signature.
            while (!hasStaticMethod(owner, method, parameters)) {
                owner = owner.getSuperclass();
            }

            // translate
            current.addOperand(new OperandMethodCall(AccessMode.THIS, owner, method, parameters, new OperandType(owner), contexts));
            break;
        }

        // if this method (not constructor and not static initializer) return void, we must
        // write out the expression of method invocation immediatly.
        if (immediately && current.stack.size() != 0) {
            current.addExpression(current.remove(0));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitMultiANewArrayInsn(String desc, int dimension) {
        List<Operand> dimensions = new ArrayList();

        for (int i = 0; i <= dimension - 1; i++) {
            dimensions.add(0, current.remove(0).fix(int.class));
        }
        current.addOperand(new OperandArray(dimensions, load(desc)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitParameter(String name, int access) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitTableSwitchInsn(int min, int max, Label defaults, Label... labels) {
        // recode current instruction
        record(TABLESWITCH);

        int[] keys = new int[max - min + 1];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = min + i;
        }
        visitSwitchInsn(defaults, keys, labels);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitLookupSwitchInsn(Label defaultLabel, int[] keys, Label[] labels) {
        // recode current instruction
        record(LOOKUPSWITCH);

        visitSwitchInsn(defaultLabel, keys, labels);
    }

    /**
     * General switch instruction.
     * 
     * @param defaultLabel
     * @param keys
     * @param labels
     */
    private void visitSwitchInsn(Label defaultLabel, int[] keys, Label[] labels) {
        if (tries.isTry(current)) {
            current = createNodeAfter(current, true, true);
        }

        List<Node> caseNodes = I.signal(labels).map(this::getNode).toList();
        Node defaultNode = getNode(defaultLabel);

        // connect from entrance to each cases and default
        I.signal(caseNodes).startWith(defaultNode).to(current::connect);

        Operand switchCondition = current.remove(0);
        OperandSwitch operand = new OperandSwitch(current, switchCondition, keys, caseNodes, defaultNode);

        // for ECJ
        if (match(DUP, ASTORE, INVOKEVIRTUAL, SWITCH)) {
            operand.convertToStringSwitch();
            operand.caseConverter = cases -> {
                MultiMap<Node, Object> renewed = new MultiMap(true);
                cases.keys().to(oldCaseBlock -> {
                    OperandCondition condition = oldCaseBlock.child(OperandCondition.class).exact();

                    // retrieve the actual matching text
                    OperandString text = oldCaseBlock.children(OperandCondition.class, OperandMethodCall.class, OperandString.class)
                            .to()
                            .exact();

                    renewed.put(condition.then, text);

                    dispose(condition.elze, true, true);
                    dispose(oldCaseBlock, true, true);
                });
                return renewed;
            };
            locals.unregister(latestLocalVariableAccess());
        }

        // for Javac
        if (match(ALOAD, ASTORE, ICONST_M1, ISTORE, ALOAD, INVOKEVIRTUAL, SWITCH)) {
            if (current.peek(1) instanceof OperandAssign assign) {
                stringSwitchForJavac = operand;
                operand.convertToStringSwitch();

                OperandLocalVariable right = assign.assign().as(OperandLocalVariable.class).to().exact();
                OperandLocalVariable left = (OperandLocalVariable) assign.left;
                right.original.observing().to(left.original::set);
            }
        } else if (stringSwitchForJavac != null) {
            stringSwitchForJavac.caseConverter = cases -> {
                AtomicInteger caseIndex = new AtomicInteger();
                MultiMap<Node, Object> renewed = new MultiMap(true);
                cases.keys().to(old -> {
                    // retrieve the actual matching text
                    OperandString text = old.children(OperandCondition.class, OperandMethodCall.class, OperandString.class).to().exact();

                    renewed.put(caseNodes.get(caseIndex.getAndIncrement()), text);

                    // dispose(condition.elze, true, true);
                    // dispose(oldCaseBlock, true, true);
                });
                return renewed;
            };
            stringSwitchForJavac.defaultConverter = old -> defaultNode;
            stringSwitchForJavac = null;
        }

        current.addOperand(operand);
        switches.addFirst(operand);
    }

    private OperandSwitch stringSwitchForJavac;

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        tries.addBlock(getNode(start), getNode(end), getNode(handler), load(type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitTypeInsn(int opcode, String type) {
        // recode current instruction
        record(opcode);

        Class clazz = load(type);

        switch (opcode) {
        case NEW:
            countInitialization++;

            current.addOperand(new OperandType(load(type)));
            break;

        case ANEWARRAY:
            if (type.charAt(0) == '[') {
                type = "[" + type;
            } else {
                type = "[L" + type + ";";
            }
            current.addOperand(new OperandArray(current.remove(0), load(type)));
            break;

        case CHECKCAST:
            current.peek(0).fix(clazz);
            current.addOperand(new OperandCast(current.remove(0), clazz));
            break;

        case INSTANCEOF:
            if (match(DUP, ASTORE, INSTANCEOF)) {
                // In ECJ compiler, the instanceof operator with pattern matching generates code
                // that assigns the target variable to a temporary variable. So we optimize the code
                // to remove that variable and use the original variable.
                //
                // ================= Sample Bytecode =================
                // methodVisitor.visitInsn(DUP);
                // methodVisitor.visitVarInsn(ASTORE, 3);
                // methodVisitor.visitTypeInsn(INSTANCEOF, "java/lang/String");
                Operand extra = current.remove(1);
                current.remove(0).as(OperandAssign.class).exact().assignedTo(extra).to(current::addOperand);
            } else if (match(ASTORE, ALOAD, INSTANCEOF)) {
                // In Javac compiler, the instanceof operator with pattern matching generates code
                // that assigns the target variable to a temporary variable. So we optimize the code
                // to remove that variable and use the original variable.
                //
                // ================= Sample Bytecode =================
                // methodVisitor.visitVarInsn(ASTORE, 3);
                // methodVisitor.visitVarInsn(ALOAD, 3);
                // methodVisitor.visitTypeInsn(INSTANCEOF, "java/lang/String");
                Operand extra = current.remove(0);
                current.remove(0).as(OperandAssign.class).exact().assignedTo(extra).to(current::addOperand);
            }

            source.require(clazz);
            current.addOperand(new OperandInstanceOf(current.remove(0), clazz));
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitVarInsn(int opcode, int position) {
        // recode current instruction
        record(opcode);
        recordLocalVariableAccess(position);

        // Array#length for enhanced for-loop produces special bytecode
        if (match(ALOAD, DUP, ASTORE)) {
            locals.register(position, (OperandLocalVariable) current.remove(0));
            return;
        }

        // retrieve local variable name
        OperandLocalVariable variable = locals
                .find(position, opcode, match(FRAME_SAME1, ASTORE) || match(FRAME_FULL, ASTORE) ? null : current);

        switch (opcode) {
        case ILOAD:
        case FLOAD:
        case LLOAD:
        case DLOAD:
            if (match(INCREMENT, ILOAD)) {
                Operand prev = current.peek(0);

                if (prev instanceof OperandUnary unary) {
                    if (unary.operator == UnaryOperator.PRE_INCREMENT || unary.operator == UnaryOperator.PRE_DECREMENT) {
                        if (unary.value.equals(variable)) {
                            break;
                        }
                    }
                } else if (prev instanceof OperandAssign assign) {
                    if (assign.left.equals(variable)) {
                        assign.encolose();
                        break;
                    }
                }
            }

        case ALOAD:
            current.addOperand(variable);
            break;

        case ASTORE:
            // instanceof with cast produces special bytecode, so we must handle it by special way.
            // For ECJ - DUP STORE INSTANCEOF ....
            // For Javac - ASTORE ALOAD INSTANCEOF ...
            if (match(DUP, ASTORE, INSTANCEOF, IFEQ, ALOAD, CHECKCAST, ASTORE) || match(ASTORE, ALOAD, INSTANCEOF, IFEQ, ALOAD, CHECKCAST, ASTORE)) {
                current.remove(0);
                current.peek(0).children(OperandInstanceOf.class).to(o -> o.withCast(variable));
                return;
            }

            if (match(FRAME_SAME1, ASTORE) || match(FRAME_FULL, ASTORE)) {
                tries.assignExceptionVariable(current, variable);
            }

        case ISTORE:
        case LSTORE:
        case FSTORE:
        case DSTORE:
            if (matchLocalVariableAccess(position, position)) {
                // Increment not-int type doesn't use Iinc instruction, so we must distinguish
                // increment from addition by pattern matching. Post increment code of non-int type
                // leaves characteristic pattern like the following.
                if (match(LOAD, DUPLICATE, CONSTANT_1, ADD, STORE)) {
                    // for long, float and double
                    current.remove(0);
                    current.remove(0);

                    current.addOperand(increment(variable, load(opcode), true, true));
                    return;
                } else if (match(LOAD, CONSTANT_1, ADD, DUPLICATE, STORE)) {
                    // for long, float and double
                    current.remove(0);
                    current.remove(0);

                    current.addOperand(increment(variable, load(opcode), true, false));
                    return;
                } else if (match(LOAD, DUPLICATE, CONSTANT_1, SUB, STORE)) {
                    // for long, float and double
                    current.remove(0);
                    current.remove(0);

                    current.addOperand(increment(variable, load(opcode), false, true));
                    return;
                } else if (match(LOAD, CONSTANT_1, SUB, DUPLICATE, STORE)) {
                    // for long, float and double
                    current.remove(0);
                    current.remove(0);

                    current.addOperand(increment(variable, load(opcode), false, false));
                    return;
                }
            }

            // for other
            if (current.peek(0) != null) {
                // retrieve and remove it
                Operand operand = current.remove(0, false);

                // Enum#values produces special bytecode, so we must handle it by special way.
                if (match(ASTORE, ICONST_0, ALOAD, ARRAYLENGTH, DUP, ISTORE)) {
                    enumValues[0] = current.remove(0);
                    enumValues[1] = current.remove(0);
                }

                OperandAssign assign = new OperandAssign(variable, AssignOperator.ASSIGN, operand);

                if (!operand.duplicated) {
                    current.addOperand(assign);
                } else {
                    operand.duplicated = false;

                    // Enum#values produces special bytecode,
                    // so we must handle it by special way.
                    if (match(ARRAYLENGTH, DUP, ISTORE, ANEWARRAY, DUP, ASTORE)) {
                        current.addOperand(enumValues[1]);
                        current.addOperand(enumValues[0]);
                    }

                    if (locals.isLocal(variable) && match(DUP, STORE)) {
                        current.stack.add(variable);
                    }

                    // duplicate pointer
                    current.addOperand(assign.encolose());
                }
            }
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isDisposed(Node node) {
        return !nodes.contains(node);
    }

    /**
     * Helper method to dispose the specified node.
     * 
     * @param target A target node to dipose.
     * @param clearStack true will clear all operands in target node, false will transfer them into
     *            the previous node.
     * @param recursive true will dispose the previous node if it is empty.
     */
    @Override
    public final void dispose(Node target, boolean clearStack, boolean recursive) {
        if (nodes.contains(target)) {
            // remove actually
            nodes.remove(target);

            // update case or default node on switch
            for (OperandSwitch switche : switches) {
                switche.replaceCase(target, target.next);
            }

            link(target.previous, target.next);

            // Connect from incomings to outgouings
            for (Node out : target.outgoing) {
                for (Node in : target.incoming) {
                    in.connect(out);
                }
            }

            // Remove the target node from its incomings and outgoings.
            for (Node node : target.incoming) {
                node.disconnect(target);

                if (node.destination == target) {
                    node.destination = target.getDestination();
                }

                for (Operand operand : node.stack) {
                    if (operand instanceof OperandCondition) {
                        OperandCondition condition = (OperandCondition) operand;

                        if (condition.then == target) {
                            condition.then = target.getDestination();
                        }

                        if (condition.elze == target) {
                            condition.elze = target.getDestination();
                        }
                    }
                }
            }
            for (Node node : target.outgoing) {
                target.disconnect(node);
            }

            // Copy all operands to the previous node if needed
            if (!clearStack) {
                if (target.previous != null) {
                    target.previous.stack.addAll(target.stack);
                }
            }

            // Delete all operands from the current processing node
            target.stack.clear();

            // switch current node if needed
            if (target == current) {
                current = target.previous;
            }

            // dispose empty node recursively
            if (recursive && target.previous != null) {
                if (target.previous.stack.isEmpty() && !target.previous.incoming.isEmpty()) {
                    dispose(target.previous, clearStack, recursive);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node createNodeBefore(Node index, boolean connectable, boolean transferOperands) {
        Node created = new Node(alphaId());

        // switch line number
        created.lineNumber = index.lineNumber;
        index.lineNumber = -1;

        // switch previous and next nodes
        link(index.previous, created, index);

        created.destination = index;

        if (connectable) {
            for (Node in : index.incoming) {
                in.disconnect(index);
                in.connect(created);
            }
            created.connect(index);
        }

        if (transferOperands) {
            index.transferTo(created);
        }

        // insert to node list
        nodes.add(nodes.indexOf(index), created);

        // reset dominator
        index.dominator = null;

        // API definition
        return created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Node createNodeAfter(Node index, boolean connectable, boolean transferOperands) {
        Node created = new Node(alphaId());

        // switch line number
        created.lineNumber = index.lineNumber;
        index.lineNumber = -1;

        // switch previous and next nodes
        link(index, created, index.next);

        if (index.destination == null) {
            index.destination = created;
        }

        if (connectable) {
            for (Node out : index.outgoing) {
                index.disconnect(out);
                created.connect(out);

                // reset dominator
                out.dominator = null;
            }
            index.connect(created);
        }

        if (transferOperands) {
            index.transferTo(created);
        }

        // insert to node list
        nodes.add(nodes.indexOf(index) + 1, created);

        // API definition
        return created;
    }

    /**
     * Generate the aphpabetical identifier.
     * 
     * @return
     */
    private String alphaId() {
        StringBuilder id = new StringBuilder();

        int num = counterAlpha++;
        while (0 < num) {
            id.insert(0, (char) (--num % 26 + 'A'));
            num /= 26;
        }
        return id.toString();
    }

    /**
     * Retrieve the asossiated node of the specified label.
     * 
     * @param label A label for node.
     * @return An asossiated and cached node.
     */
    private Node getNode(Label label) {
        Node node = (Node) label.info;

        // search cached node
        if (node == null) {
            label.info = node = new Node(counter++);
        }

        // API definition
        return node;
    }

    /**
     * Helper method to merge all conditional operands.
     */
    private void merge(Node node) {
        if (node == null) {
            return;
        }

        SequentialConditionInfo info = new SequentialConditionInfo(node);

        if (info.conditions.isEmpty()) {
            return;
        }

        try (Printable diff = debugger.diff(nodes, "Merging logical condition")) {
            // Search and merge the sequencial conditional operands in this node from right to left.
            int start = info.start;
            OperandCondition left = null;
            OperandCondition right = node.peek(start).as(OperandCondition.class).v;

            for (int index = 1; index < info.conditions.size(); index++) {
                left = (OperandCondition) node.peek(start + index);

                if (info.canMerge(left, right)) {
                    // Merge two adjucent conditional operands.
                    right = new OperandCondition(left, node.remove(--start + index).as(OperandCondition.class).v);

                    node.set(start + index, right);
                } else {
                    right = left;
                    left = null;
                }
            }

            // If the previous node is terminated by conditional operand and the target node is
            // started by conditional operand, we should try to merge them.
            if (info.conditionalHead && node.previous != null) {
                // The direct self-backedge node is not mergeable
                if (node.incoming.contains(node)) {
                    return;
                }

                Operand operand = node.previous.peek(0);

                if (operand instanceof OperandCondition condition) {
                    if (info.canMerge(condition, right) && condition.elze == node) {
                        dispose(node);

                        // Merge recursively
                        merge(node.previous);
                    }
                }
            }
        }
    }

    /**
     * Record the current instruction.
     */
    private void record(int opcode) {
        // insert anonymous label at head if the processing method has no label
        if (records[0] == 0 && opcode != LABEL) {
            visitLabel(new Label());
        }

        records[recordIndex++] = opcode;

        if (recordIndex == records.length) {
            recordIndex = 0; // loop index
        }
    }

    /**
     * Record the access to local variable.
     */
    private void recordLocalVariableAccess(int position) {
        localVarialbeAccess[localVarialbeAccessIndex++] = position;

        if (localVarialbeAccessIndex == localVarialbeAccess.length) {
            localVarialbeAccessIndex = 0; // loop index
        }
    }

    /**
     * Pattern matching for the recent instructions.
     * 
     * @param opcodes A sequence of opecodes to match.
     * @return A result.
     */
    private boolean match(int... opcodes) {
        root: for (int i = opcodes.length - 1, j = opcodes.length - 1; 0 <= i; i--, j--) {
            int record = records[(recordIndex + j + records.length - opcodes.length) % records.length];

            switch (opcodes[i]) {
            case ADD:
                switch (record) {
                case IADD:
                case LADD:
                case FADD:
                case DADD:
                    continue root;

                default:
                    return false;
                }

            case SUB:
                switch (record) {
                case ISUB:
                case LSUB:
                case FSUB:
                case DSUB:
                    continue root;

                default:
                    return false;
                }

            case CONSTANT_0:
                switch (record) {
                case ICONST_0:
                case LCONST_0:
                case FCONST_0:
                case DCONST_0:
                    continue root;

                default:
                    return false;
                }

            case CONSTANT_1:
                switch (record) {
                case ICONST_1:
                case LCONST_1:
                case FCONST_1:
                case DCONST_1:
                    continue root;

                default:
                    return false;
                }

            case DUPLICATE:
                switch (record) {
                case DUP:
                case DUP2:
                    continue root;

                default:
                    return false;
                }

            case DUPLICATE_AWAY:
                switch (record) {
                case DUP_X1:
                case DUP2_X1:
                    continue root;

                default:
                    return false;
                }

            case RETURNS:
                switch (record) {
                case RETURN:
                case IRETURN:
                case ARETURN:
                case LRETURN:
                case FRETURN:
                case DRETURN:
                    continue root;

                default:
                    return false;
                }

            case JUMP:
                switch (record) {
                case IFEQ:
                case IFGE:
                case IFGT:
                case IFLE:
                case IFLT:
                case IFNE:
                case IFNONNULL:
                case IFNULL:
                case IF_ACMPEQ:
                case IF_ACMPNE:
                case IF_ICMPEQ:
                case IF_ICMPGE:
                case IF_ICMPGT:
                case IF_ICMPLE:
                case IF_ICMPLT:
                case IF_ICMPNE:
                case GOTO:
                    continue root;

                default:
                    return false;
                }

            case CMP:
                switch (record) {
                case IFEQ:
                case IFGE:
                case IFGT:
                case IFLE:
                case IFLT:
                case IFNE:
                case IFNONNULL:
                case IFNULL:
                case IF_ACMPEQ:
                case IF_ACMPNE:
                case IF_ICMPEQ:
                case IF_ICMPGE:
                case IF_ICMPGT:
                case IF_ICMPLE:
                case IF_ICMPLT:
                case IF_ICMPNE:
                    continue root;

                default:
                    return false;
                }

            case FCMP:
                switch (record) {
                case FCMPG:
                case FCMPL:
                    continue root;

                default:
                    return false;
                }

            case DCMP:
                switch (record) {
                case DCMPG:
                case DCMPL:
                    continue root;

                default:
                    return false;
                }

            case FRAME:
                switch (record) {
                case FRAME_APPEND:
                case FRAME_CHOP:
                case FRAME_FULL:
                case FRAME_NEW:
                case FRAME_SAME:
                case FRAME_SAME1:
                    continue root;

                default:
                    return false;
                }

            case FRAME_ANY_SAME:
                switch (record) {
                case FRAME_SAME:
                case FRAME_SAME1:
                    continue root;

                default:
                    return false;
                }

            case INVOKE:
                switch (record) {
                case INVOKEINTERFACE:
                case INVOKESPECIAL:
                case INVOKESTATIC:
                case INVOKEVIRTUAL:
                    continue root;

                default:
                    return false;
                }

            case STORE:
                switch (record) {
                case ISTORE:
                case LSTORE:
                case FSTORE:
                case DSTORE:
                case ASTORE:
                    continue root;

                default:
                    return false;
                }

            case LOAD:
                switch (record) {
                case ILOAD:
                case LLOAD:
                case FLOAD:
                case DLOAD:
                case ALOAD:
                    continue root;

                default:
                    return false;
                }

            case OptionalLABEL:
                if (record != LABEL) j++;
                continue root;

            case SWITCH:
                switch (record) {
                case LOOKUPSWITCH:
                case TABLESWITCH:
                    continue root;

                default:
                    return false;
                }

            default:
                if (record != opcodes[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Find the id of local variable which is accessed at latest.
     * 
     * @return
     */
    private int latestLocalVariableAccess() {
        return localVarialbeAccess[(localVarialbeAccessIndex - 1 + localVarialbeAccess.length) % localVarialbeAccess.length];
    }

    /**
     * Pattern matching for the recent local variable access.
     * 
     * @param position A sequence of local variable positions to match.
     * @return A result.
     */
    private boolean matchLocalVariableAccess(int... position) {
        for (int i = 0; i < position.length; i++) {
            if (localVarialbeAccess[(localVarialbeAccessIndex + i + localVarialbeAccess.length - position.length) % localVarialbeAccess.length] != position[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Write increment/decrement code.
     * 
     * @param context A current context value.
     * @param type A current context type.
     * @param increase Increment or decrement.
     * @param post Post or pre.
     * @return A suitable code.
     */
    private Operand increment(Operand context, Class type, boolean increase, boolean post) {
        UnaryOperator operator;

        if (post) {
            operator = increase ? UnaryOperator.POST_INCREMENT : UnaryOperator.POST_DECREMENT;
        } else {
            operator = increase ? UnaryOperator.PRE_INCREMENT : UnaryOperator.PRE_DECREMENT;
        }
        return new OperandUnary(context, operator);
    }

    /**
     * Check static method.
     * 
     * @param owner
     * @param name
     * @param types
     * @return
     */
    private boolean hasStaticMethod(Class owner, String name, Class[] types) {
        if (owner == null) {
            return false;
        }

        try {
            Method method = owner.getDeclaredMethod(name, types);

            return Modifier.isStatic(method.getModifiers());
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    /**
     * @version 2018/10/03 15:57:48
     */
    private class SequentialConditionInfo {

        /** The start location of this sequence. */
        private int start;

        /** The base node. */
        private final Node base;

        /** The sequential conditions. */
        private final ArrayList<OperandCondition> conditions = new ArrayList();

        /** The flag whether this node is started by conditional operand or not. */
        private final boolean conditionalHead;

        /** The flag whether this node is started by conditional operand or not. */
        private final boolean conditionalTail;

        /**
         * <p>
         * Search the sequencial conditional operands in the specified node from right to left.
         * </p>
         * 
         * @param node A target node.
         */
        private SequentialConditionInfo(Node node) {
            this.base = node;

            boolean returned = false;

            // Search the sequential conditional operands in the specified node from right to left.
            for (int index = 0; index < node.stack.size(); index++) {
                Operand operand = node.peek(index);
                if (operand.as(OperandCondition.class).isAbsent()) {
                    // non-conditional operand is found
                    if (conditions.isEmpty()) {
                        if (operand instanceof OperandReturn) {
                            returned = true;
                        }
                        // conditional operand is not found as yet, so we should continue to search
                        continue;
                    } else {
                        // stop searching
                        break;
                    }
                }

                // conditional operand is found
                OperandCondition condition = operand.as(OperandCondition.class).v;

                if (conditions.isEmpty()) {
                    // this is first condition
                    start = index;

                    if (!returned && condition.elze == null && condition.then != node.destination) {
                        condition.elze = node.destination;
                    }
                } else {
                    // this is last condition
                }
                conditions.add(condition);

                // if (index + 1 == node.stack.size()) {
                // if (node.peek(index + 1) instanceof OperandCondition) {
                // node = node.previous;
                // index = 0;
                // }
                // }
            }

            this.conditionalHead = node.stack.size() == start + conditions.size();
            this.conditionalTail = start == 0 && !conditions.isEmpty();
        }

        /**
         * Test whether target condition is able to merge.
         * 
         * @param left A left condition.
         * @param right A right condition.
         * @return A result.
         */
        private boolean canMerge(OperandCondition left, OperandCondition right) {
            return left.then == right.then || left.then == right.elze;
        }

        /**
         * <p>
         * Split mixed contents into condition part and non-condition part.
         * </p>
         */
        private void split() {
            int size = conditions.size();

            if (size != 0 && size != base.stack.size()) {
                if (conditionalTail) {
                    // transfer condition operands to the created node
                    // [non-condition] [condition]
                    Node created = createNodeAfter(base, false);

                    for (int i = 0; i < size; i++) {
                        OperandCondition condition = (OperandCondition) base.stack.pollLast();

                        // disconnect from base node
                        base.disconnect(condition.then);
                        base.disconnect(condition.elze);

                        // connect from created node
                        created.connect(condition.then);
                        created.connect(condition.elze);

                        // transfer operand
                        created.stack.addFirst(condition);
                    }

                    // connect from base to created
                    base.connect(created);
                } else if (conditionalHead) {
                    // transfer non-condition operands to the created node
                    // [condition] [non-condition]
                    Node created = createNodeAfter(base, false);

                    // transfer operand
                    for (int i = 0; i < start; i++) {
                        created.stack.addFirst(base.stack.pollLast());
                    }

                    // search non-conditional operand's transition
                    Set<Node> transitions = new HashSet(base.outgoing);

                    for (OperandCondition condition : conditions) {
                        transitions.remove(condition.then);
                    }

                    for (Node transition : transitions) {
                        // disconnect from base
                        base.disconnect(transition);

                        // connect from created
                        created.connect(transition);
                    }

                    // connect from base to created
                    base.connect(created);

                    // connect from created to next
                    created.connect(base.destination == null ? created.destination : base.destination);

                    List<OperandCondition> conditions = base.children(OperandCondition.class).toList();
                    if (conditions.size() == 2) {
                        splitCondition(base);
                    }
                }
            }
        }

        private void splitCondition(Node node) {
            int index = searchSplittableIndex(node);

            List<OperandCondition> front = I.signal(node.stack).take(index).as(OperandCondition.class).toList();
            List<OperandCondition> rear = I.signal(node.stack).skip(index).as(OperandCondition.class).toList();

            Node after = createNodeAfter(node, rear);

            // for (OperandCondition condition : rear) {
            // base.disconnect(condition.then);
            // base.disconnect(condition.elze);
            // }
            for (OperandCondition condition : front) {
                base.connect(condition.then);
                base.connect(condition.elze);
            }

            for (OperandCondition condition : front) {
                after.disconnect(condition.then);
                after.disconnect(condition.elze);
            }
            // for (OperandCondition condition : rear) {
            // after.connect(condition.then);
            // after.connect(condition.elze);
            // }
        }
    }

    private int searchSplittableIndex(Node node) {
        Set<Node> outgoings = new HashSet();

        for (int i = 0; i < node.stack.size(); i++) {
            Operand operand = node.stack.get(i);
            if (operand instanceof OperandCondition condition) {
                if (condition.then != node) outgoings.add(condition.then);
                if (condition.elze != node) outgoings.add(condition.elze);
            }

            if (2 < outgoings.size()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Manage all try-catch-finally blocks.
     */
    private class TryCatchFinallyManager {

        /** The managed try-catch-finally blocks. */
        private final List<TryCatchFinally> blocks = new ArrayList();

        /** The copied finally node manager. */
        private final MultiMap<CopiedFinally, CopiedFinally> finallyCopies = new MultiMap(true);

        /** The analyze state. */
        private boolean analyzed;

        /**
         * @param start
         * @param end
         * @param catcher
         * @param exception
         */
        private void addBlock(Node start, Node end, Node catcher, Class exception) {
            if (exception == null) {
                // with finally block
                CopiedFinally c = new CopiedFinally(start, end, catcher);
                finallyCopies.put(c, c);

                for (TryCatchFinally block : blocks) {
                    if (block.catcher == catcher) {
                        return;
                    }

                    // The try-catch-finally block which indicates the same start and end nodes
                    // means multiple catches.
                    if (block.start == start && end != catcher) {
                        block.addCatchOrFinallyBlock(exception, catcher);
                        return;
                    }

                    for (CatchOrFinally cof : block.blocks) {
                        if (cof.node == catcher) {
                            return;
                        }
                    }
                }

                blocks.add(0, new TryCatchFinally(start, end, catcher, exception));
            } else {
                // without finally block
                for (TryCatchFinally block : blocks) {
                    if (block.catcher == catcher) {
                        return;
                    }

                    // The try-catch-finally block which indicates the same start and end nodes
                    // means multiple catches.
                    if (block.start == start && block.end == end) {
                        block.addCatchOrFinallyBlock(exception, catcher);
                        return;
                    }
                }
                blocks.add(0, new TryCatchFinally(start, end, catcher, exception));
            }
        }

        /**
         * 
         */
        private void disposeEmptyTryCatchFinallyBlock() {
            // for (TryCatchFinally block : blocks) {
            // if (block.isEmpty()) {
            // block.dispose();
            // }
            // }
        }

        /**
         * In Java 6 and later, the old jsr and ret instructions are effectively deprecated. These
         * instructions were used to build mini-subroutines inside methods. The finally node is
         * copied on all exit nodes by compiler , so we must remove it.
         */
        private void disposeCopiedFinallyBlock() {
            finallyCopies.forEach((key, copies) -> {
                if (!isDisposed(key.start)) {
                    // calculate the size of finally block
                    int deletableSize = key.handler.outgoingRecursively()
                            .takeWhile(n -> !n.isThrow())
                            .take(Node::isNotEmpty)
                            .count()
                            .to()
                            .or(0L)
                            .intValue();

                    try (Printable diff = debugger
                            .diff(nodes, "Remove copied finally nodes [size: " + deletableSize + "] from the next node of handler's [" + key.handler.id + "] last tail.")) {
                        if (!match(key)) {
                            key.handler.tails()
                                    .last()
                                    .map(n -> n.next)
                                    .flatMap(Node::outgoingRecursively)
                                    .take(Node::isNotEmpty)
                                    .take(deletableSize)
                                    .buffer()
                                    .flatIterable(n -> n)
                                    .to(n -> dispose(n, true, true));
                        }
                    }

                    try (Printable diff = debugger
                            .diff(nodes, "Remove copied finally nodes [size: " + deletableSize + "] from end's outgoings")) {
                        for (CopiedFinally copy : copies) {
                            I.signal(copy)
                                    .take(c -> c.end != c.handler)
                                    .flatMap(c -> c.end.outgoingRecursively())
                                    .take(Node::isNotEmpty)
                                    .take(deletableSize)
                                    .buffer()
                                    .flatIterable(n -> n)
                                    .to(n -> dispose(n, true, true));
                        }
                    }

                    // Dispose the throw operand from the tail node in finally block.
                    key.handler.tails().take(Node::isThrow).to(n -> dispose(n, true, true));
                }
            });
        }

        private boolean match(CopiedFinally copied) {
            for (TryCatchFinally block : blocks) {
                if (block.hasCatch() && block.start == copied.start && block.end == copied.end) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Test whether the given node is try node or not.
         * 
         * @param node
         * @return
         */
        private boolean isTry(Node node) {
            return blocks.stream().anyMatch(x -> x.start == node);
        }

        /**
         * Test whether the given node is catch node or not.
         * 
         * @param node
         * @return
         */
        private boolean isCatch(Node node) {
            return blocks.stream().flatMap(x -> x.blocks.stream()).anyMatch(x -> x.isCatch(node));
        }

        /**
         * Test whether the given node is catch node or not.
         * 
         * @param node
         * @return
         */
        private boolean isFinally(Node node) {
            return blocks.stream().flatMap(x -> x.blocks.stream()).anyMatch(x -> x.isFinally(node));
        }

        /**
         * Analyze and process.
         */
        private void process() {
            if (analyzed == false) {
                analyzed = true;

                // To analyze try-catch-finally statement tree, we must connect each nodes.
                // But these connections disturb the analysis of other statements (e.g. if, for).
                // So we must disconnect them immediately after analysis of try-catch-finally
                // statement.

                // At first, do connecting only.
                for (TryCatchFinally block : blocks) {
                    block.start.connect(block.catcher);

                    for (CatchOrFinally catchBlock : block.blocks) {
                        block.start.connect(catchBlock.node);
                    }
                }

                // Then, we can analyze.
                for (TryCatchFinally block : blocks) {
                    // Associate node with block.
                    block.searchExit();
                    block.purgeUnreachableCatch();
                }

                // At last, disconnect immediately after analysis.
                for (TryCatchFinally block : blocks) {
                    block.start.disconnect(block.catcher);

                    for (CatchOrFinally catchBlock : block.blocks) {
                        block.start.disconnect(catchBlock.node);
                    }
                }

                // Purge the catch block which is inside loop structure directly.
                for (TryCatchFinally block : blocks) {
                    for (CatchOrFinally catchOrFinally : block.blocks) {
                        Set<Node> recorder = new HashSet<>();
                        recorder.add(catchOrFinally.node);

                        Deque<Node> queue = new ArrayDeque<>();
                        queue.add(catchOrFinally.node);

                        while (!queue.isEmpty()) {
                            Node node = queue.pollFirst();

                            for (Node out : node.outgoing) {
                                if (out.hasDominator(catchOrFinally.node)) {
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
        }

        /**
         * Set exception variable name.
         * 
         * @param current A target node.
         * @param variable A variable name.
         */
        private void assignExceptionVariable(Node current, OperandLocalVariable variable) {
            for (TryCatchFinally block : blocks) {
                for (CatchOrFinally catchOrFinally : block.blocks) {
                    if (catchOrFinally.node == current) {
                        catchOrFinally.variable = variable;
                    }
                }
            }
        }
    }

    /**
     * Data holder for try-catch-finally block.
     */
    static class TryCatchFinally {

        /** The start node. */
        final Node start;

        /** The end node. */
        final Node end;

        /** The catcher node. */
        final Node catcher;

        /** The catch or finally blocks. */
        final List<CatchOrFinally> blocks = new ArrayList<>();

        /** The exit node. */
        Node exit;

        /**
         * @param start
         * @param end
         * @param catcher
         * @param exception
         */
        private TryCatchFinally(Node start, Node end, Node catcher, Class<?> exception) {
            this.start = start;
            this.end = end;
            this.catcher = catcher;

            start.disposable = false;
            end.disposable = false;
            catcher.disposable = false;

            start.tries.add(0, this);
            addCatchOrFinallyBlock(exception, catcher);
        }

        private boolean isEmpty() {
            // check start
            if (start != null && !start.isEmpty()) {
                return false;
            }

            // check end
            if (end != null && !end.isEmpty()) {
                return false;
            }

            if (catcher != null && !catcher.isEmpty()) {
                return false;
            }

            for (CatchOrFinally block : blocks) {
                if (!block.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Test whether this try-catch-finally block has any catch block or not.
         * 
         * @return
         */
        private boolean hasCatch() {
            return blocks.stream().anyMatch(n -> n.exception != null);
        }

        /**
         * Add catch or finally block.
         * 
         * @param exception
         * @param catcherOrFinally
         */
        private void addCatchOrFinallyBlock(Class<?> exception, Node catcherOrFinally) {
            for (CatchOrFinally block : blocks) {
                if (block.exception == exception) {
                    return;
                }
            }
            catcherOrFinally.disposable = false;
            catcherOrFinally.additionalCall++;
            catcherOrFinally.dominator = start;
            blocks.add(new CatchOrFinally(exception, catcherOrFinally));
        }

        /**
         * Search exit node of this try-catch-finally block.
         */
        private void searchExit() {
            Deque<Node> nodes = new ArrayDeque<>();
            if (catcher != null) nodes.addAll(catcher.outgoing); // catcher node must be first
            if (end != null) nodes.addAll(end.outgoing); // then end node

            Set<Node> recorder = new HashSet<>(nodes);

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

            I.signal(blocks).flatMap(c -> c.node.tails()).flatMap(Node::next).skipNull().diff(Node::isAfter).last().to(n -> exit = n);
        }

        /**
         * 
         */
        private void purgeUnreachableCatch() {
            if (exit == null) {
                catcher.junction().to(junction -> {
                    catcher.outgoingRecursively().takeWhile(node -> node != junction).reverse().first().to(node -> {
                        node.disconnect(junction);
                    });
                });
            }
        }

        private void dispose() {
            if (start != null) start.tries.remove(this);
            if (end != null) end.tries.remove(this);
            if (catcher != null) catcher.tries.remove(this);
            if (exit != null) exit.tries.remove(this);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "TryCatchFinally [start=" + start.id + ", end=" + end.id + ", catcher=" + catcher.id + ", blocks=" + blocks + ", exit=" + exit + "]";
        }
    }

    /**
     * 
     */
    static class CatchOrFinally {

        /** The Throwable class, may be null for finally statmenet. */
        final Class exception;

        /** The associated node. */
        final Node node;

        OperandLocalVariable variable;

        /**
         * @param exception
         * @param node
         */
        CatchOrFinally(Class<?> exception, Node node) {
            this.exception = exception;
            this.node = node;
        }

        private boolean isCatch(Node node) {
            return exception != null && this.node == node;
        }

        private boolean isFinally(Node node) {
            return exception == null && this.node == node;
        }

        /**
         * Test whether this catch or finally block is empty or not.
         * 
         * @return
         */
        private boolean isEmpty() {
            return node == null || node.isEmpty();
        }
    }

    /**
     * 
     */
    private static class CopiedFinally {

        private final Node start;

        private final Node end;

        private final Node handler;

        /**
         * @param key
         */
        private CopiedFinally(Node start, Node end, Node handler) {
            this.start = start;
            this.end = end;
            this.handler = handler;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return handler.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            return obj instanceof CopiedFinally other ? Objects.equals(handler, other.handler) : false;
        }
    }
}