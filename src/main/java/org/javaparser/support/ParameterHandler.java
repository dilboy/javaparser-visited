package org.javaparser.support;

import com.github.javaparser.ast.body.Parameter;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author liang
 * @date 2022/8/13
 */
public interface ParameterHandler {

    Stream<Parameter> changeParameterToMulti(Parameter parameter);

    boolean isMatch(Parameter parameter);

}
