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
	 * �ر����ݿ�
	 */
	public void close() {
		db.close();
	}

	/**
	 * �����ݿ�
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
	 * ���ӱ�������
	 * 
	 * 
	 * @return long ������������ʾ���ӳɹ�����֮���ɹ�
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
	 * ɾ�����еļ�¼
	 * 
	 * @param whereClause
	 *            ɾ������ �磺( id>? and time>?)
	 * @param whereArgs
	 *            ������Ĳ��� �����滻"?" ��1�������������1���ʺţ���2�������������2���ʺţ���������......
	 * @return ����ɾ�������� Ҳ������Ϊ�ж�ֵ��������������ʾɾ���ɹ�����֮���ɹ�
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
	 * ���ұ��м�¼
	 * 
	 * @return Cursor
	 */
	public Cursor searchGrade() {
		/**
		 * ��ѯ����
		 * 
		 * @param table
		 *            ����
		 * @param columns
		 *            Ҫ��ѯ������
		 * @param selection
		 *            ��ѯ���� �磺( id=?)
		 * @param selectionArgs
		 *            ������Ĳ����������滻"?"
		 * @param orderBy
		 *            ���� �磺id desc
		 * @return ����Cursor
		 */
		Cursor c = db.query(Constants.GradeTable.TABLE_NAME, new String[] {
				Constants.GradeTable.ID, Constants.GradeTable.TIME,
				Constants.GradeTable.NAME, Constants.GradeTable.GRADE }, null,
				null, null, null, null);
		return c;
	}
}
