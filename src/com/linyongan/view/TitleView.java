package com.linyongan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.linyongan.ui.R;

/**
 * <��ͨҳ��ı���>��Ͽؼ�����˼���ǣ����ǲ�����Ҫ�Լ�ȥ������ͼ����ʾ�����ݣ���ֻ����ϵͳԭ���Ŀؼ��ͺ��ˣ������ǿ��Խ�����ϵͳԭ���Ŀؼ���ϵ�һ��
 * �����������Ŀؼ��ͱ���Ϊ��Ͽؼ���
 * 
 * @author yongan
 * 
 */
public class TitleView extends FrameLayout {

	private ImageButton backButton;

	private TextView titleText;

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 1.������LayoutInflater��inflate()���������ظոն����titleview1.xml����
		LayoutInflater.from(context).inflate(R.layout.titleview, this);
		// 2.����������findViewById()������ȡ���˷��ذ�ť��ʵ����
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