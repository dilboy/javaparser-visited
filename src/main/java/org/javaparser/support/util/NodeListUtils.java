package org.javaparser.support.util;

import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.resolution.types.ResolvedType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.javaparser.support.Tuple;

/**
 * @author liang
 * @date 2022/8/14
 */
public class NodeListUtils {
    public static Optional<Tuple<String, ResolvedType>> getReturnType(List<ReturnStmt> all) {
        return all.stream().map(rs->{
            if (rs.isReturnStmt()){
                return rs.findAll(NameExpr.class).stream().map(nameExpr -> {
                    ResolvedType resolvedType = nameExpr.calculateResolvedType();
                    return new Tuple<String,ResolvedType>(nameExpr.getNameAsString(),resolvedType);
                }).findAny().get();
            }
            return null;
        }).findAny();
    }
}
