package org.javaparser.support;

import com.github.javaparser.ast.body.Parameter;

import java.util.stream.Stream;

/**
 * @author liang
 * @date 2022/8/13
 */
public abstract class AbstractParameterHandler implements ParameterHandler{

    @Override
    public Stream<Parameter> changeParameterToMulti(Parameter parameter) {
            if (isMatch(parameter)){
                return handleParameter(parameter);
            }
            return Stream.of(parameter);
    }

    protected abstract Stream<Parameter> handleParameter(Parameter parameter);

}
