package com.linyongan.ui;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.linyongan.config.Constants;
import com.linyongan.db.GradeDbManger;
import com.linyongan.util.Util;
import com.linyongan.view.MyListView;
import com.linyongan.view.MyListView.DelButtonClickListener;
import com.linyongan.view.TitleView;

/**
 * 测试成绩界面
 */
public class TestGradeActivity extends Activity {
	/** 标题 */
	private TitleView titleView;

	private GradeDbManger dbManger;
	private CursorAdapter listAdapter;
	private MyListView listView;
	private Cursor cursor;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 设置全屏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.test_grade);

		dbManger = new GradeDbManger(this);
		listView = (MyListView) findViewById(R.id.test_grade_listview);
		listView.setDelButtonClickListener(new DelButtonClickListener() {
			@Override
			public void clickHappend(int position) {
				// 查找到当前的item
				View view = listView.getChildAt(position);
				TextView textview = (TextView) view
						.findViewById(R.id.test_grade_item_time);
				// 查找到当前item的时间
				String string = textview.getText().toString();
				// 更改数据库的数据，删除成绩
				dbManger.open();
				dbManger.delete(string);
				// 从数据库重新获取数据，更新listview
				cursor = dbManger.searchGrade();
				listAdapter.changeCursor(cursor);
				dbManger.close();
			}

		});

		dbManger.open();
		cursor = dbManger.searchGrade();
		listAdapter = new SimpleCursorAdapter(
				this,
				R.layout.test_grade_item,
				cursor,
				new String[] { Constants.GradeTable.TIME,
						Constants.GradeTable.NAME, Constants.GradeTable.GRADE },
				new int[] { R.id.test_grade_item_time,
						R.id.test_grade_item_name, R.id.test_grade_item_grade });
		listView.setAdapter(listAdapter);
		dbManger.close();

		titleView = (TitleView) findViewById(R.id.TitleView);
		titleView.setTitle("成绩查询");
		titleView.setBackButtonListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goBack();
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

	/** 返回上一个界面 */
	private void goBack() {
		Util.goBack(TestGradeActivity.this, TestActivity.class);
	}
}