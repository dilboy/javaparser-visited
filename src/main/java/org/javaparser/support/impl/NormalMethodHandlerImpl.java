package org.javaparser.support.impl;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.javaparser.support.MethodHandler;
import org.javaparser.support.ParameterHandler;

/**
 * 一般接口的方法改变,碰见poiid直接修改paramter方法
 *
 * @author liang
 * @date 2022/8/14
 */
public class NormalMethodHandlerImpl implements MethodHandler {

    private ParameterHandler parameterHandler = new IntToLongParameterHandlerImpl();

    @Override
    public Stream<MethodDeclaration> changeMethodToMulti(MethodDeclaration method) {
        method.setParameters(method.getParameters().stream().flatMap(parameterHandler::changeParameterToMulti).collect(Collectors.toCollection(NodeList::new)));
        return Stream.of(method);

    }

    @Override
    public boolean isMatch(MethodDeclaration method) {
        return method.getParameters().stream().anyMatch(x -> x.getNameAsString().contains("poiId"));
    }

}
