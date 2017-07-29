package com.example.sagar.newsapp;

/**
 * Created by sagar on 6/25/17.
 */
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagar.newsapp.Data.Contract;
import com.squareup.picasso.Picasso;




public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ItemClickListener listener;
    private Context context;
    private Cursor cursor;

    NewsAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_rows;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
        cursor.moveToPosition(position);

        // Setting Data to TextViews
        holder.newsTitle.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_TITLE)));
        holder.newsTime.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_AUTHOR))
                + " " + cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COMLUMN_NAME_PUBLISHED_AT)));
        holder.newsDescription.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION)));

        // Adding Image using Picasso
        String imageURL = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_URL_TO_IMAGE));
        Picasso.with(context).load(imageURL).into(holder.newsImageView);
    }

    @Override
    public int getItemCount() {

        return cursor.getCount();
    }


    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView newsTitle;
        TextView newsDescription;
        TextView newsTime;
        ImageView newsImageView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            newsTitle = (TextView) itemView.findViewById(R.id.newsTitle);
            newsDescription = (TextView) itemView.findViewById(R.id.newsDescription);
            newsTime = (TextView) itemView.findViewById(R.id.newsPublishedAt);
            newsImageView= (ImageView) itemView.findViewById(R.id.newsImage);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onItemClick(cursor, position);
        }
    }

}