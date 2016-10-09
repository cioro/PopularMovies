package com.example.rmolina16.popularmovies;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    private MovieAdapter adapter;
    private ProgressDialog progressDialog;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.movies_fragment, container, false);
        initViews(rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MoviePosterFetcher moviePosterFetcher = new MoviePosterFetcher(progressDialog, adapter);

        if (item.getItemId() == R.id.menu_popular) {
            moviePosterFetcher.execute("popular");
        } else if (item.getItemId() == R.id.menu_top_rated) {
            moviePosterFetcher.execute("top_rated");

        } else {
            return super.onContextItemSelected(item);
        }
        return true;
    }

    private void initViews(View rootView) {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MovieAdapter(this, new ArrayList<Movie>());
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading data");
        progressDialog.show();

        MoviePosterFetcher moviesFetcher = new MoviePosterFetcher(progressDialog, adapter);
        moviesFetcher.execute("popular");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
