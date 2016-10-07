package com.example.rmolina16.popularmovies;

public class Movie {

    private String movie_img_url;

    Movie(String movie_img_url){
        this.movie_img_url = movie_img_url;
    }

    public String getMovie_img_url() {
        return movie_img_url;
    }

    public void setMovie_img_url(String movie_img_url) {
        this.movie_img_url = movie_img_url;
    }
}