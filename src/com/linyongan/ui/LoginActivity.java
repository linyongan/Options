package com.linyongan.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.listener.SaveListener;

import com.linyongan.model.Person;
import com.linyongan.util.Util;
import com.linyongan.view.TitleView;

public class LoginActivity extends Activity {

	EditText name_et, password_et;
	Button login;
	TextView register;
	/** 标题 */
	private TitleView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);

		name_et = (EditText) findViewById(R.id.login_username_et);
		password_et = (EditText) findViewById(R.id.login_password_et);
		login = (Button) findViewById(R.id.login_login_btn);
		register = (TextView) findViewById(R.id.login_register_btn);
		titleView = (TitleView) findViewById(R.id.TitleView);

		login.setOnClickListener(new ButtonListener());
		register.setOnClickListener(new ButtonListener());
		titleView.setBackButtonListener(new ButtonListener());

		titleView.setTitle("登录账号");

	}

	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.login_login_btn:
				final Person user = new Person();
				String name = name_et.getText().toString();
				String password = password_et.getText().toString();

				if (TextUtils.isEmpty(name)) {
					Util.errorFul(LoginActivity.this, 1);
					return;
				}

				if (TextUtils.isEmpty(password)) {
					Util.errorFul(LoginActivity.this, 2);
					return;
				}

				if (!Util.isNetworkConnected(LoginActivity.this)) {
					Util.errorFul(LoginActivity.this, 9016);
					return;
				}
				final ProgressDialog progress = new ProgressDialog(
						LoginActivity.this);
				progress.setMessage("正在登陆...");
				progress.setCanceledOnTouchOutside(false);
				progress.show();

				user.setUsername(name);
				user.setPassword(password);
				user.login(LoginActivity.this, new SaveListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						progress.dismiss();
						Util.ShowToast(LoginActivity.this, "登陆成功！");
						goBack();
					}

					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
						progress.dismiss();
						Util.errorFul(LoginActivity.this, code);
					}
				});
				break;
			case R.id.login_register_btn:
				Util.goToByClass(LoginActivity.this, RegisterActivity.class);
				break;

			case R.id.titleview_back_btn:
				goBack();
				break;
			}
		}
	}

	public void goBack() {
		Util.goBack(LoginActivity.this, LearnActivity.class);
	}
}
