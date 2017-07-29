package com.example.sagar.newsapp.Data;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.ArrayList;

/**
 * Created by sagar on 7/26/17.
 */

public class DatabaseUtils {
    public static Cursor getAll(SQLiteDatabase db){
        Cursor cursor = db.query(
            Contract.TABLE_TODO.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            Contract.TABLE_TODO.COMLUMN_NAME_PUBLISHED_AT);
        return cursor;

        }

        public static void updatenewDB(SQLiteDatabase db, ArrayList<NewsItem> newsitems) throws Exception {

            db.beginTransaction();
            try{
                for(NewsItem newsItem: newsitems){

                    ContentValues c= new ContentValues();
                    c.put(Contract.TABLE_TODO.COLUMN_NAME_URL, newsItem.getUrl());
                    c.put(Contract.TABLE_TODO.COLUMN_TITLE, newsItem.getTitle());
                    c.put(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION, newsItem.getDescription());
                    c.put(Contract.TABLE_TODO.COLUMN_URL_TO_IMAGE, newsItem.getUrlToImage());
                    c.put(Contract.TABLE_TODO.COLUMN_NAME_AUTHOR,newsItem.getAuthor());
                    c.put(Contract.TABLE_TODO.COMLUMN_NAME_PUBLISHED_AT, newsItem.getPublishedAt());
                    db.insert(Contract.TABLE_TODO.TABLE_NAME,null, c);

                }
                db.setTransactionSuccessful();

            }
            finally {
                db.endTransaction();
                db.close();
            }
        }
        public static void deleteAll(SQLiteDatabase db){
            db.delete(Contract.TABLE_TODO.TABLE_NAME, null,  null);
        }
    }


