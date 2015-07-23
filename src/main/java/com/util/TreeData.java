package com.util;

public class TreeData {

    private String id;
    private Integer parentId;
    private String text;
    private Boolean leaf;
    private Boolean checked;
    private Boolean expanded = false;
    public Boolean getExpanded() {
        return expanded;
    }
    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Boolean getLeaf() {
        return leaf;
    }
    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }
    public Boolean getChecked() {
        return checked;
    }
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    
}
