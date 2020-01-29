/*
 * Copyright (C) 2018 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation;

import reincarnation.coder.Coder;

class LocalVariableManager {

    OperandLocalVariable getVariableAt(int position, int opecode) {
        return null;
    }

    InferredType getTypeAt(int position) {
        return null;
    }

    /**
     * 
     */
    class OperandLocalVariable extends Operand {

        private String name;

        private boolean declarable;

        /**
         * {@inheritDoc}
         */
        @Override
        protected void writeCode(Coder coder) {
            coder.writeLocalVariable(type.v, name, declarable);
        }
    }
}
