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
 * 期权学堂页面
 * https://github.com/linyongan/Options.git
 */
public class LearnActivity extends BaseActivity {
	/** 标记，第一次运行加载数据 */
	private static boolean mark = true;
	// 代表从网络下载得到的图片
	Bitmap bitmap;
	ImageView head;
	String HEAD_NAME;
	Person user;
	ImageButton download;
	ExpandableListView view;
	// 配置中间课展开列表需要的信息
	private String[] item = new String[] { "基础课程", "进阶课程(一)", "进阶课程(二)" };
	private String[][] subItem = new String[][] {
			{ "第一节 期权的历史与现状", "第二节 期权的基本概念", "第三节 认购期权的基本概念", "第四节 认沽期权的基本概念",
					"第五节 个股期权的实值、平值和虚值", "第六节 个股期权的内在价值与时间价值",
					"第七节 个股期权的基本特征及重要术语", "第八节 个股期权与权证、期货的主要区别", "第九节 个股期权的功能",
					"第十节 个股期权合约的基本要素", "第十一节 个股期权价值变动的影响因素", "第十二节 个股期权的风险" },
			{ "第一节 备兑开仓策略介绍及运作原理", "第二节 备兑开仓策略应用指南", "第三节 备兑开仓的风险",
					"第四节 买入股票期权的简单交易策略", "第五节 以风险对冲为目的的基本策略介绍，损益及风险" },
			{ "第一节 卖出开仓的运作原理", "第二节 卖出股票期权的简单交易策略", "第三节 牛市行情期权交易策略",
					"第四节 熊市行情期权交易策略", "第五节 盘整行情期权交易策略" } };

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
		/*** 添加侧滑菜单 ***/
		super.addMenu(this);
		if (mark) {
			LoadTask load = new LoadTask(LearnActivity.this);
			load.execute();
			mark = false;
		}

		adapter = new ExpandableListAdapter(LearnActivity.this, item, subItem);
		// 设置该窗口显示列表
		view.setAdapter(adapter);

		user = Util.getUser(this);
		// 如果用户已经登录
		if (user != null) {
			HEAD_NAME = user.getUsername() + ".jpg";
			// 如果SD卡可用
			if (Util.isSDCardUsabled()) {
				// 检查头像图片是否存在
				if ((new File(Constants.basePath + HEAD_NAME)).exists() == false) {
					download();// 用户已登录，本地没有头像所以去下载
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
					// 获取头像文件的下载地址
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
	 * 下载到SD卡
	 * 
	 * @param string
	 */
	public void DownLoadToSD(final String string) {
		new Thread() {
			public void run() {
				try {
					// 定义一个URL对象
					URL url = new URL(string);
					// 打开该URL对应的资源的输入流
					InputStream is = url.openStream();
					// 从InputStream中解析出图片
					bitmap = BitmapFactory.decodeStream(is);
					// 再次打开URL对应的资源的输入流
					is = url.openStream();
					// 打开手机文件对应的输出流
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
	 * 加载数据
	 */
	class LoadTask extends AsyncTask<Object, Object, Object> {
		// 可变长的输入参数，与AsyncTask.exucute()对应
		ProgressDialog pdialog;
		Context mContext;

		// 这里的参数可以自定义，需要什么控件就传入什么控件
		public LoadTask(Context ctx) {
			mContext = ctx;
		}

		// 该方法运行在UI线程当中,主要用于进行异步操作之前的UI准备工作
		@Override
		protected void onPreExecute() {
			pdialog = new ProgressDialog(mContext);
			// 设置对话框的标题
			pdialog.setTitle("正在读取数据");
			// 设置对话框 显示的内容
			pdialog.setMessage("第一次运行程序，读取数据中，敬请等待十几秒...");
			// 设置对话框不能用“取消”按钮关闭
			pdialog.setCancelable(false);
			// 设置对话框的进度条是否显示进度
			pdialog.setIndeterminate(false);
			pdialog.show();
		}

		// 该方法并不运行在UI线程当中，所以在该方法当中，不能对UI当中的控件进行设置和修改
		// 主要用于进行异步操作。
		@Override
		protected String doInBackground(Object... params) {
			// com.test.db 是程序的包名，请根据自己的程序调整
			// /data/data/com.test.db/
			// databases 目录是准备放 SQLite 数据库的地方，也是 Android 程序默认的数据库存储目录
			// 数据库名为 test.db
			String DB_PATH = "/data/data/com.linyongan.ui/databases/";
			String DB_NAME = "xy.db";

			// 检查 SQLite 数据库文件是否存在
			if ((new File(DB_PATH + DB_NAME)).exists() == false) {
				// 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在
				File f = new File(DB_PATH);
				// 如 database 目录不存在，新建该目录
				if (!f.exists()) {
					f.mkdir();
				}
				try {
					// 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
					InputStream is = getBaseContext().getAssets().open(DB_NAME);
					// 输出流
					OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
					// 文件写入
					byte[] buffer = new byte[1024];
					int length;
					while ((length = is.read(buffer)) > 0) {
						os.write(buffer, 0, length);
					}
					// 关闭文件流
					os.flush();
					os.close();
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		// 在doInBackground方法执行结束之后再运行，并且运行在UI线程当中。
		// 主要用于将异步任务执行的结果展示给客户
		@Override
		protected void onPostExecute(Object result) {
			pdialog.dismiss();
		}

	}

	/**
	 * 设置图片的ID，然后调整到相应的页面
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
	 * 获取一个打开ppt文件的intent
	 * 
	 * @param num
	 * @return
	 */
	public Intent getPptFileIntent(int num)

	{
		String param = Environment.getExternalStorageDirectory().getPath()
				+ "/Options/" + DownLoadActivity.subItem[num] + ".ppt";
		if ((new File(param)).exists() == false) {
			Util.ShowToast(LearnActivity.this, "文件不存在，请到下载界面下载该ppt");
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