package org.footoo.cjflsq.neck.database;

import org.footoo.cjflsq.neck.MyApplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TABLE_DETAIL_CREATE = "CREATE TABLE "
			+ DatabaseMetadata.TABLE_DETAIL_NAME
			+ " ( _id INTEGER PRIMARY KEY, z0_2 TEXT, z2_4 TEXT, z4_6 TEXT"
			+ ", z6_8 TEXT, z8_10 TEXT, z10_12 TEXT, z12_14 TEXT, z14_16 TEXT"
			+ ", z16_18 TEXT, z18_20 TEXT, z20_22 TEXT, z22_24 TEXT)";

	private static final String TABLE_DETAIL_LENGTH_CREATE = "CREATE TABLE "
			+ DatabaseMetadata.TABLE_DETAIL_LENGTH_NAME
			+ " ( _id INTEGER PRIMARY KEY, a0_5 TEXT, a5_10 TEXT, a10_30 TEXT, a30_INF TEXT)";
		
	private static final String TABLE_STATISTICS_TIME_CREATE = "CREATE TABLE "
			+ DatabaseMetadata.TABLE_STATISTICS_TIME_NAME
			+ " ( _id INTEGER PRIMARY KEY, z0_2 TEXT, z2_4 TEXT, z4_6 TEXT"
			+ ", z6_8 TEXT, z8_10 TEXT, z10_12 TEXT, z12_14 TEXT, z14_16 TEXT"
			+ ", z16_18 TEXT, z18_20 TEXT, z20_22 TEXT, z22_24 TEXT)";

	private static final String TABLE_STATISTICS_LENGTH_CREATE = "CREATE TABLE "
			+ DatabaseMetadata.TABLE_STATISTICS_LENGTH_NAME
			+ " ( _id INTEGER PRIMARY KEY, a0_5 TEXT, a5_10 TEXT, a10_30 TEXT, a30_INF TEXT)";

	private static final String TABLE_HISTORY_CREATE = "CREATE TABLE "
			+ DatabaseMetadata.TABLE_HISTORY_NAME
			+ "( _id INTEGER PRIMARY KEY, date TEXT, time TEXT)";

	public DatabaseHelper() {
		super(MyApplication.getAppContext(), 
				DatabaseMetadata.DB_NAME, null, DatabaseMetadata.DB_VERSION);
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