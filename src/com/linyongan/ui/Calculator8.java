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
 * 个人理财页面
 */
public class Calculator8 extends CalculateBaseActivity {
	/** 计算按钮 */
	private Button calculateButton;
	/** 计算类型 */
	private RadioGroup radioGroup;
	/** 年金周期 */
	private RadioGroup period;
	/** 到期年限|期数(输入框) */
	private EditText year_et;
	/** 年利率(输入框) */
	private EditText rate_et;
	/** 每年收付金额(输入框) */
	private EditText annuity_et;
	/** 现值(输入框) */
	private EditText presentValue_et;
	/** 终值(输入框) */
	private EditText finalValue_et;
	/** 每年收付金额(文字) */
	private TextView annuity_tv;
	/** 现值(文字) */
	private TextView presentValue_tv;
	/** 终值(文字) */
	private TextView finalValue_tv;
	/** 到期年限|期数(文字) */
	private TextView year_tv;
	/** 结果(文字) */
	private TextView out_tv;
	/** 到期年限|期数 */
	private int year;
	/** 年利率 */
	private Double rate;
	/** 每年收付金额 */
	private Double annuity;
	/** 现值 */
	private Double presentValue;
	/** 终值 */
	private Double finalValue;
	/**
	 * 标记1：计算现值 2：计算年金现值 3：计算终值 4：计算年金终值
	 */
	private int mark = 0;
	/**
	 * 标记1：每月 2：每年
	 */
	private int mark2 = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 设置全屏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calculator8);
		initBaseActivity();
		// 找到所有的RadioGroup
		radioGroup = (RadioGroup) findViewById(R.id.finance_RadioGroup);
		radioGroup.setOnCheckedChangeListener(new radioGroupListener());
		period = (RadioGroup) findViewById(R.id.period);
		period.setOnCheckedChangeListener(new radioGroupListener());
		// 找到所有的EditText
		year_et = (EditText) findViewById(R.id.finance_year_et);
		rate_et = (EditText) findViewById(R.id.finance_rate_et);
		annuity_et = (EditText) findViewById(R.id.finance_annuity_et);
		presentValue_et = (EditText) findViewById(R.id.finance_presentValue_et);
		finalValue_et = (EditText) findViewById(R.id.finance_finalValue_et);
		// 找到所有的TextView
		annuity_tv = (TextView) findViewById(R.id.finance_annuity_tv);
		presentValue_tv = (TextView) findViewById(R.id.finance_presentValue_tv);
		finalValue_tv = (TextView) findViewById(R.id.finance_finalValue_tv);
		year_tv = (TextView) findViewById(R.id.finance_year_tv);
		out_tv = (TextView) findViewById(R.id.finance_out_tv);
		// 找到所有的Button
		calculateButton = (Button) findViewById(R.id.finance_calculate_bt);
		calculateButton.setOnClickListener(new ButtonListener());
		titleView.setTitleText("个人理财");
	}

	/** 选择计算类型的RadioGroup的监听事件 */
	private class radioGroupListener implements
			RadioGroup.OnCheckedChangeListener {

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.finance_RadioButton1:
				// 清空EditText里的值
				cleanEditText();
				// 设置EditText是否可以显示
				annuity_et.setVisibility(View.GONE);
				period.setVisibility(View.GONE);
				presentValue_et.setVisibility(View.GONE);
				finalValue_et.setVisibility(View.VISIBLE);
				// 设置TextView是否可以显示
				annuity_tv.setVisibility(View.GONE);
				presentValue_tv.setVisibility(View.GONE);
				finalValue_tv.setVisibility(View.VISIBLE);
				year_tv.setText("到期年限");

				mark = 1;
				break;
			case R.id.finance_RadioButton2:
				// 清空EditText里的值
				cleanEditText();
				// 设置EditText是否可以显示
				annuity_et.setVisibility(View.VISIBLE);
				period.setVisibility(View.VISIBLE);
				presentValue_et.setVisibility(View.GONE);
				finalValue_et.setVisibility(View.GONE);
				// 设置TextView是否可以显示
				annuity_tv.setVisibility(View.VISIBLE);
				presentValue_tv.setVisibility(View.GONE);
				finalValue_tv.setVisibility(View.GONE);
				year_tv.setText("期数");

				mark = 2;
				break;
			case R.id.finance_RadioButton3:
				// 清空EditText里的值
				cleanEditText();
				// 设置EditText是否可以显示
				annuity_et.setVisibility(View.GONE);
				period.setVisibility(View.GONE);
				presentValue_et.setVisibility(View.VISIBLE);
				finalValue_et.setVisibility(View.GONE);
				// 设置TextView是否可以显示
				annuity_tv.setVisibility(View.GONE);
				presentValue_tv.setVisibility(View.VISIBLE);
				finalValue_tv.setVisibility(View.GONE);
				year_tv.setText("到期年限");

				mark = 3;
				break;
			case R.id.finance_RadioButton4:
				// 清空EditText里的值
				cleanEditText();
				// 设置EditText是否可以显示
				annuity_et.setVisibility(View.VISIBLE);
				period.setVisibility(View.VISIBLE);
				presentValue_et.setVisibility(View.GONE);
				finalValue_et.setVisibility(View.GONE);
				// 设置TextView是否可以显示
				annuity_tv.setVisibility(View.VISIBLE);
				presentValue_tv.setVisibility(View.GONE);
				finalValue_tv.setVisibility(View.GONE);
				year_tv.setText("期数");

				mark = 4;
				break;
			case R.id.finance_month_rb:
				mark2 = 1;
				break;
			case R.id.finance_year_rb:
				mark2 = 2;
				break;
			}

		}

	}

	private class ButtonListener implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.finance_calculate_bt:
				// 获取输入框的数字，必须先取值！！！
				String Syear = year_et.getText().toString();
				String Srate = rate_et.getText().toString();
				String Sannuity = annuity_et.getText().toString();
				String SpresentValue = presentValue_et.getText().toString();
				String SfinalValue = finalValue_et.getText().toString();

				if (mark == 1 && Syear.length() != 0
						&& SfinalValue.length() != 0 && Srate.length() != 0) {
					year = Integer.parseInt(Syear);
					rate = Double.parseDouble(Srate);
					finalValue = Double.parseDouble(SfinalValue);
					Double a = calculatePresentValue(year, finalValue, rate);
					out_tv.setText("计算结果：\n现值为：" + String.format("%.2f", a));
				} else if (mark == 2 && Syear.length() != 0
						&& Sannuity.length() != 0 && Srate.length() != 0) {
					year = Integer.parseInt(Syear);
					rate = Double.parseDouble(Srate);
					annuity = Double.parseDouble(Sannuity);
					Double a = calculateYearPresentValue(year, annuity, rate);
					out_tv.setText("计算结果：\n年金现值为：" + String.format("%.2f", a));
				} else if (mark == 3 && Syear.length() != 0
						&& SpresentValue.length() != 0 && Srate.length() != 0) {
					year = Integer.parseInt(Syear);
					rate = Double.parseDouble(Srate);
					presentValue = Double.parseDouble(SpresentValue);
					Double a = calculateFinalValue(year, presentValue, rate);
					out_tv.setText("计算结果：\n终值为：" + String.format("%.2f", a));
				} else if (mark == 4 && Syear.length() != 0
						&& Sannuity.length() != 0 && Srate.length() != 0) {
					year = Integer.parseInt(Syear);
					rate = Double.parseDouble(Srate);
					annuity = Double.parseDouble(Sannuity);
					Double a = calculateYearFinalValue(year, annuity, rate);
					out_tv.setText("计算结果：\n年金终值为：" + String.format("%.2f", a));
				} else {
					ShowToast("输入数字不能为空！");
				}

				break;
			}
		}

	}

	/**
	 * 计算现值
	 * 
	 * @param year
	 * @param finalValue
	 * @param rate
	 * @return
	 */
	private Double calculatePresentValue(int year, Double finalValue,
			Double rate) {
		Double result, a;
		a = Math.pow(1 + rate, year);
		result = finalValue / a;
		return result;
	}

	/**
	 * 计算终值
	 * 
	 * @param year
	 * @param presentValue
	 * @param rate
	 * @return
	 */
	private Double calculateFinalValue(int year, Double presentValue,
			Double rate) {
		Double result, a;
		a = Math.pow(1 + rate, year);
		result = presentValue * a;
		return result;
	}

	/**
	 * 计算年金现值
	 * 
	 * @param year
	 * @param rate
	 * @return
	 */
	private Double calculateYearPresentValue(int year, Double annuity,
			Double rate) {
		if (mark2 == 1) {
			rate = rate / 12;
		}
		Double result, a, b, c;
		a = Math.pow(1 + rate, year);
		b = 1 / a;
		c = 1 - b;
		result = annuity * c / rate;
		return result;
	}

	/**
	 * 计算年金终值
	 * 
	 * @param year
	 * @param annuity
	 * @param rate
	 * @return
	 */
	private Double calculateYearFinalValue(int year, Double annuity, Double rate) {
		if (mark2 == 1) {
			rate = rate / 12;
		}
		Double result, a, b, c;
		a = Math.pow(1 + rate, year);
		b = a - 1;
		c = b / rate;
		result = annuity * c;
		return result;
	}

	/**
	 * 清空函数
	 */
	public void cleanEditText() {
		year_et.setText("");
		rate_et.setText("");
		annuity_et.setText("");
		presentValue_et.setText("");
		finalValue_et.setText("");
		out_tv.setText("");
	}

	/** 返回上一个界面 */
	public void goBack() {
		finish();
	}

}