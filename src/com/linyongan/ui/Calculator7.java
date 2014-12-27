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
 * Delta与杠杆倍数页面
 */
public class Calculator7 extends CalculateBaseActivity {
	/** 计算按钮 */
	private Button calculateButton;
	/** 标的证券变化量(输入框) */
	private EditText editText1;
	/** 期权价格变化量(输入框) */
	private EditText editText2;
	/** 标的价格(输入框) */
	private EditText editText3;
	/** 期权价格(输入框) */
	private EditText editText4;
	/** Delta(输入框) */
	private EditText editText5;
	/** 结果(文字) */
	private TextView out_tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 设置全屏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calculator7);
		initBaseActivity();
		// 找到所有的TextView
		out_tv = (TextView) findViewById(R.id.Button7_out_tv);
		// 找到所有的EditText
		editText1 = (EditText) findViewById(R.id.Button7_editText1);
		editText2 = (EditText) findViewById(R.id.Button7_editText2);
		editText3 = (EditText) findViewById(R.id.Button7_editText3);
		editText4 = (EditText) findViewById(R.id.Button7_editText4);
		editText5 = (EditText) findViewById(R.id.Button7_editText5);
		// 找到所有的Button
		calculateButton = (Button) findViewById(R.id.Button7_calculate_bt);
		calculateButton.setOnClickListener(new ButtonListener());
		titleView.setTitleText("Delta与杠杆倍数");
	}

	private class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Button7_calculate_bt:
				// 获取输入框的数字，必须先取值！！！
				String test1 = editText1.getText().toString();
				String test2 = editText2.getText().toString();
				String test3 = editText3.getText().toString();
				String test4 = editText4.getText().toString();
				String test5 = editText5.getText().toString();
				String out = "计算结果：\n";
				if (test1.length() != 0 && test2.length() != 0) {
					out = out
							+ "Delta: "
							+ String.format("%.2f", Double.parseDouble(test2)
									/ Double.parseDouble(test1));
				}
				if (test5.length() != 0 && test3.length() != 0
						&& test4.length() != 0) {
					out = out
							+ "\n杠杆倍数: "
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