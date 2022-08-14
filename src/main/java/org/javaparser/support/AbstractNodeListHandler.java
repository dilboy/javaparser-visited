package org.javaparser.support;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author liang
 * @date 2022/8/14
 */
public abstract class AbstractNodeListHandler<T extends Node> implements NodeListHandler<T> {

    protected abstract ParameterHandler getParameterHandler();

    @Override
    public NodeList<T> handleNodeList(NodeList<T> nodeList) {
        NodeList<T> result = new NodeList<>();
        nodeList.stream()
            .flatMap(this::handleOriginStream)
            .map(x -> {
                if (Objects.nonNull(x) && x instanceof Parameter) {
                    return (T) getParameterHandler().changeParameterToMulti((Parameter) x);
                }
                return x;
            })
            .forEach(result::add);
        return result;
    }

    protected abstract Stream<T> handleOriginStream(T x);

}
