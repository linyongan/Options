package com.linyongan.db;

import com.linyongan.config.Constants;
import com.linyongan.model.Grade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GradeDbManger {

	private static final String TAG = "GradeDbManger";
	private static SQLiteDatabase db;
	private static DBHelper helper;

	public GradeDbManger(Context c) {
		helper = new DBHelper(c, Constants.DATABASE_NAME, null,
				Constants.Version);
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		db.close();
	}

	/**
	 * 打开数据库
	 */
	public void open() throws SQLException {
		try {
			db = helper.getWritableDatabase();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			db = helper.getReadableDatabase();
		}

	}

	/**
	 * 增加表中数据
	 * 
	 * 
	 * @return long 如果是正数则表示增加成功，反之不成功
	 * 
	 */
	public long addGrade(Grade grade) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(Constants.GradeTable.TIME, grade.getTime());
			contentValues.put(Constants.GradeTable.NAME, grade.getName());
			contentValues.put(Constants.GradeTable.GRADE, grade.getGrade());

			return db.insert(Constants.GradeTable.TABLE_NAME, null,
					contentValues);

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return -1;
		}

	}

	/**
	 * 删除表中的记录
	 * 
	 * @param whereClause
	 *            删除条件 如：( id>? and time>?)
	 * @param whereArgs
	 *            条件里的参数 用来替换"?" 第1个参数，代表第1个问号；第2个参数，代表第2个问号；依此类推......
	 * @return 返回删除的条数 也可以作为判断值，如果是正数则表示删除成功，反之不成功
	 */
	public int delete(String time) {
		try {

			return db.delete(Constants.GradeTable.TABLE_NAME,
					Constants.GradeTable.TIME + " =?",
					new String[] { time });
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, e.getMessage());
			return -1;
		}
	}
	
	/**
	 * 查找表中记录
	 * 
	 * @return Cursor
	 */
	public Cursor searchGrade() {
		/**
		 * 查询数据
		 * 
		 * @param table
		 *            表名
		 * @param columns
		 *            要查询的列名
		 * @param selection
		 *            查询条件 如：( id=?)
		 * @param selectionArgs
		 *            条件里的参数，用来替换"?"
		 * @param orderBy
		 *            排序 如：id desc
		 * @return 返回Cursor
		 */
		Cursor c = db.query(Constants.GradeTable.TABLE_NAME, new String[] {
				Constants.GradeTable.ID, Constants.GradeTable.TIME,
				Constants.GradeTable.NAME, Constants.GradeTable.GRADE }, null,
				null, null, null, null);
		return c;
	}
}
