package com.example.rmolina16.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_activity_container, new MoviesFragment())
                    .commit();
        }
    }

}
//TODO Modify the data adapter to take in the URL
//TODO Create a movieData Model class
//TODO investigate where to best place the loading of my images
//TODO see if I can cache them.