package com.linyongan.ui.base;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.linyongan.ui.R;
import com.linyongan.view.TitleViewWithCleanBtn;

/**
 * ��Ȩ�������ҳ��
 */
public abstract class CalculateBaseActivity extends Activity {
	/** ���� */
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
	 * ��ʾ����
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

	/****************************** ������ȥʵ�ֵķ��� ***********************************/
	/**
	 * ������һҳ
	 */
	public abstract void goBack();

	/**
	 * ���
	 */
	public abstract void cleanEditText();
}