package com.example.sagar.newsapp.Utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sagar.newsapp.Data.DBHelper;
import com.example.sagar.newsapp.Data.DatabaseUtils;
import com.example.sagar.newsapp.Data.NewsItem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagar on 7/26/17.
 */

public class RefreshNews {
public static final String ACTION_REFRESH="refresh";
    public static void refreshNews(Context context){
        URL url=NetworkUtility.buildUrl();
        SQLiteDatabase db=new DBHelper(context).getWritableDatabase();
        try{
            DatabaseUtils.deleteAll(db);
            String json= NetworkUtility.getResponseFromURL(url);
            List<NewsItem> result = NetworkUtility.parseJSON(json);
            DatabaseUtils.updatenewDB(db, (ArrayList<NewsItem>) result);
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }
}
