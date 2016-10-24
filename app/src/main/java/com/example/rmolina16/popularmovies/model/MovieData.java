package com.example.rmolina16.popularmovies.model;

public class MovieData {

    private String movieImageURL;
    private String movieReleaseDate;
    private String movieTitle;
    private String movieSynopsis;
    private String movieUserRating;

    private int movieId;

    public MovieData(String movieImageURL, String movieReleaseDate, String movieTitle, String movieSynopsis, String movieUserRating, int movieId) {
        this.movieImageURL = movieImageURL;
        this.movieReleaseDate = movieReleaseDate;
        this.movieTitle = movieTitle;
        this.movieSynopsis = movieSynopsis;
        this.movieUserRating = movieUserRating;
        this.movieId = movieId;
    }

    public void setMovieImageURL(String movieImageURL) {
        this.movieImageURL = movieImageURL;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieSynopsis() {
        return movieSynopsis;
    }

    public void setMovieSynopsis(String movieSynopsis) {
        this.movieSynopsis = movieSynopsis;
    }

    public String getMovieUserRating() {
        return movieUserRating;
    }

    public void setMovieUserRating(String movieUserRating) {
        this.movieUserRating = movieUserRating;
    }

    public String getMovieImageURL() {
        return movieImageURL;
    }

    public int getMovieId() {
        return movieId;
    }
}
