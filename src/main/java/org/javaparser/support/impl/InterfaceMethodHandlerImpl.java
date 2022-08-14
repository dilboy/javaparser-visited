package org.javaparser.support.impl;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.javaparser.support.MethodHandler;
import org.javaparser.support.ParameterHandler;
import org.javaparser.support.matcher.PoiIdNameMatcher;

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
            return Stream.of(clone);
        }else if (method.getParameters().stream().anyMatch(p->p.getNameAsString().contains("poiId"))){
            method.setParameters(method.getParameters().stream().flatMap(parameterHandler::changeParameterToMulti).collect(Collectors.toCollection(NodeList::new)));
        }
        return Stream.of();

    }

    @Override
    public boolean isMatch(MethodDeclaration method) {
        return method.getAnnotations().stream().anyMatch(x -> x.getNameAsString().contains("Mapping")) && method.getParameters().stream().anyMatch(x -> PoiIdNameMatcher.ifMatch(x.getNameAsString()));
    }

}
