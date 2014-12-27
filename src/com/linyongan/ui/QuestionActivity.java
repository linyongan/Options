package com.linyongan.ui;

import android.database.Cursor;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.linyongan.config.Constants;
import com.linyongan.db.QuestionDbManger;
import com.linyongan.ui.base.BaseActivity;
import com.linyongan.util.Util;

/**
 * 常见问题页面
 */
public class QuestionActivity extends BaseActivity {
	private ListView listView;
	/** 数据库管理类 */
	private QuestionDbManger dbManger;
	/** 12个问题 */
	private String[] strings = new String[12];
	private int i = 0;

	private ArrayAdapter<String> adapter;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.question);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.question_listview);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				popupView(position);
			}
		});
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		dbManger = new QuestionDbManger(this);
		getQuestion();
		initBackButton();
		titleView.setTitle("常见问题");
		super.addMenu(this);
	}

	@Override
	public void goBack() {
		Util.goBack(QuestionActivity.this, LearnActivity.class);
	}

	/**
	 * 获取问题
	 */
	private void getQuestion() {
		dbManger.open();
		Cursor cursor = dbManger.searchQuestion();
		if (cursor.moveToFirst()) {
			do {
				String string1 = cursor.getString(cursor
						.getColumnIndex(Constants.QuestionTable.QUESTION));
				System.out.println("--string:-- " + string1);
				strings[i] = string1;
				i++;
			} while (cursor.moveToNext());

		}
		adapter = new ArrayAdapter<String>(this, R.layout.question_item,
				strings);
		listView.setAdapter(adapter);
		dbManger.close();
	}

	/**
	 * 弹出窗口
	 * 
	 * @param position
	 */
	private void popupView(int position) {
		View root = getLayoutInflater().inflate(R.layout.question_popup, null);
		// 创建PopupWindow对象
		final PopupWindow popup = new PopupWindow(root,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		// 将PopupWindow显示在指定位置
		popup.showAtLocation(findViewById(R.id.question_listview),
				Gravity.CENTER, 0, 0);
		TextView textView;
		textView = (TextView) root.findViewById(R.id.question_show_tv);
		String string = adapter.getItem(position).toString();
		dbManger.open();
		Cursor cursor = dbManger.search(string);
		if (cursor.moveToFirst()) {
			String string1 = cursor.getString(cursor
					.getColumnIndex(Constants.QuestionTable.ANSWER));
			System.out.println("--string1:-- " + string1);
			textView.setText(string1);
		}
		dbManger.close();
		textView.setMovementMethod(ScrollingMovementMethod.getInstance());
		// 获取PopupWindow中的关闭按钮。
		root.findViewById(R.id.question_closeButton).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View v) {
						// 关闭PopupWindow
						popup.dismiss();
					}
				});
	}
}