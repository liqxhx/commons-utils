package com.liqh.commons.lang.utils.linecounter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public abstract class Node {
    public static final String DOT = ".";
    public static final int FILE = 0;
    public static final int DIR = 0;

    public static final String[] includeFiles = {"java", "jsp", "xml", "txt", "js", "css", "html", "properties"};
    public static final String[] excludeDirs = {"target", ".settings"};

    private int nodeType;
    private int lineCount;
    private File targetFile;

    public Node(File file) {
        this.targetFile = file;
    }

    public static Node getInstance(String name) throws IOException{
        File file = new File(name) ;
        if(!file.exists()){
            throw new FileNotFoundException(name) ;
        }

        if(file.isDirectory()) return new DirNode(file) ;
        return new FileNode(file);
    }

    public abstract int countLine() throws IOException;

    public abstract void output();

    public static boolean shouldIncludeFile(File file) {
        for (String suffix : includeFiles) {
            if (StringUtils.endsWith(file.getName(), DOT + suffix)) return true;
        }
        return false;
    }

    public static boolean shouldExcludeDir(File dir) {
        for (String suffix : excludeDirs) {
            if (StringUtils.equalsIgnoreCase(dir.getName(), suffix)) return true;
        }
        return false;
    }

    public static int countLine(File file) throws IOException {
        int line = 0;
        if (file.isFile()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            try {
                while (reader.ready()) {
                    reader.readLine();
                    line++;
                }
            } finally {
                reader.close();
            }
        } else {
            for (File _f : file.listFiles()) {
                line += countLine(_f);
            }
        }
        return line;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public File getTargetFile() {
        return targetFile;
    }

    public static String getFileSuffix(File file) {
        return StringUtils.substringAfterLast(file.getName(), DOT);
    }

    public static void classify(Node node, Map<String, List<Node>> nodesMap, Map<String,Integer> counterMap) {
        if (node == null) return;
        if (nodesMap == null) {
            nodesMap = new HashMap<String, List<Node>>();
        }
        if (counterMap == null) {
            counterMap = new HashMap<String, Integer>();
        }

        if (node instanceof FileNode) {
            FileNode _fileNode = (FileNode) node;
            String key = getFileSuffix(_fileNode.getTargetFile());
            List<Node> nodes = nodesMap.get(key);
            if (nodes == null) {
                nodes = new ArrayList<Node>();
                nodesMap.put(key, nodes);
            }
            nodes.add(_fileNode);
            Integer counter = counterMap.get(key) ;
            if(counter == null) counter= 0;
            counterMap.put(key, counter+_fileNode.getLineCount()) ;
        } else if (node instanceof DirNode) {
            DirNode dirNode = (DirNode) node;
            for (Node _node : dirNode.getSubNodes()) classify(_node, nodesMap, counterMap);
        }

    }
}
