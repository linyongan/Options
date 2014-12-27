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

	// ������˵���Ҫ����Ϣ
	protected String[] names = new String[] { "��Ȩѧ��", "��Ȩ����", "���Ҳ���", "���ʲ�ѯ",
			"��������", "��������", "��������" };
	protected int[] imageIds = new int[] { R.drawable.left_learn,
			R.drawable.left_calculate, R.drawable.test_btn_normal,
			R.drawable.left_noun, R.drawable.left_question,
			R.drawable.left_adout, R.drawable.left_question };
	protected Toast mToast;
	/** ������ͼ */
	protected TitleView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* ����ȫ�� */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ��ʼ��Ӧ�õ�ID
		Bmob.initialize(this, Constants.Bmob_APPID);
		setContentView();
		initViews();
		initListeners();
		initData();
		init();
	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		// ��¼��ť
		RelativeLayout login = (RelativeLayout) findViewById(R.id.left_menu_login);
		ImageView head = (ImageView) findViewById(R.id.left_menu_login_head);
		// ��¼״̬
		TextView state = (TextView) findViewById(R.id.left_menu_login_state);
		Person user = Util.getUser(getApplicationContext());
		// ����û��Ѿ���¼
		if (user != null) {
			// ���SD������
			if (Util.isSDCardUsabled()) {
				// ���ͷ��ͼƬ���ڣ�����ʾ��
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
			state.setText("δ��¼");
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
	 * ��ʼ�����ذ�ť
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
	 * ��ʾ����
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
	 * ��Ӳ˵�����
	 * 
	 * @param context
	 */
	protected void addMenu(Activity context) {
		/*** ��ʼ���໬�˵� Begin ***/
		SlidingMenu menu = new SlidingMenu(context);
		// �����󻬲˵�
		menu.setMode(SlidingMenu.LEFT);
		// ���û�������Ļ��Χ��������Ϊȫ�����򶼿��Ի���
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// ������ӰͼƬ�Ŀ��
		menu.setShadowWidthRes(R.dimen.shadow_width);
		// ������ӰͼƬ
		menu.setShadowDrawable(R.drawable.shadow);
		// SlidingMenu����ʱ��ҳ����ʾ��ʣ����
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// SlidingMenu����ʱ�Ľ���̶�
		menu.setFadeDegree(0.35f);
		// ʹSlidingMenu������Activity��
		menu.attachToActivity(context, SlidingMenu.SLIDING_CONTENT);
		// ����menu�Ĳ����ļ�
		menu.setMenu(R.layout.left_menu);

		// ����һ��List���ϣ�List���ϵ�Ԫ����Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < names.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("header", imageIds[i]);
			listItem.put("personName", names[i]);
			listItems.add(listItem);
		}
		// ����һ��SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(context, listItems,
				R.layout.simple_item, new String[] { "personName", "header" },
				new int[] { R.id.name, R.id.header });
		ListView list = (ListView) findViewById(R.id.mylist);
		// ΪListView����Adapter
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

	/** �˳���ť */
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

	/****************************** ������ȥʵ�ֵķ��� ***********************************/

	/**
	 * ���ò����ļ�
	 */
	public abstract void setContentView();

	/**
	 * ��ʼ�������ļ��еĿؼ�
	 */
	public abstract void initViews();

	/**
	 * ��ʼ���ؼ��ļ���
	 */
	public abstract void initListeners();

	/**
	 * �������ݳ�ʼ�� initData
	 */
	public abstract void initData();

	/**
	 * ������һҳ
	 */
	public abstract void goBack();
}