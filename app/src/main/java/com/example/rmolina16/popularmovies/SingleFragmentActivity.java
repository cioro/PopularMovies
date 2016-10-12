package com.example.rmolina16.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment c​r​e​a​t​e​F​r​a​g​m​e​n​t​();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_activity_container);

        if(fragment == null) {
            fragment = c​r​e​a​t​e​F​r​a​g​m​e​n​t​();
            fm.beginTransaction()
                    .add(R.id.main_activity_container, fragment)
                    .commit();
        }
    }

}

