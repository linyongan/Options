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
	/** 标题 */
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
		titleView.setTitle("个人信息");
		titleView.setBackButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goBack();
			}
		});

		user = Util.getUser(getApplicationContext());
		// 如果用户已经登录
		if (user != null) {
			email.setText(user.getEmail());
			name.setText(user.getUsername());
			HEAD_NAME = user.getUsername() + ".jpg";
			// 如果SD卡可用
			if (Util.isSDCardUsabled()) {
				// 检查头像图片是否存在
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
	 * 从SD卡选择图片
	 */
	private void ShowPickDialog() {

		/**
		 * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
		 * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下
		 */
		Intent intent = new Intent(Intent.ACTION_PICK, null);

		/**
		 * 下面这句话，与其它方式写是一样的效果，如果： intent.setData(MediaStore.Images
		 * .Media.EXTERNAL_CONTENT_URI); intent.setType(""image/*");设置数据类型
		 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如 ："image/jpeg 、 image/png等的类型"
		 */
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, 1);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 如果是直接从相册获取
		case 1:
			if (data != null) {
				startPhotoZoom(data.getData());
			}
			break;
		// 取得裁剪后的图片
		case 3:
			/**
			 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
			 * 当前功能时，会报NullException，小马只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
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
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * 保存裁剪之后的图片数据
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
	 * 异步保存头像数据到内存卡
	 */
	class SaveToSDTask extends AsyncTask<Object, Object, Object> {
		Bitmap bitmap;

		public SaveToSDTask(Bitmap map) {
			bitmap = map;
		}

		// 该方法并不运行在UI线程当中，所以在该方法当中，不能对UI当中的控件进行设置和修改
		// 主要用于进行异步操作。
		@Override
		protected String doInBackground(Object... params) {
			if (Util.isSDCardUsabled()) {
				File f = null;
				// 检查文件是否存在
				if ((new File(Constants.basePath + HEAD_NAME)).exists() == false) {
					// 如文件不存在，再检查一下目录是否存在
					f = new File(Constants.basePath);
					// 如目录不存在，新建该目录
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
			// 保存图片到服务器
			SaveToWeb();
		}
	}

	/**
	 * 保存图片到服务器
	 */
	public void SaveToWeb() {
		// 上传图片
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
	 * 清除本地用户
	 */
	private void testLogOut() {
		// if (Environment.getExternalStorageState().equals(
		// Environment.MEDIA_MOUNTED)) {
		// // 检查文件是否存在
		// if ((new File(HEAD_PATH + HEAD_NAME)).exists() == true) {
		// new File(HEAD_PATH + HEAD_NAME).delete();
		// }
		// }
		BmobUser.logOut(this);
	}

	/**
	 * 验证旧密码是否正确
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
		// 如果你传的密码是正确的，那么arg0.size()的大小是1，这个就代表你输入的旧密码是正确的，否则是失败的
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
				//toast("查询密码成功:" + arg0.size());

			}
		});
	}

	
	public void goBack() {
		Util.goBack(MyInfoActivity.this, LearnActivity.class);
	}
}
