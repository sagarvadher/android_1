package com.example.sagar.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sagar.newsapp.Data.Contract;
import com.example.sagar.newsapp.Data.DBHelper;
import com.example.sagar.newsapp.Data.DatabaseUtils;
import com.example.sagar.newsapp.Utilities.RefreshNews;
import com.example.sagar.newsapp.Utilities.ScheduleUtilities;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends  AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>,
        NewsAdapter.ItemClickListener{


    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Cursor cursor;
    private SQLiteDatabase db;
    private static final int NEWS_LOADER = 1;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isfirst", true);

        if (isFirst) {
            initialload();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isfirst", false);
            editor.commit();
        }
        ScheduleUtilities.scheduleRefresh(this);

    }
    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        newsAdapter = new NewsAdapter(cursor, this);
        recyclerView.setAdapter(newsAdapter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }

    public void initialload() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.newser, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_refresh) {
            initialload();
        }
        return true;
    }

    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public Void loadInBackground() {
                RefreshNews.refreshNews(MainActivity.this);
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        progressBar.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DatabaseUtils.getAll(db);
        newsAdapter = new NewsAdapter(cursor, this);
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {
    }

    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_URL));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


}
