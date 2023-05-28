package com.example.clothingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.clothingapp.R;
import com.example.clothingapp.adapters.CardListAdapter;
import com.example.clothingapp.data.StaticClothingItemProvider;

public class ListActivity extends AppCompatActivity {

    private static class ViewHolder {
        public RecyclerView items;

        public ViewHolder(Activity activity) {
            items = activity.findViewById(R.id.list_items);
        }
    }

    private ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        vh = new ViewHolder(this);
        vh.items.setAdapter(new CardListAdapter(new StaticClothingItemProvider(), R.layout.component_item_card));
        vh.items.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}