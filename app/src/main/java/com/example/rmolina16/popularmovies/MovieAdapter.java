package com.example.rmolina16.popularmovies;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private List<Movie> movies;
    private Context context;

    MovieAdapter(Context context, List<Movie> android) {
        this.movies = android;
        this.context = context;
    }

    void update(List<Movie> movies) {
        if (movies == this.movies) {
            return;
        }

        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder viewHolder, int i) {

        Log.v(LOG_TAG, movies.get(i).getMovie_img_url());

        Glide.with(context)
                .load(movies.get(i).getMovie_img_url())
                .placeholder(R.color.colorPrimary)
                .dontAnimate()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(viewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;

        ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.img_android);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Snackbar.make(v,"Hello Beautiful",Snackbar.LENGTH_SHORT).show();
            this.getAdapterPosition();
        }

    }

}

//Todo think of moving the click listener to the adapter and create methods to update viewholder
//Todo move viewholder outside of movideAdapter
//TODO pass the context instead of the fragment