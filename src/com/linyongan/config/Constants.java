package com.linyongan.config;

import android.os.Environment;

/**
 * 数据库配置常量类
 * 
 */
public class Constants {

	/** 应用的ID */
	public static String Bmob_APPID = "fcaada794a793baefb7ce5ddbe9e9071";
	/** 文件的基础路径 */
	//用模拟器测试时用
//	public static String basePath = "/data/data/com.linyongan.ui/databases/";
	public static String basePath = Environment.getExternalStorageDirectory()
			.getPath() + "/Options/";
	/** 数据库名字 */
	public static final String DATABASE_NAME = "xy.db";
	/** 数据库版本 */
	public static final int Version = 1;

	public static class CalculateTable {
		/** 表名 */
		public static final String TABLE_NAME = "xy";
		/** X */
		public static final String ID = "_id";
		/** Y */
		public static final String Y = "y";
	}

	public static class NounTable {
		/** 表名 */
		public static final String TABLE_NAME = "noun";
		/** name */
		public static final String NAME = "name";
		/** value */
		public static final String VALUE = "value";
	}

	public static class QuestionTable {
		/** 表名 */
		public static final String TABLE_NAME = "question";
		/** question */
		public static final String QUESTION = "question";
		/** answer */
		public static final String ANSWER = "answer";
	}

	public static class TestTable {
		/** 表名 */
		public static final String TABLE_NAME = "test";
		/** id */
		public static final String ID = "_id";
		/** 问题 */
		public static final String QUESTION = "question";
		/** 选项1 */
		public static final String OPTION1 = "option1";
		/** 选项2 */
		public static final String OPTION2 = "option2";
		/** 选项3 */
		public static final String OPTION3 = "option3";
		/** 选项4 */
		public static final String OPTION4 = "option4";
		/** 收藏的标记 */
		public static final String MARK = "mark";
		/** 答案 */
		public static final String ANSWER = "answer";
	}

	public static class GradeTable {
		/** id */
		public static final String ID = "_id";
		/** 表名 */
		public static final String TABLE_NAME = "grade";
		/** 时间 */
		public static final String TIME = "time";
		/** 难度 */
		public static final String NAME = "name";
		/** 成绩 */
		public static final String GRADE = "grade";
	}

}
