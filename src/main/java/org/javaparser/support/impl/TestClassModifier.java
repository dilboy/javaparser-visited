package org.javaparser.support.impl;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;

import org.javaparser.support.MethodHandler;

/**
 * @author liang
 * @date 2022/8/14
 */
public class TestClassModifier extends ModifierVisitor<Void> {

    private MethodHandler interfaceMethodHandler = new InterfaceMethodHandlerImpl();
    private MethodHandler normalMethodHandler = new NormalMethodHandlerImpl();

    @Override
    public Visitable visit(ClassOrInterfaceDeclaration n, Void arg) {
        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) super.visit(n, arg);
        if (isInterface(classOrInterfaceDeclaration)){
            classOrInterfaceDeclaration.getMethods().stream().flatMap(interfaceMethodHandler::changeMethodToMulti)
                .forEach(classOrInterfaceDeclaration::addMember);
        }else{
            classOrInterfaceDeclaration.getMethods().stream().forEach(normalMethodHandler::changeMethodToMulti);
        }
        return classOrInterfaceDeclaration;
    }

    private boolean isInterface(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        return classOrInterfaceDeclaration.getAnnotations().stream()
            .anyMatch(x->x.getNameAsString().toLowerCase().contains("controller"));
    }

    private boolean hasPoiParameter(MethodDeclaration methodDeclaration) {
        return methodDeclaration.getParameters().stream()
            .anyMatch(x->x.getNameAsString().equals("poiId"));
    }


}
