package com.linyongan.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.listener.SaveListener;

import com.linyongan.model.Person;
import com.linyongan.util.Util;
import com.linyongan.view.TitleView;

public class RegisterActivity extends Activity {

	EditText name_et, password_et, email_et;
	Button register;
	/** 标题 */
	private TitleView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		name_et = (EditText) findViewById(R.id.register_username_et);
		password_et = (EditText) findViewById(R.id.register_password_et);
		email_et = (EditText) findViewById(R.id.register_e);
		register = (Button) findViewById(R.id.register_register_btn);
		titleView = (TitleView) findViewById(R.id.TitleView);

		register.setOnClickListener(new ButtonListener());
		titleView.setBackButtonListener(new ButtonListener());

		titleView.setTitle("注册账号");
	}

	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.register_register_btn:
				final Person user = new Person();
				String name = name_et.getText().toString();
				String password = password_et.getText().toString();
				String email = email_et.getText().toString();
				// 判断输入的邮箱是否合法
				if (!Util.isEmailValid(email)) {
					Util.errorFul(RegisterActivity.this, 301);
					return;
				}
				// 判断输入的昵称是否为空
				if (TextUtils.isEmpty(name)) {
					Util.errorFul(RegisterActivity.this, 1);
					return;
				}
				// 判断输入的密码是否为空
				if (TextUtils.isEmpty(password)) {
					Util.errorFul(RegisterActivity.this, 2);
					return;
				}
				//判断网络是否连接
				if (!Util.isNetworkConnected(RegisterActivity.this)) {
					Util.errorFul(RegisterActivity.this, 9016);
					return;
				}
				final ProgressDialog progress = new ProgressDialog(
						RegisterActivity.this);
				progress.setMessage("正在注册...");
				progress.setCanceledOnTouchOutside(false);
				progress.show();

				user.setUsername(name);
				user.setPassword(password);
				user.setEmail(email);
				user.signUp(RegisterActivity.this, new SaveListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						progress.dismiss();
						Util.ShowToast(RegisterActivity.this, "注册成功！");
						goBack();
					}

					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						progress.dismiss();
						Util.errorFul(RegisterActivity.this, code);
					}
				});
				break;

			case R.id.titleview_back_btn:
				goBack();
				break;
			}
		}

	}

	public void goBack() {
		Util.goBack(RegisterActivity.this, LoginActivity.class);
	}
}
