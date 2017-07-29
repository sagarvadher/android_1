package com.example.sagar.newsapp.Utilities;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import android.net.Uri;

import com.example.sagar.newsapp.Data.NewsItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by sagar on 7/26/17.
 */

public class NetworkUtility {
    private static final String NEWS_API_URL = "https://newsapi.org/v1/articles", format="json";
    private static final String source="the-next-web";
    static final String apikey="d35d9f873a5f4338a096b89a9b6cc403";
    static final String sortby="latest";
    static final String PARAM_SOURCE="source";
    static final String PARAM_API="apikey";
    static final String PARAM_SORT="sortBy";

    public static URL buildUrl()
    {
        Uri builtUri = Uri.parse(NEWS_API_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE,source)
                .appendQueryParameter(PARAM_SORT, sortby)
                .appendQueryParameter(PARAM_API,apikey).build();

        URL url=null;
        try{
            url=new URL(builtUri.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromURL(URL url) throws IOException{
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try{
            InputStream istream = conn.getInputStream();

            Scanner scanner=new Scanner(istream);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput)
            {
                return scanner.next();
            }
            else{
                return null;
            }


        }
        finally{
            conn.disconnect();
        }
    }
    public static List<NewsItem> parseJSON(String json)
    {
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        try{
            JSONObject root = new JSONObject(json);
            JSONArray articles = root.getJSONArray("articles");
            for(int i=0; i< articles.length(); i++)
            {
                JSONObject article=articles.getJSONObject(i);
                String url=article.getString("url");
                String title=article.getString ("title");
                String author=article.getString("author");
                String imageUrl=article.getString("urlToImage");
                String description=article.getString("description");
                String datetime=article.getString("publishedAt");
                newsItems.add(new NewsItem(url, title, description,imageUrl, author, datetime));

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return newsItems;
    }

}
