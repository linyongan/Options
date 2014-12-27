package com.linyongan.adapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.linyongan.config.Constants;
import com.linyongan.model.Comment;
import com.linyongan.model.Feedback;
import com.linyongan.model.Person;
import com.linyongan.ui.R;
import com.linyongan.util.Util;

public class FeedbackAdapter extends BaseAdapter {

	private List<Feedback> fbs;
	private LayoutInflater inflater;
	/** FeedbackAdapter必须有commentList，否则重绘item时评论列表会乱甚至消失 */
	private List<List<Comment>> commentList = new ArrayList<List<Comment>>();
	private Activity context;
	private int year1 = 0;
	private int year2 = 0;
	private int month1 = 0;
	private int month2 = 0;
	private int day1 = 0;
	private int day2 = 0;
	private int hour1 = 0;
	private int hour2 = 0;
	private int minute1 = 0;
	private int minute2 = 0;
	/** 当前登录的用户 */
	private Person user;
	/** 发表评论的弹出窗口 */
	private PopupWindow popup;
	/** 发表评论的类型 */
	int type = 0;
	/** 目前被回复的人 */
	private String master;
	/** 目前发表评论的人 */
	private String visitor;
	/** 填写评论的EditText */
	private EditText content;

	public FeedbackAdapter(Activity context, List<Feedback> feedbacks) {
		this.context = context;
		this.fbs = feedbacks;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fbs.size();
	}

	@Override
	public Feedback getItem(int position) {
		// TODO Auto-generated method stub
		return fbs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// 只能是局部变量！！！！
		final Feedback feedback;
		final ViewHolder holder;
		final CommentAdapter commentAdapter;
		user = Util.getUser(context);
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.feedback_item, null);
			holder.content = (TextView) convertView
					.findViewById(R.id.tv_content);
			holder.time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.contacts = (TextView) convertView.findViewById(R.id.tv_name);
			holder.head = (ImageView) convertView.findViewById(R.id.iv_head);
			holder.love = (TextView) convertView
					.findViewById(R.id.item_action_love);
			holder.comment = (TextView) convertView
					.findViewById(R.id.item_action_comment);
			holder.commetLv = (ListView) convertView
					.findViewById(R.id.item_comment_list);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		feedback = fbs.get(position);
		List<Comment> list = commentList.get(position);
		commentAdapter = new CommentAdapter(context, list);
		// 绑定监听器
		holder.love.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (user == null) {
					Util.ShowToast(context, "请先登录。");
					return;
				}
				saveLove(feedback, holder.love);
			}
		});
		holder.comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (user == null) {
					Util.ShowToast(context, "请先登录。");
					return;
				}
				if (user.getUsername().equals(
						holder.contacts.getText().toString()))
					// 如果是自己评论自己
					type = 1;
				else
					// 评论别人
					type = 0;
				master = holder.contacts.getText().toString();
				visitor = user.getUsername();
				popupView("来评论一句吧", feedback, commentAdapter, holder.comment);
			}
		});
		// 通过点击评论列表，直接回复别人的评论
		holder.commetLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (user == null) {
					Util.ShowToast(context, "请先登录。");
					return;
				}
				TextView chirlmaster = (TextView) view
						.findViewById(R.id.comment_master);
				TextView chirlvisitor = (TextView) view
						.findViewById(R.id.comment_visitor);
				// 之前“游客”评论“发帖人”，现在我们要回复“游客”,且游客不能是自己
				if (TextUtils.isEmpty(chirlmaster.getText().toString())) {
					if (chirlvisitor.getText().toString()
							.equals(user.getUsername())) {
						Util.ShowToast(context, "自己不能回复自己");
					} else {
						type = 2;
						master = chirlvisitor.getText().toString();
						visitor = user.getUsername();
						popupView("回复" + master, feedback, commentAdapter,
								holder.comment);
					}
				} else {// XXX回复XXX
					if (user.getUsername().equals(
							chirlmaster.getText().toString()))
						Util.ShowToast(context, "自己不能回复自己");
					else {
						type = 2;
						master = chirlmaster.getText().toString();
						visitor = user.getUsername();
						popupView("回复" + master, feedback, commentAdapter,
								holder.comment);
					}
				}
			}
		});

		holder.commetLv.setAdapter(commentAdapter);
		setListViewHeightBasedOnChildren(holder.commetLv);

		holder.content.setText(feedback.getContent());
		holder.time.setText(calculateTime(getTime(), feedback.getCreatedAt()));
		holder.contacts.setText(feedback.getContacts());
		if (feedback.getLove() != 0) {
			if (feedback.isMyLove()) {
				holder.love.setTextColor(Color.parseColor("#003399"));
				holder.love.setText(feedback.getLove() + "(已赞)");
			} else
				holder.love.setText(feedback.getLove() + "");
		} else {
			holder.love.setText("赞");
			holder.love.setTextColor(Color.parseColor("#888888"));
		}
		if (feedback.getComment() != 0) {
			holder.comment.setText(feedback.getComment() + "");
		} else {
			holder.comment.setText("评论");
		}

		if (Util.isSDCardUsabled()) {
			// 检查头像图片是否存在
			if ((new File(Constants.basePath + feedback.getContacts() + ".jpg"))
					.exists() == true) {
				Bitmap bit = BitmapFactory.decodeFile(Constants.basePath
						+ feedback.getContacts() + ".jpg");
				holder.head.setImageBitmap(bit);
			}
		}
		// // 用手机模拟器测试
		// if ((new File("/data/data/com.linyongan.ui/databases/"
		// + feedback.getContacts() + ".jpg")).exists() == true) {
		// Bitmap bit = BitmapFactory
		// .decodeFile("/data/data/com.linyongan.ui/databases/"
		// + feedback.getContacts() + ".jpg");
		// holder.head.setImageBitmap(bit);
		// }
		return convertView;
	}

	public void refresh(List<Feedback> feedbacks,
			List<List<Comment>> commentList) {
		fbs.addAll(feedbacks);
		this.commentList.addAll(commentList);
		notifyDataSetChanged();
	}

	public void clear(List<Feedback> feedbacks, List<List<Comment>> commentList) {
		fbs = feedbacks;
		this.commentList = commentList;
		notifyDataSetChanged();
	}

	static class ViewHolder {
		TextView content;
		TextView time;
		TextView contacts;
		ImageView head;
		TextView love;
		TextView comment;
		ListView commetLv;
	}

	/**
	 * 点赞处理
	 * 
	 * @param feedback
	 */
	private void saveLove(final Feedback feed, TextView love_tv) {
		if (feed.isMyLove()) {
			Util.ShowToast(context, "您已经赞过啦");
			return;
		}
		feed.setLove(feed.getLove() + 1);
		feed.setMyLove(true);
		love_tv.setTextColor(Color.parseColor("#003399"));
		love_tv.setText(feed.getLove() + "(已赞)");
		feed.update(context, new UpdateListener() {
			@Override
			public void onSuccess() {
				Util.ShowToast(context, "点赞成功~");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
			}
		});
	}

	/**
	 * 弹出填写评论的窗口
	 */
	private void popupView(String hint, final Feedback feed,
			final CommentAdapter commentAdapter, final TextView comment_tv) {
		View view = inflater.inflate(R.layout.comment_popup, null);
		popup = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		// 将PopupWindow显示在指定位置
		popup.showAtLocation(
				context.findViewById(R.id.feedback_relativeLayout),
				Gravity.CENTER, 0, 0);
		popup.setOutsideTouchable(true);
		content = (EditText) view.findViewById(R.id.comment_popup_content);
		content.setHint(hint);
		Button commit = (Button) view.findViewById(R.id.comment_popup_commit);
		LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.comment_popup_layout);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击窗口则外关闭窗口
				if (popup != null && popup.isShowing())
					popup.dismiss();
			}
		});
		commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 如果输入内容不为空，则关闭输入界面，发送评论
				if (!TextUtils.isEmpty(content.getText().toString())) {
					popup.dismiss();
					publishComment(master, visitor, content.getText()
							.toString(), feed, type, commentAdapter, comment_tv);
				}
			}
		});
	}

	/**
	 * 发表评论
	 * 
	 * @param content
	 * @param feed
	 */
	private void publishComment(String master, String visitor, String content,
			final Feedback feed, int type, final CommentAdapter commentAdapter,
			final TextView comment_tv) {
		final Comment comment = new Comment();
		comment.setMaster(master);
		comment.setVisitor(visitor);
		comment.setCommentContent(content);
		comment.setType(type);
		comment.save(context, new SaveListener() {

			@Override
			public void onSuccess() {
				commentAdapter.getDataList().add(comment);
				// 发表评论之后刷新listView
				commentAdapter.notifyDataSetChanged();
				notifyDataSetChanged();
				Util.ShowToast(context, "发表评论成功");
				// 将该评论与消息绑定到一起
				BmobRelation relation = new BmobRelation();
				relation.add(comment);
				feed.setRelation(relation);
				feed.setComment(feed.getComment() + 1);
				comment_tv.setText(feed.getComment() + "");
				feed.update(context, new UpdateListener() {

					@Override
					public void onSuccess() {
					}

					@Override
					public void onFailure(int arg0, String arg1) {
					}
				});

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * 动态设置listview的高度(item总布局必须是linearLayout)
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1))
				+ 6;
		listView.setLayoutParams(params);
	}

	/**
	 * 获取当前的年、月、日 对应的时间
	 * 
	 * @return 当前时间
	 */
	@SuppressLint("SimpleDateFormat")
	public String getTime() {
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateNowStr = formatter.format(d);
		return dateNowStr;
	}

	/**
	 * 计算时间（有个缺陷就是显示成“昨天”的情况没有考虑完整）
	 * 
	 * @param s1
	 *            现在的时间
	 * @param s2
	 *            消息的时间
	 */
	public String calculateTime(String s1, String s2) {
		if (!TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2)) {
			year1 = Integer.valueOf(s1.substring(2, 4));
			year2 = Integer.valueOf(s2.substring(2, 4));
			month1 = Integer.valueOf(s1.substring(5, 7));
			month2 = Integer.valueOf(s2.substring(5, 7));
			day1 = Integer.valueOf(s1.substring(8, 10));
			day2 = Integer.valueOf(s2.substring(8, 10));
			hour1 = Integer.valueOf(s1.substring(11, 13));
			hour2 = Integer.valueOf(s2.substring(11, 13));
			minute1 = Integer.valueOf(s1.substring(14, 16));
			minute2 = Integer.valueOf(s2.substring(14, 16));
			if (year1 == year2) {
				if (month1 == month2) {
					if (day1 == day2) {
						// 一天之内
						if ((minute1 + (hour1 - hour2) * 60 - minute2) < 60)
							return (minute1 + (hour1 - hour2) * 60 - minute2 + 1)
									+ "分钟前";// 一小时之内
						else
							return (hour1 - hour2) + "小时前";// 大于一小时
					} else if ((hour1 + (day1 - day2) * 24 - hour2) < 24) {
						return (hour1 + (day1 - day2) * 24 - hour2) + "小时前";// 一天之内，
																			// 大于一小时
					} else if ((day1 - 1) == day2)
						return "昨天";
				}
			}
			// 两天前，直接显示消息的时间
			return s2.substring(0, s2.length() - 3);
		}
		return null;
	}
}
