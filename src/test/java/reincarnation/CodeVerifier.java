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

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import bee.UserInterface;
import bee.api.Command;
import bee.util.JavaCompiler;
import kiss.I;
import reincarnation.Code.BooleanParam;
import reincarnation.Code.ByteParam;
import reincarnation.Code.CharParam;
import reincarnation.Code.DoubleParam;
import reincarnation.Code.FloatParam;
import reincarnation.Code.Int;
import reincarnation.Code.IntParam;
import reincarnation.Code.LongParam;
import reincarnation.Code.ShortParam;
import reincarnation.Code.TextParam;

/**
 * @version 2018/10/04 14:37:22
 */
public class CodeVerifier {

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Int code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(IntParam code) {
        int[] params = {Integer.MIN_VALUE, -10, -1, 0, 1, 10, Integer.MAX_VALUE};
        IntParam recompiled = recompile(code);

        for (int param : params) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Long code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(LongParam code) {
        long[] params = {Long.MIN_VALUE, -10L, -1L, 0L, 1L, 10L, Long.MAX_VALUE};
        LongParam recompiled = recompile(code);

        for (long param : params) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Float code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(FloatParam code) {
        float[] params = {Float.MIN_VALUE, -1F, -0.5F, 0F, 0.5F, 1.0F, Float.MAX_VALUE};
        FloatParam recompiled = recompile(code);

        for (float param : params) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Double code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(DoubleParam code) {
        double[] params = {Double.MIN_VALUE, -1D, -0.5D, 0D, 0.5D, 1.0D, Double.MAX_VALUE};
        DoubleParam recompiled = recompile(code);

        for (double param : params) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Byte code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(ByteParam code) {
        byte[] params = {Byte.MIN_VALUE, -10, -1, 0, 1, 10, Byte.MAX_VALUE};
        ByteParam recompiled = recompile(code);

        for (byte param : params) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Short code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(ShortParam code) {
        short[] params = {Short.MIN_VALUE, -10, -1, 0, 1, 10, Short.MAX_VALUE};
        ShortParam recompiled = recompile(code);

        for (short param : params) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Char code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(CharParam code) {
        char[] params = {Character.MIN_VALUE, '0', ' ', 'A', Character.MAX_VALUE};
        CharParam recompiled = recompile(code);

        for (char param : params) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Boolean code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(BooleanParam code) {
        boolean[] params = {false, true};
        BooleanParam recompiled = recompile(code);

        for (boolean param : params) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Text code) {
        assert code.run() == recompile(code).run() : code(code);
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(TextParam code) {
        String[] params = {"", " ", "a", "A", "あ", "\\", "\t"};
        TextParam recompiled = recompile(code);

        for (String param : params) {
            assert code.run(param) == recompiled.run(param) : code(code);
        }
    }

    /**
     * Recompile and recompile code.
     * 
     * @param code
     * @return
     */
    private <T extends Code> T recompile(T code) {
        Class<?> originalClass = code.getClass();
        Class<?> enclosingClass = originalClass.getEnclosingClass();
        String fqcn = originalClass.getName();

        CompilationUnit unit = Reincarnation.exhume(code.getClass());

        if (enclosingClass != null) {
            unit = enclose(enclosingClass.getSimpleName(), unit);

            if (originalClass.isAnonymousClass()) {
                fqcn = fqcn.replace("$", "$" + enclosingClass.getSimpleName() + "$");
            }
        }

        String decompiled = unit.toString();
        Silent notifier = new Silent();

        try {
            JavaCompiler compiler = new JavaCompiler(notifier);
            compiler.addSource(unit.getType(0).getNameAsString(), decompiled);
            compiler.addCurrentClassPath();

            ClassLoader loader = compiler.compile();
            Class<?> loadedClass = loader.loadClass(fqcn);
            assert originalClass != loadedClass; // load from different classloader

            Constructor constructor = loadedClass.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return (T) constructor.newInstance((Object[]) Array.newInstance(Object.class, constructor.getParameterCount()));
        } catch (Exception e) {
            throw I.quiet(e);
        } catch (Error e) {
            throw Failuer.type("Compile Error")
                    .reason(e)
                    .reason("=================================================")
                    .reason(notifier.message)
                    .reason("-------------------------------------------------")
                    .reason(format(decompiled))
                    .reason("=================================================");
        }
    }

    /**
     * Enclose by the specified class.
     * 
     * @param className A simple class name.
     * @param base A target to enclose.
     * @return An enclosed unit.
     */
    private CompilationUnit enclose(String className, CompilationUnit base) {
        CompilationUnit enclose = new CompilationUnit(base.getPackageDeclaration().orElseThrow(), base.getImports(), new NodeList(), null);

        // create enclosing class
        ClassOrInterfaceDeclaration clazz = enclose.addClass(className);

        base.getTypes().forEach(type -> {
            type.addModifier(Modifier.STATIC);

            clazz.addMember(type);
        });
        return enclose;
    }

    /**
     * Show decompiled formated code.
     * 
     * @param code
     * @return
     */
    private Throwable code(Code code) {
        return Failuer.type("Invalid Decompilation")
                .reason("=================================================")
                .reason(format(Reincarnation.exhume(code.getClass()).toString()))
                .reason("=================================================");
    }

    /**
     * Format as line-numbered code.
     * 
     * @param code
     * @return
     */
    private String[] format(String code) {
        String[] lines = code.split("\r\n");
        int max = (int) (Math.log10(lines.length) + 1);

        for (int i = 0; i < lines.length; i++) {
            int number = i + 1;
            int size = (int) (Math.log10(number) + 1);
            lines[i] = "0".repeat(max - size) + number + "    " + lines[i];
        }
        return lines;
    }

    /**
     * @version 2018/10/04 8:48:47
     */
    private static class Silent extends UserInterface {

        /** The message buffer. */
        private StringBuilder message = new StringBuilder();

        /**
         * {@inheritDoc}
         */
        @Override
        protected void write(String message) {
            this.message.append(message).append("\r\n");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Appendable getInterface() {
            return System.out;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void startCommand(String name, Command command) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void endCommand(String name, Command command) {
        }
    }
}
