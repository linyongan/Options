package com.linyongan.ui.base;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.linyongan.config.Constants;
import com.linyongan.model.Person;
import com.linyongan.ui.R;
import com.linyongan.util.Util;
import com.linyongan.view.TitleView;

public abstract class BaseActivity extends Activity {

	// 配置左菜单需要的信息
	protected String[] names = new String[] { "期权学堂", "期权计算", "自我测试", "名词查询",
			"常见问题", "关于我们", "社区讨论" };
	protected int[] imageIds = new int[] { R.drawable.left_learn,
			R.drawable.left_calculate, R.drawable.test_btn_normal,
			R.drawable.left_noun, R.drawable.left_question,
			R.drawable.left_adout, R.drawable.left_question };
	protected Toast mToast;
	/** 标题视图 */
	protected TitleView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 设置全屏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 初始化应用的ID
		Bmob.initialize(this, Constants.Bmob_APPID);
		setContentView();
		initViews();
		initListeners();
		initData();
		init();
	}

	/**
	 * 初始化数据
	 */
	private void init() {
		// 登录按钮
		RelativeLayout login = (RelativeLayout) findViewById(R.id.left_menu_login);
		ImageView head = (ImageView) findViewById(R.id.left_menu_login_head);
		// 登录状态
		TextView state = (TextView) findViewById(R.id.left_menu_login_state);
		Person user = Util.getUser(getApplicationContext());
		// 如果用户已经登录
		if (user != null) {
			// 如果SD卡可用
			if (Util.isSDCardUsabled()) {
				// 如果头像图片存在，则显示它
				String basePath = Constants.basePath + user.getUsername()
						+ ".jpg";
				if ((new File(basePath)).exists() == true) {
					Bitmap bit = BitmapFactory.decodeFile(basePath);
					head.setImageBitmap(bit);
				}
			}
			state.setText(user.getUsername());
			login.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Util.goToByXml(BaseActivity.this, "LoginEditActivity");
				}
			});
		} else {
			head.setBackgroundResource(R.drawable.left_nologin);
			state.setText("未登录");
			login.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent("LoginActivity");
					startActivityForResult(intent, 11);
					overridePendingTransition(R.anim.activity_left_in1,
							R.anim.activity_left_out1);
					finish();
				}
			});
		}

	}

	/**
	 * 初始化返回按钮
	 */
	protected void initBackButton() {
		titleView = (TitleView) findViewById(R.id.TitleView);
		titleView.setBackButtonListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
	}

	/**
	 * 提示方法
	 * 
	 * @param text
	 */
	public void ShowToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}

	/**
	 * 添加菜单功能
	 * 
	 * @param context
	 */
	protected void addMenu(Activity context) {
		/*** 初始化侧滑菜单 Begin ***/
		SlidingMenu menu = new SlidingMenu(context);
		// 设置左滑菜单
		menu.setMode(SlidingMenu.LEFT);
		// 设置滑动的屏幕范围，该设置为全屏区域都可以滑动
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置阴影图片的宽度
		menu.setShadowWidthRes(R.dimen.shadow_width);
		// 设置阴影图片
		menu.setShadowDrawable(R.drawable.shadow);
		// SlidingMenu划出时主页面显示的剩余宽度
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// SlidingMenu滑动时的渐变程度
		menu.setFadeDegree(0.35f);
		// 使SlidingMenu附加在Activity上
		menu.attachToActivity(context, SlidingMenu.SLIDING_CONTENT);
		// 设置menu的布局文件
		menu.setMenu(R.layout.left_menu);

		// 创建一个List集合，List集合的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < names.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("header", imageIds[i]);
			listItem.put("personName", names[i]);
			listItems.add(listItem);
		}
		// 创建一个SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(context, listItems,
				R.layout.simple_item, new String[] { "personName", "header" },
				new int[] { R.id.name, R.id.header });
		ListView list = (ListView) findViewById(R.id.mylist);
		// 为ListView设置Adapter
		list.setAdapter(simpleAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					Util.goToByXml(BaseActivity.this, "LearnActivity");
					break;
				case 1:
					Intent intent = new Intent("CalculateActivity");
					startActivity(intent);
					overridePendingTransition(R.anim.activity_left_in1,
							R.anim.activity_left_out1);
					break;
				case 2:
					Util.goToByXml(BaseActivity.this, "TestActivity");
					break;
				case 3:
					Util.goToByXml(BaseActivity.this, "SearchActivity");
					break;
				case 4:
					Util.goToByXml(BaseActivity.this, "QuestionActivity");
					break;
				case 5:
					Util.goToByXml(BaseActivity.this, "AboutActivity");
					break;
				case 6:
					Util.goToByXml(BaseActivity.this, "SendFeedbackActivity");
					break;
				}
			}
		});
	}

	/** 退出按钮 */
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				goBack();
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	/****************************** 由子类去实现的方法 ***********************************/

	/**
	 * 设置布局文件
	 */
	public abstract void setContentView();

	/**
	 * 初始化布局文件中的控件
	 */
	public abstract void initViews();

	/**
	 * 初始化控件的监听
	 */
	public abstract void initListeners();

	/**
	 * 进行数据初始化 initData
	 */
	public abstract void initData();

	/**
	 * 返回上一页
	 */
	public abstract void goBack();
}