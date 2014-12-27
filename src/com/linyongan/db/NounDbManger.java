package com.linyongan.db;

import com.linyongan.config.Constants;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NounDbManger {

	private static final String TAG = "NounDbManger";
	private static SQLiteDatabase db;
	private static DBHelper helper;

	public NounDbManger(Context c) {
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
		Cursor c = db.query(Constants.NounTable.TABLE_NAME,
				new String[] { Constants.NounTable.VALUE },
				Constants.NounTable.NAME + " =?", new String[] { x }, null,
				null, null);
		return c;
	}

}
