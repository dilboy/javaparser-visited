package org.javaparser.support.impl;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.resolution.types.ResolvedType;
import com.google.common.base.Preconditions;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.javaparser.support.MethodHandler;
import org.javaparser.support.ParameterHandler;
import org.javaparser.support.Tuple;
import org.javaparser.support.matcher.ParameterMatcher;
import org.javaparser.support.matcher.PoiMatcher;

import static org.javaparser.support.util.NodeListUtils.getReturnType;

/**
 * 接口的方法改变,碰见poiid直接新增方法
 *
 * @author liang
 * @date 2022/8/14
 */
public class InterfaceMethodHandlerImpl implements MethodHandler {

    private ParameterHandler parameterHandler = new IntToLongParameterHandlerImpl();

    @Override
    public Stream<MethodDeclaration> changeMethodToMulti(MethodDeclaration method) {
        if (isMatch(method)) {
            MethodDeclaration clone = method.clone();
            clone.setParameters(clone.getParameters().stream().flatMap(parameterHandler::changeParameterToMulti).collect(Collectors.toCollection(NodeList::new)));
            clone.setName(clone.getNameAsString() + "WithLong");
            method.getParentNode().ifPresent(x->{
                if (x instanceof ClassOrInterfaceDeclaration){
                    ((ClassOrInterfaceDeclaration) x).addMember(clone);
                }
            });
            transformReturn(clone);
            return Stream.of(clone);
        }else if (method.getParameters().stream().anyMatch(p->p.getNameAsString().contains("poiId"))){
            method.setParameters(method.getParameters().stream().flatMap(parameterHandler::changeParameterToMulti).collect(Collectors.toCollection(NodeList::new)));
        }
        return Stream.of();

    }

    private void transformReturn(MethodDeclaration methodDeclaration) {
        Preconditions.checkNotNull(methodDeclaration);
        List<ReturnStmt> all = methodDeclaration.findAll(ReturnStmt.class);
        Optional<Tuple<String, ResolvedType>> returnType = getReturnType(all);
        if (returnType.isPresent()){
            String returnName = returnType.get().getKey1();
            ResolvedType resolvedType = returnType.get().getKey2();
            if (PoiMatcher.isMatchName(returnName)&&PoiMatcher.isMatchResolveType(resolvedType)){
                //如果匹配,替换method type
                methodDeclaration.setType(new ClassOrInterfaceType("Long"));
            }
        }
    }

    @Override
    public boolean isMatch(MethodDeclaration method) {
        return method.getAnnotations().stream().anyMatch(x -> x.getNameAsString().contains("Mapping")) && method.getParameters().stream().anyMatch(x -> ParameterMatcher.ifMatch(x));
    }

}
