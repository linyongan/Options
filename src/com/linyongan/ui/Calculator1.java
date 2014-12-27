package com.linyongan.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.linyongan.config.Constants;
import com.linyongan.db.CalculateDbManger;
import com.linyongan.ui.base.CalculateBaseActivity;

/**
 * ��Ȩ���ۼ۸�ҳ��
 */
public class Calculator1 extends CalculateBaseActivity {
	/** ���㰴ť */
	private Button calculateButton;
	private RadioGroup radioGroup;
	/**
	 * ���1���Ϲ���Ȩ���ۼ۸� 2���Ϲ���Ȩ���ۼ۸�
	 */
	private int mark = 1;
	/**
	 * ���1���������� 2����������
	 */
	private int mark2 = 1;
	/** ����ʲ��ļ۸�(�����) */
	private EditText editText1;
	/** ��Ȩ�۸�(�����) */
	private EditText editText2;
	/** ����ʲ��۸񲨶���(�����) */
	private EditText editText3;
	/** ��������(�����) */
	private EditText editText4;
	/** ������(�����) */
	private EditText editText5;
	/** ���(����) */
	private TextView out_tv;
	/** ���ݿ������ */
	private CalculateDbManger dbmanger;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* ����ȫ�� */
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.calculator1);
		initBaseActivity();
		dbmanger = new CalculateDbManger(this);
		// �ҵ����е�TextView
		out_tv = (TextView) findViewById(R.id.Button1_out_tv);
		// �ҵ����е�EditText
		editText1 = (EditText) findViewById(R.id.Button1_editText1);
		editText2 = (EditText) findViewById(R.id.Button1_editText2);
		editText3 = (EditText) findViewById(R.id.Button1_editText3);
		editText4 = (EditText) findViewById(R.id.Button1_editText4);
		editText5 = (EditText) findViewById(R.id.Button1_editText5);
		// �ҵ����е�RadioGroup
		radioGroup = (RadioGroup) findViewById(R.id.Button1_radioGroup);
		radioGroup.setOnCheckedChangeListener(new radioGroupListener());
		// �ҵ����е�Button
		calculateButton = (Button) findViewById(R.id.Button1_calculate_bt);
		calculateButton.setOnClickListener(new ButtonListener());
		titleView.setTitleText("��Ȩ���ۼ۸�");
	}

	/** RadioGroup�ļ����¼� */
	private class radioGroupListener implements
			RadioGroup.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.Button1_radioButton1:
				mark = 1;
				break;
			case R.id.Button1_radioButton2:
				mark = 2;
				break;
			}
		}

	}

	private class ButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Button1_calculate_bt:
				// ��ȡ���������֣�������ȡֵ������
				String test1 = editText1.getText().toString();
				String test2 = editText2.getText().toString();
				String test3 = editText3.getText().toString();
				String test4 = editText4.getText().toString();
				String test5 = editText5.getText().toString();

				if (mark == 1 && test1.length() != 0 && test2.length() != 0
						&& test3.length() != 0 && test4.length() != 0
						&& test5.length() != 0) {
					mark2 = 1;
					Double a = calculate1(Double.parseDouble(test1),
							Double.parseDouble(test2),
							Double.parseDouble(test3),
							Double.parseDouble(test4),
							Double.parseDouble(test5));
					if (mark2 == 1) {
						out_tv.setText("��������\n�Ϲ���Ȩ���ۼ۸�Ϊ��"
								+ String.format("%.2f", a - 0.005));
					} else {
						out_tv.setText("������������������������������룡");
					}

				} else if (mark == 2 && test1.length() != 0
						&& test2.length() != 0 && test3.length() != 0
						&& test4.length() != 0 && test5.length() != 0) {
					mark2 = 1;
					Double a = calculate2(Double.parseDouble(test1),
							Double.parseDouble(test2),
							Double.parseDouble(test3),
							Double.parseDouble(test4),
							Double.parseDouble(test5));
					if (mark2 == 1) {
						out_tv.setText("��������\n�Ϲ���Ȩ���ۼ۸�Ϊ��"
								+ String.format("%.2f", a - 0.005));
					} else {
						out_tv.setText("������������������������������룡");
					}
				} else {
					ShowToast("�������ֲ���Ϊ�գ�");
				}
				break;
			}

		}
	}

	/**
	 * �����Ϲ���Ȩ���ۼ۸�
	 * 
	 * @param a
	 *            ����ʲ��ļ۸�
	 * @param b
	 *            ��Ȩ�۸�
	 * @param c
	 *            ����ʲ��۸񲨶���
	 * @param d
	 *            ��������
	 * @param e
	 *            ������
	 * @return
	 */
	private Double calculate1(Double a, Double b, Double c, Double d, Double e) {
		Double d1 = (Math.log(a / b) + (e + Math.pow(c, 2) * 0.5) * d)
				/ (c * Math.sqrt(d));
		System.out.println("--d1:-- " + d1);
		Double d2 = d1 - c * Math.sqrt(d);
		System.out.println("--d2:-- " + d2);
		Double result = a * change(d1) - b * Math.pow(2.71, (-e * d))
				* change(d2);
		return result;
	}

	/**
	 * 
	 * �����Ϲ���Ȩ���ۼ۸�
	 * 
	 * @param a
	 *            ����ʲ��ļ۸�
	 * @param b
	 *            ��Ȩ�۸�
	 * @param c
	 *            ����ʲ��۸񲨶���
	 * @param d
	 *            ��������
	 * @param e
	 *            ������
	 * @return
	 */
	private Double calculate2(Double a, Double b, Double c, Double d, Double e) {
		Double d1 = (Math.log(a / b) + (e + Math.pow(c, 2) * 0.5) * d)
				/ (c * Math.sqrt(d));
		System.out.println("--d1:-- " + d1);
		Double d2 = d1 - c * Math.sqrt(d);
		System.out.println("--d2:-- " + d2);
		Double result = b * Math.pow(2.718, -e * d) * change(-d2) - a
				* change(-d1);
		return result;

	}

	/**
	 * ��Doubleת��String���� �����߼��жϣ������ݿ���ҵ�N(d1)��ֵ���ٷ��ظ�������
	 * 
	 * @param a
	 * @return
	 */
	private Double change(Double a) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.000");
		String string = df.format(a);
		// ���С�������һλ��0����ɾ����
		if (string.endsWith("0")) {
			string = string.substring(0, string.length() - 1);
			if (string.endsWith("0")) {
				string = string.substring(0, string.length() - 1);
				if (string.endsWith("0")) {
					string = string.substring(0, string.length() - 2);
					if (string.endsWith("0")) {
						string = "0";
					}
				}
			}
		}
		System.out.println("--string:-- " + string);
		dbmanger.open();
		Cursor cursor = dbmanger.search(string);
		if (cursor.moveToFirst()) {
			String string1 = cursor.getString(cursor
					.getColumnIndex(Constants.CalculateTable.Y));
			System.out.println("--string1:-- " + string1);
			return Double.parseDouble(string1);
		} else {
			mark2 = 2;
			ShowToast("�������󣡣���");
		}
		dbmanger.close();
		return a;

	}

	/**
	 * ��պ���
	 */
	@Override
	public void cleanEditText() {
		editText1.setText("");
		editText2.setText("");
		editText3.setText("");
		editText4.setText("");
		editText5.setText("");
		out_tv.setText("");
	}

	/** ������һ������ */
	@Override
	public void goBack() {
		finish();
	}
}