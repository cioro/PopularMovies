package com.example.rmolina16.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class MovieDetailActivity extends SingleFragmentActivity {

    final public static String MOVIE_ID = "MOVIE_ID";

    public static Intent newIntent(Context context, int movie_id)
    {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MOVIE_ID, movie_id);
        return intent;
    }

    @Override
    protected Fragment c​r​e​a​t​e​F​r​a​g​m​e​n​t​() {
        return new MovieDetailFragment();
    }
}
