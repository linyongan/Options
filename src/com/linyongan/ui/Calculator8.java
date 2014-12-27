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
 * �������ҳ��
 */
public class Calculator8 extends CalculateBaseActivity {
	/** ���㰴ť */
	private Button calculateButton;
	/** �������� */
	private RadioGroup radioGroup;
	/** ������� */
	private RadioGroup period;
	/** ��������|����(�����) */
	private EditText year_et;
	/** ������(�����) */
	private EditText rate_et;
	/** ÿ���ո����(�����) */
	private EditText annuity_et;
	/** ��ֵ(�����) */
	private EditText presentValue_et;
	/** ��ֵ(�����) */
	private EditText finalValue_et;
	/** ÿ���ո����(����) */
	private TextView annuity_tv;
	/** ��ֵ(����) */
	private TextView presentValue_tv;
	/** ��ֵ(����) */
	private TextView finalValue_tv;
	/** ��������|����(����) */
	private TextView year_tv;
	/** ���(����) */
	private TextView out_tv;
	/** ��������|���� */
	private int year;
	/** ������ */
	private Double rate;
	/** ÿ���ո���� */
	private Double annuity;
	/** ��ֵ */
	private Double presentValue;
	/** ��ֵ */
	private Double finalValue;
	/**
	 * ���1��������ֵ 2�����������ֵ 3��������ֵ 4�����������ֵ
	 */
	private int mark = 0;
	/**
	 * ���1��ÿ�� 2��ÿ��
	 */
	private int mark2 = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* ����ȫ�� */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calculator8);
		initBaseActivity();
		// �ҵ����е�RadioGroup
		radioGroup = (RadioGroup) findViewById(R.id.finance_RadioGroup);
		radioGroup.setOnCheckedChangeListener(new radioGroupListener());
		period = (RadioGroup) findViewById(R.id.period);
		period.setOnCheckedChangeListener(new radioGroupListener());
		// �ҵ����е�EditText
		year_et = (EditText) findViewById(R.id.finance_year_et);
		rate_et = (EditText) findViewById(R.id.finance_rate_et);
		annuity_et = (EditText) findViewById(R.id.finance_annuity_et);
		presentValue_et = (EditText) findViewById(R.id.finance_presentValue_et);
		finalValue_et = (EditText) findViewById(R.id.finance_finalValue_et);
		// �ҵ����е�TextView
		annuity_tv = (TextView) findViewById(R.id.finance_annuity_tv);
		presentValue_tv = (TextView) findViewById(R.id.finance_presentValue_tv);
		finalValue_tv = (TextView) findViewById(R.id.finance_finalValue_tv);
		year_tv = (TextView) findViewById(R.id.finance_year_tv);
		out_tv = (TextView) findViewById(R.id.finance_out_tv);
		// �ҵ����е�Button
		calculateButton = (Button) findViewById(R.id.finance_calculate_bt);
		calculateButton.setOnClickListener(new ButtonListener());
		titleView.setTitleText("�������");
	}

	/** ѡ��������͵�RadioGroup�ļ����¼� */
	private class radioGroupListener implements
			RadioGroup.OnCheckedChangeListener {

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.finance_RadioButton1:
				// ���EditText���ֵ
				cleanEditText();
				// ����EditText�Ƿ������ʾ
				annuity_et.setVisibility(View.GONE);
				period.setVisibility(View.GONE);
				presentValue_et.setVisibility(View.GONE);
				finalValue_et.setVisibility(View.VISIBLE);
				// ����TextView�Ƿ������ʾ
				annuity_tv.setVisibility(View.GONE);
				presentValue_tv.setVisibility(View.GONE);
				finalValue_tv.setVisibility(View.VISIBLE);
				year_tv.setText("��������");

				mark = 1;
				break;
			case R.id.finance_RadioButton2:
				// ���EditText���ֵ
				cleanEditText();
				// ����EditText�Ƿ������ʾ
				annuity_et.setVisibility(View.VISIBLE);
				period.setVisibility(View.VISIBLE);
				presentValue_et.setVisibility(View.GONE);
				finalValue_et.setVisibility(View.GONE);
				// ����TextView�Ƿ������ʾ
				annuity_tv.setVisibility(View.VISIBLE);
				presentValue_tv.setVisibility(View.GONE);
				finalValue_tv.setVisibility(View.GONE);
				year_tv.setText("����");

				mark = 2;
				break;
			case R.id.finance_RadioButton3:
				// ���EditText���ֵ
				cleanEditText();
				// ����EditText�Ƿ������ʾ
				annuity_et.setVisibility(View.GONE);
				period.setVisibility(View.GONE);
				presentValue_et.setVisibility(View.VISIBLE);
				finalValue_et.setVisibility(View.GONE);
				// ����TextView�Ƿ������ʾ
				annuity_tv.setVisibility(View.GONE);
				presentValue_tv.setVisibility(View.VISIBLE);
				finalValue_tv.setVisibility(View.GONE);
				year_tv.setText("��������");

				mark = 3;
				break;
			case R.id.finance_RadioButton4:
				// ���EditText���ֵ
				cleanEditText();
				// ����EditText�Ƿ������ʾ
				annuity_et.setVisibility(View.VISIBLE);
				period.setVisibility(View.VISIBLE);
				presentValue_et.setVisibility(View.GONE);
				finalValue_et.setVisibility(View.GONE);
				// ����TextView�Ƿ������ʾ
				annuity_tv.setVisibility(View.VISIBLE);
				presentValue_tv.setVisibility(View.GONE);
				finalValue_tv.setVisibility(View.GONE);
				year_tv.setText("����");

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
				// ��ȡ���������֣�������ȡֵ������
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
					out_tv.setText("��������\n��ֵΪ��" + String.format("%.2f", a));
				} else if (mark == 2 && Syear.length() != 0
						&& Sannuity.length() != 0 && Srate.length() != 0) {
					year = Integer.parseInt(Syear);
					rate = Double.parseDouble(Srate);
					annuity = Double.parseDouble(Sannuity);
					Double a = calculateYearPresentValue(year, annuity, rate);
					out_tv.setText("��������\n�����ֵΪ��" + String.format("%.2f", a));
				} else if (mark == 3 && Syear.length() != 0
						&& SpresentValue.length() != 0 && Srate.length() != 0) {
					year = Integer.parseInt(Syear);
					rate = Double.parseDouble(Srate);
					presentValue = Double.parseDouble(SpresentValue);
					Double a = calculateFinalValue(year, presentValue, rate);
					out_tv.setText("��������\n��ֵΪ��" + String.format("%.2f", a));
				} else if (mark == 4 && Syear.length() != 0
						&& Sannuity.length() != 0 && Srate.length() != 0) {
					year = Integer.parseInt(Syear);
					rate = Double.parseDouble(Srate);
					annuity = Double.parseDouble(Sannuity);
					Double a = calculateYearFinalValue(year, annuity, rate);
					out_tv.setText("��������\n�����ֵΪ��" + String.format("%.2f", a));
				} else {
					ShowToast("�������ֲ���Ϊ�գ�");
				}

				break;
			}
		}

	}

	/**
	 * ������ֵ
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
	 * ������ֵ
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
	 * ���������ֵ
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
	 * ���������ֵ
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
	 * ��պ���
	 */
	public void cleanEditText() {
		year_et.setText("");
		rate_et.setText("");
		annuity_et.setText("");
		presentValue_et.setText("");
		finalValue_et.setText("");
		out_tv.setText("");
	}

	/** ������һ������ */
	public void goBack() {
		finish();
	}

}