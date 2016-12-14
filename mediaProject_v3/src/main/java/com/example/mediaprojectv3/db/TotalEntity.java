package com.example.mediaprojectv3.db;

import android.provider.BaseColumns;

public class TotalEntity implements BaseColumns {
	final public static String TABLE_NAME = "total";

	final public static String TOTALTYPE = "totaltype";
	final public static String NEED = "need";
	final public static String DONE = "done";

	/** BaseColumns �w�]�� _ID�B_COUNT �G�� */
	final public static String CREAT_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TOTALTYPE
			+ " CHAR, " + NEED + " CHAR, " + DONE + " CHAR);";

	final public static String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;
}
