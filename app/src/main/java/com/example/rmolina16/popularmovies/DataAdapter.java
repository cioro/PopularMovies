package com.example.rmolina16.popularmovies;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private static final String LOG_TAG = DataAdapter.class.getSimpleName();
    private ArrayList<Movie> movies;
    private Context context;

    public DataAdapter(Context context,ArrayList<Movie> android) {
        this.movies = android;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        Log.v(LOG_TAG, movies.get(i).getMovie_img_url());

        Glide.with(context).load(movies.get(i).getMovie_img_url()).error(R.drawable.ic_camera_black_24dp).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_android;
        public ViewHolder(View view) {
            super(view);

            img_android = (ImageView) view.findViewById(R.id.img_android);
        }
    }

}