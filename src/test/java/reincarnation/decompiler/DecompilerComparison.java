
/*
 * Copyright (C) 2023 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package reincarnation.decompiler;

import java.util.List;

public class DecompilerComparison {

    public static void main(String[] args) {
        for (Decompiler decompiler : List.of(new CFRDecompiler(), new ReincarnationDecompiler())) {
            try {
                System.out.println(decompiler.decompile(DecompilerComparison.class));
            } catch (Exception e) {
                throw new Error("FAIL!");
            }
        }
    }
}
