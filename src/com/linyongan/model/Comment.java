package com.linyongan.model;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {
	private String master;
	private String visitor;
	private String commentContent;
	/**
	 * 0：客人评论主人（隐藏主人），1：主人评论自己（隐藏自己），2:XXX回复XXX（但是主人不能回复主人），3：测试内容（不显示）
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
