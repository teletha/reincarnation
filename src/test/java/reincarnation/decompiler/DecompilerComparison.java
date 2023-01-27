
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

import java.util.ArrayList;
import java.util.List;

public class DecompilerComparison {

    public static void main(String[] args) {
        List<String> names = new ArrayList();

        for (Decompiler decompiler : List.of(new ReincarnationDecompiler(), new FernFlowerDecompiler())) {
            try {
                System.out.println(decompiler.decompile(DecompilerComparison.class));

                root: for (int i = 0; i < args.length; i++) {
                    System.out.println(args[i]);

                    if (args[i].equals("stop")) {
                        return;
                    } else {
                        names.add(i % 2 == 0 ? "none" : args[i]);

                        do {
                            if (i == 5) {
                                break root;
                            } else if (i == 8) {
                                continue root;
                            }
                        } while (i++ < 10);

                        System.out.println("END");
                    }
                }

                // I.signal(names).to(name -> {
                // System.out.println(name);
                // });
            } catch (Exception e) {
                System.out.println("Fail to decompile.");
                e.printStackTrace(System.out);
            } finally {
                System.out.println("=====================================");
            }
        }
    }
}
