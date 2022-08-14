package org.javaparser.liang.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * A Simple Reverse Polish Notation calculator with memory function.
 */
public class DemoNormalClass {

    // What does this do?
    public static int ONE_BILLION = 1000000000;

    private double memory = 0;

    /**
     * Memory Recall uses the number in stored memory, defaulting to 0.
     *
     * @return the double
     */
    @RequestMapping
    public double memoryRecall(int poiId){
        String name = String.valueOf(poiId);
        int result = poiId + 1;
        return memory;
    }

    private void handle(int poiId){
        System.out.println(poiId);
    }

}
/* EOF */
