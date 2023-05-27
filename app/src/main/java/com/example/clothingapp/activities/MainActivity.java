package com.example.clothingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.clothingapp.R;

public class MainActivity extends AppCompatActivity {

    private static class ViewHolder {
        public RecyclerView trending;
        public RecyclerView categories;
        public SearchView searchBar;
    }

    private ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}