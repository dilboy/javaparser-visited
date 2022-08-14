package org.javaparser.support.impl;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;

import java.util.stream.Stream;

import org.javaparser.support.AbstractNodeListHandler;
import org.javaparser.support.ParameterHandler;

/**
 * @author liang
 * @date 2022/8/14
 */
public class ParameterNodeListHandlerImpl extends AbstractNodeListHandler<Parameter> {

    private static final ParameterHandler handler = new IntToLongParameterHandlerImpl();

    @Override
    protected ParameterHandler getParameterHandler() {
        return handler;
    }

    @Override
    protected Stream<Parameter> handleOriginStream(Parameter x) {
        return Stream.of(x);
    }

}
