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
 * ��������ҳ��
 */
public class QuestionActivity extends BaseActivity {
	private ListView listView;
	/** ���ݿ������ */
	private QuestionDbManger dbManger;
	/** 12������ */
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
		titleView.setTitle("��������");
		super.addMenu(this);
	}

	@Override
	public void goBack() {
		Util.goBack(QuestionActivity.this, LearnActivity.class);
	}

	/**
	 * ��ȡ����
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
	 * ��������
	 * 
	 * @param position
	 */
	private void popupView(int position) {
		View root = getLayoutInflater().inflate(R.layout.question_popup, null);
		// ����PopupWindow����
		final PopupWindow popup = new PopupWindow(root,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		// ��PopupWindow��ʾ��ָ��λ��
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
		// ��ȡPopupWindow�еĹرհ�ť��
		root.findViewById(R.id.question_closeButton).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View v) {
						// �ر�PopupWindow
						popup.dismiss();
					}
				});
	}
}