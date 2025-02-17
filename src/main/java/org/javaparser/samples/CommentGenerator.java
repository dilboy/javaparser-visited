package org.javaparser.samples;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CommentGenerator {

    private static final String FILE_PATH = "src/main/java/org/javaparser/samples/ReversePolishNotation.java";

    private static final Pattern FIND_UPPERCASE = Pattern.compile("(.)(\\p{Upper})");

    public static void main(String[] args) throws Exception {

        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));

        List<MethodDeclaration> methodDeclarations = new ArrayList<>();
        VoidVisitorAdapter<List<MethodDeclaration>> unDocumentedMethodCollector = new UnDocumentedMethodCollector();
        unDocumentedMethodCollector.visit(cu, methodDeclarations);

//        cu.findAll(MethodDeclaration.class).stream()
//                .filter(md -> !md.getJavadoc().isPresent())
//                .forEach(md -> md.setJavadocComment(generateJavaDoc(md)));

        cu.findAll(MethodDeclaration.class).stream()
                .filter(md->md.getParameters().size()>0)
                .forEach(md->md.setParameters(convert(md.getParameters())));

        System.out.println(cu.toString());
    }

    private static NodeList<Parameter> convert(NodeList<Parameter> parameters) {
        NodeList<Parameter> result = new NodeList<>();
        for (Parameter parameter : parameters) {
            Parameter target = null;
            if(matchInt(parameter)){
                target = changeIntToLong(parameter);
            }else {
                target = parameter;
            }
            result.add(target);
        }
        return result;
    }

    private static Parameter changeIntToLong(Parameter parameter) {
        parameter.setType(Long.class);
        return parameter;
    }

    private static boolean matchInt(Parameter parameter) {
        return parameter.getType().asString().equals("int") || parameter.getType().asString().equals("Integer");
    }

    private static class UnDocumentedMethodCollector extends VoidVisitorAdapter<List<MethodDeclaration>> {

        @Override
        public void visit(MethodDeclaration md, List<MethodDeclaration> collector) {
            super.visit(md, collector);
            // value == null
            if (!md.getJavadoc().isPresent()) {
                collector.add(md);
            }
        }
    }

    private static String generateJavaDoc(MethodDeclaration md){
        return " " + camelCaseToTitleFormat(md.getNameAsString()) + "test";
    }

    private static String camelCaseToTitleFormat(String text){
        String split = FIND_UPPERCASE.matcher(text).replaceAll("$1 $2");
        return split.substring(0,1).toUpperCase() + split.substring(1);
    }
}
