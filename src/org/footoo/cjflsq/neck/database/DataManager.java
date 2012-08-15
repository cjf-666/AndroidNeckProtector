package org.footoo.cjflsq.neck.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class DataManager {
	
	private static final DataManager theSingleton = new DataManager();

	private DatabaseHelper dbHelper = new DatabaseHelper();

	private Time start = new Time();
	
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
		cursor = db.query(name + "_length", null, null, null, null, null, null);
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
		
		cv.put("a0_5", cursor.getLong(1));
		cv.put("a5_10", cursor.getLong(2));
		cv.put("a10_30", cursor.getLong(3));
		cv.put("a30_INF", cursor.getLong(4));
		
		cursor.close();
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

			Cursor cursor = db.query(DatabaseMetadata.TABLE_DETAIL_NAME, col, null, null, null,
					null, null);
			if (cursor != null && cursor.moveToFirst() != false) {
				
				long time = Long.parseLong(cursor.getString(cursor.getColumnIndex(col[0])));
				long tmp = end.toMillis(true) - begin.toMillis(true);
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000 + time);
				db.update(DatabaseMetadata.TABLE_DETAIL_NAME, cv, null, null);
			} else {
				long tmp = end.toMillis(true) - begin.toMillis(true);
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000);
				db.insert(DatabaseMetadata.TABLE_DETAIL_NAME, null, cv);
			}
			
			cursor = db.query(DatabaseMetadata.TABLE_STATISTICS_TIME_NAME, col, null, null, null,
					null, null);
			if (cursor != null && cursor.moveToFirst() != false) {
				long time = Long.parseLong(cursor.getString(cursor.getColumnIndex(col[0])));
				long tmp = end.toMillis(true) - begin.toMillis(true);
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000 + time);
				db.update(DatabaseMetadata.TABLE_STATISTICS_TIME_NAME, cv, null, null);
				
			} else {
				long tmp = end.toMillis(true) - begin.toMillis(true);
				ContentValues cv = new ContentValues();
				cv.put(col[0], tmp / 1000);
				db.insert(DatabaseMetadata.TABLE_STATISTICS_TIME_NAME, null, cv);
			}
			
			if (cursor != null)
				cursor.close();
			
			db.close();
		} else {
			int sep = (int)(end.hour / 2) * 2;
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
		return getStat(DatabaseMetadata.TABLE_DETAIL_NAME);
	}
	
	public ContentValues getStat(){
		return getStat(DatabaseMetadata.TABLE_STATISTICS_TIME_NAME);
	}
	
	public long getHistory(Time date){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		String[] col = new String[]{"date"};
		String[] d = new String[]{String.valueOf(date.year)+date.month+date.monthDay};
		Cursor cursor = db.query(DatabaseMetadata.TABLE_HISTORY_NAME, col, "date=?", d, null, null, null);
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
