package org.javaparser.support.matcher;

import com.github.javaparser.ast.type.Type;
import com.github.javaparser.resolution.types.ResolvedType;

/**
 * @author liang
 * @date 2022/8/15
 */
public class PoiMatcher {
    public static boolean isMatchName(String s) {
        return s.contains("poiid") || s.contains("shopid");
    }

    public static boolean isMatchType(Type parameterType) {
        return "int".equals(parameterType.getElementType().asString().toLowerCase()) ||
            "integer".equals(parameterType.getElementType().asString().toLowerCase());
    }

    public static boolean isMatchResolveType(ResolvedType resolvedType) {
        return "int".equals(resolvedType.asReferenceType().toString()) ||
            "integer".equals(resolvedType.asReferenceType().toString());
    }

}
