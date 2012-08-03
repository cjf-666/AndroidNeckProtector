package org.footoo.cjflsq.neck.database;

import org.footoo.cjflsq.neck.MyApplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;

public class DataManager {
	private static final String DB_NAME = "neck.db";

	private static final int DB_VERSION = 1;

	private static final String TABLE_DETAIL_NAME = "detail";
	
	private static final String TABLE_DETAIL_LENGTH_NAME = "detail_length";

	private static final String TABLE_STATISTICS_TIME_NAME = "stat";

	private static final String TABLE_STATISTICS_LENGTH_NAME = "stat_length";

	private static final String TABLE_HISTORY_NAME = "histroy";
	
	private static final DataManager theSingleton = new DataManager();

	private DatabaseHelper dbHelper = new DatabaseHelper();

	private Time start = new Time();
	// ----------------------------------------------
	class DatabaseHelper extends SQLiteOpenHelper {

		private static final String TABLE_DETAIL_CREATE = "CREATE TABLE "
				+ TABLE_DETAIL_NAME
				+ " ( _id INTEGER PRIMARY KEY, z0_2 TEXT, z2_4 TEXT, z4_6 TEXT"
				+ ", z6_8 TEXT, z8_10 TEXT, z10_12 TEXT, z12_14 TEXT, z14_16 TEXT"
				+ ", z16_18 TEXT, z18_20 TEXT, z20_22 TEXT, z22_24 TEXT)";

		private static final String TABLE_DETAIL_LENGTH_CREATE = "CREATE TABLE "
				+ TABLE_DETAIL_LENGTH_NAME
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
		start.set(0);
	}

	public static DataManager getInstance() {
		return theSingleton;
	}

	private ContentValues getStat(String name){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query(name, null, null, null, null, null, null);
		if (cursor == null)
		{
			db.close();
			return null;
		}
		
		if (cursor.moveToFirst() == false)
		{
			cursor.close();
			db.close();
			return null;
		}
		
		ContentValues cv = new ContentValues();
		for (int i = 0; i < 24; i+=2)
		{
			String title = "z" + i + "_" + (i+2);
			long value = cursor.getLong(i / 2 +1);
			cv.put(title, value);
		}	
		cursor.close();
		cursor = db.query(name, null, null, null, null, null, null);
		if (cursor == null)
		{
			db.close();
			return null;
		}	
		
		
		
		db.close();
		return cv;	
	}
	
	public void submitStartTime(Time begin){
		start = begin;
	}
	
	public void submitEndTime(Time end){
		if (start.toMillis(true) == 0)
			return;
		putData(start, end);
		start.set(0);
		return;
	}
	
	public void putData(Time begin, Time end) {
		if ((int)(end.hour / 2) == (int)(begin.hour / 2)) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			String[] col = new String[1];
			if (begin.hour % 2 == 0)
				col[0] = "z" + begin.hour + "_" + (begin.hour + 2);
			else
				col[0] = "z" + (begin.hour - 1) + "_"
						+ (begin.hour + 1);

			Cursor cursor = db.query(TABLE_DETAIL_NAME, col, null, null, null,
					null, null);
			if (cursor != null && cursor.moveToFirst() != false) {
				
				long time = Long.parseLong(cursor.getString(cursor.getColumnIndex(col[0])));
				long tmp = end.toMillis(true) - begin.toMillis(true);
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000 + time);
				db.update(TABLE_DETAIL_NAME, cv, null, null);
			} else {
				long tmp = end.toMillis(true) - begin.toMillis(true);
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000);
				db.insert(TABLE_DETAIL_NAME, null, cv);
			}
			
			cursor = db.query(TABLE_STATISTICS_TIME_NAME, col, null, null, null,
					null, null);
			if (cursor != null && cursor.moveToFirst() != false) {
				long time = Long.parseLong(cursor.getString(cursor.getColumnIndex(col[0])));
				long tmp = end.toMillis(true) - begin.toMillis(true);
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000 + time);
				db.update(TABLE_STATISTICS_TIME_NAME, cv, null, null);
				
			} else {
				long tmp = end.toMillis(true) - begin.toMillis(true);
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000);
				db.insert(TABLE_STATISTICS_TIME_NAME, null, cv);
			}
			
			if (cursor != null)
				cursor.close();
			
			db.close();
		} else {
			int sep = end.hour / 2;
			Time tmp = new Time(begin);
			tmp.hour = sep - 1;
			tmp.minute = 59;
			tmp.second = 59;
			putData(begin, tmp);
			
			tmp = new Time(end);
			tmp.hour = sep;
			tmp.minute = 0;
			tmp.second = 0;
			putData(tmp, end);
		}
	}
	
	public ContentValues getStatOfToday(){
		return getStat(TABLE_DETAIL_NAME);
	}
	
	public ContentValues getStat(){
		return getStat(TABLE_STATISTICS_TIME_NAME);
	}
	
	public long getHistory(Time date){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		String[] col = new String[]{"date"};
		String[] d = new String[]{String.valueOf(date.year)+date.month+date.monthDay};
		Cursor cursor = db.query(TABLE_HISTORY_NAME, col, "date=?", d, null, null, null);
		if (cursor == null || cursor.moveToFirst() == false)
		{
			db.close();
			return -1;
		}
		cursor.moveToFirst();
		long ret = cursor.getLong(2);
		cursor.close();
		db.close();
		return ret;
	}

}
