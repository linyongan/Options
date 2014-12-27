package com.linyongan.ui;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.linyongan.ui.base.BaseActivity;
import com.linyongan.util.Util;

/**
 * ��Ȩ������ҳ��
 */
public class CalculateActivity extends BaseActivity {
	/** ��Ȩ���ۼ۸� */
	private Button Button1;
	/** ��Ȩ�������� */
	private Button Button2;
	/** ��Ȩ���������� */
	private Button Button3;
	/** ��֤�� */
	private Button Button4;
	/** ��ȨDeltaֵ */
	private Button Button5;
	/** ���ڼ�ֵ&ʱ���ֵ */
	private Button Button6;
	/** Delta&�ܸ˱��� */
	private Button Button7;
	/** ������� */
	private Button Button8;
	/** ������� */
	private Button Button9;

	@Override
	public void setContentView() {
		setContentView(R.layout.calculate);
	}

	@Override
	public void initViews() {
		Button1 = (Button) findViewById(R.id.derivative_Button1);
		Button2 = (Button) findViewById(R.id.derivative_Button2);
		Button3 = (Button) findViewById(R.id.derivative_Button3);
		Button4 = (Button) findViewById(R.id.derivative_Button4);
		Button5 = (Button) findViewById(R.id.derivative_Button5);
		Button6 = (Button) findViewById(R.id.derivative_Button6);
		Button7 = (Button) findViewById(R.id.derivative_Button7);
		Button8 = (Button) findViewById(R.id.derivative_Button8);
		Button9 = (Button) findViewById(R.id.derivative_Button9);
	}

	@Override
	public void initListeners() {
		Button1.setOnClickListener(new ButtonListener());
		Button2.setOnClickListener(new ButtonListener());
		Button3.setOnClickListener(new ButtonListener());
		Button4.setOnClickListener(new ButtonListener());
		Button5.setOnClickListener(new ButtonListener());
		Button6.setOnClickListener(new ButtonListener());
		Button7.setOnClickListener(new ButtonListener());
		Button8.setOnClickListener(new ButtonListener());
		Button9.setOnClickListener(new ButtonListener());
	}

	@Override
	public void initData() {
		initBackButton();
		titleView.setTitle("��Ȩ����");
		super.addMenu(this);
	}

	@Override
	public void goBack() {
		finish();
	}

	/**
	 * Button�ļ����¼�����
	 * 
	 * @author yongan
	 * 
	 */
	private class ButtonListener implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.derivative_Button1:
				Util.goToNoFinish(CalculateActivity.this, Calculator1.class);
				break;
			case R.id.derivative_Button2:
				Util.goToNoFinish(CalculateActivity.this, Calculator2.class);
				break;
			case R.id.derivative_Button3:
				Util.goToNoFinish(CalculateActivity.this, Calculator3.class);
				break;
			case R.id.derivative_Button4:
				Util.goToNoFinish(CalculateActivity.this, Calculator4.class);
				break;
			case R.id.derivative_Button5:
				Util.goToNoFinish(CalculateActivity.this, Calculator5.class);
				break;
			case R.id.derivative_Button6:
				Util.goToNoFinish(CalculateActivity.this, Calculator6.class);
				break;
			case R.id.derivative_Button7:
				Util.goToNoFinish(CalculateActivity.this, Calculator7.class);
				break;
			case R.id.derivative_Button8:
				Util.goToNoFinish(CalculateActivity.this, Calculator8.class);
				break;
			case R.id.derivative_Button9:
				Util.goToNoFinish(CalculateActivity.this, Calculator9.class);
				break;
			}
		}
	}
}