package org.javaparser.support.impl;

import com.github.javaparser.ast.body.Parameter;

import java.util.stream.Stream;

import org.javaparser.support.AbstractParameterHandler;

/**
 * @author liang
 * @date 2022/8/13
 */
public class IntToLongParameterHandlerImpl extends AbstractParameterHandler {

    protected Stream<Parameter> handleParameter(Parameter parameter) {
        Parameter clone = parameter.clone();
        clone.setType(Long.class);
//        clone.setName(clone.getNameAsString()+"WithLong");
//        parameter.setName(parameter.getNameAsString()+"WithLong");

        return Stream.of(clone);
    }

    @Override
    public boolean isMatch(Parameter parameter) {
        return parameter.getType().asString().equals("int") || parameter.getType().asString().equals("Integer");
    }

}
