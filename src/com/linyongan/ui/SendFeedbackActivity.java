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
	/** 标题 */
	private TitleView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.feedback_send);

		titleView = (TitleView) findViewById(R.id.TitleView);
		titleView.setTitle("发表消息");
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
					Util.ShowToast(SendFeedbackActivity.this, "请勿重复发表消息");
				} else {
					msg = content;
					// 发送反馈信息
					saveFeedbackMsg(content);
					Util.ShowToast(SendFeedbackActivity.this, "您的信息已发送");
				}
			} else {
				Util.ShowToast(SendFeedbackActivity.this, "请填写要发表的内容");
			}
		}

	}

	/**
	 * 保存反馈信息到服务器
	 * 
	 * @param msg
	 *            反馈信息
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
			Util.ShowToast(SendFeedbackActivity.this, "请先登录账号！");
		}

	}

}
