package com.example.rmolina16.popularmovies;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MovieDetailFragment extends Fragment {

    private Movie movie;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer movie_id = getActivity().getIntent().getIntExtra(MovieDetailActivity.MOVIE_ID,0);
        //Load movie object with data.
    }

    // if not got movie data fetch it        (model)
    // if fetching show loading indicator  (UI)
    // do the fetch                        (model)
    // when fetched update some persistent store (in-memory, database) (model)
    // when fetched update list            (UI)


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Integer movie_id = getActivity().getIntent().getIntExtra(MovieDetailActivity.MOVIE_ID,0);
        View rootView = inflater.inflate(R.layout.movie_detail_fragment, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.movie_title);
        textView.setText(movie_id.toString());

        return rootView;
    }

}
