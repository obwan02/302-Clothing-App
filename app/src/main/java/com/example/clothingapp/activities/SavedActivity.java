package com.example.clothingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.clothingapp.R;
import com.example.clothingapp.adapters.CardListAdapter;
import com.example.clothingapp.util.SavedManager;

public class SavedActivity extends AppCompatActivity {

    private static class ViewHolder {
        public final RecyclerView savedItems;
        ViewHolder(Activity a) {
            savedItems = a.findViewById(R.id.saved_items);
        }
    }

    private ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        vh = new ViewHolder(this);
        vh.savedItems.setAdapter(new CardListAdapter(this, SavedManager.getInstance(), R.layout.component_item_card));
        vh.savedItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}