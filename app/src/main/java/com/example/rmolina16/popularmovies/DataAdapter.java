package com.example.rmolina16.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<String> movieURLS;

    DataAdapter(ArrayList<String> movieURLs) {
        this.movieURLS = movieURLs;
    }

    void set(ArrayList<String> movieURLs) {
        this.movieURLS = movieURLs;
    }

    void clear() {
        this.movieURLS = null;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_country.setText(movieURLS.get(i));
    }

    @Override
    public int getItemCount() {
        return movieURLS.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_country;

        ViewHolder(View view) {
            super(view);

            tv_country = (TextView) view.findViewById(R.id.movie_url);
        }
    }
}