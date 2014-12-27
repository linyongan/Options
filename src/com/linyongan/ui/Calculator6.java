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
 * ���ڼ�ֵ��ʱ���ֵҳ��
 */
public class Calculator6 extends CalculateBaseActivity {
	/** ���㰴ť */
	private Button calculateButton;
	/** ��Ĺɼ�(�����) */
	private EditText editText1;
	/** ��Ȩ��(�����) */
	private EditText editText2;
	/** Ȩ����(�����) */
	private EditText editText3;
	/** ���(����) */
	private TextView out_tv;
	private RadioGroup radioGroup;
	/**
	 * ���1���Ϲ���Ȩ 2���Ϲ���Ȩ
	 */
	private int mark = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* ����ȫ�� */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calculator6);
		initBaseActivity();
		// �ҵ����е�RadioGroup
		radioGroup = (RadioGroup) findViewById(R.id.Button6_radioGroup);
		radioGroup.setOnCheckedChangeListener(new radioGroupListener());
		// �ҵ����е�TextView
		out_tv = (TextView) findViewById(R.id.Button6_out_tv);
		// �ҵ����е�EditText
		editText1 = (EditText) findViewById(R.id.Button6_editText1);
		editText2 = (EditText) findViewById(R.id.Button6_editText2);
		editText3 = (EditText) findViewById(R.id.Button6_editText3);
		// �ҵ����е�Button
		calculateButton = (Button) findViewById(R.id.Button6_calculate_bt);
		calculateButton.setOnClickListener(new ButtonListener());
		titleView.setTitleText("���ڼ�ֵ��ʱ���ֵ");
	}

	/** RadioGroup�ļ����¼� */
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
				// ��ȡ���������֣�������ȡֵ������
				String test1 = editText1.getText().toString();
				String test2 = editText2.getText().toString();
				String test3 = editText3.getText().toString();
				String out = "��������\n";
				if (test1.length() != 0 && test2.length() != 0
						&& test3.length() != 0) {
					if (mark == 1) {
						double d1 = Math.max(
								(Double.parseDouble(test1) - Double
										.parseDouble(test2)), 0);
						out = out
								+ "�Ϲ���Ȩ���ڼ�ֵ: "
								+ String.format("%.2f", d1)
								+ "\n�Ϲ���Ȩʱ���ֵ: "
								+ String.format("%.2f",
										Double.parseDouble(test3) - d1);
					}
					if (mark == 2) {
						double d2 = Math.max(
								(Double.parseDouble(test2) - Double
										.parseDouble(test1)), 0);
						out = out
								+ "�Ϲ���Ȩ���ڼ�ֵ: "
								+ String.format("%.2f", d2)
								+ "\n�Ϲ���Ȩ���ڼ�ֵ: "
								+ String.format("%.2f",
										Double.parseDouble(test3) - d2);
					}
					out_tv.setText(out);
				} else {
					ShowToast("�������ֲ���Ϊ�գ�");
					out_tv.setText("���������������ֲ���Ϊ�գ�");
				}

				break;
			}

		}
	}

	/**
	 * ��պ���
	 */
	public void cleanEditText() {
		editText1.setText("");
		editText2.setText("");
		editText3.setText("");
		out_tv.setText("");
	}

	/** ������һ������ */
	public void goBack() {
		finish();
	}
}