package org.javaparser.examples.chapter5;

class A {

    public void foo(Object param) {
        System.out.println(1);
        System.out.println("hi");
        System.out.println(param);
    }

    public void foo2(Object param) {
        System.out.println("hi");
        System.out.println(param);
        System.out.println(1);
    }
}