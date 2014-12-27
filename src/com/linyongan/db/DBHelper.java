package com.linyongan.db;

import com.linyongan.config.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DBHelper";
	private final String CALCULATE_TABLE = "create table if not exists "
			+ Constants.CalculateTable.TABLE_NAME + " ("
			+ Constants.CalculateTable.ID + " text , " + Constants.CalculateTable.Y
			+ " text)";
	private final String NOUN_TABLE = "create table if not exists "
			+ Constants.NounTable.TABLE_NAME + " (" + Constants.NounTable.NAME
			+ " text, " + Constants.NounTable.VALUE + " text)";
	private final String QUESTION_TABLE = "create table if not exists "
			+ Constants.QuestionTable.TABLE_NAME + " ("
			+ Constants.QuestionTable.QUESTION + " text, "
			+ Constants.QuestionTable.ANSWER + " text)";
	private final String TEST_TABLE = "create table if not exists "
			+ Constants.TestTable.TABLE_NAME + " (" + Constants.TestTable.ID
			+ " integer primary key, " + Constants.TestTable.QUESTION
			+ " text, " + Constants.TestTable.OPTION1 + " text, "
			+ Constants.TestTable.OPTION2 + " text, "
			+ Constants.TestTable.OPTION3 + " text, "
			+ Constants.TestTable.OPTION4 + " text, "
			+ Constants.TestTable.MARK + " text, " + Constants.TestTable.ANSWER
			+ " text)";
	private final String GRADE_TABLE = "create table if not exists "
			+ Constants.GradeTable.TABLE_NAME + " (" + Constants.GradeTable.ID
			+ " integer primary key, " + Constants.GradeTable.TIME + " text, "
			+ Constants.GradeTable.NAME + " text, "
			+ Constants.GradeTable.GRADE + " integer)";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(CALCULATE_TABLE);
			db.execSQL(NOUN_TABLE);
			db.execSQL(QUESTION_TABLE);
			db.execSQL(TEST_TABLE);
			db.execSQL(GRADE_TABLE);
			Log.i(TAG, "创建数据库成功！！！！！");
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
