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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	LinearLayout changehead;
	ImageView head;
	/** ���� */
	private TitleView titleView;
	String HEAD_NAME;
	Person user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo);
		changehead = (LinearLayout) findViewById(R.id.login_edit_changehead);
		changehead.setOnClickListener(new ButtonListener());

		head = (ImageView) findViewById(R.id.login_edit_head);

		exit = (Button) findViewById(R.id.login_edit_exit);
		exit.setOnClickListener(new ButtonListener());

		email = (TextView) findViewById(R.id.login_edit_email);
		name = (TextView) findViewById(R.id.login_edit_name);

		titleView = (TitleView) findViewById(R.id.TitleView);
		titleView.setTitle("������Ϣ");
		titleView.setBackButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.login_edit_changehead:
				ShowPickDialog();
				break;
			case R.id.login_edit_exit:
				testLogOut();
				goBack();
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
			 * �ǿ��жϴ��һ��Ҫ��֤���������֤�Ļ��� �ڼ���֮��������ֲ����⣬Ҫ���²ü�������
			 * ��ǰ����ʱ���ᱨNullException��С��ֻ ������ط����£���ҿ��Ը��ݲ�ͬ����ں��ʵ� �ط����жϴ����������
			 * 
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
		 * ֱ��������Ctrl+F�ѣ�CROP ��֮ǰС��û��ϸ��������ʵ��׿ϵͳ���Ѿ����Դ�ͼƬ�ü�����, ��ֱ�ӵ����ؿ��
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

		// �÷�������������UI�̵߳��У������ڸ÷������У����ܶ�UI���еĿؼ��������ú��޸�
		// ��Ҫ���ڽ����첽������
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
				// TODO Auto-generated method stub
				user.setIcon(icon);
				user.update(MyInfoActivity.this, new UpdateListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
					}
				});
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgress(Integer arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	/**
	 * ��������û�
	 */
	private void testLogOut() {
		// if (Environment.getExternalStorageState().equals(
		// Environment.MEDIA_MOUNTED)) {
		// // ����ļ��Ƿ����
		// if ((new File(HEAD_PATH + HEAD_NAME)).exists() == true) {
		// new File(HEAD_PATH + HEAD_NAME).delete();
		// }
		// }
		BmobUser.logOut(this);
	}

	/**
	 * ��֤�������Ƿ���ȷ
	 * 
	 * @Title: updatePassword
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 */
	private void checkPassword() {
		BmobQuery<Person> query = new BmobQuery<Person>();
		final Person bmobUser = BmobUser.getCurrentUser(this, Person.class);
		// ����㴫����������ȷ�ģ���ôarg0.size()�Ĵ�С��1������ʹ���������ľ���������ȷ�ģ�������ʧ�ܵ�
		query.addWhereEqualTo("password", "123456");
		query.addWhereEqualTo("username", bmobUser.getUsername());
		query.findObjects(this, new FindListener<Person>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<Person> arg0) {
				// TODO Auto-generated method stub
				//toast("��ѯ����ɹ�:" + arg0.size());

			}
		});
	}

	
	public void goBack() {
		Util.goBack(MyInfoActivity.this, LearnActivity.class);
	}
}
