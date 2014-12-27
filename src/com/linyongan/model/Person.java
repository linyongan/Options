package com.linyongan.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class Person extends BmobUser {
	private BmobFile icon;

	public BmobFile getIcon() {
		return icon;
	}

	public void setIcon(BmobFile icon) {
		this.icon = icon;
	}
	
}
