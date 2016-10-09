package com.example.rmolina16.popularmovies;

class Movie {

    private String movie_img_url;

    Movie(String movie_img_url){
        this.movie_img_url = movie_img_url;
    }

    String getMovie_img_url() {
        return movie_img_url;
    }

}