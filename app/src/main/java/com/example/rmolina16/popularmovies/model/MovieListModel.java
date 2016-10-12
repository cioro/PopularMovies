package com.example.rmolina16.popularmovies.model;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rmolina16.popularmovies.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieListModel implements MovieList {

    public static MovieListModel singleton;

    private List<Observer> movieObservers = new ArrayList<>();

    private List<MovieURL> movieURLList = Collections.emptyList();

    public static MovieListModel getSingleton() {
        if (singleton == null) {
            singleton = new MovieListModel();
        }

        return singleton;
    }

    @Override
    public List<MovieURL> getDefaultMovies() {
        if (movieURLList.isEmpty()) {
            MoviePosterAsyncTask fetcher = new MoviePosterAsyncTask();
            fetcher.execute(Type.POPULAR.getLabel());
        }
        return movieURLList;
    }

    @Override
    public List<MovieURL> getMovies(Type value) {
        MoviePosterAsyncTask fetcher = new MoviePosterAsyncTask();
        fetcher.execute(value.getLabel());
        return movieURLList;
    }

    public enum Type {

        POPULAR("popular"), TOP_RATED("top_rated");

        final String mLabel;

        Type(String mLabel) {
            this.mLabel = mLabel;
        }

        public String getLabel() {
            return mLabel;
        }
    }

    private void notifyObservers() {
        for (Observer movieObserver : movieObservers) {
            movieObserver.onUpdatedMovies(movieURLList);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        movieObservers.add(observer);
        observer.onUpdatedMovies(movieURLList);
    }

    @Override
    public void removeObserver(Observer observer) {
        movieObservers.remove(observer);
    }

    private class MoviePosterAsyncTask extends AsyncTask<String, Void, Void> {

        private final String LOG_TAG =  MoviePosterAsyncTask.class.getSimpleName();

        private List<MovieURL> getMovieURL(String moviesJSONStr) throws JSONException {

            final String MDB_RESULTS = "results";
            final String MDB_IMAGE_KEY = "poster_path";
            final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
            final String IMAGE_SIZE = "w185";

            JSONObject moviesJSON = new JSONObject(moviesJSONStr);
            JSONArray moviesArray = moviesJSON.getJSONArray(MDB_RESULTS);

            List<MovieURL> result = new ArrayList<>();
            for (int i = 0; i < moviesArray.length(); ++i) {
                JSONObject movie = moviesArray.getJSONObject(i);
                String movieUrl = IMAGE_BASE_URL + IMAGE_SIZE + movie.getString(MDB_IMAGE_KEY);
                result.add(new MovieURL(movieUrl));
            }

            return result;
        }

        @Override
        protected Void doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieData = null;

            try {

                final String BASE_URL = "https://api.themoviedb.org/3/movie/";
                final String API_KEY = "api_key";
                final String LANGUAGE = "language";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(params[0])
                        .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .appendQueryParameter(LANGUAGE, "en-US")
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieData = buffer.toString();
                Log.v(LOG_TAG, "Popular Movies data: " + movieData);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                movieURLList = getMovieURL(movieData);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            notifyObservers();
            super.onPostExecute(aVoid);
        }
    }
}

//TODO create a class tha keeps the state of the model and the model queries this to decide its actions