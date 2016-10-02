package com.example.rmolina16.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        popularMoviesFetcher.execute(view.getTag().toString());
    }

    public class PopularMoviesFetcher extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = PopularMoviesFetcher.class.getSimpleName();

        private String[] getMovieURL(String moviesJSONStr) throws JSONException {

            final String MDB_RESULTS = "results";
            final String MDB_IMAGE_KEY = "poster_path";
            final String IMAGE_BASE_URL = " http://image.tmdb.org/t/p/";
            final String IMAGE_SIZE = "w185";

            JSONObject moviesJSON = new JSONObject(moviesJSONStr);
            JSONArray moviesArray = moviesJSON.getJSONArray(MDB_RESULTS);

            String[] result = new String[moviesArray.length()];
            for (int i = 0; i < moviesArray.length(); ++i) {
                JSONObject movie = moviesArray.getJSONObject(i);
                result[i] = IMAGE_BASE_URL + IMAGE_SIZE + movie.getString(MDB_IMAGE_KEY);
            }

            return result;
        }

        @Override
        protected String[] doInBackground(String... params) {

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
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
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
                return getMovieURL(movieData);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }
    }
}
