package com.linyongan.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class ppt extends BmobObject {
	private BmobFile name;

	public BmobFile getName() {
		return name;
	}

	public void setName(BmobFile name) {
		this.name = name;
	}

	
}
