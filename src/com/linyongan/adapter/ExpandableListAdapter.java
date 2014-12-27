package com.linyongan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linyongan.ui.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private LayoutInflater inflater;
	/** 栏目 */
	private String[] item = new String[] {};
	/** 子栏目 */
	private String[][] subItem = new String[][] {};

	public ExpandableListAdapter(Context context, String[] item,
			String[][] subItem) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.item = item;
		this.subItem = subItem;
	}

	// 获取指定组位置、指定子列表项处的子列表项数据

	public Object getChild(int groupPosition, int childPosition) {
		return subItem[groupPosition][childPosition];
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
		return subItem[groupPosition].length;
	}

	// 该方法决定每个子选项的外观
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.learn_child, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.item);
		textView.setText(getChild(groupPosition, childPosition).toString());
		return convertView;
	}

	// 获取指定组位置处的组数据
	public Object getGroup(int groupPosition) {
		return item[groupPosition];
	}

	public int getGroupCount() {
		return item.length;
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// 该方法决定每个组选项的外观
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder groupHolder = null;
		if (convertView == null) {
			groupHolder = new GroupHolder();
			convertView = inflater.inflate(R.layout.learn_group, null);
			groupHolder.textView = (TextView) convertView
					.findViewById(R.id.group);
			groupHolder.imageView = (ImageView) convertView
					.findViewById(R.id.image);
			convertView.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder) convertView.getTag();
		}

		groupHolder.textView.setText(getGroup(groupPosition).toString());
		if (isExpanded)// ture is Expanded or false is not isExpanded
			groupHolder.imageView.setImageResource(R.drawable.expanded);
		else
			groupHolder.imageView.setImageResource(R.drawable.collapse);
		return convertView;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

	class GroupHolder {
		TextView textView;
		ImageView imageView;
	}

}
