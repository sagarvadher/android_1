package com.example.sagar.newsapp.Utilities;
import com.example.sagar.newsapp.MainActivity;

import android.app.job.JobParameters;
import android.os.AsyncTask;
import android.widget.Toast;



/**
 * Created by sagar on 7/26/17.
 */

public class NewsJob extends com.firebase.jobdispatcher.JobService{
    AsyncTask BackgroundTask;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        BackgroundTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                Toast.makeText(NewsJob.this, "News Refreshed", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }
            @Override
            protected Object doInBackground(Object[] params) {
                RefreshNews.refreshNews(NewsJob.this);
                return null;
            }
            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
                super.onPostExecute(o);
            }
        };
        BackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}