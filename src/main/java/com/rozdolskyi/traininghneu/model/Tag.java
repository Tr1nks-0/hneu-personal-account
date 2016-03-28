package com.rozdolskyi.traininghneu.model;

public class Tag {

	public String id;
	public String tagName;

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

	public Tag(String id, String tagName) {
		this.id = id;
		this.tagName = tagName;
	}

}