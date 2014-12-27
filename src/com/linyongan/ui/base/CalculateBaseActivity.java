package com.linyongan.ui.base;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.linyongan.ui.R;
import com.linyongan.view.TitleViewWithCleanBtn;

/**
 * 期权计算基础页面
 */
public abstract class CalculateBaseActivity extends Activity {
	/** 标题 */
	public TitleViewWithCleanBtn titleView;
	protected Toast mToast;
	
	protected void initBaseActivity() {
		titleView = (TitleViewWithCleanBtn) findViewById(R.id.TitleView2);
		titleView.setBackButtonListener(new ButtonListener());
		titleView.setCleanButtonListener(new ButtonListener());
	}
	
	public class ButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.titleViewVithCleanBtn_back_bn:
				goBack();
				break;
			case R.id.titleViewVithCleanBtn_clean_bn:
				cleanEditText();
				break;
			}

		}
	}
	
	/**
	 * 提示方法
	 * 
	 * @param text
	 */
	public void ShowToast(String text) {
		if (!TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}

	/****************************** 由子类去实现的方法 ***********************************/
	/**
	 * 返回上一页
	 */
	public abstract void goBack();

	/**
	 * 清空
	 */
	public abstract void cleanEditText();
}