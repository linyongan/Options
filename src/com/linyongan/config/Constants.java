package com.linyongan.config;

import android.os.Environment;

/**
 * ���ݿ����ó�����
 * 
 */
public class Constants {

	/** Ӧ�õ�ID */
	public static String Bmob_APPID = "fcaada794a793baefb7ce5ddbe9e9071";
	/** �ļ��Ļ���·�� */
	//��ģ��������ʱ��
//	public static String basePath = "/data/data/com.linyongan.ui/databases/";
	public static String basePath = Environment.getExternalStorageDirectory()
			.getPath() + "/Options/";
	/** ���ݿ����� */
	public static final String DATABASE_NAME = "xy.db";
	/** ���ݿ�汾 */
	public static final int Version = 1;

	public static class CalculateTable {
		/** ���� */
		public static final String TABLE_NAME = "xy";
		/** X */
		public static final String ID = "_id";
		/** Y */
		public static final String Y = "y";
	}

	public static class NounTable {
		/** ���� */
		public static final String TABLE_NAME = "noun";
		/** name */
		public static final String NAME = "name";
		/** value */
		public static final String VALUE = "value";
	}

	public static class QuestionTable {
		/** ���� */
		public static final String TABLE_NAME = "question";
		/** question */
		public static final String QUESTION = "question";
		/** answer */
		public static final String ANSWER = "answer";
	}

	public static class TestTable {
		/** ���� */
		public static final String TABLE_NAME = "test";
		/** id */
		public static final String ID = "_id";
		/** ���� */
		public static final String QUESTION = "question";
		/** ѡ��1 */
		public static final String OPTION1 = "option1";
		/** ѡ��2 */
		public static final String OPTION2 = "option2";
		/** ѡ��3 */
		public static final String OPTION3 = "option3";
		/** ѡ��4 */
		public static final String OPTION4 = "option4";
		/** �ղصı�� */
		public static final String MARK = "mark";
		/** �� */
		public static final String ANSWER = "answer";
	}

	public static class GradeTable {
		/** id */
		public static final String ID = "_id";
		/** ���� */
		public static final String TABLE_NAME = "grade";
		/** ʱ�� */
		public static final String TIME = "time";
		/** �Ѷ� */
		public static final String NAME = "name";
		/** �ɼ� */
		public static final String GRADE = "grade";
	}

}
