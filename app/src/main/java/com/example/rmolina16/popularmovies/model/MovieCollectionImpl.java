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

public class MovieCollectionImpl implements MovieCollection {

    @Override
    public MovieData getMovie(int movie_id) {
        return null;
    }

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }
}

