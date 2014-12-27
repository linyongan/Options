package com.linyongan.ui;

import com.linyongan.ui.base.CalculateBaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * 内在价值与时间价值页面
 */
public class Calculator6 extends CalculateBaseActivity {
	/** 计算按钮 */
	private Button calculateButton;
	/** 标的股价(输入框) */
	private EditText editText1;
	/** 行权价(输入框) */
	private EditText editText2;
	/** 权利金(输入框) */
	private EditText editText3;
	/** 结果(文字) */
	private TextView out_tv;
	private RadioGroup radioGroup;
	/**
	 * 标记1：认购期权 2：认沽期权
	 */
	private int mark = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 设置全屏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calculator6);
		initBaseActivity();
		// 找到所有的RadioGroup
		radioGroup = (RadioGroup) findViewById(R.id.Button6_radioGroup);
		radioGroup.setOnCheckedChangeListener(new radioGroupListener());
		// 找到所有的TextView
		out_tv = (TextView) findViewById(R.id.Button6_out_tv);
		// 找到所有的EditText
		editText1 = (EditText) findViewById(R.id.Button6_editText1);
		editText2 = (EditText) findViewById(R.id.Button6_editText2);
		editText3 = (EditText) findViewById(R.id.Button6_editText3);
		// 找到所有的Button
		calculateButton = (Button) findViewById(R.id.Button6_calculate_bt);
		calculateButton.setOnClickListener(new ButtonListener());
		titleView.setTitleText("内在价值与时间价值");
	}

	/** RadioGroup的监听事件 */
	private class radioGroupListener implements
			RadioGroup.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.Button6_radioButton1:
				mark = 1;
				break;
			case R.id.Button6_radioButton2:
				mark = 2;
				break;
			}
		}

	}

	private class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Button6_calculate_bt:
				// 获取输入框的数字，必须先取值！！！
				String test1 = editText1.getText().toString();
				String test2 = editText2.getText().toString();
				String test3 = editText3.getText().toString();
				String out = "计算结果：\n";
				if (test1.length() != 0 && test2.length() != 0
						&& test3.length() != 0) {
					if (mark == 1) {
						double d1 = Math.max(
								(Double.parseDouble(test1) - Double
										.parseDouble(test2)), 0);
						out = out
								+ "认购期权内在价值: "
								+ String.format("%.2f", d1)
								+ "\n认购期权时间价值: "
								+ String.format("%.2f",
										Double.parseDouble(test3) - d1);
					}
					if (mark == 2) {
						double d2 = Math.max(
								(Double.parseDouble(test2) - Double
										.parseDouble(test1)), 0);
						out = out
								+ "认沽期权内在价值: "
								+ String.format("%.2f", d2)
								+ "\n认沽期权内在价值: "
								+ String.format("%.2f",
										Double.parseDouble(test3) - d2);
					}
					out_tv.setText(out);
				} else {
					ShowToast("输入数字不能为空！");
					out_tv.setText("计算结果：输入数字不能为空！");
				}

				break;
			}

		}
	}

	/**
	 * 清空函数
	 */
	public void cleanEditText() {
		editText1.setText("");
		editText2.setText("");
		editText3.setText("");
		out_tv.setText("");
	}

	/** 返回上一个界面 */
	public void goBack() {
		finish();
	}
}