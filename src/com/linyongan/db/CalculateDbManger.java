package com.linyongan.db;


import com.linyongan.config.Constants;
import com.linyongan.model.Calculate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CalculateDbManger {

	private static final String TAG = "CalculateDbManger";
	private static SQLiteDatabase db;
	private static DBHelper helper;

	public CalculateDbManger(Context c) {
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
	 * ���
	 * 
	 * @return long ������������ʾ���ӳɹ�����֮���ɹ�
	 */
	public long add(Calculate calculate) {
		try {
			ContentValues values = new ContentValues();
			values.put(Constants.CalculateTable.ID, calculate.getId());
			values.put(Constants.CalculateTable.Y, calculate.getY());
			return db.insert(Constants.CalculateTable.TABLE_NAME, null, values);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return -1;
		}

	}

	/**
	 * ���ұ��м�¼
	 * 
	 * @return Cursor
	 */
	public Cursor search(String x) {
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
		Cursor c = db.query(Constants.CalculateTable.TABLE_NAME,
				new String[] { Constants.CalculateTable.Y },
				Constants.CalculateTable.ID + " =?", new String[] { x }, null, null, null);
		return c;
	}

}
