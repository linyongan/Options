package com.linyongan.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.linyongan.config.Constants;
import com.linyongan.model.Person;
import com.linyongan.util.Util;
import com.linyongan.view.TitleView;

public class MyInfoActivity extends Activity {

	Button exit;
	TextView email, name;
	/** ����޸�ͷ�� */
	LinearLayout changehead;
	ImageView head;
	/** ���� */
	private TitleView titleView;
	/** ͷ������� */
	String HEAD_NAME;
	/** ��ǰ��¼���û� */
	Person user;
	PopupWindow popup;
	TextView changepw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		changehead = (LinearLayout) findViewById(R.id.myinfo_changehead);
		changehead.setOnClickListener(new ButtonListener());

		head = (ImageView) findViewById(R.id.myinfo_head);
		exit = (Button) findViewById(R.id.myinfo_exit);
		exit.setOnClickListener(new ButtonListener());
		email = (TextView) findViewById(R.id.myinfo_email);
		name = (TextView) findViewById(R.id.myinfo_name);
		changepw = (TextView) findViewById(R.id.myinfo_changepw);
		changepw.setOnClickListener(new ButtonListener());
		titleView = (TitleView) findViewById(R.id.TitleView);
		titleView.setTitle("������Ϣ");
		titleView.setBackButtonListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});

		user = Util.getUser(getApplicationContext());
		// ����û��Ѿ���¼
		if (user != null) {
			email.setText(user.getEmail());
			name.setText(user.getUsername());
			HEAD_NAME = user.getUsername() + ".jpg";
			// ���SD������
			if (Util.isSDCardUsabled()) {
				// ���ͷ��ͼƬ�Ƿ����
				if ((new File(Constants.basePath + HEAD_NAME)).exists() == true) {
					Bitmap bit = BitmapFactory.decodeFile(Constants.basePath
							+ HEAD_NAME);
					head.setImageBitmap(bit);
				}
			}
		}
	}

	class ButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.myinfo_changehead:
				ShowPickDialog();
				break;
			case R.id.myinfo_exit:
				// ��������û�
				BmobUser.logOut(MyInfoActivity.this);
				goBack();
				break;
			case R.id.myinfo_changepw:
				popupView();
				break;
			}
		}
	}

	/**
	 * ��SD��ѡ��ͼƬ
	 */
	private void ShowPickDialog() {

		/**
		 * �տ�ʼ�����Լ�Ҳ��֪��ACTION_PICK�Ǹ���ģ�����ֱ�ӿ�IntentԴ�룬
		 * ���Է�������ܶණ����Intent�Ǹ���ǿ��Ķ��������һ����ϸ�Ķ���
		 */
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		/**
		 * ������仰����������ʽд��һ����Ч��������� intent.setData(MediaStore.Images
		 * .Media.EXTERNAL_CONTENT_URI); intent.setType(""image/*");������������
		 * ���������Ҫ�����ϴ�����������ͼƬ����ʱ����ֱ��д�� ��"image/jpeg �� image/png�ȵ�����"
		 */
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, 1);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// �����ֱ�Ӵ�����ȡ
		case 1:
			if (data != null) {
				startPhotoZoom(data.getData());
			}
			break;
		// ȡ�òü����ͼƬ
		case 3:
			/**
			 * �ǿ��жϴ��һ��Ҫ��֤���������֤�Ļ��� �ڼ���֮��������ֲ����⣬Ҫ���²ü������� ��ǰ����ʱ���ᱨNullException
			 */
			if (data != null) {
				setPicToView(data);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/*
		 * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * ֱ��������Ctrl+F�ѣ�CROP ��֮ǰû��ϸ��������ʵ��׿ϵͳ���Ѿ����Դ�ͼƬ�ü�����, ��ֱ�ӵ����ؿ��
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// �������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			head.setImageBitmap(photo);
			SaveToSDTask saveSD = new SaveToSDTask(photo);
			saveSD.execute();
		}
	}

	/**
	 * �첽����ͷ�����ݵ��ڴ濨
	 */
	class SaveToSDTask extends AsyncTask<Object, Object, Object> {
		Bitmap bitmap;

		public SaveToSDTask(Bitmap map) {
			bitmap = map;
		}

		// �÷�����Ҫ���ڽ����첽����������������UI�̵߳��У������ڸ÷������У����ܶ�UI���еĿؼ��������ú��޸�
		@Override
		protected String doInBackground(Object... params) {
			if (Util.isSDCardUsabled()) {
				File f = null;
				// ����ļ��Ƿ����
				if ((new File(Constants.basePath + HEAD_NAME)).exists() == false) {
					// ���ļ������ڣ��ټ��һ��Ŀ¼�Ƿ����
					f = new File(Constants.basePath);
					// ��Ŀ¼�����ڣ��½���Ŀ¼
					if (!f.exists()) {
						f.mkdir();
					}
				}
				try {
					f = new File(Constants.basePath + HEAD_NAME);
					FileOutputStream out = new FileOutputStream(f);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out);
					out.flush();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			// ����ͼƬ��������
			SaveToWeb();
		}
	}

	/**
	 * ����ͼƬ��������
	 */
	public void SaveToWeb() {
		// �ϴ�ͼƬ
		String imgpath = Constants.basePath + HEAD_NAME;
		final BmobFile icon = new BmobFile(new File(imgpath));
		icon.uploadblock(this, new UploadFileListener() {
			@Override
			public void onSuccess() {
				user.setIcon(icon);
				user.update(MyInfoActivity.this, new UpdateListener() {
					@Override
					public void onSuccess() {
					}

					@Override
					public void onFailure(int arg0, String arg1) {
					}
				});
			}

			@Override
			public void onFailure(int arg0, String arg1) {

			}

			@Override
			public void onProgress(Integer arg0) {
			}
		});
	}

	/**
	 * ��֤�������Ƿ���ȷ
	 * 
	 */
	private void checkPassword(String oldPassWord, final String newPassWord) {
		// ����㴫����������ȷ�ģ���ôarg0.size()�Ĵ�С��1������ʹ���������ľ���������ȷ�ģ�������ʧ�ܵ�
		BmobQuery<Person> query = new BmobQuery<Person>();
		query.addWhereEqualTo("password", oldPassWord);
		query.addWhereEqualTo("username", user.getUsername());
		query.findObjects(this, new FindListener<Person>() {
			@Override
			public void onError(int arg0, String arg1) {
			}

			@Override
			public void onSuccess(List<Person> arg0) {
				if (arg0.size() == 1) {
					user.setPassword(newPassWord);
					user.update(MyInfoActivity.this, new UpdateListener() {

						@Override
						public void onSuccess() {
							Util.ShowToast(MyInfoActivity.this, "�޸�����ɹ���");
							popup.dismiss();
						}

						@Override
						public void onFailure(int arg0, String arg1) {
						}
					});
				} else {
					Util.ShowToast(MyInfoActivity.this, "����ľ����벻��ȷ");
				}
			}
		});
	}

	/**
	 * ������д���۵Ĵ���
	 */
	private void popupView() {
		View view = getLayoutInflater().inflate(R.layout.popup_password, null);
		popup = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		// ��PopupWindow��ʾ��ָ��λ��
		popup.showAtLocation(findViewById(R.id.myinfo_changehead),
				Gravity.CENTER, 0, 0);
		popup.setOutsideTouchable(true);
		// EditTextһ��Ҫfinal�����ܸĳ� EditText.getText().toString()Ϊfinal
		final EditText oldPassWord = (EditText) view
				.findViewById(R.id.pw_oldpw);
		final EditText newPassWord = (EditText) view
				.findViewById(R.id.pw_newpw);
		final EditText newpw_again = (EditText) view
				.findViewById(R.id.pw_newpw_again);
		Button confirm = (Button) view.findViewById(R.id.pw_confirm_btn);
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.pw_popup_layout);
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �����������رմ���
				if (popup != null && popup.isShowing())
					popup.dismiss();
			}
		});
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(oldPassWord.getText().toString())) {
					Util.ShowToast(MyInfoActivity.this, "�����벻��Ϊ��");
					return;
				}
				if (TextUtils.isEmpty(newPassWord.getText().toString())
						|| TextUtils.isEmpty(newpw_again.getText().toString())) {
					Util.ShowToast(MyInfoActivity.this, "�����벻��Ϊ��");
					return;
				}
				if (!newPassWord.getText().toString()
						.equals(newpw_again.getText().toString())) {
					Util.ShowToast(MyInfoActivity.this, "������������벻һ��");
					return;
				}
				checkPassword(oldPassWord.getText().toString(), newPassWord
						.getText().toString());
			}
		});
	}

	public void goBack() {
		Util.goBack(MyInfoActivity.this, LearnActivity.class);
	}
}
