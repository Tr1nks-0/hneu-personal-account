package edu.hneu.studentsportal.model;


public class Tag {

    private String id;
    private String tagName;

    public Tag(String id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}