/*
 * Copyright (C) 2018 Reincarnation Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Type.*;
import static reincarnation.Node.*;
import static reincarnation.OperandCondition.*;
import static reincarnation.Util.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.AssignExpr.Operator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;

import kiss.I;
import reincarnation.Node.Switch;
import reincarnation.Node.TryCatchFinallyBlocks;

/**
 * @version 2018/10/04 8:47:28
 */
class JavaMethodDecompiler extends MethodVisitor {

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

    /** The extra opcode for byte code parsing. */
    private static final int LABEL = 300;

    /** The frequently used operand for cache. */
    private static final OperandNumber ZERO = new OperandNumber(0);

    /** The frequently used operand for cache. */
    private static final OperandNumber ONE = new OperandNumber(1);

    /** The current processing class. */
    private final Class clazz;

    /** The current processing method name. */
    private final String methodName;

    /** The method return type. */
    private final Type returnType;

    /** The method return type. */
    private final Type[] parameterTypes;

    /** The local variable manager. */
    private final LocalVariables variables;

    /** The pool of try-catch-finally blocks. */
    private final TryCatchFinallyBlocks tries = new TryCatchFinallyBlocks();

    /** The method body. */
    private final BodyDeclaration<?> root;

    /** The current processing node. */
    private Node current = null;

    /** The all node list for this method. */
    private List<Node> nodes = new CopyOnWriteArrayList();

    /** The counter for the current processing node identifier. */
    private int counter = 0;

    /** The counter for construction of the object initialization. */
    private int countInitialization = 0;

    /** The record of recent instructions. */
    private int[] records = new int[10];

    /** The current start position of instruction records. */
    private int recordIndex = 0;

    /**
     * {@link Enum#values} produces special bytecode, so we must handle it by special way.
     */
    private Operand[] enumValues = new Operand[2];

    /**
     * <p>
     * Switch statement with enum produces special bytecode, so we must handle it by special way.
     * The following asmfier code is typical code for enum switch.
     * </p>
     * <pre>
     * // invoke compiler generated static method to retrieve the user class specific number array
     * // we should ignore this oeprand
     * mv.visitMethodInsn(INVOKESTATIC, "EnumSwitchUserClass", "$SWITCH_TABLE$EnumClass", "()[I");
     *
     * // load target enum variable
     * mv.visitVarInsn(ALOAD, 1);
     * 
     * // invoke Enum#ordinal method to retieve identical number
     * mv.visitMethodInsn(INVOKEVIRTUAL, "EnumClass", "ordinal", "()I");
     * 
     * // access mapping number array
     * //we should ignore this operand
     * mv.visitInsn(IALOAD);
     * </pre>
     */
    private boolean enumSwitchInvoked;

    /** The synchronized block related nodes. */
    private Set<Node> synchronizer = new HashSet();

    /** The flag whether the next jump instruction is used for assert statement or not. */
    private boolean assertJump = false;

    /** The flag whether the next new instruction is used for assert statement or not. */
    private boolean assertNew = false;

    /**
     * @param root
     * @param api
     */
    JavaMethodDecompiler(Class clazz, BodyDeclaration<?> root, String name, String description, boolean isStatic) {
        super(ASM7);

        this.clazz = clazz;
        this.root = Objects.requireNonNull(root);
        this.methodName = name;
        this.returnType = Type.getReturnType(description);
        this.parameterTypes = Type.getArgumentTypes(description);
        this.variables = new LocalVariables(isStatic, parameterTypes);

        Debugger.recordMethodName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (desc.equals(DEBUGGER)) {
            return I.make(Debugger.class);
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
        Debugger.print(nodes);

        // build parameters
        if (root instanceof CallableDeclaration) {
            CallableDeclaration declaration = (CallableDeclaration) root;

            for (int i = 0; i < parameterTypes.length; i++) {
                Parameter param = new Parameter();
                param.setType(load(parameterTypes[i]));
                param.setName(variables.name(i + 1).toString());
                declaration.addParameter(param);
            }
        }

        BlockStmt body = new BlockStmt();
        nodes.get(0).build(body);

        if (root instanceof MethodDeclaration) {
            ((MethodDeclaration) root).setBody(body);
        } else if (root instanceof ConstructorDeclaration) {
            ((ConstructorDeclaration) root).setBody(body);
        } else if (root instanceof InitializerDeclaration) {
            ((InitializerDeclaration) root).setBody(body);
        }

        // optimize
        removeLastEmptyReturn(body);
    }

    /**
     * Remove {@link ReturnStmt} if it is empty at last.
     * 
     * @param block
     */
    private void removeLastEmptyReturn(BlockStmt block) {
        NodeList<Statement> statements = block.getStatements();
        Statement last = statements.get(statements.size() - 1);

        if (last.isReturnStmt()) {
            Optional<Expression> e = last.asReturnStmt().getExpression();

            if (e.isEmpty()) {
                statements.removeLast();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitFieldInsn(int opcode, String ownerClassName, String name, String desc) {
        // If this field access instruction is used for assertion, we should skip it to erase
        // compiler generated extra code.
        if (opcode == GETSTATIC && name.equals("$assertionsDisabled")) {
            assertJump = assertNew = true;
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
            // Increment (decrement) of field doesn't use increment instruction, so we must
            // distinguish increment (decrement) from addition by pattern matching.
            if (match(DUP, GETFIELD, DUPLICATE_AWAY, CONSTANT_1, ADD, PUTFIELD)) {
                // The pattenr of post-increment field is like above.
                current.remove(0);

                // current.addOperand(increment(current.remove(0) + "." + computeFieldName(owner,
                // name), type, true, true));
            } else if (match(DUP, GETFIELD, DUPLICATE_AWAY, CONSTANT_1, SUB, PUTFIELD)) {
                // The pattenr of post-decrement field is like above.
                current.remove(0);

                // current.addOperand(increment(current.remove(0) + "." + computeFieldName(owner,
                // name), type, false, true));
            } else if (match(DUP, GETFIELD, CONSTANT_1, ADD, DUPLICATE_AWAY, PUTFIELD)) {
                // The pattenr of pre-increment field is like above.
                current.remove(0);

                // current.addOperand(increment(current.remove(0) + "." + computeFieldName(owner,
                // name), type, true, false));
            } else if (match(DUP, GETFIELD, CONSTANT_1, SUB, DUPLICATE_AWAY, PUTFIELD)) {
                // The pattenr of pre-decrement field is like above.
                current.remove(0);

                // current.addOperand(increment(current.remove(0) + "." + computeFieldName(owner,
                // name), type, false, false));
            } else {
                AssignExpr assign = new AssignExpr();
                assign.setTarget(new FieldAccessExpr(current.remove(1).build(), name));
                assign.setValue(current.remove(0).cast(type).build());
                assign.setOperator(Operator.ASSIGN);

                if (match(DUPLICATE_AWAY, PUTFIELD)) {
                    // multiple assignment (i.e. this.a = this.b = 0;)
                    // current.addOperand(assignment.encolose());
                } else {
                    // normal assignment
                    current.addExpression(assign);
                }
            }
            break;

        case GETFIELD:
            // current.addOperand(translator.translateField(owner, name, current.remove(0)), type);
            break;

        case PUTSTATIC:
            if (match(GETSTATIC, DUPLICATE, CONSTANT_1, ADD, PUTSTATIC)) {
                // The pattenr of post-increment field is like above.
                current.remove(0);

                current.addOperand(increment(current.remove(0).build(), type, true, true));
            } else if (match(GETSTATIC, DUPLICATE, CONSTANT_1, SUB, PUTSTATIC)) {
                // The pattenr of post-decrement field is like above.
                current.remove(0);

                current.addOperand(increment(current.remove(0).build(), type, false, true));
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
                AssignExpr assign = new AssignExpr();
                assign.setTarget(accessClassField(owner, name));
                assign.setValue(current.remove(0).cast(type).build());
                assign.setOperator(Operator.ASSIGN);

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
            current.addOperand(accessClassField(owner, name), type);
            break;
        }
    }

    private Expression accessClassField(Class owner, String name) {
        Expression scope;

        if (owner == clazz) {
            scope = new NameExpr(owner.getSimpleName());
        } else {
            root.tryAddImportToParentCompilationUnit(owner);
            scope = new TypeExpr(loadType(owner));
        }
        return new FieldAccessExpr(scope, name);
    }

    /**
     * <p>
     * Helper method to resolve ternary operator.
     * </p>
     */
    private void processTernaryOperator() {
        Operand third = current.peek(2);

        if (third instanceof OperandCondition) {
            Operand first = current.peek(0);

            // if (first == Node.END) {
            // return;
            // }

            Operand second = current.peek(1);

            // if (second == Node.END) {
            // return;
            // }

            Node right = findNodeBy(first);
            Node left = findNodeBy(second);
            Node condition = findNodeBy(third);

            if (right == left) {
                return;
            }

            // The condition's transition node must be right node. (not left node)
            // The bytecode order is:
            // [jump to RIGHT value]
            // [label]?
            // [LEFT value]
            // [label]
            // [RIGHT value]
            boolean conditionTransition = collect(((OperandCondition) third).then).contains(right);

            // In ternary operator, the left node's outgoing node must not contain right node.
            // But the outgoing nodes contains right node, this sequence will be logical expression.
            // boolean leftTransition = condition == left || !left.outgoing.contains(right);

            // The condition node must be dominator of the left and right nodes.
            boolean dominator = left.hasDominator(condition) && right.hasDominator(condition);

            // The left node must not be dominator of the right node except when condtion and left
            // value are in same node.
            boolean values = condition != left && right.hasDominator(left);

            if (conditionTransition && dominator && !values) {
                Debugger.print("Create ternary operator. condition[" + third + "]  left[" + second + "]  right[" + first + "]");
                Debugger.print(nodes);

                if (first == ONE && second == ZERO) {
                    current.remove(0);
                    current.remove(0);
                    condition.addOperand(new OperandAmbiguousZeroOneTernary(current.remove(0)));
                } else if (first == ZERO && second == ONE) {
                    current.remove(0);
                    current.remove(0);
                    condition.addOperand(new OperandAmbiguousZeroOneTernary(current.remove(0).invert()));
                } else {
                    current.remove(0);
                    current.remove(0);
                    current.remove(0);

                    if (first instanceof OperandCondition && second instanceof OperandCondition) {
                        condition
                                .addOperand(new OperandTernaryCondition((OperandCondition) third, (OperandCondition) second, (OperandCondition) first));
                    } else {
                        condition.addOperand(new OperandTernary(((OperandCondition) third).invert(), second, first).encolose());
                    }
                }

                // dispose empty nodes
                if (right.stack.isEmpty()) {
                    dispose(right);
                }

                if (left.stack.isEmpty()) {
                    dispose(left);
                }

                // process recursively
                processTernaryOperator();
            }
        }
    }

    private Set<Node> collect(Node node) {
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
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
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
     * {@inheritDoc}
     */
    @Override
    public void visitIincInsn(int position, int increment) {
        // recode current instruction
        record(INCREMENT);

        // retrieve the local variable name
        Expression variable = variables.name(position);
        InferredType type = variables.type(position);

        if (increment == 1) {
            // increment
            if (match(ILOAD, INCREMENT)) {
                // post increment
                current.addOperand(new UnaryExpr(current.remove(0).build(), UnaryExpr.Operator.POSTFIX_INCREMENT), type);
            } else {
                // pre increment
                current.addExpression(new UnaryExpr(variable, UnaryExpr.Operator.PREFIX_INCREMENT), type);
            }
        } else if (increment == -1) {
            // increment
            if (match(ILOAD, INCREMENT)) {
                // post increment
                current.addOperand(new UnaryExpr(current.remove(0).build(), UnaryExpr.Operator.POSTFIX_DECREMENT), type);
            } else {
                // pre increment
                current.addExpression(new UnaryExpr(variable, UnaryExpr.Operator.PREFIX_DECREMENT), type);
            }
        } else {
            current.addExpression(new AssignExpr(variable, new AssignExpr(variable, new IntegerLiteralExpr(increment), Operator.PLUS), Operator.ASSIGN));
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
            // When the JDK compiler compiles the code including "instance method reference", it
            // generates the byte code expressed in following ASM codes.
            //
            // visitInsn(DUP);
            // visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object","getClass","()Ljava/lang/Class;");
            // visitInsn(POP);
            //
            // Although i guess that it is the initialization code for the class to
            // which the lambda method belongs, ECJ doesn't generated such code.
            // In Javascript runtime, it is a completely unnecessary code,
            // so we should delete them unconditionally.
            if (match(DUP, INVOKEVIRTUAL, POP)) {
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
            current.addOperand(ZERO);
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
            current.addOperand(ONE);
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
            current.addOperand(null); // not "null"
            break;

        // + operand
        case IADD:
        case FADD:
        case DADD:
        case LADD:
            current.join(BinaryExpr.Operator.PLUS).enclose();
            break;

        // - operand
        case ISUB:
        case FSUB:
        case DSUB:
        case LSUB:
            current.join(BinaryExpr.Operator.MINUS).enclose();
            break;

        // * operand
        case IMUL:
        case FMUL:
        case DMUL:
        case LMUL:
            current.join(BinaryExpr.Operator.MULTIPLY);
            break;

        // / operand
        case IDIV:
        case FDIV:
        case DDIV:
        case LDIV:
            current.join(BinaryExpr.Operator.DIVIDE);
            break;

        // % operand
        case IREM:
        case FREM:
        case DREM:
        case LREM:
            current.join(BinaryExpr.Operator.REMAINDER);
            break;

        // & operand
        case IAND:
        case LAND:
            current.join(BinaryExpr.Operator.BINARY_AND).enclose();
            break;

        // | operand
        case IOR:
        case LOR:
            current.join(BinaryExpr.Operator.BINARY_OR).enclose();
            break;

        // ^ operand
        case IXOR:
        case LXOR:
            current.join(BinaryExpr.Operator.XOR);
            break;

        // << operand
        case ISHL:
        case LSHL:
            current.join(BinaryExpr.Operator.LEFT_SHIFT).enclose();
            break;

        // >> operand
        case ISHR:
        case LSHR:
            current.join(BinaryExpr.Operator.SIGNED_RIGHT_SHIFT).enclose();
            break;

        // >>> operand
        case IUSHR:
        case LUSHR:
            current.join(BinaryExpr.Operator.UNSIGNED_RIGHT_SHIFT).enclose();
            break;

        // negative operand
        case INEG:
        case FNEG:
        case DNEG:
        case LNEG:;
            current.addOperand(new UnaryExpr(current.remove(0).build(), UnaryExpr.Operator.MINUS));
            break;

        case RETURN:
            current.addExpression(new ReturnStmt());
            current.destination = Termination;
            break;

        case IRETURN:
            // Java bytecode represents boolean value as integer value (0 or 1).
            if (match(JUMP, ICONST_0, IRETURN, LABEL, FRAME, ICONST_1, IRETURN) || match(JUMP, LABEL, FRAME, ICONST_0, IRETURN, LABEL, FRAME, ICONST_1, IRETURN)) {
                // Current operands is like the following, so we must remove four operands to
                // represent boolean value.
                //
                // JUMP [Condition] return false [Expression] 1 [Number]
                current.remove(0); // remove "1"
                current.remove(0); // remove "return false"

                // remove empty node if needed
                if (current.previous.stack.isEmpty()) dispose(current.previous);
            } else if (match(JUMP, ICONST_1, IRETURN, LABEL, FRAME, ICONST_0, IRETURN) || match(JUMP, LABEL, FRAME, ICONST_1, IRETURN, LABEL, FRAME, ICONST_0, IRETURN)) {
                // Current operands is like the following, so we must remove four operands to
                // represent boolean value.
                //
                // JUMP [Condition] return true [Expression] 0 [Number]
                current.remove(0); // remove "0"
                current.remove(0); // remove "return true"

                // remove empty node if needed
                if (current.previous.stack.isEmpty()) dispose(current.previous);

                // invert the latest condition
                current.peek(0).invert();
            }
            Operand operand = current.remove(0);

            if (returnType == BOOLEAN_TYPE) {
                if (operand.toString().equals("0")) {
                    operand = Operand.False;
                } else if (operand.toString().equals("1")) {
                    operand = Operand.True;
                } else if (operand instanceof OperandAmbiguousZeroOneTernary) {
                    operand = operand.cast(boolean.class);
                }
            }
            current.addOperand(new ReturnStmt(operand.build()));
            current.destination = Termination;
            break;

        case ARETURN:
        case LRETURN:
        case FRETURN:
        case DRETURN:
            current.addOperand(new ReturnStmt(current.remove(match(DUP, JUMP, ARETURN) ? 1 : 0).build()));
            current.destination = Termination;
            break;

        // write array value by index
        case IALOAD:
            if (enumSwitchInvoked) {
                enumSwitchInvoked = false; // reset

                // enum switch table starts from 1, but Enum#ordinal starts from 0
                current.addOperand(current.remove(0) + "+1");
                break;
            }
        case AALOAD:
        case BALOAD:
        case LALOAD:
        case FALOAD:
        case DALOAD:
        case CALOAD:
        case SALOAD:
            current.addOperand(current.remove(1) + "[" + current.remove(0) + "]");
            break;

        // read array value by index
        case AASTORE:
        case BASTORE:
        case IASTORE:
        case LASTORE:
        case FASTORE:
        case DASTORE:
        case CASTORE:
        case SASTORE:
            Operand contextMaybeArray = current.remove(2);
            Operand value = current.remove(0, false);

            if (opcode == CASTORE) {
                // convert assign value (int -> char)
                value = value.cast(char.class);
            }

            if (contextMaybeArray instanceof OperandArray) {
                // initialization of syntax sugar
                ((OperandArray) contextMaybeArray).set(current.remove(0), value);
            } else {
                // write by index
                OperandExpression assignment = new OperandExpression(contextMaybeArray + "[" + current.remove(0) + "]=" + value.toString());

                if (!value.duplicated) {
                    current.addExpression(assignment);
                } else {
                    value.duplicated = false;

                    // duplicate pointer
                    current.addOperand(new OperandEnclose(assignment));
                }
            }
            break;

        // read array length
        case ARRAYLENGTH:
            current.addOperand(current.remove(0) + ".length");
            break;

        // throw
        case ATHROW:
            current.addExpression("throw ", current.remove(0));
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
            current.addOperand("String.fromCharCode(" + current.remove(0) + ")", char.class);
            break;

        case L2I:
            // cast long to int
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();

        case I2L:
            // cast int to long
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();

        case L2D:
            // cast long to double
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();

        case D2L:
            // cast double to long
            // If this exception will be thrown, it is bug of this program. So we must rethrow the
            // wrapped error in here.
            throw new Error();
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
                current.condition(left, EQ, ZERO.cast(left.infer()), node);
            }
            break;
        case IFNE: // != 0
            if (match(LCMP, JUMP) || match(DCMP, JUMP) || match(FCMP, JUMP)) {
                // for long, float and double
                current.condition(current.remove(1), NE, current.remove(0), node);
            } else {
                // others
                Operand left = current.remove(0);
                current.condition(left, NE, ZERO.cast(left.infer()), node);
            }
            break;

        case IFGE: // => 0
            if (match(LCMP, JUMP) || match(DCMP, JUMP) || match(FCMP, JUMP)) {
                // for long, float and double
                current.condition(current.remove(1), GE, current.remove(0), node);
            } else {
                // others
                current.condition(current.remove(0), GE, ZERO, node);
            }
            break;

        case IFGT: // > 0
            if (match(LCMP, JUMP) || match(DCMP, JUMP) || match(FCMP, JUMP)) {
                // for long, float and double
                current.condition(current.remove(1), GT, current.remove(0), node);
            } else {
                // others
                current.condition(current.remove(0), GT, ZERO, node);
            }
            break;

        case IFLE: // <= 0
            if (match(LCMP, JUMP) || match(DCMP, JUMP) || match(FCMP, JUMP)) {
                // for long, float and double
                current.condition(current.remove(1), LE, current.remove(0), node);
            } else {
                // others
                current.condition(current.remove(0), LE, ZERO, node);
            }
            break;

        case IFLT: // < 0
            if (match(LCMP, JUMP) || match(DCMP, JUMP) || match(FCMP, JUMP)) {
                // for long, float and double
                current.condition(current.remove(1), LT, current.remove(0), node);
            } else {
                // others
                current.condition(current.remove(0), LT, ZERO, node);
            }
            break;

        case IFNULL: // object == null
            current.condition(current.remove(0), EQ, new OperandExpression("null"), node);
            break;

        case IFNONNULL: // object != null
            current.condition(current.remove(0), NE, new OperandExpression("null"), node);
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
            current.addOperand(new OperandClass(Util.load((Type) constant)));
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

        Debugger.recordMethodLineNumber(line);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        variables.name(index, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitMethodInsn(int opcode, String className, String methodName, String desc, boolean access) {
        // recode current instruction
        record(opcode);

        // compute owner class
        Class owner = load(className);

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

        // if (JavaMethodInliner.isInlinable(methodName, returnType)) {
        // String expression = JavaMethodInliner.inline(owner, methodName, desc).apply(contexts,
        // current);
        //
        // if (match(DUPLICATE, INVOKE)) {
        // current.remove(0);
        // current.addOperand(expression);
        // } else if (match(DUPLICATE_AWAY, INVOKE)) {
        // current.addOperand(expression);
        // } else {
        // current.addExpression(expression);
        // }
        // return;
        // }

        switch (opcode) {
        // Invoke instance method; special handling for superclass constructor, private method,
        // and instance initialization method invocations
        case INVOKESPECIAL:
            // push "this" operand
            contexts.add(0, current.remove(0));

            // Analyze method argument
            if (!methodName.equals("<init>")) {
                if (owner == clazz) {
                    // private method invocation
                    // current.addOperand(translator.translateMethod(owner, methodName, desc,
                    // parameters, contexts), returnType);
                } else {
                    // super method invocation
                    // current.addOperand(translator.translateSuperMethod(owner, methodName, desc,
                    // parameters, contexts), returnType);
                }
            } else {
                // constructor
                if (countInitialization != 0) {
                    // instance initialization method invocation
                    // current.addOperand(translator.translateConstructor(owner, desc, parameters,
                    // contexts), owner);

                    countInitialization--;

                    // don't write
                    immediately = false;
                } else {
                    if (className.equals("java/lang/Object")) {
                        // ignore
                    } else {
                        // current.addOperand(translator.translateSuperMethod(owner, methodName,
                        // desc, parameters, contexts), returnType);
                    }
                }
            }
            break;

        case INVOKEVIRTUAL: // method call
        case INVOKEINTERFACE: // interface method call
            // push "this" operand
            contexts.add(0, current.remove(0));

            // translate
            // current.addOperand(translator.translateMethod(owner, methodName, desc, parameters,
            // contexts), returnType);
            break;

        case INVOKESTATIC: // static method call
            if (Switch.isEnumSwitchTable(methodName, desc)) {
                enumSwitchInvoked = true;
            } else {
                // Non-private static method which is called from child class have parent
                // class signature.
                while (!hasStaticMethod(owner, methodName, parameters)) {
                    owner = owner.getSuperclass();
                }

                // push class operand
                // contexts.add(0, new OperandExpression(Javascript.computeClassName(owner, true)));

                // translate
                // current.addOperand(translator.translateStaticMethod(owner, methodName, desc,
                // parameters, contexts), returnType);
            }
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
    public void visitVarInsn(int opcode, int position) {
        // recode current instruction
        record(opcode);

        // retrieve local variable name
        Expression variable = variables.name(position, opcode);

        switch (opcode) {
        case ILOAD:
        case FLOAD:
        case LLOAD:
        case DLOAD:
            if (match(INCREMENT, ILOAD)) {
                String expression = current.peek(0).toString();

                if (expression.startsWith("++") || expression.startsWith("--")) {
                    if (expression.substring(2).equals(variable.toString())) {
                        break;
                    }
                }
            }

        case ALOAD:
            current.addOperand(new OperandExpression(variable, variables.type(position)));
            break;

        case ASTORE:
            if (match(FRAME_SAME1, ASTORE) || match(FRAME_FULL, ASTORE)) {
                tries.assignVariableName(current, variable);
            }

        case ISTORE:
        case LSTORE:
        case FSTORE:
        case DSTORE:
            // Increment not-int type doesn't use Iinc instruction, so we must distinguish
            // increment from addition by pattern matching. Post increment code of non-int type
            // leaves characteristic pattern like the following.
            if (match(FLOAD, DUP, FCONST_1, FADD, FSTORE) || match(DLOAD, DUP2, DCONST_1, DADD, DSTORE)) {
                // for float and double
                current.remove(0);
                current.remove(0);

                current.addOperand(variable + "++");
            } else if (match(LLOAD, DUP2, LCONST_1, LADD, LSTORE)) {
                // for long
                current.remove(0);
                current.remove(0);

                current.addOperand(increment(variable, long.class, true, true));
            } else {
                // for other
                if (current.peek(0) != null) {
                    // retrieve and remove it
                    Operand operand = current.remove(0, false);

                    // Enum#values produces special bytecode, so we must handle it by special way.
                    if (match(ASTORE, ICONST_0, ALOAD, ARRAYLENGTH, DUP, ISTORE)) {
                        enumValues[0] = current.remove(0);
                        enumValues[1] = current.remove(0);
                    }

                    // infer local variable type
                    InferredType type = variables.type(position);
                    type.type(operand.infer().type());

                    AssignExpr assign = new AssignExpr(variable, operand.build(), AssignExpr.Operator.ASSIGN);

                    if (!operand.duplicated) {
                        current.addExpression(assign);
                    } else {
                        operand.duplicated = false;

                        // Enum#values produces special bytecode,
                        // so we must handle it by special way.
                        if (match(ARRAYLENGTH, DUP, ISTORE, ANEWARRAY, DUP, ASTORE)) {
                            current.addOperand(enumValues[1]);
                            current.addOperand(enumValues[0]);
                        }

                        // duplicate pointer
                        current.addOperand(new OperandEnclose(assign));
                    }
                }
            }
            break;
        }
    }

    /**
     * <p>
     * Helper method to dispose the specified node.
     * </p>
     */
    private final void dispose(Node target) {
        dispose(target, false, true);
    }

    /**
     * <p>
     * Helper method to dispose the specified node.
     * </p>
     * 
     * @param target A target node to dipose.
     * @param clearStack true will clear all operands in target node, false will transfer them into
     *            the previous node.
     * @param recursive true will dispose the previous node if it is empty.
     */
    private final void dispose(Node target, boolean clearStack, boolean recursive) {
        if (nodes.contains(target)) {

            // remove actually
            nodes.remove(target);

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
                if (target.previous.stack.isEmpty()) {
                    dispose(target.previous, clearStack, recursive);
                }
            }
        }
    }

    /**
     * <p>
     * Create new node after the specified node.
     * </p>
     * 
     * @param index A index node.
     * @return A created node.
     */
    private final Node createNodeAfter(Node index) {
        Node created = new Node(index.id + "+");

        // switch line number
        created.lineNumber = index.lineNumber;
        index.lineNumber = -1;

        // switch previous and next nodes
        // index -> created -> next
        link(index, created, index.next);

        if (index.destination == null) {
            index.destination = created;
        }

        // insert to node list
        nodes.add(nodes.indexOf(index) + 1, created);

        // API definition
        return created;
    }

    /**
     * <p>
     * Retrieve the asossiated node of the specified label.
     * </p>
     * 
     * @param label A label for node.
     * @return An asossiated and cached node.
     */
    private final Node getNode(Label label) {
        Node node = (Node) label.info;

        // search cached node
        if (node == null) {
            label.info = node = new Node(counter++);
        }

        // API definition
        return node;
    }

    /**
     * <p>
     * Link all nodes as order of appearance.
     * </p>
     * 
     * @param nodes A sequence of nodes.
     * @return A last node.
     */
    private final Node link(Node... nodes) {
        int size = nodes.length - 1;

        for (int i = 0; i < size; i++) {
            Node prev = nodes[i];
            Node next = nodes[i + 1];

            if (prev != null) prev.next = next;
            if (next != null) next.previous = prev;
        }
        return nodes[size];
    }

    /**
     * <p>
     * Helper method to merge all conditional operands.
     * </p>
     */
    private final void merge(Node node) {
        if (node == null) {
            return;
        }

        SequentialConditionInfo info = new SequentialConditionInfo(node);

        if (info.conditions.isEmpty()) {
            return;
        }

        // Search and merge the sequencial conditional operands in this node from right to left.
        int start = info.start;
        OperandCondition left = null;
        OperandCondition right = (OperandCondition) node.peek(start);

        for (int index = 1; index < info.conditions.size(); index++) {
            left = (OperandCondition) node.peek(start + index);

            if (info.canMerge(left, right)) {
                // Merge two adjucent conditional operands.
                right = new OperandCondition(left, (OperandCondition) node.remove(--start + index));

                node.set(start + index, right);
            } else {
                right = left;
                left = null;
            }
        }

        // If the previous node is terminated by conditional operand and the target node is started
        // by conditional operand, we should try to merge them.
        if (info.conditionalHead && node.previous != null) {
            Operand operand = node.previous.peek(0);

            if (operand instanceof OperandCondition) {
                OperandCondition condition = (OperandCondition) operand;

                if (info.canMerge(condition, right) && condition.elze == node) {
                    dispose(node);

                    // Merge recursively
                    merge(node.previous);
                }
            }
        }
    }

    /**
     * Record the current instruction.
     */
    private final void record(int opcode) {
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
     * <p>
     * Pattern matching for the recent instructions.
     * </p>
     * 
     * @param opcodes A sequence of opecodes to match.
     * @return A result.
     */
    private final boolean match(int... opcodes) {
        root: for (int i = 0; i < opcodes.length; i++) {
            int record = records[(recordIndex + i + records.length - opcodes.length) % records.length];

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

            default:
                if (record != opcodes[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * <p>
     * Write increment/decrement code.
     * </p>
     * 
     * @param context A current context value.
     * @param type A current context type.
     * @param increase Increment or decrement.
     * @param post Post or pre.
     * @return A suitable code.
     */
    private final Expression increment(Expression context, Class type, boolean increase, boolean post) {
        UnaryExpr.Operator operator;

        if (post) {
            operator = increase ? UnaryExpr.Operator.POSTFIX_INCREMENT : UnaryExpr.Operator.POSTFIX_DECREMENT;
        } else {
            operator = increase ? UnaryExpr.Operator.PREFIX_INCREMENT : UnaryExpr.Operator.PREFIX_DECREMENT;
        }
        return new UnaryExpr(context, operator);
    }

    /**
     * <p>
     * Check static method.
     * </p>
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
     * Manage local variables.
     * 
     * @version 2018/10/04 12:53:34
     */
    private class LocalVariables {

        /** The current processing method is static or not. */
        private final boolean isStatic;

        /** The parameter size. */
        private final int parameterSize;

        /** The max size of variables. */
        private int max = 0;

        /** The ignorable variable index. */
        private final List<Integer> ignores = new ArrayList();

        /** The local variable manager. */
        private final Map<Integer, LocalVariable> locals = new HashMap();

        /**
         * @param isStatic
         */
        private LocalVariables(boolean isStatic, Type[] parameterTypes) {
            this.isStatic = isStatic;
            this.parameterSize = parameterTypes.length;

            for (int i = 0; i < parameterTypes.length; i++) {
                locals.put(i, new LocalVariable(i, load(parameterTypes[i]), true));
            }
        }

        /**
         * <p>
         * Compute the identified qualified local variable name for ECMAScript.
         * </p>
         * 
         * @param order An order by which this variable was declared.
         * @return An identified local variable name for ECMAScript.
         */
        private Expression name(int order) {
            return name(order, 0);
        }

        /**
         * <p>
         * Compute the identified qualified local variable name for ECMAScript.
         * </p>
         * 
         * @param order An order by which this variable was declared.
         * @return An identified local variable name for ECMAScript.
         */
        private Expression name(int order, int opcode) {
            // ignore long or double second index
            switch (opcode) {
            case LLOAD:
            case LSTORE:
            case DLOAD:
            case DSTORE:
                ignores.add(order + 1);
                break;
            }

            // order 0 means "this", but static method doesn't have "this" variable
            if (!isStatic) {
                order--;
            }

            if (order == -1) {
                return new ThisExpr();
            }

            // Compute local variable name
            return locals.computeIfAbsent(order, key -> new LocalVariable(key, load(opcode), false)).use();
        }

        /**
         * Rename local variable.
         * 
         * @param index
         * @param name
         */
        private void name(int index, String name) {
            if (!isStatic) {
                index--;
            }

            LocalVariable local = locals.get(index);

            if (local != null) {
                local.name.setIdentifier(name);
            }
        }

        /**
         * List up all valid variable names.
         * 
         * @return
         */
        private List<Expression> names() {
            List<Expression> names = new ArrayList();

            for (int i = isStatic ? 0 : 1; i < max; i++) {
                if (!ignores.contains(i)) {
                    names.add(name(i));
                }
            }
            return names;
        }

        /**
         * <p>
         * Find {@link InferredType} for the specified position.
         * </p>
         * 
         * @param position
         * @return
         */
        private InferredType type(int position) {
            // order 0 means "this", but static method doesn't have "this" variable
            if (!isStatic) {
                position--;
            }

            if (position == -1) {
                return new InferredType(clazz);
            }

            LocalVariable local = locals.get(position);

            if (local == null) {
                return new InferredType();
            } else {
                return new InferredType(load(local.declarator.getType()));
            }
        }
    }

    /**
     * @version 2018/10/04 23:19:59
     */
    private static class LocalVariable {

        /** The variable name. */
        private final SimpleName name = new SimpleName();

        /** The variable model. */
        private final VariableDeclarator declarator = new VariableDeclarator();

        /** The initialization state. */
        private boolean initialized;

        /**
         * Create local variable with index.
         * 
         * @param index A local index.
         * @param initialized A initialization state. (for parameter)
         */
        private LocalVariable(int index, Class type, boolean initialized) {
            this.name.setIdentifier("local" + index);
            this.declarator.setName(name);
            this.declarator.setType(type);
            this.initialized = initialized;
        }

        /**
         * Aquire local variable model.
         * 
         * @return
         */
        private Expression use() {
            if (initialized == false) {
                initialized = true;
                return new VariableDeclarationExpr(declarator);
            } else {
                return new NameExpr(declarator.getName());
            }
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
                if (operand instanceof OperandCondition == false) {
                    // non-conditional operand is found
                    if (conditions.isEmpty()) {
                        // if (operand == Return) {
                        // returned = true;
                        // }
                        // conditional operand is not found as yet, so we should continue to search
                        continue;
                    } else {
                        // stop searching
                        break;
                    }
                }

                // conditional operand is found
                OperandCondition condition = (OperandCondition) operand;

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
                    Node created = createNodeAfter(base);

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
                    Node created = createNodeAfter(base);

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
                }
            }
        }

        /**
         * <p>
         * Test whether target conditions is able to merge.
         * </p>
         * 
         * @param left A left condition.
         * @param right A right condition.
         * @return A result.
         */
        private boolean canMerge(OperandCondition left, OperandCondition right) {
            return left.then == right.then || left.then == right.elze;
        }
    }
}
