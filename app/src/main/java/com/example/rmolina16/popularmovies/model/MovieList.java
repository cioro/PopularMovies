package com.example.rmolina16.popularmovies.model;

import java.util.List;


public interface MovieList {
    interface Observer {
        void onUpdatedMovies(List<MovieURL> newMovies);
    }

    List<MovieURL> getDefaultMovies();
    List<MovieURL> getMovies(MovieListModel.Type type);
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
}
