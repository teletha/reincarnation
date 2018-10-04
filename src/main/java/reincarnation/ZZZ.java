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

import java.nio.file.Path;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.SymbolResolver;
import com.github.javaparser.resolution.types.ResolvedType;

/**
 * @version 2018/10/05 8:16:53
 */
public class ZZZ {

    private static int value;

    public static void main(String[] args) throws Exception {
        ZZZ.value = 10;

        ParserConfiguration config = new ParserConfiguration();
        config.setSymbolResolver(new SymbolResolver() {

            @Override
            public <T> T toResolvedType(Type javaparserType, Class<T> resultClass) {
                return null;
            }

            @Override
            public <T> T resolveDeclaration(Node node, Class<T> resultClass) {
                return null;
            }

            @Override
            public ResolvedType calculateType(Expression expression) {
                return null;
            }
        });
        JavaParser.setStaticConfiguration(config);
        CompilationUnit parse = JavaParser.parse(Path.of("src/main/java/reincarnation/ZZZ.java"));
        parse.walk(AssignExpr.class, node -> {
            System.out.println(node.getClass() + "   " + node);

            Expression target = node.getTarget();
            System.out.println(target.getClass());

            FieldAccessExpr f = target.asFieldAccessExpr();
            Expression scope = f.getScope();
            System.out.println(scope.getClass());
            System.out.println(f.resolve());
        });
    }
}
