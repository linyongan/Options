package com.linyongan.ui;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.linyongan.adapter.FeedbackAdapter;
import com.linyongan.model.Comment;
import com.linyongan.model.Feedback;
import com.linyongan.ui.base.BaseActivity;
import com.linyongan.util.Util;

public class FeedbackListActivity extends BaseActivity {

	private FeedbackAdapter adapter;
	private ImageButton backButton, sendButton;
	/** 加载数据之前的进度条页面 */
	private LinearLayout progress;
	// 下拉刷新
	private PullToRefreshListView mPullToRefreshView;
	private ILoadingLayout loadingLayout;
	private ListView mMsgListView;
	private static final int STATE_REFRESH = 0;// 下拉刷新
	private static final int STATE_MORE = 1;// 加载更多
	private int limit = 5; // 每页的数据是5条
	private int curPage = 0; // 当前页的编号，从0开始
	/** 消息列表 */
	private List<Feedback> FeedbackList = new ArrayList<Feedback>();
	/** 消息列表对应的所有评论列表 */
	private List<List<Comment>> commentList = new ArrayList<List<Comment>>();
	int i = 0;
	/** 0：下拉刷新 /1：加载更多 */
	int actionType = 0;
	/** 从哪里开始加载 */
	int skip = 0;

	@Override
	public void setContentView() {
		setContentView(R.layout.feedback_list);
	}

	@Override
	public void initViews() {
		mPullToRefreshView = (PullToRefreshListView) findViewById(R.id.feedback_lv);
		loadingLayout = mPullToRefreshView.getLoadingLayoutProxy();
		mMsgListView = mPullToRefreshView.getRefreshableView();
		backButton = (ImageButton) findViewById(R.id.feedback_back_btn);
		sendButton = (ImageButton) findViewById(R.id.feedback_send_btn);
		progress = (LinearLayout) findViewById(R.id.feedback_progress);
	}

	@Override
	public void initListeners() {
		backButton.setOnClickListener(new ButtonListener());
		sendButton.setOnClickListener(new ButtonListener());
		// 滑动监听
		mPullToRefreshView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				if (firstVisibleItem == 0) {
					loadingLayout.setLastUpdatedLabel("");
					loadingLayout
							.setPullLabel(getString(R.string.pull_to_refresh_top_pull));
					loadingLayout
							.setRefreshingLabel(getString(R.string.pull_to_refresh_top_refreshing));
					loadingLayout
							.setReleaseLabel(getString(R.string.pull_to_refresh_top_release));
				} else if (firstVisibleItem + visibleItemCount + 1 == totalItemCount) {
					loadingLayout.setLastUpdatedLabel("");
					loadingLayout
							.setPullLabel(getString(R.string.pull_to_refresh_bottom_pull));
					loadingLayout
							.setRefreshingLabel(getString(R.string.pull_to_refresh_bottom_refreshing));
					loadingLayout
							.setReleaseLabel(getString(R.string.pull_to_refresh_bottom_release));
				}
			}
		});

		// 下拉刷新监听
		mPullToRefreshView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// 下拉刷新(从第一页开始装载数据)
						commentList = new ArrayList<List<Comment>>();
						actionType = STATE_REFRESH;
						// 设置从第一条数据开始加载
						skip = 0;
						queryData(actionType);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// 上拉加载更多(加载下一页数据)
						commentList = new ArrayList<List<Comment>>();
						actionType = STATE_MORE;
						queryData(actionType);
					}
				});

	}

	@Override
	public void initData() {
		super.addMenu(this);
		// 初始化下拉刷新和加载更多
		loadingLayout.setLastUpdatedLabel("");
		loadingLayout
				.setPullLabel(getString(R.string.pull_to_refresh_bottom_pull));
		loadingLayout
				.setRefreshingLabel(getString(R.string.pull_to_refresh_bottom_refreshing));
		loadingLayout
				.setReleaseLabel(getString(R.string.pull_to_refresh_bottom_release));
		adapter = new FeedbackAdapter(FeedbackListActivity.this,
				(List<Feedback>) FeedbackList);
		mMsgListView.setAdapter(adapter);
		progress.setVisibility(View.VISIBLE);
		// 进入界面，默认加载第一页
		actionType = STATE_REFRESH;
		skip = 0;
		queryData(actionType);
	}

	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.feedback_back_btn:
				goBack();
				break;
			case R.id.feedback_send_btn:
				Util.goToByClass(FeedbackListActivity.this,
						SendFeedbackActivity.class);
				break;
			}
		}

	}

	/**
	 * 分页获取数据
	 * 
	 * @param page
	 *            页码
	 * @param actionType
	 *            ListView的操作类型（下拉刷新、上拉加载更多）
	 */
	private void queryData(final int actionType) {
		BmobQuery<Feedback> query = new BmobQuery<Feedback>();
		query.setLimit(limit); // 设置每页多少条数据
		query.setSkip(skip); // 从第几条数据开始，
		query.order("-createdAt");// 按照时间降序
		query.findObjects(this, new FindListener<Feedback>() {

			@Override
			public void onSuccess(List<Feedback> arg0) {
				if (arg0.size() > 0) {
					FeedbackList = arg0;
					fetchComment();
				} else if (actionType == STATE_MORE) {
					ShowToast("没有更多数据了");
					// 停止加载动画
					mPullToRefreshView.onRefreshComplete();
					progress.setVisibility(View.GONE);
				} else if (actionType == STATE_REFRESH) {
					ShowToast("没有新数据");
					// 停止加载动画
					mPullToRefreshView.onRefreshComplete();
					progress.setVisibility(View.GONE);
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				progress.setVisibility(View.GONE);
				mPullToRefreshView.onRefreshComplete();
				// 显示错误
				Util.errorFul(FeedbackListActivity.this, arg0);
			}
		});
	}

	/**
	 * 迭代加载评论列表
	 */
	public void fetchComment() {
		while (i < FeedbackList.size() && FeedbackList.get(i).getComment() == 0) {
			i++;
			addNormalComment();
		}
		if (i < FeedbackList.size()) {
			Feedback f = FeedbackList.get(i);
			BmobQuery<Comment> query = new BmobQuery<Comment>();
			query.addWhereRelatedTo("relation", new BmobPointer(f));
			query.order("createdAt");
			query.findObjects(this, new FindListener<Comment>() {
				@Override
				public void onSuccess(List<Comment> data) {
					if (data.size() != 0) {
						commentList.add(data);
					} else {
						// 没有评论，则添加默认评论，但是在Adapter里屏蔽掉，不显示出来
						addNormalComment();
					}
					i++;
					if (i < FeedbackList.size()) {// 还没有加载完成，继续迭代
						fetchComment();
					} else {
						// 已加载完成
						finishLoadComment();
					}
				}

				@Override
				public void onError(int arg0, String arg1) {
					for (Feedback f : FeedbackList)
						addNormalComment();
					finishLoadComment();
				}
			});
		}else{
			finishLoadComment();
		}
	}

	/**
	 * 设置默认评论
	 */
	public void addNormalComment() {
		List<Comment> list = new ArrayList<Comment>();
		Comment c = new Comment();
		c.setMaster("系统");
		c.setVisitor("系统");
		c.setCommentContent("没有评论");
		c.setType(3);
		list.add(c);
		commentList.add(list);
	}

	/**
	 * 加载完评论之后的处理
	 */
	public void finishLoadComment() {
		if (actionType == STATE_REFRESH) {
			// 当是下拉刷新操作时，将当前页的编号重置为0，并把FeedbackList清空，重新添加
			curPage = 0;
			// 通知Adapter数据更新
			adapter.clear((List<Feedback>) FeedbackList, commentList);
		} else {
			// 通知Adapter数据更新
			adapter.refresh((List<Feedback>) FeedbackList, commentList);
		}
		// 这里在每次加载完数据后，将当前页码+1
		if (FeedbackList.size() >= limit) {
			curPage++;
			skip = curPage * limit;
		} else {
			// 如果消息的数目小于5条
			skip = FeedbackList.size();
		}
		progress.setVisibility(View.GONE);
		// 停止加载动画
		mPullToRefreshView.onRefreshComplete();
		i = 0;
	}

	@Override
	public void goBack() {
		Util.goBack(FeedbackListActivity.this, LearnActivity.class);

	}
}
