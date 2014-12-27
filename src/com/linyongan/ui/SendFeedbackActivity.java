package com.linyongan.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.listener.SaveListener;

import com.linyongan.model.Feedback;
import com.linyongan.model.Person;
import com.linyongan.util.Util;
import com.linyongan.view.TitleView;

public class SendFeedbackActivity extends Activity {

	EditText et_content;
	static String msg;
	Button send;
	Person bmobUser;
	/** ���� */
	private TitleView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.feedback_send);

		titleView = (TitleView) findViewById(R.id.TitleView);
		titleView.setTitle("������Ϣ");
		titleView.setBackButtonListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goBack();
			}
		});

		bmobUser = Util.getUser(getApplicationContext());
		et_content = (EditText) findViewById(R.id.et_content);
		send = (Button) findViewById(R.id.sendfeedback);
		send.setOnClickListener(new ButtonListener());
	}

	public void goBack() {
		Util.goBack(SendFeedbackActivity.this, FeedbackListActivity.class);
	}

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!Util.isNetworkConnected(SendFeedbackActivity.this)) {
				Util.errorFul(SendFeedbackActivity.this, 9016);
				return;
			}

			String content = et_content.getText().toString();
			if (!TextUtils.isEmpty(content)) {
				if (content.equals(msg)) {
					Util.ShowToast(SendFeedbackActivity.this, "�����ظ�������Ϣ");
				} else {
					msg = content;
					// ���ͷ�����Ϣ
					saveFeedbackMsg(content);
					Util.ShowToast(SendFeedbackActivity.this, "������Ϣ�ѷ���");
				}
			} else {
				Util.ShowToast(SendFeedbackActivity.this, "����дҪ���������");
			}
		}

	}

	/**
	 * ���淴����Ϣ��������
	 * 
	 * @param msg
	 *            ������Ϣ
	 */
	private void saveFeedbackMsg(String msg) {
		if (bmobUser != null) {
			Feedback feedback = new Feedback();
			feedback.setContent(msg);
			feedback.setContacts(bmobUser.getUsername());
			feedback.save(this, new SaveListener() {

				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onFailure(int code, String arg0) {
					// TODO Auto-generated method stub
				}
			});
		} else {
			Util.ShowToast(SendFeedbackActivity.this, "���ȵ�¼�˺ţ�");
		}

	}

}
