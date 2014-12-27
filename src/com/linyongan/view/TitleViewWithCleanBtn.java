package com.linyongan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.linyongan.ui.R;

/**
 * 计算器页面的标题
 * 
 * @author yongan
 * 
 */
public class TitleViewWithCleanBtn extends FrameLayout {

	private ImageButton backButton;
	private ImageButton cleanButton;
	private TextView titleText;

	public TitleViewWithCleanBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.titleviewwithcleanbtn, this);
		titleText = (TextView) findViewById(R.id.titleViewVithCleanBtn_title);
		backButton = (ImageButton) findViewById(R.id.titleViewVithCleanBtn_back_bn);
		cleanButton = (ImageButton) findViewById(R.id.titleViewVithCleanBtn_clean_bn);
	}

	public void setTitleText(String text) {
		titleText.setText(text);
	}

	public void setBackButtonListener(OnClickListener l) {
		backButton.setOnClickListener(l);
	}

	public void setCleanButtonListener(OnClickListener l) {
		cleanButton.setOnClickListener(l);
	}

}