package com.sucl.jpa.core.view;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/9
 */
@Data
public class TreeNode implements ITreeNode {

    private String id;
    private String label;
    private String href;
    private boolean disabled;
    private boolean fixed;
    private boolean leaf;
    private String cls;
    private List<ITreeNode> children;
    private Object obj;//扩展属性

    public void add(TreeNode treeNode) {
        if(children==null){
            this.children = new ArrayList<>();
        }
        this.children.add(treeNode);
    }

    @Override
    public String getHref() {
        return href==null?"javascript:;":href;
    }

    @Override
    public List<ITreeNode> getChildren() {
        return children;
    }
}
