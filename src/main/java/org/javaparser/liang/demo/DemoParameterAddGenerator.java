package org.javaparser.liang.demo;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.javaparser.examples.chapter2.VoidVisitorComplete;
import org.javaparser.support.ParameterHandler;
import org.javaparser.support.impl.IntToLongParameterHandlerImpl;
import org.javaparser.support.impl.TestMethodModifier;

/**
 * 针对int门店Id的接口,新增一个Long办法的接口
 * 1. 新增接口 --done
 * 2. 更换名称  --done
 * 3. 更换参数类型和参数名称 --done
 * 4. 更换方法内的引用
 *      4.1 (找到int类型的变量
 *      4.2 然后 把int 转换成 Long 来处理
 *      4.3 这个时候结果 要换成long,条件也要换成Long
 *      4.4 如果是method,method要加上withLong的方法
 */
public class DemoParameterAddGenerator {

    private static final String FILE_PATH = "src/main/java/org/javaparser/liang/demo/DemoClass.java";

    private static final Pattern FIND_UPPERCASE = Pattern.compile("(.)(\\p{Upper})");

    static ParameterHandler intToLongParameterHandler = new IntToLongParameterHandlerImpl();

    public static void main(String[] args) throws Exception {

        StaticJavaParser.getConfiguration()
            .setSymbolResolver( new JavaSymbolSolver(new CombinedTypeSolver(new ReflectionTypeSolver())));
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));

        List<MethodDeclaration> methodDeclarations = new ArrayList<>();
        VoidVisitorAdapter<List<MethodDeclaration>> unDocumentedMethodCollector = new UnDocumentedMethodCollector();
        unDocumentedMethodCollector.visit(cu, methodDeclarations);

        cu.getClassByName("DemoClass").ifPresent(x->x.addMethod("liangtest", Modifier.Keyword.PUBLIC));

        cu.findAll(MethodDeclaration.class).stream()
            .filter(md -> md.getParameters().size() > 0)
            .forEach(md -> {
                cu.getClassByName("DemoClass")
                        .ifPresent(x->{
                            MethodDeclaration cloneMethod = md.clone();
                            if (isMatchMethod(cloneMethod)){
                                NodeList<Parameter> parameters = md.getParameters();
                                cloneMethod.setParameters(convert(parameters.stream().map(Parameter::clone).collect(Collectors.collectingAndThen(Collectors.toList(), NodeList::new))));
                                cloneMethod.setName(cloneMethod.getNameAsString()+"withLongPoi");
                                x.getMembers().add(cloneMethod);
                            }
                        });
            });

        System.out.println(cu.toString());

        new VoidVisitorComplete.MethodNamePrinter().visit(cu,null);

        ArrayList<String> list = Lists.newArrayList();
        /**
         * 通过visit获取方法名称
         */
        System.out.println("===================");
        new VoidVisitorComplete.MethodNameCollector().visit(cu, list);
        list.forEach(System.out::println);

        /**
         * 通过resolver搜索方法调用的参数
         */
        System.out.println("===================");
        cu.findAll(AssignExpr.class).forEach(ae -> {
            ResolvedType resolvedType = ae.calculateResolvedType();
            System.out.println(ae.toString() + " is a: " + resolvedType);
        });

        /**
         * visit
         */
        new TestMethodModifier().visit(cu,null);

    }

    private static boolean isMatchMethod(MethodDeclaration cloneMethod) {
        if (cloneMethod.getNameAsString().equals("memoryRecall")){
            return true;
        }
        return false;
    }

    private static NodeList<Parameter> convert(NodeList<Parameter> parameters) {
        return parameters.stream().flatMap(intToLongParameterHandler::changeParameterToMulti)
            .collect(Collectors.toCollection(NodeList::new));
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

    private static String generateJavaDoc(MethodDeclaration md) {
        return " " + camelCaseToTitleFormat(md.getNameAsString()) + "test";
    }

    private static String camelCaseToTitleFormat(String text) {
        String split = FIND_UPPERCASE.matcher(text).replaceAll("$1 $2");
        return split.substring(0, 1).toUpperCase() + split.substring(1);
    }

}
