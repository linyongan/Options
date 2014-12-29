package com.linyongan.ui;

import com.linyongan.ui.base.BaseActivity;
import com.linyongan.util.Util;

/**
 * 关于我们页面
 */
public class AboutActivity extends BaseActivity {
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_about);
	}

	@Override
	public void initViews() {
	}

	@Override
	public void initListeners() {
	}

	@Override
	public void initData() {
		initBackButton();
		titleView.setTitle("关于我们");
		super.addMenu(this);
	}

	@Override
	public void goBack() {
		Util.goBack(this, LearnActivity.class);
	}
}