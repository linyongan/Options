package com.linyongan.db;

import com.linyongan.config.Constants;
import com.linyongan.model.Test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * @description 测试表的数据库管理类
 * 
 * */
public class TestDbManger {
	private final static String TAG = "TestDbManger";
	private static SQLiteDatabase db;
	private static DBHelper dbHelper;

	public TestDbManger(Context c) {
		dbHelper = new DBHelper(c, Constants.DATABASE_NAME, null,
				Constants.Version);
	}

	/**
	 * 关闭数据�?
	 * 
	 * */

	public void close() {
		db.close();
	}

	/**
	 * �?��数据�?
	 * */
	public void open() throws SQLiteException {

		try {
			db = dbHelper.getWritableDatabase();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, e.getMessage());
			db = dbHelper.getReadableDatabase();
		}

	}
	
	/**
	 * 通过试题ID，查找表中指定的�?��记录
	 * 
	 * @return Cursor
	 */
	public Cursor getTest(int id) {
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
		 *            条件里的参数，用来替�??"
		 * @param orderBy
		 *            排序 如：id desc
		 * @return 返回Cursor
		 */
		Cursor c = db.query(true, Constants.TestTable.TABLE_NAME, new String[] {
				Constants.TestTable.QUESTION, Constants.TestTable.OPTION1,
				Constants.TestTable.OPTION2, Constants.TestTable.OPTION3,
				Constants.TestTable.OPTION4, Constants.TestTable.MARK,
				Constants.TestTable.ANSWER }, Constants.TestTable.ID + " =?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		return c;
	}

	/**
	 * 通过试题ID，查找表中指定的�?��记录
	 * 
	 * @return Cursor
	 */
	public Cursor getCollect() {
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
		 *            条件里的参数，用来替�??"
		 * @param orderBy
		 *            排序 如：id desc
		 * @return 返回Cursor
		 */
		Cursor c = db.query(true, Constants.TestTable.TABLE_NAME, new String[] {
				Constants.TestTable.ID, Constants.TestTable.QUESTION,
				Constants.TestTable.ANSWER }, Constants.TestTable.MARK + " =?",
				new String[] { String.valueOf(1) }, null, null,
				Constants.TestTable.ID + " ASC", null);
		return c;
	}

	/**
	 * 通过试题ID，查找表中指定的�?��记录
	 * 
	 * @return Cursor
	 */
	public Cursor getMark(int id) {
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
		 *            条件里的参数，用来替�??"
		 * @param orderBy
		 *            排序 如：id desc
		 * @return 返回Cursor
		 */
		Cursor c = db.query(true, Constants.TestTable.TABLE_NAME,
				new String[] { Constants.TestTable.MARK },
				Constants.TestTable.ID + " =?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		return c;
	}

	public int updateMark(Test test) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(Constants.TestTable.MARK, test.getMark());
			return db.update(Constants.TestTable.TABLE_NAME, contentValues,
					"_id = ?", new String[] { String.valueOf(test.get_id()) });
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return -1;
		}

	}

}
