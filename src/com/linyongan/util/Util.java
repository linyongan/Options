package com.linyongan.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;

import com.linyongan.model.Person;
import com.linyongan.ui.R;

/**
 * 工具类
 */
public class Util {

	private static Toast mToast;

	/**
	 * 提示方法
	 * 
	 * @param text
	 */
	public static void ShowToast(Context context, String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}

	/**
	 * 错误处理
	 * 
	 * @param code
	 */
	public static void errorFul(Context context, int code) {
		switch (code) {
		case 101:
			Toast.makeText(context, "用户名或密码不正确。", Toast.LENGTH_SHORT).show();
			break;
		case 202:
			Toast.makeText(context, "用户名已经被注册。", Toast.LENGTH_SHORT).show();
			break;
		case 203:
			Toast.makeText(context, "邮箱已经被注册。", Toast.LENGTH_SHORT).show();
			break;
		case 301:
			Toast.makeText(context, "邮箱格式不正确 。", Toast.LENGTH_SHORT).show();
			break;
		case 9010:
			Toast.makeText(context, "现在网络不好，请稍后再试 。", Toast.LENGTH_SHORT)
					.show();
			break;
		case 9016:
			Toast.makeText(context, "无网络连接，请检查您的手机网络。", Toast.LENGTH_SHORT)
					.show();
			break;
		case 1:
			Toast.makeText(context, "用户名不正确。", Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(context, "密码不正确。", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	/**
	 * 判断邮箱地址是否有效
	 * 
	 * @param email
	 * @return true 有效 / false 无效
	 */
	public static boolean isEmailValid(String email) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return email.matches(regex);
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 * @return true表示已连接 / false表示未连接
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取当前已登录的用户名
	 * 
	 * @param content
	 * @return
	 */
	public static Person getUser(Context context) {
		return BmobUser.getCurrentUser(context, Person.class);
	}

	/**
	 * 隐式地跳转到一个Activity
	 * 
	 * @param from
	 *            当前Activity
	 * @param to
	 *            要跳转到的Activity
	 */
	public static void goToByXml(Activity from, String to) {
		Intent intent = new Intent(to);
		from.startActivity(intent);
		from.overridePendingTransition(R.anim.activity_left_in1,
				R.anim.activity_left_out1);
		from.finish();

	}

	/**
	 * 显式地跳转到一个Activity
	 * 
	 * @param from
	 *            当前Activity
	 * @param to
	 *            要跳转到的Activity
	 */
	public static void goToByClass(Activity from, Class<?> to) {
		Intent intent = new Intent(from, to);
		from.startActivity(intent);
		from.overridePendingTransition(R.anim.activity_left_in,
				R.anim.activity_left_out);
		from.finish();
	}

	/**
	 * 显式地跳转到一个Activity
	 * 
	 * @param from
	 *            当前Activity
	 * @param to
	 *            要跳转到的Activity
	 */
	public static void goToNoFinish(Activity from, Class<?> to) {
		Intent intent = new Intent(from, to);
		from.startActivity(intent);
		from.overridePendingTransition(R.anim.activity_left_in,
				R.anim.activity_left_out);
	}

	/**
	 * 返回到上一个Activity
	 * 
	 * @param from
	 *            当前Activity
	 * @param to
	 *            要返回到的Activity
	 */
	public static void goBack(Activity from, Class<?> to) {
		Intent intent = new Intent(from, to);
		from.startActivity(intent);
		from.overridePendingTransition(R.anim.activity_right_in,
				R.anim.activity_right_out);
		from.finish();
	}

	/**
	 * 判断SD卡是否可用
	 * 
	 * @return true表示可用 / false表示不可用
	 */
	public static boolean isSDCardUsabled() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);

	}
}
