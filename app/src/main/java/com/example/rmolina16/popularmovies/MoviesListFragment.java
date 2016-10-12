package com.example.rmolina16.popularmovies;

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

import java.util.List;

public class MoviesListFragment extends Fragment {

    private MovieAdapter adapter;
    private RecyclerView recyclerView;

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

    private void initViews(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MovieAdapter(getContext(), MovieListModel.getSingleton().getDefaultMovies());
        recyclerView.setAdapter(adapter);

    }

    // if not got movies fetch them        (model)
    // if fetching show loading indicator  (UI)
    // do the fetch                        (model)
    // when fetched update some persistent store (in-memory, database) (model)
    // when fetched update list            (UI)

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_popular) {
            MovieListModel.getSingleton().getMovies(MovieListModel.Type.POPULAR);
        } else if (item.getItemId() == R.id.menu_top_rated) {
            MovieListModel.getSingleton().getMovies(MovieListModel.Type.TOP_RATED);
        } else {
            return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        MovieListModel.getSingleton().addObserver(mObserver);
    }

    @Override
    public void onPause() {
        super.onPause();
        MovieListModel.getSingleton().removeObserver(mObserver);
    }

    MovieList.Observer mObserver = new MovieList.Observer() {

        @Override
        public void onUpdatedMovies(List<Movie> newMovies) {
            adapter.update(newMovies);
        }
    };

}

//TODO how can I keep the movies offline. Saving it to a db, caching it and then retrieving it.
//Think of the levels of abstraction req
