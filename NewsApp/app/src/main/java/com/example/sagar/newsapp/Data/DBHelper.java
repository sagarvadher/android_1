package com.example.sagar.newsapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sagar on 7/25/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "VadherS.db";
    //ToDo modifications: set name of DATABASE_NAME

    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String queryString ="CREATE TABLE "+Contract.TABLE_TODO.TABLE_NAME+ " ("+
                Contract.TABLE_TODO.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                Contract.TABLE_TODO.COLUMN_NAME_URL+" TEXT , "+
                Contract.TABLE_TODO.COLUMN_TITLE + " TEXT , " +
                Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION+" TEXT , " +
                Contract.TABLE_TODO.COLUMN_URL_TO_IMAGE+" TEXT , " +
                Contract.TABLE_TODO.COLUMN_NAME_AUTHOR+" TEXT , " +
                Contract.TABLE_TODO.COMLUMN_NAME_PUBLISHED_AT+" DATE "+
                ");";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table " + Contract.TABLE_NEWS.TABLE_NAME + " if exists;");
    }
}