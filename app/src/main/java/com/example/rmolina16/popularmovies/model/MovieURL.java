package com.example.rmolina16.popularmovies.model;

public class MovieURL {

    private String movie_img_url;
    private int movie_id;

    public MovieURL(int movie_id, String movie_img_url){
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