package org.javaparser.examples.chapter2;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.io.FileInputStream;
import java.util.regex.Pattern;

public class ModifyingVisitorComplete {

    private static final String FILE_PATH = "src/main/java/org/javaparser/samples/ReversePolishNotation.java";

    private static final Pattern LOOK_AHEAD_THREE = Pattern.compile("(\\d)(?=(\\d{3})+$)");

    public static void main(String[] args) throws Exception {

        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(FILE_PATH));

        ModifierVisitor<?> numericLiteralVisitor = new IntegerLiteralModifier();
        numericLiteralVisitor.visit(cu, null);

        System.out.println(cu.toString());
    }

    private static class IntegerLiteralModifier extends ModifierVisitor<Void> {

        @Override
        public FieldDeclaration visit(FieldDeclaration fd, Void arg) {
            super.visit(fd, arg);
            fd.getVariables().forEach(v ->
                    v.getInitializer().ifPresent(i ->
                            i.ifIntegerLiteralExpr(il ->
                                    v.setInitializer(formatWithUnderscores(il.getValue()))
                            )
                    )
            );
            return fd;
        }
    }

    static String formatWithUnderscores(String value) {
        String withoutUnderscores = value.replaceAll("_", "");
        //通过正则表达式替换Long数字为带下划线的数字(10000000->100_000_000)
        return LOOK_AHEAD_THREE.matcher(withoutUnderscores).replaceAll("$1_");
    }
}
