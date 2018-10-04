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

import java.lang.reflect.Constructor;

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
        assert code.run() == recompile(code).run();
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
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Long code) {
        assert code.run() == recompile(code).run();
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
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Float code) {
        assert code.run() == recompile(code).run();
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
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Double code) {
        assert code.run() == recompile(code).run();
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
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Byte code) {
        assert code.run() == recompile(code).run();
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
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Short code) {
        assert code.run() == recompile(code).run();
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
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Char code) {
        assert code.run() == recompile(code).run();
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
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Boolean code) {
        assert code.run() == recompile(code).run();
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
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Verify decompiled code.
     * 
     * @param code A target code to verify.
     */
    protected final void verify(Code.Text code) {
        assert code.run() == recompile(code).run();
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
            assert code.run(param) == recompiled.run(param);
        }
    }

    /**
     * Recompile and recompile code.
     * 
     * @param code
     * @return
     */
    private <T extends Code> T recompile(T code) {
        Silent notifier = new Silent();
        String decompiled = Reincarnation.exhume(code.getClass()).toString();

        try {
            JavaCompiler compiler = new JavaCompiler(notifier);
            compiler.addSource(code.getSimpleName(), decompiled);
            compiler.addCurrentClassPath();

            ClassLoader loader = compiler.compile();
            Class<?> loadedClass = loader.loadClass(code.getName());
            assert loadedClass != code.getClass(); // load from different class loaders

            Constructor<?> constructor = loadedClass.getDeclaredConstructor(getClass());
            constructor.setAccessible(true);
            return (T) constructor.newInstance(this);
        } catch (Exception e) {
            throw I.quiet(e);
        } catch (Error e) {
            throw Failuer.type("Compile Error")
                    .reason("=================================================")
                    .reason(notifier.message)
                    .reason("-------------------------------------------------")
                    .reason(format(decompiled))
                    .reason("=================================================");
        }
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
            this.message.append(message);
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
