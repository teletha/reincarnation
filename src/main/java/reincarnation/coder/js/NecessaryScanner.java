/*
 * Copyright (C) 2026 The REINCARNATION Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package reincarnation.coder.js;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Finds the classes annotated by {@link Necessary} in the code source of the entry class.
 */
final class NecessaryScanner {

    /** The maximum number of the scanned class files. */
    private static final int LIMIT = 50000;

    /**
     * Hide constructor.
     */
    private NecessaryScanner() {
    }

    /**
     * Scan the classes annotated by {@link Necessary}.
     * 
     * @param entry An entry class.
     * @return A found classes.
     */
    static Set<Class> scan(Class entry) {
        Set<Class> found = new LinkedHashSet();

        try {
            URL location = entry.getProtectionDomain().getCodeSource().getLocation();
            if (location == null) {
                return found;
            }

            Path root = Path.of(location.toURI());
            ClassLoader loader = entry.getClassLoader();
            if (loader == null) {
                loader = ClassLoader.getSystemClassLoader();
            }

            if (Files.isDirectory(root)) {
                scanDirectory(root, loader, found);
            } else if (root.toString().endsWith(".jar")) {
                scanJar(root, loader, found);
            }
        } catch (Throwable e) {
            // The scan is best effort, so the failure is ignored.
        }
        return found;
    }

    /**
     * Scan the class directory.
     * 
     * @param root A root directory.
     * @param loader A class loader.
     * @param found A found classes.
     */
    private static void scanDirectory(Path root, ClassLoader loader, Set<Class> found) throws Exception {
        try (var stream = Files.walk(root)) {
            stream.filter(x -> x.toString().endsWith(".class")).limit(LIMIT).forEach(x -> {
                String name = root.relativize(x).toString().replace(File.separatorChar, '.');
                load(name.substring(0, name.length() - 6), loader, found);
            });
        }
    }

    /**
     * Scan the jar file.
     * 
     * @param root A jar file.
     * @param loader A class loader.
     * @param found A found classes.
     */
    private static void scanJar(Path root, ClassLoader loader, Set<Class> found) throws Exception {
        try (JarFile jar = new JarFile(root.toFile())) {
            var entries = jar.entries();
            int count = 0;

            while (entries.hasMoreElements() && count++ < LIMIT) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.endsWith(".class")) {
                    load(name.substring(0, name.length() - 6).replace('/', '.'), loader, found);
                }
            }
        }
    }

    /**
     * Load and check the class.
     * 
     * @param name A class name.
     * @param loader A class loader.
     * @param found A found classes.
     */
    private static void load(String name, ClassLoader loader, Set<Class> found) {
        if (name.startsWith("module-info") || name.endsWith("package-info")) {
            return;
        }
        try {
            Class type = Class.forName(name, false, loader);
            if (type.isAnnotationPresent(Necessary.class)) {
                found.add(type);
            }
        } catch (Throwable e) {
            // The class may reference a missing dependency, so it is ignored.
        }
    }
}
