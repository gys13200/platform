package com.guoys.platform.webcomponents.common;

import com.guoys.platform.webcomponents.entity.DO.Function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gys on 2018/1/8.
 */
public class ExtTreeNodeBuilder {

    private List<Function> functions;

    private boolean checked = false;

    public ExtTreeNodeBuilder(List<Function> functions){
        this.functions = functions;
    }

    public List<ExtTreeNode> build(){

        List<Function> children = findChildren(null);
        List<ExtTreeNode> result = new ArrayList<>();
        for (Function child : children) {
            ExtTreeNode treeNode = buildSingleNode(child);
            result.add(treeNode);
            setLeaf(treeNode);
        }

        return result;
    }

    public ExtTreeNodeBuilder check(boolean checked){
        this.checked = checked;
        return this;
    }

    private ExtTreeNode buildSingleNode(Function function){
        ExtTreeNode treeNode = new ExtTreeNode();
        treeNode.setOrigin(function);
        treeNode.setId(function.getMenuCode());
        treeNode.setText(function.getName());
        treeNode.setExpanded(false);
        //treeNode.setChecked(checked);

        List<ExtTreeNode> _children = new ArrayList<>();
        List<Function> children = findChildren(function);
        for (Function child : children) {
            _children.add(buildSingleNode(child));
        }
        treeNode.setChildren(_children);
        return treeNode;
    }

    private void setLeaf(ExtTreeNode node){
        if(node.getChildren() == null || node.getChildren().isEmpty()){
            node.setLeaf(true);
            for (ExtTreeNode treeNode : node.getChildren()) {
                setLeaf(treeNode);
            }
        }else {
            node.setLeaf(false);
            node.setExpanded(true);
        }
    }

    private List<Function> findChildren(Function function){
        List<Function> children = new ArrayList<>();
        String pid = function == null ? null : function.getId();
        for (Function item : this.functions) {
            if(sameOne(pid, item.getPid())){
                children.add(item);
            }
        }
        return children;
    }

    private boolean sameOne(String p1, String p2){
        String _p1 = p1 == null ? "" : p1;
        String _p2 = p2 == null ? "" : p2;
        return _p1.equals(_p2);

    }

    public static class ExtTreeNode implements Serializable {

        private Function origin;

        private String id;

        private String text;

        private List<ExtTreeNode> children;

        private boolean expanded = true;

        //private boolean checked = false;

        private boolean leaf = true;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<ExtTreeNode> getChildren() {
            return children;
        }

        public void setChildren(List<ExtTreeNode> children) {
            this.children = children;
        }

        public boolean isExpanded() {
            return expanded;
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public Function getOrigin() {
            return origin;
        }

        public void setOrigin(Function origin) {
            this.origin = origin;
        }

    }
}
