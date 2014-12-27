package com.linyongan.ui;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.linyongan.ui.base.BaseActivity;
import com.linyongan.util.Util;

/**
 * ���Ҳ���ҳ��
 */
public class TestActivity extends BaseActivity {
	/** �������԰�ť */
	private Button baseButton;
	/** ���ײ���1��ť */
	private Button advanceButton1;
	/** ���ײ���2��ť */
	private Button advanceButton2;
	/** �����ղذ�ť */
	private Button collectButton;
	/** ���Գɼ���ť */
	private Button gradeButton;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.test_main);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		baseButton = (Button) findViewById(R.id.test_baseButton);
		advanceButton1 = (Button) findViewById(R.id.test_advanceButton1);
		advanceButton2 = (Button) findViewById(R.id.test_advanceButton2);
		collectButton = (Button) findViewById(R.id.test_collectButton);
		gradeButton = (Button) findViewById(R.id.test_gradeButton);

	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		baseButton.setOnClickListener(new ButtonListener());
		advanceButton1.setOnClickListener(new ButtonListener());
		advanceButton2.setOnClickListener(new ButtonListener());
		collectButton.setOnClickListener(new ButtonListener());
		gradeButton.setOnClickListener(new ButtonListener());
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		initBackButton();
		titleView.setTitle("���Ҳ���");
		super.addMenu(this);
	}

	@Override
	public void goBack() {
		Util.goBack(TestActivity.this, LearnActivity.class);
	}

	/**
	 * button�ļ����¼�
	 * 
	 * @author yongan
	 * 
	 */
	private class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.test_baseButton:
				Intent intent2 = new Intent(TestActivity.this,
						TestingActivity.class);
				int testNum2 = 120;
				// �ǵ÷����ַ�������������
				intent2.putExtra("testNum", testNum2 + "");
				startActivity(intent2);
				overridePendingTransition(R.anim.activity_left_in,
						R.anim.activity_left_out);
				break;
			case R.id.test_advanceButton1:
				Intent intent3 = new Intent(TestActivity.this,
						TestingActivity.class);
				int testNum3 = 30;
				// �ǵ÷����ַ�������������
				intent3.putExtra("testNum", testNum3 + "");
				startActivity(intent3);
				overridePendingTransition(R.anim.activity_left_in,
						R.anim.activity_left_out);
				break;
			case R.id.test_advanceButton2:
				Intent intent4 = new Intent(TestActivity.this,
						TestingActivity.class);
				int testNum4 = 90;
				// �ǵ÷����ַ�������������
				intent4.putExtra("testNum", testNum4 + "");
				startActivity(intent4);
				overridePendingTransition(R.anim.activity_left_in,
						R.anim.activity_left_out);
				break;
			case R.id.test_collectButton:
				Util.goToByClass(TestActivity.this,
						TestCollectActivity.class);
				break;
			case R.id.test_gradeButton:
				Util.goToByClass(TestActivity.this,
						TestGradeActivity.class);
				break;
			}
		}

	}
}