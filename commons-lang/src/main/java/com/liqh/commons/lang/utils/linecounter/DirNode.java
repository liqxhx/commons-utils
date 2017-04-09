package com.liqh.commons.lang.utils.linecounter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirNode extends FileNode {
    private List<Node> subNodes = null;
    Map<String, List<Node>> nodesMap;
    Map<String, Integer> fileCounterMap;

    public DirNode(File dir) {
        super(dir);
        setNodeType(Node.DIR);
        setLineCount(0);
        subNodes = new ArrayList<Node>();
        nodesMap = new HashMap<String, List<Node>>();

        fileCounterMap = new HashMap<String,Integer>();
    }

    @Override
    public int countLine() throws IOException {
        int line = 0;
        Node node;
        for (File _f : getTargetFile().listFiles()) {
            node = null;
            if (_f.isFile() && Node.shouldIncludeFile(_f)) {
                node = new FileNode(_f);
            } else if (_f.isDirectory() && !Node.shouldExcludeDir(_f)) {
                node = new DirNode(_f);
                Node.classify(node, ((DirNode)node).getNodesMap(),((DirNode)node).getFileCounterMap());
            }
            if (node != null) {
                line += node.countLine();
                subNodes.add(node);
            }
        }
        Node.classify(this, nodesMap, fileCounterMap);
        setLineCount(line);
        return line;
    }

    @Override
    public void output() {
        for (Node node : subNodes) node.output();
        System.out.println(getTargetFile().getAbsolutePath() + " ---> " + getLineCount() + fileCounterMap);
    }

    public List<Node> getSubNodes() {
        return subNodes;
    }

    public void setSubNodes(List<Node> subNodes) {
        this.subNodes = subNodes;
    }

    public Map<String, List<Node>> getNodesMap(){
        return this.nodesMap ;
    }

    public Map<String, Integer> getFileCounterMap(){
        return this.fileCounterMap ;
    }
}
