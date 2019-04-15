package org.javaparser.examples.chapter2;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.FileInputStream;

public class VoidVisitorStarter {

    private static final String FILE_PATH = "src/main/java/org/javaparser/examples/samples/ReversePolishNotation.java";

    public static void main(String[] args) throws Exception {

        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(FILE_PATH));

    }

}
