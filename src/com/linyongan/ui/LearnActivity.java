package com.linyongan.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.linyongan.adapter.ExpandableListAdapter;
import com.linyongan.config.Constants;
import com.linyongan.model.Person;
import com.linyongan.ui.base.BaseActivity;
import com.linyongan.util.Util;

/**
 * ��Ȩѧ��ҳ��
 * https://github.com/linyongan/Options.git
 */
public class LearnActivity extends BaseActivity {
	/** ��ǣ���һ�����м������� */
	private static boolean mark = true;
	// ������������صõ���ͼƬ
	Bitmap bitmap;
	ImageView head;
	String HEAD_NAME;
	Person user;
	ImageButton download;
	ExpandableListView view;
	// �����м��չ���б���Ҫ����Ϣ
	private String[] item = new String[] { "�����γ�", "���׿γ�(һ)", "���׿γ�(��)" };
	private String[][] subItem = new String[][] {
			{ "��һ�� ��Ȩ����ʷ����״", "�ڶ��� ��Ȩ�Ļ�������", "������ �Ϲ���Ȩ�Ļ�������", "���Ľ� �Ϲ���Ȩ�Ļ�������",
					"����� ������Ȩ��ʵֵ��ƽֵ����ֵ", "������ ������Ȩ�����ڼ�ֵ��ʱ���ֵ",
					"���߽� ������Ȩ�Ļ�����������Ҫ����", "�ڰ˽� ������Ȩ��Ȩ֤���ڻ�����Ҫ����", "�ھŽ� ������Ȩ�Ĺ���",
					"��ʮ�� ������Ȩ��Լ�Ļ���Ҫ��", "��ʮһ�� ������Ȩ��ֵ�䶯��Ӱ������", "��ʮ���� ������Ȩ�ķ���" },
			{ "��һ�� ���ҿ��ֲ��Խ��ܼ�����ԭ��", "�ڶ��� ���ҿ��ֲ���Ӧ��ָ��", "������ ���ҿ��ֵķ���",
					"���Ľ� �����Ʊ��Ȩ�ļ򵥽��ײ���", "����� �Է��նԳ�ΪĿ�ĵĻ������Խ��ܣ����漰����" },
			{ "��һ�� �������ֵ�����ԭ��", "�ڶ��� ������Ʊ��Ȩ�ļ򵥽��ײ���", "������ ţ��������Ȩ���ײ���",
					"���Ľ� ����������Ȩ���ײ���", "����� ����������Ȩ���ײ���" } };

	private ExpandableListAdapter adapter;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_learn);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		view = (ExpandableListView) findViewById(R.id.expandableListView);
		download = (ImageButton) findViewById(R.id.learn_download_btn);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		view.setOnChildClickListener(new ExpandableListViewListener());
		download.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Util.goToByClass(LearnActivity.this, DownLoadActivity.class);
			}
		});
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		/*** ��Ӳ໬�˵� ***/
		super.addMenu(this);
		if (mark) {
			LoadTask load = new LoadTask(LearnActivity.this);
			load.execute();
			mark = false;
		}

		adapter = new ExpandableListAdapter(LearnActivity.this, item, subItem);
		// ���øô�����ʾ�б�
		view.setAdapter(adapter);

		user = Util.getUser(this);
		// ����û��Ѿ���¼
		if (user != null) {
			HEAD_NAME = user.getUsername() + ".jpg";
			// ���SD������
			if (Util.isSDCardUsabled()) {
				// ���ͷ��ͼƬ�Ƿ����
				if ((new File(Constants.basePath + HEAD_NAME)).exists() == false) {
					download();// �û��ѵ�¼������û��ͷ������ȥ����
				}
			}
		}
	}

	public void download() {
		head = (ImageView) findViewById(R.id.left_menu_login_head);
		BmobQuery<Person> query = new BmobQuery<Person>();
		query.addWhereEqualTo("username", user.getUsername());
		query.findObjects(this, new FindListener<Person>() {

			public void onSuccess(List<Person> arg0) {
				// TODO Auto-generated method stub
				if (arg0.get(0).getIcon() != null) {
					// ��ȡͷ���ļ������ص�ַ
					String url = arg0.get(0).getIcon().getFileUrl();
					DownLoadToSD(url);
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
			}
		});

	}

	/**
	 * ���ص�SD��
	 * 
	 * @param string
	 */
	public void DownLoadToSD(final String string) {
		new Thread() {
			public void run() {
				try {
					// ����һ��URL����
					URL url = new URL(string);
					// �򿪸�URL��Ӧ����Դ��������
					InputStream is = url.openStream();
					// ��InputStream�н�����ͼƬ
					bitmap = BitmapFactory.decodeStream(is);
					// �ٴδ�URL��Ӧ����Դ��������
					is = url.openStream();
					// ���ֻ��ļ���Ӧ�������
					File f = new File(Constants.basePath + HEAD_NAME);
					FileOutputStream os = new FileOutputStream(f);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 40, os);
					os.flush();
					is.close();
					os.close();
					head.setImageBitmap(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * ��������
	 */
	class LoadTask extends AsyncTask<Object, Object, Object> {
		// �ɱ䳤�������������AsyncTask.exucute()��Ӧ
		ProgressDialog pdialog;
		Context mContext;

		// ����Ĳ��������Զ��壬��Ҫʲô�ؼ��ʹ���ʲô�ؼ�
		public LoadTask(Context ctx) {
			mContext = ctx;
		}

		// �÷���������UI�̵߳���,��Ҫ���ڽ����첽����֮ǰ��UI׼������
		@Override
		protected void onPreExecute() {
			pdialog = new ProgressDialog(mContext);
			// ���öԻ���ı���
			pdialog.setTitle("���ڶ�ȡ����");
			// ���öԻ��� ��ʾ������
			pdialog.setMessage("��һ�����г��򣬶�ȡ�����У�����ȴ�ʮ����...");
			// ���öԻ������á�ȡ������ť�ر�
			pdialog.setCancelable(false);
			// ���öԻ���Ľ������Ƿ���ʾ����
			pdialog.setIndeterminate(false);
			pdialog.show();
		}

		// �÷�������������UI�̵߳��У������ڸ÷������У����ܶ�UI���еĿؼ��������ú��޸�
		// ��Ҫ���ڽ����첽������
		@Override
		protected String doInBackground(Object... params) {
			// com.test.db �ǳ���İ�����������Լ��ĳ������
			// /data/data/com.test.db/
			// databases Ŀ¼��׼���� SQLite ���ݿ�ĵط���Ҳ�� Android ����Ĭ�ϵ����ݿ�洢Ŀ¼
			// ���ݿ���Ϊ test.db
			String DB_PATH = "/data/data/com.linyongan.ui/databases/";
			String DB_NAME = "xy.db";

			// ��� SQLite ���ݿ��ļ��Ƿ����
			if ((new File(DB_PATH + DB_NAME)).exists() == false) {
				// �� SQLite ���ݿ��ļ������ڣ��ټ��һ�� database Ŀ¼�Ƿ����
				File f = new File(DB_PATH);
				// �� database Ŀ¼�����ڣ��½���Ŀ¼
				if (!f.exists()) {
					f.mkdir();
				}
				try {
					// �õ� assets Ŀ¼������ʵ��׼���õ� SQLite ���ݿ���Ϊ������
					InputStream is = getBaseContext().getAssets().open(DB_NAME);
					// �����
					OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
					// �ļ�д��
					byte[] buffer = new byte[1024];
					int length;
					while ((length = is.read(buffer)) > 0) {
						os.write(buffer, 0, length);
					}
					// �ر��ļ���
					os.flush();
					os.close();
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		// ��doInBackground����ִ�н���֮�������У�����������UI�̵߳��С�
		// ��Ҫ���ڽ��첽����ִ�еĽ��չʾ���ͻ�
		@Override
		protected void onPostExecute(Object result) {
			pdialog.dismiss();
		}

	}

	/**
	 * ����ͼƬ��ID��Ȼ���������Ӧ��ҳ��
	 * 
	 * @author yongan
	 * 
	 */
	private class ExpandableListViewListener implements OnChildClickListener {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			switch (groupPosition) {
			case 0:
				if (getPptFileIntent(childPosition) != null)
					startActivity(getPptFileIntent(childPosition));
				break;
			case 1:
				if (getPptFileIntent(childPosition + 12) != null)
					startActivity(getPptFileIntent(childPosition + 12));
				break;
			case 2:
				if (getPptFileIntent(childPosition + 17) != null)
					startActivity(getPptFileIntent(childPosition + 17));
				break;
			}

			return false;
		}
	}

	@Override
	public void goBack() {
		// TODO Auto-generated method stub
		Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
		mHomeIntent.addCategory(Intent.CATEGORY_HOME);
		mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		startActivity(mHomeIntent);
		finish();
	}

	/**
	 * ��ȡһ����ppt�ļ���intent
	 * 
	 * @param num
	 * @return
	 */
	public Intent getPptFileIntent(int num)

	{
		String param = Environment.getExternalStorageDirectory().getPath()
				+ "/Options/" + DownLoadActivity.subItem[num] + ".ppt";
		if ((new File(param)).exists() == false) {
			Util.ShowToast(LearnActivity.this, "�ļ������ڣ��뵽���ؽ������ظ�ppt");
			return null;
		}
		Intent intent = new Intent("android.intent.action.VIEW");

		intent.addCategory("android.intent.category.DEFAULT");

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Uri uri = Uri.fromFile(new File(param));

		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");

		return intent;

	}
}