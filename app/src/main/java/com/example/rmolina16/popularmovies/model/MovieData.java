package com.example.rmolina16.popularmovies.model;

/**
 * Created by rmolina16 on 10/13/16.
 */

public class MovieData {

    private String movie_img_url;

    private int movie_id;

    public MovieData(int movie_id, String movie_img_url){
        this.movie_img_url = movie_img_url;
        this.movie_id = movie_id;
    }

    public String getMovie_img_url() {
        return movie_img_url;
    }

    public int getMovie_id() {
        return movie_id;
    }
}
