package com.linyongan.model;

/**
 * ����X��ȡYֵ��ʵ����
 * 
 * @author yongan
 * 
 */
public class Calculate {
	public Calculate() {
		super();
	}

	public Calculate(String id, String y) {
		super();
		this.id = id;
		this.y = y;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	private String id;
	private String y;

}
