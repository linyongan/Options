package com.linyongan.model;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {
	private String master;
	private String visitor;
	private String commentContent;
	/**
	 * 0�������������ˣ��������ˣ���1�����������Լ��������Լ�����2:XXX�ظ�XXX���������˲��ܻظ����ˣ���3���������ݣ�����ʾ��
	 */
	private int type;

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
