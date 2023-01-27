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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;

import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IBytecodeProvider;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;

import psychopath.Location;
import psychopath.Locator;

public class FernFlowerDecompiler implements Decompiler {

    /**
     * {@inheritDoc}
     */
    @Override
    public String decompile(Class target) {
        Map<String, Object> options = new HashMap();
        options.put(IFernflowerPreferences.REMOVE_SYNTHETIC, "1");
        options.put(IFernflowerPreferences.INDENT_STRING, "\t");
        options.put(IFernflowerPreferences.LOG_LEVEL, "WARN");

        IBytecodeProvider provider = (externalPath, internalPath) -> {
            return Locator.file(externalPath).bytes();
        };

        String[] decompiled = new String[1];

        IResultSaver saver = new IResultSaver() {

            @Override
            public void saveFolder(String path) {
                System.out.println("Save foler " + path);
            }

            @Override
            public void saveDirEntry(String path, String archiveName, String entryName) {
                System.out.println("Save dir entry  " + path);
            }

            @Override
            public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
                decompiled[0] = content;
            }

            @Override
            public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {
                System.out.println("Save class entry " + path + "  " + qualifiedName + "  " + entryName);
            }

            @Override
            public void createArchive(String path, String archiveName, Manifest manifest) {
                System.out.println("Save archive " + path + "   " + archiveName);
            }

            @Override
            public void copyFile(String source, String path, String entryName) {
                System.out.println("Copy  file " + path + "  " + source);
            }

            @Override
            public void copyEntry(String source, String path, String archiveName, String entry) {
                System.out.println("Copy  entry " + path + "  " + source);
            }

            @Override
            public void closeArchive(String path, String archiveName) {
                System.out.println("clise archive " + path);
            }
        };

        IFernflowerLogger logger = new IFernflowerLogger() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void writeMessage(String message, Severity severity) {
                System.out.println(message);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void writeMessage(String message, Severity severity, Throwable t) {
                System.out.println(message);
                t.printStackTrace(System.out);
            }
        };

        List<Class> classes = new ArrayList();
        classes.add(target);

        Class parent = target.getEnclosingClass();
        while (parent != null) {
            classes.add(parent);
            parent = parent.getEnclosingClass();
        }

        Fernflower fernflower = new Fernflower(provider, saver, options, logger);
        for (Class clazz : classes) {
            fernflower.addSource(locate(clazz));
        }
        fernflower.decompileContext();

        return decompiled[0];
    }

    private java.io.File locate(Class clazz) {
        Location root = Locator.locate(clazz);
        return root.asDirectory().file(clazz.getName().replace('.', '/').concat(".class")).asJavaFile();
    }
}
