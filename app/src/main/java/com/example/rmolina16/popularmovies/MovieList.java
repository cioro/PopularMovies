package com.example.rmolina16.popularmovies;

import java.util.List;


public interface MovieList {
    interface Observer {
        void onUpdatedMovies(List<Movie> newMovies);
    }

    List<Movie> getDefaultMovies();
    List<Movie>  getMovies(MovieListModel.Type type);
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
}
