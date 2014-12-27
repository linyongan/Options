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
 * Delta��ܸ˱���ҳ��
 */
public class Calculator7 extends CalculateBaseActivity {
	/** ���㰴ť */
	private Button calculateButton;
	/** ���֤ȯ�仯��(�����) */
	private EditText editText1;
	/** ��Ȩ�۸�仯��(�����) */
	private EditText editText2;
	/** ��ļ۸�(�����) */
	private EditText editText3;
	/** ��Ȩ�۸�(�����) */
	private EditText editText4;
	/** Delta(�����) */
	private EditText editText5;
	/** ���(����) */
	private TextView out_tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* ����ȫ�� */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calculator7);
		initBaseActivity();
		// �ҵ����е�TextView
		out_tv = (TextView) findViewById(R.id.Button7_out_tv);
		// �ҵ����е�EditText
		editText1 = (EditText) findViewById(R.id.Button7_editText1);
		editText2 = (EditText) findViewById(R.id.Button7_editText2);
		editText3 = (EditText) findViewById(R.id.Button7_editText3);
		editText4 = (EditText) findViewById(R.id.Button7_editText4);
		editText5 = (EditText) findViewById(R.id.Button7_editText5);
		// �ҵ����е�Button
		calculateButton = (Button) findViewById(R.id.Button7_calculate_bt);
		calculateButton.setOnClickListener(new ButtonListener());
		titleView.setTitleText("Delta��ܸ˱���");
	}

	private class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Button7_calculate_bt:
				// ��ȡ���������֣�������ȡֵ������
				String test1 = editText1.getText().toString();
				String test2 = editText2.getText().toString();
				String test3 = editText3.getText().toString();
				String test4 = editText4.getText().toString();
				String test5 = editText5.getText().toString();
				String out = "��������\n";
				if (test1.length() != 0 && test2.length() != 0) {
					out = out
							+ "Delta: "
							+ String.format("%.2f", Double.parseDouble(test2)
									/ Double.parseDouble(test1));
				}
				if (test5.length() != 0 && test3.length() != 0
						&& test4.length() != 0) {
					out = out
							+ "\n�ܸ˱���: "
							+ String.format(
									"%.2f",
									Double.parseDouble(test5)
											* Double.parseDouble(test3)
											/ Double.parseDouble(test4));
				}
				out_tv.setText(out);
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