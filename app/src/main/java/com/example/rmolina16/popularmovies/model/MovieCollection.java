package com.example.rmolina16.popularmovies.model;


/**
 * Created by rmolina16 on 10/16/16.
 */

public interface MovieCollection {

    MovieData getMovie(int movie_id);

    interface Observer {
        void onUpdatedMovie(MovieData movie);
    }

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
}

// if not got movie data fetch it        (model)
// if fetching show loading indicator  (UI)
// do the fetch                        (model)
// when fetched update some persistent store (in-memory, database) (model)
// when fetched update list            (UI)
