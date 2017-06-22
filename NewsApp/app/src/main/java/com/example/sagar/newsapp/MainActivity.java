package com.example.sagar.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sagar.newsapp.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {


    private TextView showNewsDataView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        showNewsDataView = (TextView)findViewById(R.id.news_data_fetch);
        fetchData();


    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.newser, menu);
        return true;

    }

    protected void fetchData()
    {
        News work = new News();

        URL news = NetworkUtils.buildUrl();
        work.execute(news);


    }

    public  boolean onOptionsItemSelected(MenuItem list)
    {
        int whenClicked = list.getItemId();
        if(whenClicked == R.id.action_refresh)
        {
            showNewsDataView.setText("");
            fetchData();

            return true;
        }
        return super.onOptionsItemSelected(list);
    }


    public class News extends AsyncTask<URL, Void, String> {



        protected void onPreExecute(Menu menu)
        {
        super.onPreExecute();
        showNewsDataView.setVisibility(View.VISIBLE);
        }




        @Override
        protected String doInBackground(URL... params) {


            try{
                return NetworkUtils.getResponseFromHttpUrl(params[0]);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }



        protected void onPostExecute(String token)
        {
            super.onPostExecute(token);
            showNewsDataView.setText(token);

            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
