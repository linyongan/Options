package com.linyongan.ui;

import com.linyongan.ui.base.CalculateBaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 期权到期收益页面
 */
public class Calculator2 extends CalculateBaseActivity {
	/** 计算按钮 */
	private Button calculateButton;
	private RadioGroup radioGroup1;
	private RadioGroup radioGroup2;
	/**
	 * 标记1：多头 2：空头
	 */
	private int mark = 1;
	/**
	 * 标记1：认购期权到期收益分布 2：认沽期权到期收益分布 3:股票到期收益分布
	 */
	private int mark2 = 1;
	/** 标的资产到期价格(输入框) */
	private EditText editText1;
	/** 行权价格(输入框) */
	private EditText editText2;
	/** 股票到期价格(输入框) */
	private EditText editText3;
	/** 股票价格(输入框) */
	private EditText editText4;
	/** 期权权益金(输入框) */
	private EditText editText5;
	/** 结果(文字) */
	private TextView out_tv;
	private TableRow tableRow1;
	private TableRow tableRow2;
	private TableRow tableRow3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 设置全屏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calculator2);
		initBaseActivity();
		// 找到所有的TextView
		out_tv = (TextView) findViewById(R.id.Button2_out_tv);
		// 找到所有的EditText
		editText1 = (EditText) findViewById(R.id.Button2_editText1);
		editText2 = (EditText) findViewById(R.id.Button2_editText2);
		editText3 = (EditText) findViewById(R.id.Button2_editText3);
		editText4 = (EditText) findViewById(R.id.Button2_editText4);
		editText5 = (EditText) findViewById(R.id.Button2_editText5);
		// 找到所有的TableRow
		tableRow1 = (TableRow) findViewById(R.id.Button2_tableRow1);
		tableRow2 = (TableRow) findViewById(R.id.Button2_tableRow2);
		tableRow3 = (TableRow) findViewById(R.id.Button2_tableRow3);
		// 找到所有的RadioGroup
		radioGroup1 = (RadioGroup) findViewById(R.id.Button2_radioGroup1);
		radioGroup1.setOnCheckedChangeListener(new radioGroupListener());
		radioGroup2 = (RadioGroup) findViewById(R.id.Button2_radioGroup2);
		radioGroup2.setOnCheckedChangeListener(new radioGroupListener());
		// 找到所有的Button
		calculateButton = (Button) findViewById(R.id.Button2_calculate_bt);
		calculateButton.setOnClickListener(new ButtonListener());
		titleView.setTitleText("期权到期收益");
	}

	private class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Button2_calculate_bt:
				// 获取输入框的数字，必须先取值！！！
				String test1 = editText1.getText().toString();
				String test2 = editText2.getText().toString();
				String test3 = editText3.getText().toString();
				String test4 = editText4.getText().toString();
				String test5 = editText5.getText().toString();

				if (mark == 1 && mark2 == 1 && test1.length() != 0
						&& test2.length() != 0 && test5.length() != 0) {
					Double a = calculate1(Double.parseDouble(test1),
							Double.parseDouble(test2),
							Double.parseDouble(test5));
					out_tv.setText("计算结果：\n(多头)认购期权到期收益："
							+ String.format("%.2f", a));
				} else if (mark == 1 && mark2 == 2 && test1.length() != 0
						&& test2.length() != 0 && test5.length() != 0) {
					Double a = calculate2(Double.parseDouble(test1),
							Double.parseDouble(test2),
							Double.parseDouble(test5));
					out_tv.setText("计算结果：\n(多头)认沽期权到期收益："
							+ String.format("%.2f", a));
				} else if (mark == 1 && mark2 == 3 && test3.length() != 0
						&& test4.length() != 0) {
					Double a = Double.parseDouble(test3)
							- Double.parseDouble(test4);
					out_tv.setText("计算结果：\n(多头)股票到期收益："
							+ String.format("%.2f", a));
				} else if (mark == 2 && mark2 == 1 && test1.length() != 0
						&& test2.length() != 0 && test5.length() != 0) {
					Double a = calculate1(Double.parseDouble(test1),
							Double.parseDouble(test2),
							Double.parseDouble(test5));
					out_tv.setText("计算结果：\n(空头)认购期权到期收益："
							+ String.format("%.2f", -a));
				} else if (mark == 2 && mark2 == 2 && test1.length() != 0
						&& test2.length() != 0 && test5.length() != 0) {
					Double a = calculate2(Double.parseDouble(test1),
							Double.parseDouble(test2),
							Double.parseDouble(test5));
					out_tv.setText("计算结果：\n(空头)认沽期权到期收益："
							+ String.format("%.2f", -a));
				} else if (mark == 2 && mark2 == 3 && test3.length() != 0
						&& test4.length() != 0) {
					Double a = Double.parseDouble(test4)
							- Double.parseDouble(test3);
					out_tv.setText("计算结果：\n(空头)股票到期收益："
							+ String.format("%.2f", a));
				} else {
					ShowToast("输入数字不能为空！");
				}

				break;
			}

		}
	}

	/** RadioGroup的监听事件 */
	private class radioGroupListener implements
			RadioGroup.OnCheckedChangeListener {

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.Button2_radioButton1:
				mark = 1;
				break;
			case R.id.Button2_radioButton2:
				mark = 2;
				break;
			case R.id.Button2_radioButton3:
				tableRow1.setVisibility(View.VISIBLE);
				tableRow3.setVisibility(View.VISIBLE);
				tableRow2.setVisibility(View.GONE);
				mark2 = 1;
				break;
			case R.id.Button2_radioButton4:
				tableRow1.setVisibility(View.VISIBLE);
				tableRow3.setVisibility(View.VISIBLE);
				tableRow2.setVisibility(View.GONE);
				mark2 = 2;
				break;
			case R.id.Button2_radioButton5:
				tableRow2.setVisibility(View.VISIBLE);
				tableRow1.setVisibility(View.GONE);
				tableRow3.setVisibility(View.GONE);
				mark2 = 3;
				break;
			}
		}

	}

	/**
	 * (多头)认购期权到期收益分布 (空头)前面加个负号
	 * 
	 * @param a
	 *            标的资产到期价格
	 * @param b
	 *            行权价格
	 * @param c
	 *            期权权益金
	 * @return
	 */
	private Double calculate1(Double a, Double b, Double c) {
		Double result = Math.max((a - b), 0) - c;
		return result;
	}

	/**
	 * (多头)认沽期权到期收益分布 (空头)前面加个负号
	 * 
	 * @param a
	 *            标的资产到期价格
	 * @param b
	 *            行权价格
	 * @param c
	 *            期权权益金
	 * @return
	 */
	private Double calculate2(Double a, Double b, Double c) {
		Double result = Math.max((b - a), 0) - c;
		return result;
	}

	/**
	 * 清空函数
	 */
	public void cleanEditText() {
		editText1.setText("");
		editText2.setText("");
		editText3.setText("");
		editText4.setText("");
		editText5.setText("");
		out_tv.setText("");
	}

	/** 返回上一个界面 */
	public void goBack() {
		finish();
	}
}