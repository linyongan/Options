package com.linyongan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.linyongan.ui.R;

/**
 * <普通页面的标题>组合控件的意思就是，我们并不需要自己去绘制视图上显示的内容，而只是用系统原生的控件就好了，但我们可以将几个系统原生的控件组合到一起，
 * 这样创建出的控件就被称为组合控件。
 * 
 * @author yongan
 * 
 */
public class TitleView extends FrameLayout {

	private ImageButton backButton;

	private TextView titleText;

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 1.调用了LayoutInflater的inflate()方法来加载刚刚定义的titleview1.xml布局
		LayoutInflater.from(context).inflate(R.layout.titleview, this);
		// 2.接下来调用findViewById()方法获取到了返回按钮的实例，
		titleText = (TextView) findViewById(R.id.titleview_title);
		backButton = (ImageButton) findViewById(R.id.titleview_back_btn);
	}

	public void setTitle(String text) {
		titleText.setText(text);
	}

	public void setBackButtonListener(OnClickListener l) {
		backButton.setOnClickListener(l);
	}

}