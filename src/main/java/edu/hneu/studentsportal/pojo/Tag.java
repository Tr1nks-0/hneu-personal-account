package edu.hneu.studentsportal.pojo;


public class Tag {

    private String id;
    private String tagName;

    public Tag(final String id, final String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(final String tagName) {
        this.tagName = tagName;
    }
}