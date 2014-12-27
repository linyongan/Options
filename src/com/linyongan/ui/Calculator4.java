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
 * 保证金页面
 */
public class Calculator4 extends CalculateBaseActivity {
	/** 计算按钮 */
	private Button calculateButton;
	private RadioGroup radioGroup1;
	private RadioGroup radioGroup2;
	/**
	 * 标记1：股票期权义务仓合约维持保证金 2：ETF期权义务合约维持保证金
	 */
	private int mark = 1;
	/**
	 * 标记1：认购期权义务仓持仓维持保证金 2：认沽期权义务仓持仓维持保证金
	 */
	private int mark2 = 1;
	/** 结算价(输入框) */
	private EditText editText1;
	/** 合约标的收盘价(输入框) */
	private EditText editText2;
	/** 行权价(输入框) */
	private EditText editText3;
	/** 合约单位(输入框) */
	private EditText editText4;
	/** 结果(文字) */
	private TextView out_tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 设置全屏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calculator4);
		initBaseActivity();
		// 找到所有的TextView
		out_tv = (TextView) findViewById(R.id.Button4_out_tv);
		// 找到所有的EditText
		editText1 = (EditText) findViewById(R.id.Button4_editText1);
		editText2 = (EditText) findViewById(R.id.Button4_editText2);
		editText3 = (EditText) findViewById(R.id.Button4_editText3);
		editText4 = (EditText) findViewById(R.id.Button4_editText4);
		// 找到所有的RadioGroup
		radioGroup1 = (RadioGroup) findViewById(R.id.Button4_radioGroup1);
		radioGroup1.setOnCheckedChangeListener(new radioGroupListener());
		radioGroup2 = (RadioGroup) findViewById(R.id.Button4_radioGroup2);
		radioGroup2.setOnCheckedChangeListener(new radioGroupListener());
		// 找到所有的Button
		calculateButton = (Button) findViewById(R.id.Button4_calculate_bt);
		calculateButton.setOnClickListener(new ButtonListener());
		titleView.setTitleText("保证金");
	}

	private class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Button4_calculate_bt:
				// 获取输入框的数字，必须先取值！！！
				String test1 = editText1.getText().toString();
				String test2 = editText2.getText().toString();
				String test3 = editText3.getText().toString();
				String test4 = editText4.getText().toString();

				if (mark == 1 && mark2 == 1 && test1.length() != 0
						&& test2.length() != 0 && test3.length() != 0
						&& test4.length() != 0) {
					Double a = calculate1(Double.parseDouble(test1),
							Double.parseDouble(test2),
							Double.parseDouble(test3),
							Double.parseDouble(test4));
					System.out.println("--a:-- " + a);
					out_tv.setText("计算结果：\n保证金为：" + String.format("%.2f", a));
				} else if (mark == 1 && mark2 == 2 && test1.length() != 0
						&& test2.length() != 0 && test3.length() != 0
						&& test4.length() != 0) {
					Double a = calculate2(Double.parseDouble(test1),
							Double.parseDouble(test2),
							Double.parseDouble(test3),
							Double.parseDouble(test4));
					System.out.println("--a:-- " + a);
					out_tv.setText("计算结果：\n保证金为：" + String.format("%.2f", a));
				} else if (mark == 2 && mark2 == 1 && test1.length() != 0
						&& test2.length() != 0 && test3.length() != 0
						&& test4.length() != 0) {
					Double a = calculate3(Double.parseDouble(test1),
							Double.parseDouble(test2),
							Double.parseDouble(test3),
							Double.parseDouble(test4));
					System.out.println("--a:-- " + a);
					out_tv.setText("计算结果：\n保证金为：" + String.format("%.2f", a));
				} else if (mark == 2 && mark2 == 2 && test1.length() != 0
						&& test2.length() != 0 && test3.length() != 0
						&& test4.length() != 0) {
					Double a = calculate4(Double.parseDouble(test1),
							Double.parseDouble(test2),
							Double.parseDouble(test3),
							Double.parseDouble(test4));
					System.out.println("--a:-- " + a);
					out_tv.setText("计算结果：\n保证金为：" + String.format("%.2f", a));
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
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.Button4_radioButton1:
				mark = 1;
				break;
			case R.id.Button4_radioButton2:
				mark = 2;
				break;
			case R.id.Button4_radioButton3:
				mark2 = 1;
				break;
			case R.id.Button4_radioButton4:
				mark2 = 2;
				break;
			}
		}
	}

	/**
	 * 股票 认购期权义务仓持仓维持保证金
	 * 
	 * @param a
	 *            结算价
	 * @param b
	 *            合约标的收盘价
	 * @param c
	 *            行权价
	 * @param d
	 *            合约单位
	 * @return
	 */
	private Double calculate1(Double a, Double b, Double c, Double d) {
		Double result = (a + Math.max(0.21 * b - Math.max((c - b), 0), 0.1 * c))
				* d;
		return result;
	}

	/**
	 * 股票 认沽期权义务仓持仓维持保证金
	 * 
	 * @param a
	 *            结算价
	 * @param b
	 *            合约标的收盘价
	 * @param c
	 *            行权价
	 * @param d
	 *            合约单位
	 * @return
	 */
	private Double calculate2(Double a, Double b, Double c, Double d) {
		Double result = Math.min(
				(a + Math.max((0.19 * b - Math.max((b - c), 0)), 0.1 * c)), c)
				* d;
		return result;
	}

	/**
	 * ETF 认购期权义务仓持仓维持保证金
	 * 
	 * @param a
	 *            结算价
	 * @param b
	 *            合约标的收盘价
	 * @param c
	 *            行权价
	 * @param d
	 *            合约单位
	 * @return
	 */
	private Double calculate3(Double a, Double b, Double c, Double d) {
		Double result = (a + Math.max((0.15 * b - Math.max((c - b), 0)),
				0.07 * c)) * d;
		return result;
	}

	/**
	 * ETF 认沽期权义务仓持仓维持保证金
	 * 
	 * @param a
	 *            结算价
	 * @param b
	 *            合约标的收盘价
	 * @param c
	 *            行权价
	 * @param d
	 *            合约单位
	 * @return
	 */
	private Double calculate4(Double a, Double b, Double c, Double d) {
		Double result = Math.min(
				(a + Math.max((0.15 * b - Math.max((b - c), 0)), 0.07 * c)), c)
				* d;
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
		out_tv.setText("");
	}

	/** 返回上一个界面 */
	public void goBack() {
		finish();
	}
}