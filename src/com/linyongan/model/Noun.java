package com.linyongan.model;
/**
 * 名词查询实体类 
 * @author yongan
 *
 */
public class Noun {
	public Noun() {
		super();
	}

	public Noun(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String name;
	private String value;

}
