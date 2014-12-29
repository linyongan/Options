package com.linyongan.ui;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.linyongan.config.Constants;
import com.linyongan.model.ppt;
import com.linyongan.util.Util;
import com.linyongan.view.TitleView;

public class DownLoadActivity extends Activity {

	/** 标题 */
	private TitleView titleView;
	TextView all, success, presses;
	Button download;
	int i = 0;
	String[] urls;
	static String[] subItem = new String[] { "第一节期权的历史与现状", "第二节期权的基本概念",
			"第三节认购期权的基本概念", "第四节认沽期权的基本概念", "第五节个股期权的实值、平值和虚值",
			"第六节个股期权的内在价值与时间价值", "第七节个股期权的基本特征及重要术语", "第八节个股期权与权证、期货的主要区别",
			"第九节个股期权的功能", "第十节个股期权合约的基本要素", "第十一节个股期权价值变动的影响因素",
			"第十二节 个股期权的风险", "第一节备兑开仓策略介绍及运作原理", "第二节备兑开仓策略应用指南", "第三节备兑开仓的风险",
			"第四节买入股票期权的简单交易策略", "第五节以风险对冲为目的的基本策略介绍，损益及风险", "第一节卖出开仓的运作原理",
			"第二 卖出股票期权的简单交易策略", "第三节牛市行情期权交易策略", "第四节熊市行情期权交易策略",
			"第五节盘整行情期权交易策略" };
	HttpHandler handler;
	NotificationCompat.Builder mBuilder;
	/** Notification管理 */
	NotificationManager mNotificationManager;
	/** Notification的ID */
	int notifyId = 102;
	Boolean ifdown = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		urls = new String[22];
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		titleView = (TitleView) findViewById(R.id.TitleView);
		titleView.setTitle("下载PPT");
		titleView.setBackButtonListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goBack();
			}
		});
		all = (TextView) findViewById(R.id.download_all);
		success = (TextView) findViewById(R.id.download_success);
		presses = (TextView) findViewById(R.id.download_presses);
		download = (Button) findViewById(R.id.download_download_btn);
		download.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i < subItem.length) {
					if (!ifdown) {
						ifdown = !ifdown;
						showProgressNotify();
						download.setText("暂停下载");
						if (Util.isSDCardUsabled()) {
							calculateI();
							setNotify("进度:"
									+ String.format("%.2f", 100.0 * i
											/ subItem.length) + "%");
							Asyndownload(i);
						}
					} else {
						ifdown = !ifdown;
						download.setText("一键下载");
						cancelDown();
					}
				} else
					Util.ShowToast(getApplicationContext(),"PPT已下载完成");
			}
		});

		find();
		initNotify();
		calculateI();

	}

	/**
	 * 计算下载到第几个ppt
	 */
	public void calculateI() {
		while (i < subItem.length
				&& (new File(Constants.basePath + subItem[i] + ".ppt"))
						.exists() == true) {
			i++;
			Log.i("Down---", "现在i的值是：" + i);
			success.setText(i + "");
			presses.setText(String.format("%.2f", 100.0 * i / subItem.length)
					+ "%");
		}
	}

	/** 初始化通知栏 */
	private void initNotify() {
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,
				new Intent(), 0);
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setContentIntent(pendingIntent)
				// .setNumber(number)//显示数量
				.setPriority(Notification.STREAM_DEFAULT)
				// 设置该通知优先级
				// .setAutoCancel(true)
				// 设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)
				// true，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,
				// 用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_VIBRATE)
				// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音
				.setSmallIcon(R.drawable.icon);
	}

	/** 显示带进度条通知栏 */
	public void showProgressNotify() {
		mBuilder.setContentTitle("PPT正在下载中...")
				.setContentText(
						"进度:"
								+ String.format("%.2f", 100.0 * i
										/ subItem.length) + "%")
				.setTicker("开始下载");// 通知首次出现在通知栏，带上升动画效果的
		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	/**
	 * 初始化URL
	 */
	public void find() {
		download.setEnabled(false);
		BmobQuery<ppt> query = new BmobQuery<ppt>();
		query.setLimit(22);
		query.order("createdAt");// 按照时间降序
		query.findObjects(this, new FindListener<ppt>() {
			@Override
			public void onSuccess(List<ppt> arg0) {
				// TODO Auto-generated method stub
				all.setText(arg0.size() + "");
				int j = 0;
				while (j < arg0.size()) {
					urls[j] = arg0.get(j).getName().getFileUrl();
					j++;
				}
				Log.i("Down---", "URL下载完成" + urls.length);
				download.setEnabled(true);
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

		});
	}

	/**
	 * 断点下载单个ppt
	 * 
	 * @param path
	 */
	public void Asyndownload(int num) {
		String path = urls[num];
		String name = Environment.getExternalStorageDirectory().getPath()
				+ "/Options/" + subItem[num] + ".ppt";
		HttpUtils http = new HttpUtils();
		handler = http.download(path, name, false, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
				false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
				new RequestCallBack<File>() {

					@Override
					public void onStart() {
						Log.i("Down---", "开始下载第" + (i + 1) + "个文件");
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {

					}

					@Override
					public void onSuccess(ResponseInfo<File> responseInfo) {
						i++;
						success.setText(i + "");
						presses.setText(String.format("%.2f", 100.0 * i
								/ subItem.length)
								+ "%");
						setNotify("进度:"
								+ String.format("%.2f", 100.0 * i
										/ subItem.length) + "%");
						if (i < subItem.length) {
							Asyndownload(i);
						} else {
							// 下载完成之后
							handler.cancel(true);
							setNotify("下载完成。");
							i--;
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Util.ShowToast(getApplicationContext(),msg);
						cancelDown();
					}
				});
	}

	/** 设置下载进度 */
	public void setNotify(String s) {
		mBuilder.setContentText(s);
		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	/**
	 * 返回动画及事件处理
	 */
	public void goBack() {
		Util.goBack(this, LearnActivity.class);
	}

	/**
	 * 取消下载（取消通知栏，删除文件，停止线程）
	 */
	public void cancelDown() {
		mNotificationManager.cancel(notifyId);
		if (i < subItem.length
				&& (new File(Environment.getExternalStorageDirectory()
						.getPath() + "/Options/" + subItem[i] + ".ppt"))
						.exists() == true) {
			new File(Environment.getExternalStorageDirectory().getPath()
					+ "/Options/" + subItem[i] + ".ppt").delete();
		}
		if (handler != null)
			handler.cancel(true);
	}

}
