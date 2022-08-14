package org.javaparser.support;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;

/**
 * @author liang
 * @date 2022/8/14
 */
public interface NodeListHandler<T extends Node> {
    public NodeList<T> handleNodeList(NodeList<T> nodeList);
}
