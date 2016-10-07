package com.example.rmolina16.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Arrays;

public class MoviesFragment extends Fragment {

    private DataAdapter adapter;

    public MoviesFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private final String android_image_urls[] = {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
            "http://api.learn2crack.com/android/images/marshmallow.png"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.movies_fragment,container,false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView){
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<AndroidVersion> androidVersions = prepareData();
        adapter = new DataAdapter(getContext(),androidVersions);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<AndroidVersion> prepareData(){

        ArrayList<AndroidVersion> android_version = new ArrayList<>();
        for(int i=0;i<android_image_urls.length;i++){
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_image_url(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }

    @Override
    public void onStart() {
        super.onStart();
        PopularMoviesFetcher moviesFetcher = new PopularMoviesFetcher();
        moviesFetcher.execute("popular");
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

        @Override
        protected void onPostExecute(String[] strings) {

            super.onPostExecute(strings);
            if (strings != null) {
                ArrayList<AndroidVersion> movies = new ArrayList<>();
                for(int i =0; i < strings.length; ++i){
                    movies.add(i, new AndroidVersion());
                    movies.get(i).setAndroid_image_url(strings[i]);
                    Log.v(LOG_TAG,strings[i]);
                }
                adapter = new DataAdapter(getContext(),movies);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}