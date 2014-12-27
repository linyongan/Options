package com.linyongan.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Feedback extends BmobObject {
	// 反馈内容
	private String content;
	// 用户
	private String contacts;
	private int love;
	private int comment;
	private boolean myLove;// 赞
	private BmobRelation relation;

	public int getLove() {
		return love;
	}

	public void setLove(int love) {
		this.love = love;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public boolean isMyLove() {
		return myLove;
	}

	public void setMyLove(boolean myLove) {
		this.myLove = myLove;
	}

	public BmobRelation getRelation() {
		return relation;
	}

	public void setRelation(BmobRelation relation) {
		this.relation = relation;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

}
