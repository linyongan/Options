package com.linyongan.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.linyongan.model.FaceText;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;

public class FaceTextUtils {

	public static List<FaceText> faceTexts = new ArrayList<FaceText>();
	static {
		faceTexts.add(new FaceText("\\ue000"));
		faceTexts.add(new FaceText("\\ue001"));
		faceTexts.add(new FaceText("\\ue002"));
		faceTexts.add(new FaceText("\\ue003"));
		faceTexts.add(new FaceText("\\ue004"));
		faceTexts.add(new FaceText("\\ue005"));
		faceTexts.add(new FaceText("\\ue006"));
		faceTexts.add(new FaceText("\\ue007"));
		faceTexts.add(new FaceText("\\ue008"));
		faceTexts.add(new FaceText("\\ue009"));
		faceTexts.add(new FaceText("\\ue010"));
		faceTexts.add(new FaceText("\\ue011"));
		faceTexts.add(new FaceText("\\ue012"));
		faceTexts.add(new FaceText("\\ue013"));
		faceTexts.add(new FaceText("\\ue014"));
		faceTexts.add(new FaceText("\\ue015"));
		faceTexts.add(new FaceText("\\ue016"));
		faceTexts.add(new FaceText("\\ue017"));
		faceTexts.add(new FaceText("\\ue018"));
		faceTexts.add(new FaceText("\\ue019"));
		faceTexts.add(new FaceText("\\ue020"));
		faceTexts.add(new FaceText("\\ue021"));
		faceTexts.add(new FaceText("\\ue022"));
		faceTexts.add(new FaceText("\\ue023"));
		faceTexts.add(new FaceText("\\ue024"));
		faceTexts.add(new FaceText("\\ue025"));
		faceTexts.add(new FaceText("\\ue026"));
		faceTexts.add(new FaceText("\\ue027"));
		faceTexts.add(new FaceText("\\ue028"));
		faceTexts.add(new FaceText("\\ue029"));
		faceTexts.add(new FaceText("\\ue030"));
		faceTexts.add(new FaceText("\\ue031"));
		faceTexts.add(new FaceText("\\ue032"));
		faceTexts.add(new FaceText("\\ue033"));
		faceTexts.add(new FaceText("\\ue034"));
		faceTexts.add(new FaceText("\\ue035"));
		faceTexts.add(new FaceText("\\ue036"));
		faceTexts.add(new FaceText("\\ue037"));
		faceTexts.add(new FaceText("\\ue038"));
		faceTexts.add(new FaceText("\\ue039"));
		faceTexts.add(new FaceText("\\ue040"));
		faceTexts.add(new FaceText("\\ue041"));
		faceTexts.add(new FaceText("\\ue042"));
		faceTexts.add(new FaceText("\\ue043"));
		faceTexts.add(new FaceText("\\ue044"));
		faceTexts.add(new FaceText("\\ue045"));
		faceTexts.add(new FaceText("\\ue046"));
		faceTexts.add(new FaceText("\\ue047"));
		faceTexts.add(new FaceText("\\ue048"));
		faceTexts.add(new FaceText("\\ue049"));
		faceTexts.add(new FaceText("\\ue050"));
		faceTexts.add(new FaceText("\\ue051"));
		faceTexts.add(new FaceText("\\ue052"));
		faceTexts.add(new FaceText("\\ue053"));
		faceTexts.add(new FaceText("\\ue054"));
		faceTexts.add(new FaceText("\\ue055"));
		faceTexts.add(new FaceText("\\ue056"));
		faceTexts.add(new FaceText("\\ue057"));
		faceTexts.add(new FaceText("\\ue058"));
		faceTexts.add(new FaceText("\\ue059"));
		faceTexts.add(new FaceText("\\ue060"));
		faceTexts.add(new FaceText("\\ue061"));
		faceTexts.add(new FaceText("\\ue062"));
		faceTexts.add(new FaceText("\\ue063"));
		faceTexts.add(new FaceText("\\ue064"));
		faceTexts.add(new FaceText("\\ue065"));
		faceTexts.add(new FaceText("\\ue066"));
		faceTexts.add(new FaceText("\\ue067"));
		faceTexts.add(new FaceText("\\ue068"));
		faceTexts.add(new FaceText("\\ue069"));
		faceTexts.add(new FaceText("\\ue070"));
		faceTexts.add(new FaceText("\\ue071"));
		faceTexts.add(new FaceText("\\ue072"));
		faceTexts.add(new FaceText("\\ue073"));
		faceTexts.add(new FaceText("\\ue074"));
		faceTexts.add(new FaceText("\\ue075"));
		faceTexts.add(new FaceText("\\ue076"));
		faceTexts.add(new FaceText("\\ue077"));
		faceTexts.add(new FaceText("\\ue078"));
		faceTexts.add(new FaceText("\\ue079"));
		faceTexts.add(new FaceText("\\ue080"));
		faceTexts.add(new FaceText("\\ue081"));
		faceTexts.add(new FaceText("\\ue082"));
		faceTexts.add(new FaceText("\\ue083"));
	}

	public static String parse(String s) {
		for (FaceText faceText : faceTexts) {
			s = s.replace("\\" + faceText.text, faceText.text);
			s = s.replace(faceText.text, "\\" + faceText.text);
		}
		return s;
	}

	/**
	 * toSpannableString
	 * 
	 * @return SpannableString
	 * @throws
	 */
	public static SpannableString toSpannableString(Context context, String text) {
		if (!TextUtils.isEmpty(text)) {
			SpannableString spannableString = new SpannableString(text);
			int start = 0;
			Pattern pattern = Pattern.compile("\\\\ue[a-z0-9]{3}",
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(text);
			while (matcher.find()) {
				String faceText = matcher.group();
				String key = faceText.substring(1);
				BitmapFactory.Options options = new BitmapFactory.Options();
				Bitmap bitmap = BitmapFactory.decodeResource(
						context.getResources(),
						context.getResources().getIdentifier(key, "drawable",
								context.getPackageName()), options);
				ImageSpan imageSpan = new ImageSpan(context, bitmap);
				int startIndex = text.indexOf(faceText, start);
				int endIndex = startIndex + faceText.length();
				if (startIndex >= 0)
					spannableString.setSpan(imageSpan, startIndex, endIndex,
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				start = (endIndex - 1);
			}

			return spannableString;
		} else {
			return new SpannableString("");
		}
	}

	public static SpannableString toSpannableString(Context context,
			String text, SpannableString spannableString) {

		int start = 0;
		Pattern pattern = Pattern.compile("\\\\ue[a-z0-9]{3}",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			String faceText = matcher.group();
			String key = faceText.substring(1);
			BitmapFactory.Options options = new BitmapFactory.Options();
			// options.inSampleSize = 2;
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(),
					context.getResources().getIdentifier(key, "drawable",
							context.getPackageName()), options);
			ImageSpan imageSpan = new ImageSpan(context, bitmap);
			int startIndex = text.indexOf(faceText, start);
			int endIndex = startIndex + faceText.length();
			if (startIndex >= 0)
				spannableString.setSpan(imageSpan, startIndex, endIndex,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			start = (endIndex - 1);
		}

		return spannableString;
	}

}
