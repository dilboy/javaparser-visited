package org.javaparser.support;

/**
 * @author liang
 * @date 2022/8/15
 */
public class Tuple<T,R> {
    private T t;
    private R r;

    public Tuple(T t, R r) {
        this.t = t;
        this.r = r;
    }

    public T getKey1(){
        return t;
    }

    public R getKey2(){
        return r;
    }
}
