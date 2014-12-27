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
 * ���ʲ�ѯҳ�档���Ƕ�ClearEditText����addTextChangedListener����������������ݷ����仯���������ֵ����ListView��
 * �����ֵΪ����ʾԭ�����б�������б����ݽ��������õ�PinyinComparator�ӿڣ��ýӿ���Ҫ�������Ƚ϶����
 */
public class SearchActivity extends BaseActivity {
	private ListView sortListView;
	private SideBar sideBar;
	/**
	 * ��ʾ��ĸ��TextView
	 */
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	/**
	 * ����ת����ƴ������
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	/**
	 * ����ƴ��������ListView�����������
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
		// �����Ҳഥ������
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			public void onTouchingLetterChanged(String s) {
				// ����ĸ�״γ��ֵ�λ��
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});
		sortListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
				// Toast.makeText(getApplication(),
				// ((SortModel) adapter.getItem(position)).getName(),
				// Toast.LENGTH_SHORT).show();
				// װ��R.layout.popup��Ӧ�Ľ��沼��
				popupView(position);

			}

		});
		// �������������ֵ�ĸı�����������
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
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
		titleView.setTitle("���ʲ�ѯ");
		// ʵ��������תƴ����
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sideBar.setTextView(dialog);
		SourceDateList = filledData(getResources().getStringArray(R.array.date));

		// ����a-z��������Դ����
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
	 * ��������
	 * 
	 * @param position
	 */
	private void popupView(int position) {
		View root = getLayoutInflater().inflate(R.layout.search_popup, null);
		// ����PopupWindow����
		final PopupWindow popup = new PopupWindow(root,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		// ��PopupWindow��ʾ��ָ��λ��
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
		// ��ȡPopupWindow�еĹرհ�ť��
		root.findViewById(R.id.search_closeButton).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View v) {
						// �ر�PopupWindow
						popup.dismiss(); // ��
					}
				});
	}

	/**
	 * ΪListView�������
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			// ����ת����ƴ��
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
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
	 * ����������е�ֵ���������ݲ�����ListView
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

		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
}