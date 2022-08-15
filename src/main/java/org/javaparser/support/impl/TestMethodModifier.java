package org.javaparser.support.impl;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import org.javaparser.support.matcher.PoiMatcher;

/**
 * @author liang
 * @date 2022/8/14
 */
public class TestMethodModifier extends ModifierVisitor<Void> {

    @Override
    public Visitable visit(MethodDeclaration n, Void arg) {
        MethodDeclaration methodDeclaration = (MethodDeclaration) super.visit(n, arg);
        if (methodDeclaration.getAnnotations().stream().noneMatch(x->x.getNameAsString().contains("Mapping"))){
            if (hasPoiParameter(methodDeclaration)){
                methodDeclaration.getParameters().stream()
                    .filter(x-> PoiMatcher.isMatchName(x.getNameAsString()))
                    .filter(x->PoiMatcher.isMatchType(x.getType()))
                    .forEach(x->x.setType("Long"));
            }
        }
        return methodDeclaration;
    }

    private boolean hasPoiParameter(MethodDeclaration methodDeclaration) {
        return methodDeclaration.getParameters().stream()
            .anyMatch(x->x.getNameAsString().equals("poiId"));
    }


}
