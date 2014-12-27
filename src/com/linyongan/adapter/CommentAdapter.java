package com.linyongan.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linyongan.model.Comment;
import com.linyongan.ui.R;

public class CommentAdapter extends BaseAdapter {

	List<Comment> commetList;
	LayoutInflater inflater;
	public CommentAdapter(Context context, List<Comment> com) {
		this.commetList = com;
		inflater = LayoutInflater.from(context);
	}

	public static class ViewHolder {
		TextView master;
		TextView content;
		TextView reply;
		TextView visitor;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commetList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return commetList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.comment_item, null);
			viewHolder.master = (TextView) convertView
					.findViewById(R.id.comment_master);
			viewHolder.content = (TextView) convertView
					.findViewById(R.id.comment_content);
			viewHolder.reply = (TextView) convertView
					.findViewById(R.id.comment_reply);
			viewHolder.visitor = (TextView) convertView
					.findViewById(R.id.comment_visitor);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Comment comment = commetList.get(position);
		switch (comment.getType()) {
		case 0:
			viewHolder.master.setVisibility(View.GONE);
			viewHolder.reply.setVisibility(View.GONE);
			viewHolder.visitor.setText(comment.getVisitor());
			viewHolder.content.setText(": "+comment.getCommentContent());
			break;
		case 1:
			viewHolder.visitor.setVisibility(View.GONE);
			viewHolder.reply.setVisibility(View.GONE);
			viewHolder.master.setText(comment.getMaster());
			viewHolder.content.setText(": "+comment.getCommentContent());
			break;
		case 2:
			viewHolder.master.setText(comment.getVisitor());
			viewHolder.visitor.setText(comment.getMaster());
			viewHolder.content.setText(": "+comment.getCommentContent());
			break;
		case 3:
			viewHolder.master.setVisibility(View.GONE);
			viewHolder.reply.setVisibility(View.GONE);
			viewHolder.visitor.setVisibility(View.GONE);
			viewHolder.content.setVisibility(View.GONE);
			break;
		case 4:
			viewHolder.master.setVisibility(View.GONE);
			viewHolder.reply.setVisibility(View.GONE);
			viewHolder.visitor.setVisibility(View.GONE);
			viewHolder.content.setText(comment.getCommentContent());
			break;
		}
		return convertView;
	}

	public List<Comment> getDataList() {
		// TODO Auto-generated method stub
		return commetList;
	}

}
