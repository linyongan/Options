package com.linyongan.sortlistview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.linyongan.ui.R;

/**
 * 该类需要实现SectionIndexer接口，该接口是用来控制ListView分组的，该接口有三个方法getSectionForPosition(int
 * position)，getPositionForSection(int section)，getSections()，我们只需要自行实现前面两个方法
 * 
 * @author yongan
 * 
 */
public class SortAdapter extends BaseAdapter implements SectionIndexer {
	private List<SortModel> list = null;
	private Context mContext;

	public SortAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<SortModel> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * getView()方法，首先我们根据ListView的position调用getSectionForPosition(int
	 * position)来获取该位置上面name的首字母的ascii值,然后根据这个ascii值调用getPositionForSection(int
	 * section)来获取第一次出现该首字母的position，如果ListView的position 等于
	 * 根据这个ascii值调用getPositionForSection(int
	 * section)来获取第一次出现该首字母的position，则显示分类字母 否则隐藏
	 */
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(
					R.layout.sortlistview_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);

		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}

		viewHolder.tvTitle.setText(this.list.get(position).getName());

		return view;

	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char
	 * ascii值.如果该position上面的name是阿妹，首字母就是A，那么此方法返回的就是'A'字母的ascii值，也就是65，
	 * 'B'是66，依次类推
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置.如果section是66
	 * ，也就是‘B’的ascii值，那么该方法返回的position就是2
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}