package com.example.sagar.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MainActivity extends AppCompatActivity {


    private TextView showNewsDataView;
    private RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    private ProgressBar bar;
    public ArrayList<NewsItem> newsList;
    private static final String TAG ="MainActivity" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ;
        newsList = new ArrayList<NewsItem>();
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        adapter=new NewsAdapter(newsList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        bar=(ProgressBar)findViewById(R.id.progressBar);

        fetchURL();


    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.newser, menu);

        return  true;
    }



    public  boolean onOptionsItemSelected(MenuItem list)
    {
        int whenClicked = list.getItemId();
        if(whenClicked == R.id.action_refresh)
        {
            recyclerView.setAdapter(null);
            fetchURL();

            return true;
        }
        return super.onOptionsItemSelected(list);
    }

    private void fetchURL(){
        News proc=new News();

        URL url = NetworkUtils.buildUrl();
        proc.execute(url);
    }


    public class News extends AsyncTask<URL, Void, ArrayList<NewsItem>> {



        protected void onPreExecute()
        {
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
        }




        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params) {

            URL url=NetworkUtils.buildUrl();

            try {
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                newsList = NetworkUtils.parseJSON(json);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return newsList;

        }



        protected void onPostExecute(final ArrayList <NewsItem> NewsArrayList)
        {
            bar.setVisibility(View.GONE);
            super.onPostExecute(NewsArrayList);
            adapter.notifyDataSetChanged();

            if(NewsArrayList!= null)
            {
                NewsAdapter adapter = new NewsAdapter(NewsArrayList, new NewsAdapter.ItemClickListener()
                {
                    @Override
                    public void onItemClick(int clickedItemIndex){
                        String url = NewsArrayList.get(clickedItemIndex).getUrl();
                        Log.d(TAG, String.format("URL %s", url));
                        openWebPAge(url);
                    }
                });
                recyclerView.setAdapter(adapter);
            }



        }
    }
    public void openWebPAge(String url){
        Uri webpage=Uri.parse(url);
        Intent intent =new Intent(Intent.ACTION_VIEW, webpage);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }

    }
}
