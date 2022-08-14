package org.javaparser.liang.demo;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;
import java.io.FileNotFoundException;

import org.javaparser.support.impl.TestClassModifier;

/**
 * @author liang
 * @date 2022/8/14
 */
public class VisitDemo {

    private static final String INTERFACE_FILE_PATH = "src/main/java/org/javaparser/liang/demo/DemoClass.java";
    private static final String NORMAL_FILE_PATH = "src/main/java/org/javaparser/liang/demo/DemoNormalClass.java";

    public static void main(String[] args) throws FileNotFoundException {
        StaticJavaParser.getConfiguration()
            .setSymbolResolver( new JavaSymbolSolver(new CombinedTypeSolver(new ReflectionTypeSolver())));
        CompilationUnit cu = StaticJavaParser.parse(new File(INTERFACE_FILE_PATH));

        /**
         * visit
         */
//        new TestMethodModifier().visit(cu,null);
        new TestClassModifier().visit(cu,null);

        System.out.println(cu.toString());
    }

}
