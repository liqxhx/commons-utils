package com.liqh.commons.lang.utils.linecounter;

import java.io.File;
import java.io.IOException;

public class FileNode extends Node {


    public FileNode(File file) {
        super(file);
        setNodeType(Node.FILE);
        setLineCount(0);
    }

    @Override
    public int countLine() throws IOException {
        setLineCount(Node.countLine(getTargetFile()));
        return getLineCount();
    }

    @Override
    public void output() {
        System.out.println(getTargetFile().getAbsolutePath() + " " + getLineCount());
    }

}
