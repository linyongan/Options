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

	/** ���� */
	private TitleView titleView;
	TextView all, success, presses;
	Button download;
	int i = 0;
	String[] urls;
	static String[] subItem = new String[] { "��һ����Ȩ����ʷ����״", "�ڶ�����Ȩ�Ļ�������",
			"�������Ϲ���Ȩ�Ļ�������", "���Ľ��Ϲ���Ȩ�Ļ�������", "����ڸ�����Ȩ��ʵֵ��ƽֵ����ֵ",
			"�����ڸ�����Ȩ�����ڼ�ֵ��ʱ���ֵ", "���߽ڸ�����Ȩ�Ļ�����������Ҫ����", "�ڰ˽ڸ�����Ȩ��Ȩ֤���ڻ�����Ҫ����",
			"�ھŽڸ�����Ȩ�Ĺ���", "��ʮ�ڸ�����Ȩ��Լ�Ļ���Ҫ��", "��ʮһ�ڸ�����Ȩ��ֵ�䶯��Ӱ������",
			"��ʮ���� ������Ȩ�ķ���", "��һ�ڱ��ҿ��ֲ��Խ��ܼ�����ԭ��", "�ڶ��ڱ��ҿ��ֲ���Ӧ��ָ��", "�����ڱ��ҿ��ֵķ���",
			"���Ľ������Ʊ��Ȩ�ļ򵥽��ײ���", "������Է��նԳ�ΪĿ�ĵĻ������Խ��ܣ����漰����", "��һ���������ֵ�����ԭ��",
			"�ڶ� ������Ʊ��Ȩ�ļ򵥽��ײ���", "������ţ��������Ȩ���ײ���", "���Ľ�����������Ȩ���ײ���",
			"���������������Ȩ���ײ���" };
	HttpHandler handler;
	NotificationCompat.Builder mBuilder;
	/** Notification���� */
	NotificationManager mNotificationManager;
	/** Notification��ID */
	int notifyId = 102;
	Boolean ifdown = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		urls = new String[22];
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		titleView = (TitleView) findViewById(R.id.TitleView);
		titleView.setTitle("����PPT");
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
						download.setText("��ͣ����");
						if (Util.isSDCardUsabled()) {
							calculateI();
							setNotify("����:"
									+ String.format("%.2f", 100.0 * i
											/ subItem.length) + "%");
							Asyndownload(i);
						}
					} else {
						ifdown = !ifdown;
						download.setText("һ������");
						cancelDown();
					}
				} else
					Util.ShowToast(getApplicationContext(),"PPT���������");
			}
		});

		find();
		initNotify();
		calculateI();

	}

	/**
	 * �������ص��ڼ���ppt
	 */
	public void calculateI() {
		while (i < subItem.length
				&& (new File(Constants.basePath + subItem[i] + ".ppt"))
						.exists() == true) {
			i++;
			Log.i("Down---", "����i��ֵ�ǣ�" + i);
			success.setText(i + "");
			presses.setText(String.format("%.2f", 100.0 * i / subItem.length)
					+ "%");
		}
	}

	/** ��ʼ��֪ͨ�� */
	private void initNotify() {
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,
				new Intent(), 0);
		mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setWhen(System.currentTimeMillis())// ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
				.setContentIntent(pendingIntent)
				// .setNumber(number)//��ʾ����
				.setPriority(Notification.STREAM_DEFAULT)
				// ���ø�֪ͨ���ȼ�
				// .setAutoCancel(true)
				// ���������־���û��������Ϳ�����֪ͨ���Զ�ȡ��
				.setOngoing(false)
				// true��������Ϊһ�����ڽ��е�֪ͨ������ͨ����������ʾһ����̨����,
				// �û���������(�粥������)����ĳ�ַ�ʽ���ڵȴ�,���ռ���豸(��һ���ļ�����,ͬ������,������������)
				.setDefaults(Notification.DEFAULT_VIBRATE)
				// ��֪ͨ������������ƺ���Ч������򵥡���һ�µķ�ʽ��ʹ�õ�ǰ���û�Ĭ�����ã�ʹ��defaults���ԣ�������ϣ�
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND �������
				.setSmallIcon(R.drawable.icon);
	}

	/** ��ʾ��������֪ͨ�� */
	public void showProgressNotify() {
		mBuilder.setContentTitle("PPT����������...")
				.setContentText(
						"����:"
								+ String.format("%.2f", 100.0 * i
										/ subItem.length) + "%")
				.setTicker("��ʼ����");// ֪ͨ�״γ�����֪ͨ��������������Ч����
		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	/**
	 * ��ʼ��URL
	 */
	public void find() {
		download.setEnabled(false);
		BmobQuery<ppt> query = new BmobQuery<ppt>();
		query.setLimit(22);
		query.order("createdAt");// ����ʱ�併��
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
				Log.i("Down---", "URL�������" + urls.length);
				download.setEnabled(true);
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

		});
	}

	/**
	 * �ϵ����ص���ppt
	 * 
	 * @param path
	 */
	public void Asyndownload(int num) {
		String path = urls[num];
		String name = Environment.getExternalStorageDirectory().getPath()
				+ "/Options/" + subItem[num] + ".ppt";
		HttpUtils http = new HttpUtils();
		handler = http.download(path, name, false, // ���Ŀ���ļ����ڣ�����δ��ɵĲ��ּ������ء���������֧��RANGEʱ���������ء�
				false, // ��������󷵻���Ϣ�л�ȡ���ļ�����������ɺ��Զ���������
				new RequestCallBack<File>() {

					@Override
					public void onStart() {
						Log.i("Down---", "��ʼ���ص�" + (i + 1) + "���ļ�");
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
						setNotify("����:"
								+ String.format("%.2f", 100.0 * i
										/ subItem.length) + "%");
						if (i < subItem.length) {
							Asyndownload(i);
						} else {
							// �������֮��
							handler.cancel(true);
							setNotify("������ɡ�");
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

	/** �������ؽ��� */
	public void setNotify(String s) {
		mBuilder.setContentText(s);
		mNotificationManager.notify(notifyId, mBuilder.build());
	}

	/**
	 * ���ض������¼�����
	 */
	public void goBack() {
		Util.goBack(this, LearnActivity.class);
	}

	/**
	 * ȡ�����أ�ȡ��֪ͨ����ɾ���ļ���ֹͣ�̣߳�
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
