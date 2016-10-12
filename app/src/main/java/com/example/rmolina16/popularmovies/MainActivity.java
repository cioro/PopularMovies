package com.example.rmolina16.popularmovies;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment c​r​e​a​t​e​F​r​a​g​m​e​n​t​() {
        return new MoviesListFragment();
    }
}