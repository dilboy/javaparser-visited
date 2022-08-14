package org.javaparser.support;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

import java.util.stream.Stream;

/**
 * @author liang
 * @date 2022/8/13
 */
public interface MethodHandler {

    Stream<MethodDeclaration> changeMethodToMulti(MethodDeclaration method);

    boolean isMatch(MethodDeclaration method);

}
