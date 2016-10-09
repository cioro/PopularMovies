package com.example.rmolina16.popularmovies;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private ArrayList<Movie> movies;
    private Fragment fragment;

    MovieAdapter(Fragment fragment, ArrayList<Movie> android) {
        this.movies = android;
        this.fragment = fragment;
    }

    void update(ArrayList<Movie> movies) {
        this.movies = movies;
    }
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder viewHolder, int i) {

        Log.v(LOG_TAG, movies.get(i).getMovie_img_url());

        Glide.with(fragment)
                .load(movies.get(i).getMovie_img_url())
                .placeholder(R.color.colorPrimary)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_android;
        ViewHolder(View view) {
            super(view);

            img_android = (ImageView) view.findViewById(R.id.img_android);
        }
    }

}