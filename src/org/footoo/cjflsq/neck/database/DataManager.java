package org.footoo.cjflsq.neck.database;

import java.util.Date;

import org.footoo.cjflsq.neck.MyApplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataManager {
	private static final String DB_NAME = "neck.db";

	private static final int DB_VERSION = 1;

	private static final String TABLE_DETAIL_NAME = "detail";
	
	private static final String TABLE_DETAIL_LENGTH_NAME = "detail_length";

	private static final String TABLE_STATISTICS_TIME_NAME = "stat_time";

	private static final String TABLE_STATISTICS_LENGTH_NAME = "stat_length";

	private static final String TABLE_HISTORY_NAME = "histroy";
	
	private static final DataManager theSingleton = new DataManager();

	private DatabaseHelper dbHelper = new DatabaseHelper();

	// ----------------------------------------------
	class DatabaseHelper extends SQLiteOpenHelper {

		private static final String TABLE_DETAIL_CREATE = "CREATE TABLE "
				+ TABLE_DETAIL_NAME
				+ " ( _id INTEGER PRIMARY KEY, z0_2 TEXT, z2_4 TEXT, z4_6 TEXT"
				+ ", z6_8 TEXT, z8_10 TEXT, z10_12 TEXT, z12_14 TEXT, z14_16 TEXT"
				+ ", z16_18 TEXT, z18_20 TEXT, z20_22 TEXT, z22_24 TEXT)";

		private static final String TABLE_DETAIL_LENGTH_CREATE = "CREATE TABLE "
				+ TABLE_STATISTICS_LENGTH_NAME
				+ " ( _id INTEGER PRIMARY KEY, a0_5 TEXT, a5_10 TEXT, a10_30 TEXT, a30_INF TEXT)";
		
		private static final String TABLE_STATISTICS_TIME_CREATE = "CREATE TABLE "
				+ TABLE_STATISTICS_TIME_NAME
				+ " ( _id INTEGER PRIMARY KEY, z0_2 TEXT, z2_4 TEXT, z4_6 TEXT"
				+ ", z6_8 TEXT, z8_10 TEXT, z10_12 TEXT, z12_14 TEXT, z14_16 TEXT"
				+ ", z16_18 TEXT, z18_20 TEXT, z20_22 TEXT, z22_24 TEXT)";

		private static final String TABLE_STATISTICS_LENGTH_CREATE = "CREATE TABLE "
				+ TABLE_STATISTICS_LENGTH_NAME
				+ " ( _id INTEGER PRIMARY KEY, a0_5 TEXT, a5_10 TEXT, a10_30 TEXT, a30_INF TEXT)";

		private static final String TABLE_HISTORY_CREATE = "CREATE TABLE "
				+ TABLE_HISTORY_NAME
				+ "( _id INTEGER PRIMARY KEY, date TEXT, time TEXT)";

		public DatabaseHelper() {
			super(MyApplication.getAppContext(), DB_NAME, null, DB_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(TABLE_DETAIL_CREATE);
			db.execSQL(TABLE_DETAIL_LENGTH_CREATE);
			db.execSQL(TABLE_STATISTICS_TIME_CREATE);
			db.execSQL(TABLE_STATISTICS_LENGTH_CREATE);
			db.execSQL(TABLE_HISTORY_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub

		}

	}

	// ----------------------------------------------
	private DataManager() {
	}

	public static DataManager getInstance() {
		return theSingleton;
	}

	private ContentValues getStat(String name){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(name, null, null, null, null, null, null);
		if (cursor == null || cursor.moveToFirst() == false)
		{
			db.close();
			return null;
		}
		ContentValues cv = new ContentValues();
		for (int i = 0; i < 24; i+=2)
		{
			String title = "z" + i + "_" + (i+2);
			long value = cursor.getLong(i+1);
			cv.put(title, value);
		}	
		return cv;	
	}
	
	public void putData(Date begin, Date end) {
		if ((int)(end.getHours() / 2) == (int)(begin.getHours() / 2)) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			String[] col = new String[1];
			if (begin.getHours() % 2 == 0)
				col[0] = "z" + begin.getHours() + "_" + (begin.getHours() + 2);
			else
				col[0] = "z" + (begin.getHours() - 1) + "_"
						+ (begin.getHours() + 1);

			Cursor cursor = db.query(TABLE_DETAIL_NAME, col, null, null, null,
					null, null);
			if (cursor != null && cursor.moveToFirst() != false) {
				
				long time = Long.parseLong(cursor.getString(cursor.getColumnIndex(col[0])));
				long tmp = end.getTime() - begin.getTime();
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000 + time);
				db.update(TABLE_DETAIL_NAME, cv, null, null);
			} else {
				long tmp = end.getTime() - begin.getTime();
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000);
				db.insert(TABLE_DETAIL_NAME, null, cv);
			}
			
			cursor = db.query(TABLE_STATISTICS_TIME_NAME, col, null, null, null,
					null, null);
			if (cursor != null && cursor.moveToFirst() != false) {
				long time = Long.parseLong(cursor.getString(cursor.getColumnIndex(col[0])));
				long tmp = end.getTime() - begin.getTime();
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000 + time);
				db.update(TABLE_STATISTICS_TIME_NAME, cv, null, null);
				
			} else {
				long tmp = end.getTime() - begin.getTime();
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000);
				db.insert(TABLE_STATISTICS_TIME_NAME, null, cv);
			}
			
			if (cursor != null)
				cursor.close();
			
			db.close();
		} else {
			int sep = end.getHours() / 2;
			Date tmp = new Date(begin.getTime());
			tmp.setHours(sep - 1);
			tmp.setMinutes(59);
			tmp.setSeconds(59);
			putData(begin, tmp);
			
			tmp = new Date(end.getTime());
			tmp.setHours(sep);
			tmp.setMinutes(0);
			tmp.setSeconds(0);
			putData(tmp, end);
		}
	}
	
	public ContentValues getStatOfToday(){
		return getStat(TABLE_DETAIL_NAME);
	}
	
	public ContentValues getStat(){
		return getStat(TABLE_STATISTICS_TIME_NAME);
	}

}
