package org.javaparser.support.matcher;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;

import static org.javaparser.support.matcher.PoiMatcher.isMatchName;
import static org.javaparser.support.matcher.PoiMatcher.isMatchType;

/**
 * @author liang
 * @date 2022/8/14
 */
public class ParameterMatcher {

    public static boolean ifMatch(Parameter parameter) {
        Type parameterType = parameter.getType();
        if (parameterType.isArrayType() && !isMatchType(parameterType.getElementType())) {
            return false;
        } else if (!isMatchType(parameterType)) {
            return false;
        }
        return isMatchName(parameter.getNameAsString().toLowerCase());
    }

}
