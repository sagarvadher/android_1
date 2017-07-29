package com.example.sagar.newsapp.Data;

import android.provider.BaseColumns;

/**
 * Created by sagar on 7/26/17.
 */

    public class Contract {

        public static class TABLE_TODO implements BaseColumns {
            public static final String TABLE_NAME = "todoitems";

            public static final String COLUMN_NAME_DESCRIPTION = "description";

            public static final String COLUMN_NAME_CATEGORY = "category";
            public static final String COLUMN_NAME_URL= "url";
            public static final String COLUMN_TITLE = "title";
            public static final String COMLUMN_NAME_PUBLISHED_AT = "publishedAt";
            public static final String COLUMN_URL_TO_IMAGE = "urlToImage";
            public static final String COLUMN_NAME_AUTHOR = "author";
            public static final String COLUMN_ID = "id";

        }
    }
