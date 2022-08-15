package org.javaparser.liang.demo;

import java.util.Stack;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * A Simple Reverse Polish Notation calculator with memory function.
 */
@Controller
public class DemoClass {

    // What does this do?
    public static int ONE_BILLION = 1000000000;

    private double memory = 0;

    /**
     * 这里会新建一个poiId的接口,基础类型换成Long
     */
    @RequestMapping
    public double memoryRecall(int poiId){
        String name = String.valueOf(poiId);
        int result = poiId + 1;
        return memory;
    }

    /**
     * 这里会新建一个poiId的接口
     *  (入参换成Long,出参也换成Long类型)
     */
    @RequestMapping
    public Integer demoIntegerInterface(Integer poiId,String name){
        return poiId;
    }

    private void handle(int poiId){
        System.out.println(poiId);
    }

}
/* EOF */
