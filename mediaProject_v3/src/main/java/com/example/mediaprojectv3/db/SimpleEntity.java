package com.example.mediaprojectv3.db;

import android.provider.BaseColumns;

public class SimpleEntity implements BaseColumns {
	final public static String TABLE_NAME = "simple";

	final public static String NEEDNAME = "needname";
	final public static String NEEDCREDIT = "needcredit";
	final public static String SEMESTER = "semester";
	final public static String DONENAME = "donename";
	final public static String DONECREDIT = "donecredit";
	final public static String SCORE = "score";
	final public static String TYPE = "type"; // 檢查未修,已修,正在修

	final public static String CREAT_TABLE = "CREATE TABLE " + TABLE_NAME
			+ " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NEEDNAME
			+ " CHAR, " + NEEDCREDIT + " CHAR, " + SEMESTER + " CHAR, "
			+ DONENAME + " CHAR, " + DONECREDIT + " CHAR, " + SCORE
			+ " CHAR, " + TYPE + " CHAR);";

	final public static String DROP_TABLE = "DROP TABLE IF EXISTS "
			+ TABLE_NAME;
}
