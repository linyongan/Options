package com.linyongan.ui;

import com.linyongan.ui.base.CalculateBaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * ƽ������ҳ��
 */
public class Calculator5 extends CalculateBaseActivity {
	/** ���㰴ť */
	private Button calculateButton;
	/** ������Ȩ�۸�(�����) */
	private EditText editText1;
	/** ������Ȩ�۸�(�����) */
	private EditText editText2;
	/** ��Ʊ�ּ�(�����) */
	private EditText editText3;
	/** ʣ������(�����) */
	private EditText editText4;
	/** ��Ȩ�۸�(�����) */
	private EditText editText5;
	/** ���(����) */
	private TextView out_tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* ����ȫ�� */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calculator5);
		initBaseActivity();
		// �ҵ����е�TextView
		out_tv = (TextView) findViewById(R.id.Button5_out_tv);
		// �ҵ����е�EditText
		editText1 = (EditText) findViewById(R.id.Button5_editText1);
		editText2 = (EditText) findViewById(R.id.Button5_editText2);
		editText3 = (EditText) findViewById(R.id.Button5_editText3);
		editText4 = (EditText) findViewById(R.id.Button5_editText4);
		editText5 = (EditText) findViewById(R.id.Button5_editText5);
		// �ҵ����е�Button
		calculateButton = (Button) findViewById(R.id.Button5_calculate_bt);
		calculateButton.setOnClickListener(new ButtonListener());
		titleView.setTitleText("ƽ������");
	}

	private class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Button5_calculate_bt:
				// ��ȡ���������֣�������ȡֵ������
				String test1 = editText1.getText().toString();
				String test2 = editText2.getText().toString();
				String test3 = editText3.getText().toString();
				String test4 = editText4.getText().toString();
				String test5 = editText5.getText().toString();

				if (test1.length() != 0 && test2.length() != 0
						&& test3.length() != 0 && test4.length() != 0
						&& test5.length() != 0) {
					String out = "������:\n";
					double b1 = Double.parseDouble(test2)
							+ Double.parseDouble(test3)
							- Double.parseDouble(test1);
					double b2 = Double.parseDouble(test5) / b1 - 1;
					out = out
							+ "��ʼͶ��: "
							+ String.format("%.4f", b1)
							+ "\n��ĩ����: "
							+ String.format("%.4f", Double.parseDouble(test5))
							+ "\n�޷�������: "
							+ String.format("%.3f", b2 * 100)
							+ "%"
							+ "\n�껯������: "
							+ String.format("%.3f",
									b2 * 36500 / Double.parseDouble(test4))
							+ "%";
					out_tv.setText(out);
				} else {
					ShowToast("�������ֲ���Ϊ�գ�");
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
		editText4.setText("");
		editText5.setText("");
		out_tv.setText("");
	}

	/** ������һ������ */
	public void goBack() {
		finish();
	}
}