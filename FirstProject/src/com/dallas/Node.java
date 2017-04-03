package com.dallas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinj-venkat on 4/2/2017.
 */
public class Node {
    private String nodeName;
    private List<String> dependencyNodes = new ArrayList();

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }


    public List<String> getDependencyNodes() {
        return dependencyNodes;
    }

    public void setDependencyNodes(List<String> dependencyNodes) {
        this.dependencyNodes = dependencyNodes;
    }
}
