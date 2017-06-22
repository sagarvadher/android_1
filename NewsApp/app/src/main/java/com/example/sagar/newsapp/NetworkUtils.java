package com.example.sagar.newsapp;




import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by sagar on 6/20/17.
 */

public class NetworkUtils {


    private static final String api = "https://newsapi.org/v1/articles";


    private static final String source_param = "source";
    private static final String source = "the-next-web";
    private static final String sortby_param = "source";
    private static final String sortBy = "latest";
    // private static final String apiKey =
    private static final String api_key_param = "apiKey";
    private static final String apiKey = "d35d9f873a5f4338a096b89a9b6cc403";




    public static URL buildUrl() {

        Uri builtUri = Uri.parse(api).buildUpon()
                .appendQueryParameter(source_param, source)
                .appendQueryParameter(sortby_param, sortBy)
                .appendQueryParameter(api_key_param, apiKey)
                .build();
        URL url=null;
        try
        {
            url = new URL(builtUri.toString());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return url;

    }



    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            return (scanner.hasNext() ? scanner.next() : null);
        }


        finally {
            urlConnection.disconnect();
        }
    }


}
