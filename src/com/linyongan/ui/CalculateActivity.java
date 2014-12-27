package com.linyongan.ui;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.linyongan.ui.base.BaseActivity;
import com.linyongan.util.Util;

/**
 * 期权计算器页面
 */
public class CalculateActivity extends BaseActivity {
	/** 期权理论价格 */
	private Button Button1;
	/** 期权到期收益 */
	private Button Button2;
	/** 期权组合损益分析 */
	private Button Button3;
	/** 保证金 */
	private Button Button4;
	/** 期权Delta值 */
	private Button Button5;
	/** 内在价值&时间价值 */
	private Button Button6;
	/** Delta&杠杆倍数 */
	private Button Button7;
	/** 个人理财 */
	private Button Button8;
	/** 贷款计算 */
	private Button Button9;

	@Override
	public void setContentView() {
		setContentView(R.layout.calculate);
	}

	@Override
	public void initViews() {
		Button1 = (Button) findViewById(R.id.derivative_Button1);
		Button2 = (Button) findViewById(R.id.derivative_Button2);
		Button3 = (Button) findViewById(R.id.derivative_Button3);
		Button4 = (Button) findViewById(R.id.derivative_Button4);
		Button5 = (Button) findViewById(R.id.derivative_Button5);
		Button6 = (Button) findViewById(R.id.derivative_Button6);
		Button7 = (Button) findViewById(R.id.derivative_Button7);
		Button8 = (Button) findViewById(R.id.derivative_Button8);
		Button9 = (Button) findViewById(R.id.derivative_Button9);
	}

	@Override
	public void initListeners() {
		Button1.setOnClickListener(new ButtonListener());
		Button2.setOnClickListener(new ButtonListener());
		Button3.setOnClickListener(new ButtonListener());
		Button4.setOnClickListener(new ButtonListener());
		Button5.setOnClickListener(new ButtonListener());
		Button6.setOnClickListener(new ButtonListener());
		Button7.setOnClickListener(new ButtonListener());
		Button8.setOnClickListener(new ButtonListener());
		Button9.setOnClickListener(new ButtonListener());
	}

	@Override
	public void initData() {
		initBackButton();
		titleView.setTitle("期权计算");
		super.addMenu(this);
	}

	@Override
	public void goBack() {
		finish();
	}

	/**
	 * Button的监听事件处理
	 * 
	 * @author yongan
	 * 
	 */
	private class ButtonListener implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.derivative_Button1:
				Util.goToNoFinish(CalculateActivity.this, Calculator1.class);
				break;
			case R.id.derivative_Button2:
				Util.goToNoFinish(CalculateActivity.this, Calculator2.class);
				break;
			case R.id.derivative_Button3:
				Util.goToNoFinish(CalculateActivity.this, Calculator3.class);
				break;
			case R.id.derivative_Button4:
				Util.goToNoFinish(CalculateActivity.this, Calculator4.class);
				break;
			case R.id.derivative_Button5:
				Util.goToNoFinish(CalculateActivity.this, Calculator5.class);
				break;
			case R.id.derivative_Button6:
				Util.goToNoFinish(CalculateActivity.this, Calculator6.class);
				break;
			case R.id.derivative_Button7:
				Util.goToNoFinish(CalculateActivity.this, Calculator7.class);
				break;
			case R.id.derivative_Button8:
				Util.goToNoFinish(CalculateActivity.this, Calculator8.class);
				break;
			case R.id.derivative_Button9:
				Util.goToNoFinish(CalculateActivity.this, Calculator9.class);
				break;
			}
		}
	}
}