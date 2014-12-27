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
	/** ��������֮ǰ�Ľ�����ҳ�� */
	private LinearLayout progress;
	// ����ˢ��
	private PullToRefreshListView mPullToRefreshView;
	private ILoadingLayout loadingLayout;
	private ListView mMsgListView;
	private static final int STATE_REFRESH = 0;// ����ˢ��
	private static final int STATE_MORE = 1;// ���ظ���
	private int limit = 5; // ÿҳ��������10��
	private int curPage = 0; // ��ǰҳ�ı�ţ���0��ʼ
	private List<Feedback> FeedbackList = new ArrayList<Feedback>();
	private List<List<Comment>> commentList = new ArrayList<List<Comment>>();
	int i = 0;
	int actionType = 0;
	/** �����￪ʼ���� */
	int skip = 0;

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.feedback_list);
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		mPullToRefreshView = (PullToRefreshListView) findViewById(R.id.feedback_lv);
		loadingLayout = mPullToRefreshView.getLoadingLayoutProxy();
		mMsgListView = mPullToRefreshView.getRefreshableView();
		backButton = (ImageButton) findViewById(R.id.feedback_back_btn);
		sendButton = (ImageButton) findViewById(R.id.feedback_send_btn);
		progress = (LinearLayout) findViewById(R.id.feedback_progress);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		backButton.setOnClickListener(new ButtonListener());
		sendButton.setOnClickListener(new ButtonListener());
		// ��������
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

		// ����ˢ�¼���
		mPullToRefreshView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// ����ˢ��(�ӵ�һҳ��ʼװ������)
						commentList = new ArrayList<List<Comment>>();
						actionType = STATE_REFRESH;
						skip = 0;
						queryData(actionType);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// �������ظ���(������һҳ����)
						commentList = new ArrayList<List<Comment>>();
						actionType = STATE_MORE;
						queryData(actionType);
					}
				});

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.addMenu(this);
		// ��ʼ������ˢ�ºͼ��ظ���
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
		// Ĭ�ϼ��ص�һҳ
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
	 * ��ҳ��ȡ����
	 * 
	 * @param page
	 *            ҳ��
	 * @param actionType
	 *            ListView�Ĳ������ͣ�����ˢ�¡��������ظ��ࣩ
	 */
	private void queryData(final int actionType) {
		BmobQuery<Feedback> query = new BmobQuery<Feedback>();
		query.setLimit(limit); // ����ÿҳ����������
		query.setSkip(skip); // �ӵڼ������ݿ�ʼ��
		query.order("-createdAt");// ����ʱ�併��
		query.findObjects(this, new FindListener<Feedback>() {

			@Override
			public void onSuccess(List<Feedback> arg0) {
				// TODO Auto-generated method stub
				if (arg0.size() > 0) {
					FeedbackList = arg0;
					fetchComment();
				} else if (actionType == STATE_MORE) {
					ShowToast("û�и���������");
					// ֹͣ���ض���
					mPullToRefreshView.onRefreshComplete();
					progress.setVisibility(View.GONE);
				} else if (actionType == STATE_REFRESH) {
					ShowToast("û��������");
					// ֹͣ���ض���
					mPullToRefreshView.onRefreshComplete();
					progress.setVisibility(View.GONE);
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				progress.setVisibility(View.GONE);
				mPullToRefreshView.onRefreshComplete();
				Util.errorFul(FeedbackListActivity.this, arg0);
			}
		});
	}

	/**
	 * ��������
	 * 
	 * @param feedback
	 */
	public void fetchComment() {
		Feedback f = FeedbackList.get(i);
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		query.addWhereRelatedTo("relation", new BmobPointer(f));
		query.order("createdAt");
		query.findObjects(this, new FindListener<Comment>() {
			@Override
			public void onSuccess(List<Comment> data) {
				// TODO Auto-generated method stub
				if (data.size() != 0) {
					commentList.add(data);
				} else {
					addNormalComment();
				}
				i++;
				if (i < FeedbackList.size()) {
					fetchComment();
				} else {
					finishLoadComment();
					i = 0;
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				for (Feedback f : FeedbackList)
					addNormalComment();
				finishLoadComment();
			}
		});
	}

	/**
	 * ����Ĭ������
	 */
	public void addNormalComment() {
		List<Comment> list = new ArrayList<Comment>();
		Comment c = new Comment();
		c.setMaster("ϵͳ");
		c.setVisitor("ϵͳ");
		c.setCommentContent("û������");
		c.setType(3);
		list.add(c);
		commentList.add(list);
	}

	/**
	 * ����������֮��Ĵ���
	 */
	public void finishLoadComment() {
		if (actionType == STATE_REFRESH) {
			// ��������ˢ�²���ʱ������ǰҳ�ı������Ϊ0������bankCards��գ���������
			curPage = 0;
			System.out
					.println("actionType == STATE_REFRESHʱcurPage:" + curPage);
			// ֪ͨAdapter���ݸ���
			adapter.clean((List<Feedback>) FeedbackList, commentList);
		} else {
			// ֪ͨAdapter���ݸ���
			adapter.refresh((List<Feedback>) FeedbackList, commentList);
		}
		// ������ÿ�μ��������ݺ󣬽���ǰҳ��+1
		if (FeedbackList.size() >= limit) {
			curPage++;
			skip = curPage * limit;
			System.out.println("FeedbackList.size() >= limitʱcurPage:"
					+ curPage + "  skip " + skip);
		} else {
			// �����Ϣ����ĿС��5��
			skip = FeedbackList.size();
		}
		progress.setVisibility(View.GONE);
		// ֹͣ���ض���
		mPullToRefreshView.onRefreshComplete();
	}

	@Override
	public void goBack() {
		Util.goBack(FeedbackListActivity.this, LearnActivity.class);

	}
}