package com.example.mediaprojectv3.db;


import android.content.Context ;
import android.database.sqlite.SQLiteDatabase ;
import android.database.sqlite.SQLiteOpenHelper ;

public class DBHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "mymentor.db" ; // ��Ʈw���W�r
    private final static int DATABASE_VERSION = 1 ; // �]�w��Ʈw������

    public DBHelper( Context context ) {
        super( context, DB_NAME, null, DATABASE_VERSION ) ;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( TotalEntity.CREAT_TABLE ) ;
        db.execSQL( SimpleEntity.CREAT_TABLE ) ;
        db.execSQL( GeneralObligatoryEntity.CREAT_TABLE ) ;
        db.execSQL( GeneralElectiveEntity.CREAT_TABLE ) ;
        db.execSQL( DepartmentObligatoryEntity.CREAT_TABLE ) ;
        db.execSQL( DepartmentElectiveEntity.CREAT_TABLE ) ;
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( TotalEntity.DROP_TABLE ) ;
        db.execSQL( SimpleEntity.DROP_TABLE ) ;
        db.execSQL( GeneralObligatoryEntity.DROP_TABLE ) ;
        db.execSQL( GeneralElectiveEntity.DROP_TABLE ) ;
        db.execSQL( DepartmentObligatoryEntity.DROP_TABLE ) ;
        db.execSQL( DepartmentElectiveEntity.DROP_TABLE ) ;
        onCreate( db ) ;
    }

    @Override
    public void onOpen( SQLiteDatabase db ) {
        super.onOpen( db ) ;
        /** ������}�Ҹ�Ʈw�ɻݭn�i�檺�u�@*/
    }

}
