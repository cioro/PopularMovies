package com.example.rmolina16.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class MovieDetailActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context)
    {
        return new Intent(context, MovieDetailActivity.class);
    }

    @Override
    protected Fragment c​r​e​a​t​e​F​r​a​g​m​e​n​t​() {
        return new MovieDetailFragment();
    }
}
