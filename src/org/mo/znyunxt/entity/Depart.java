package org.mo.znyunxt.entity;

import java.io.Serializable;

public class Depart implements Serializable {

	private String id;// 子部门id

	private String text;// 子部门名称

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

	@Override
	public String toString() {
		return "Depart [id=" + id + ", text=" + text + "]";
	}

}
