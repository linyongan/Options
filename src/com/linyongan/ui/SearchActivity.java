package com.linyongan.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.database.Cursor;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.linyongan.config.Constants;
import com.linyongan.db.NounDbManger;
import com.linyongan.sortlistview.CharacterParser;
import com.linyongan.sortlistview.ClearEditText;
import com.linyongan.sortlistview.PinyinComparator;
import com.linyongan.sortlistview.SideBar;
import com.linyongan.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.linyongan.sortlistview.SortAdapter;
import com.linyongan.sortlistview.SortModel;
import com.linyongan.ui.base.BaseActivity;
import com.linyongan.util.Util;

/**
 * 名词查询页面。我们对ClearEditText设置addTextChangedListener监听，当输入框内容发生变化根据里面的值过滤ListView，
 * 里面的值为空显示原来的列表，里面对列表数据进行排序用到PinyinComparator接口，该接口主要是用来比较对象的
 */
public class SearchActivity extends BaseActivity {
	private ListView sortListView;
	private SideBar sideBar;
	/**
	 * 显示字母的TextView
	 */
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	private NounDbManger nounDbManger;
	private TextView textView;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.search);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});
		sortListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				// Toast.makeText(getApplication(),
				// ((SortModel) adapter.getItem(position)).getName(),
				// Toast.LENGTH_SHORT).show();
				// 装载R.layout.popup对应的界面布局
				popupView(position);

			}

		});
		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		nounDbManger = new NounDbManger(this);
		initBackButton();
		titleView.setTitle("名词查询");
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sideBar.setTextView(dialog);
		SourceDateList = filledData(getResources().getStringArray(R.array.date));

		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);
		super.addMenu(this);
	}

	@Override
	public void goBack() {
		Util.goBack(SearchActivity.this, LearnActivity.class);
	}

	/**
	 * 弹出窗口
	 * 
	 * @param position
	 */
	private void popupView(int position) {
		View root = getLayoutInflater().inflate(R.layout.search_popup, null);
		// 创建PopupWindow对象
		final PopupWindow popup = new PopupWindow(root,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		// 将PopupWindow显示在指定位置
		popup.showAtLocation(findViewById(R.id.filter_edit), Gravity.CENTER, 0,
				0);
		textView = (TextView) root.findViewById(R.id.search_show_tv);
		String string = ((SortModel) adapter.getItem(position)).getName();
		nounDbManger.open();
		Cursor cursor = nounDbManger.search(string);
		if (cursor.moveToFirst()) {
			String string1 = cursor.getString(cursor
					.getColumnIndex(Constants.NounTable.VALUE));
			System.out.println("--string:-- " + string1);
			textView.setText(string1);
		}
		nounDbManger.close();
		// 获取PopupWindow中的关闭按钮。
		root.findViewById(R.id.search_closeButton).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View v) {
						// 关闭PopupWindow
						popup.dismiss(); // ①
					}
				});
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
}