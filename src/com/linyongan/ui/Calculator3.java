package com.linyongan.ui;

import com.linyongan.ui.base.CalculateBaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 期权组合页面
 */
public class Calculator3 extends CalculateBaseActivity {
	/** 计算按钮 */
	private Button calculateButton;
	private Spinner spinner;
	/** 标记，记录点击了哪个spinner item */
	private int mark = 0;
	private int mark1 = 0;
	private int mark2 = 0;
	private int mark3 = 0;
	private RadioGroup group1;
	private RadioGroup group2;
	private RadioGroup group3;
	/** 5个include View */
	private LinearLayout include1;
	private LinearLayout include2;
	private LinearLayout include3;
	private LinearLayout include4;
	private LinearLayout include5;
	private EditText editText1;
	private EditText editText2;
	private EditText editText3;
	private EditText editText4;
	private EditText editText5;
	private EditText editText6;
	private EditText editText7;
	private EditText editText8;
	private EditText editText9;
	private EditText editText10;
	private EditText editText11;
	private EditText editText12;
	private EditText editText13;
	private EditText editText14;
	private EditText editText15;
	private EditText editText16;
	private EditText editText17;
	private EditText editText18;
	private EditText editText19;
	private EditText editText20;
	private EditText editText21;
	private EditText editText22;
	/** 结果(文字) */
	private TextView out_tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 设置全屏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calculator3);
		initBaseActivity();
		// 找到所有的TextView
		out_tv = (TextView) findViewById(R.id.Button3_out_tv);
		// 找到所有的RadioGroup
		group1 = (RadioGroup) findViewById(R.id.Button3_radioGroup1);
		group2 = (RadioGroup) findViewById(R.id.Button3_radioGroup2);
		group3 = (RadioGroup) findViewById(R.id.Button3_radioGroup3);
		group1.setOnCheckedChangeListener(new radioGroupListener());
		group2.setOnCheckedChangeListener(new radioGroupListener());
		group3.setOnCheckedChangeListener(new radioGroupListener());
		// 找到所有的Spinner
		spinner = (Spinner) findViewById(R.id.Button3_Spinner);
		spinner.setOnItemSelectedListener(new SpinnerListener());
		// 找到所有的include
		include1 = (LinearLayout) findViewById(R.id.include1);
		include2 = (LinearLayout) findViewById(R.id.include2);
		include3 = (LinearLayout) findViewById(R.id.include3);
		include4 = (LinearLayout) findViewById(R.id.include4);
		include5 = (LinearLayout) findViewById(R.id.include5);
		// 找到所有的EditText
		editText1 = (EditText) findViewById(R.id.Button3_editText1);
		editText2 = (EditText) findViewById(R.id.Button3_editText2);
		editText3 = (EditText) findViewById(R.id.Button3_editText3);
		editText4 = (EditText) findViewById(R.id.Button3_editText21);
		editText5 = (EditText) findViewById(R.id.Button3_editText22);
		editText6 = (EditText) findViewById(R.id.Button3_editText23);
		editText7 = (EditText) findViewById(R.id.Button3_editText24);
		editText8 = (EditText) findViewById(R.id.Button3_editText31);
		editText9 = (EditText) findViewById(R.id.Button3_editText32);
		editText10 = (EditText) findViewById(R.id.Button3_editText33);
		editText11 = (EditText) findViewById(R.id.Button3_editText34);
		editText12 = (EditText) findViewById(R.id.Button3_editText41);
		editText13 = (EditText) findViewById(R.id.Button3_editText42);
		editText14 = (EditText) findViewById(R.id.Button3_editText43);
		editText15 = (EditText) findViewById(R.id.Button3_editText44);
		editText16 = (EditText) findViewById(R.id.Button3_editText51);
		editText17 = (EditText) findViewById(R.id.Button3_editText52);
		editText18 = (EditText) findViewById(R.id.Button3_editText53);
		editText19 = (EditText) findViewById(R.id.Button3_editText54);
		editText20 = (EditText) findViewById(R.id.Button3_editText55);
		editText21 = (EditText) findViewById(R.id.Button3_editText56);
		editText22 = (EditText) findViewById(R.id.Button3_editText45);
		// 找到所有的Button
		calculateButton = (Button) findViewById(R.id.Button3_Calculate_bt);
		calculateButton.setOnClickListener(new ButtonListener());
		titleView.setTitleText("期权组合");
	}

	/** RadioGroup的监听事件 */
	private class radioGroupListener implements
			RadioGroup.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.Button3_radioButton1:
				mark1 = 0;
				break;
			case R.id.Button3_radioButton2:
				mark1 = 1;
				break;
			case R.id.Button3_radioButton21:
				mark2 = 0;
				break;
			case R.id.Button3_radioButton22:
				mark2 = 1;
				break;
			case R.id.Button3_radioButton31:
				mark3 = 0;
				break;
			case R.id.Button3_radioButton32:
				mark3 = 1;
				break;
			}
		}

	}

	private class ButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Button3_Calculate_bt:
				switch (mark) {
				case 0:
					String test1 = editText1.getText().toString();
					String test2 = editText2.getText().toString();
					String test3 = editText3.getText().toString();
					if (test1.length() != 0 && test2.length() != 0
							&& test3.length() != 0) {
						double d1 = Double.valueOf(test1);
						double d2 = Double.valueOf(test2);
						double d3 = Double.valueOf(test3);
						double d4 = 0.0;
						String d5 = "";
						String d6 = "";
						double d7 = 0.0;
						// 计算过程
						if (mark1 == 0) {
							d4 = d1 - d2;
							d5 = String.format("%.2f", (d4 + d3)) + "";
							d6 = "无穷大";
							d7 = d4 + d3;
						} else if (mark1 == 1) {
							d4 = d2 - d1;
							d5 = "无穷大";
							d6 = String.format("%.2f", (d3 - d4)) + "";
							d7 = d3 - d4;
						}
						String out = "计算结果:\n";
						out = out + "构建成本: " + String.format("%.2f", d4)
								+ "\n到期日最大损失: " + d5 + "\n到期日最大收益: " + d6
								+ "\n盈亏平衡点: " + String.format("%.2f", d7);
						out_tv.setText(out);
					} else {
						ShowToast("输入数字不能为空！");
						out_tv.setText("输入数字不能为空！");
					}
					break;
				case 1:
					String test4 = editText4.getText().toString();
					String test5 = editText5.getText().toString();
					String test6 = editText6.getText().toString();
					String test7 = editText7.getText().toString();
					if (test4.length() != 0 && test5.length() != 0
							&& test6.length() != 0 && test7.length() != 0) {
						double d1 = Double.valueOf(test4);
						double d2 = Double.valueOf(test5);
						double d3 = Double.valueOf(test6);
						double d4 = Double.valueOf(test7);
						double d5 = 0.0;
						double d6 = 0.0;
						double d7 = 0.0;
						double d8 = 0.0;
						// 计算过程
						if (mark2 == 0) {
							d5 = d4 - d3;
							d6 = d5;
							d7 = d1 - d2 - d5;
							d8 = d5 + d2;
						} else if (mark2 == 1) {
							d5 = d3 - d4;
							d6 = d5;
							d7 = d1 - d2 - d5;
							d8 = d1 - d5;
						}
						String out = "计算结果:\n";
						out = out + "构建成本: " + String.format("%.2f", d5)
								+ "\n到期日最大损失: " + String.format("%.2f", d6)
								+ "\n到期日最大收益: " + String.format("%.2f", d7)
								+ "\n盈亏平衡点: " + String.format("%.2f", d8);
						out_tv.setText(out);
					} else {
						ShowToast("输入数字不能为空！");
						out_tv.setText("输入数字不能为空！");
					}
					break;
				case 2:
					String test8 = editText8.getText().toString();
					String test9 = editText9.getText().toString();
					String test10 = editText10.getText().toString();
					String test11 = editText11.getText().toString();
					if (test8.length() != 0 && test9.length() != 0
							&& test10.length() != 0 && test11.length() != 0) {
						double d1 = Double.valueOf(test8);
						double d2 = Double.valueOf(test9);
						double d3 = Double.valueOf(test10);
						double d4 = Double.valueOf(test11);
						double d5 = 0.0;
						double d6 = 0.0;
						String d7 = "";
						double d8 = 0.0;
						double d9 = 0.0;
						// 计算过程
						if (mark3 == 0) {
							d5 = d1 + d2;
							d6 = d5;
							d7 = "无穷大";
							d8 = d3 + d5;
							d9 = d3 - d5;
						} else if (mark3 == 1) {
							d5 = d1 + d2;
							d6 = d5;
							d7 = "无穷大";
							d8 = d3 + d5;
							d9 = d4 - d5;
						}
						String out = "计算结果:\n";
						out = out + "构建成本: " + String.format("%.2f", d5)
								+ "\n到期日最大损失: " + String.format("%.2f", d6)
								+ "\n到期日最大收益: " + d7 + "\n向上盈亏平衡点: "
								+ String.format("%.2f", d8) + "\n向下盈亏平衡点: "
								+ String.format("%.2f", d9);
						out_tv.setText(out);
					} else {
						ShowToast("输入数字不能为空！");
						out_tv.setText("输入数字不能为空！");
					}
					break;
				case 3:
					String test12 = editText12.getText().toString();
					String test13 = editText13.getText().toString();
					String test14 = editText14.getText().toString();
					String test15 = editText15.getText().toString();
					String test22 = editText22.getText().toString();
					if (test12.length() != 0 && test13.length() != 0
							&& test14.length() != 0 && test15.length() != 0
							&& test22.length() != 0) {
						double d1 = Double.valueOf(test12);
						double d2 = Double.valueOf(test13);
						double d3 = Double.valueOf(test14);
						double d4 = Double.valueOf(test15);
						double d5 = 0.0;
						double d6 = 0.0;
						double d7 = 0.0;
						double d8 = 0.0;
						double d9 = Double.valueOf(test22);
						// 计算过程
						d5 = d1 + d3 - d2;
						d6 = d1 + d3 - d2 - d9;
						d7 = d4 + d2 - d1 - d3;
						d8 = d1 + d3 - d2;
						String out = "计算结果:\n";
						out = out + "构建成本: " + String.format("%.2f", d5)
								+ "\n到期日最大损失: " + String.format("%.2f", d6)
								+ "\n到期日最大收益: " + String.format("%.2f", d7)
								+ "\n盈亏平衡点: " + String.format("%.2f", d8);
						out_tv.setText(out);
					} else {
						ShowToast("输入数字不能为空！");
						out_tv.setText("输入数字不能为空！");
					}
					break;
				case 4:
					String test16 = editText16.getText().toString();
					String test17 = editText17.getText().toString();
					String test18 = editText18.getText().toString();
					String test19 = editText19.getText().toString();
					String test20 = editText20.getText().toString();
					String test21 = editText21.getText().toString();
					if (test16.length() != 0 && test17.length() != 0
							&& test18.length() != 0 && test19.length() != 0
							&& test20.length() != 0 && test21.length() != 0) {
						double d1 = Double.valueOf(test16);
						double d2 = Double.valueOf(test17);
						double d3 = Double.valueOf(test18);
						double d4 = Double.valueOf(test19);
						double d5 = Double.valueOf(test20);
						double d6 = Double.valueOf(test21);
						double d7 = 0.0;
						double d8 = 0.0;
						double d9 = 0.0;
						double d10 = 0.0;
						double d11 = 0.0;
						// 计算过程
						d7 = d4 + d6 - 2 * d5;
						d8 = d7;
						d9 = d2 - d1 - d7;
						d10 = 2 * d2 - d1 - d7;
						d11 = d1 + d7;
						String out = "计算结果:\n";
						out = out + "构建成本: " + String.format("%.2f", d7)
								+ "\n到期日最大损失: " + String.format("%.2f", d8)
								+ "\n到期日最大收益: " + String.format("%.2f", d9)
								+ "\n向上盈亏平衡点: " + String.format("%.2f", d10)
								+ "\n向下盈亏平衡点: " + String.format("%.2f", d11);
						out_tv.setText(out);
					} else {
						ShowToast("输入数字不能为空！");
						out_tv.setText("输入数字不能为空！");
					}
					break;
				}
				break;
			}
		}
	}

	/** 清空函数 */
	public void cleanEditText() {

		switch (mark) {
		case 0:
			editText1.setText("");
			editText2.setText("");
			editText3.setText("");
			break;
		case 1:
			editText4.setText("");
			editText5.setText("");
			editText6.setText("");
			editText7.setText("");
			break;
		case 2:
			editText8.setText("");
			editText9.setText("");
			editText10.setText("");
			editText11.setText("");
			break;
		case 3:
			editText12.setText("");
			editText13.setText("");
			editText14.setText("");
			editText15.setText("");
			editText22.setText("");
			break;
		case 4:
			editText16.setText("");
			editText17.setText("");
			editText18.setText("");
			editText19.setText("");
			editText20.setText("");
			editText21.setText("");
			break;
		}
		out_tv.setText("计算结果:");
	}

	class SpinnerListener implements OnItemSelectedListener {
		// 当用户选定了一个条目时，就会调用该方法
		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			switch (position) {
			case 0:
				mark = 0;
				setGone();
				include1.setVisibility(View.VISIBLE);
				cleanEditText();
				break;
			case 1:
				mark = 1;
				setGone();
				include2.setVisibility(View.VISIBLE);
				cleanEditText();
				break;
			case 2:
				mark = 2;
				setGone();
				include3.setVisibility(View.VISIBLE);
				cleanEditText();
				break;
			case 3:
				mark = 3;
				setGone();
				include4.setVisibility(View.VISIBLE);
				cleanEditText();
				break;
			case 4:
				mark = 4;
				setGone();
				include5.setVisibility(View.VISIBLE);
				cleanEditText();
				break;
			}
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
	}

	/**
	 * 将所有的include文件设置成不可见
	 */
	public void setGone() {
		include1.setVisibility(View.GONE);
		include2.setVisibility(View.GONE);
		include3.setVisibility(View.GONE);
		include4.setVisibility(View.GONE);
		include5.setVisibility(View.GONE);
	}

	/** 返回上一个界面 */
	public void goBack() {
		Intent intent = new Intent(Calculator3.this, CalculateActivity.class);
		startActivity(intent);
		finish();
	}
}