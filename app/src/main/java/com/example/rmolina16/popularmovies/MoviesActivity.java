package com.example.rmolina16.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MoviesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
    }

    public void onClick(View view) {
        //Make API request!
        PopularMoviesFetcher popularMoviesFetcher = new PopularMoviesFetcher();
        popularMoviesFetcher.execute();
    }

    public class PopularMoviesFetcher extends AsyncTask<Void, Void, Void> {

        private final String LOG_TAG = PopularMoviesFetcher.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String popularMovies = null;

            try {

                URL url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=296e88e43db6a23316946a9df5c8095d&language=en-US");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                popularMovies = buffer.toString();
                Log.v(LOG_TAG, "Popular Movies data: " + popularMovies);
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
            return null;
        }
    }
}
