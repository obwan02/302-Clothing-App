package com.example.clothingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.clothingapp.R;
import com.example.clothingapp.adapters.CardListAdapter;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.IProvider;
import com.example.clothingapp.data.NullProvider;
import com.example.clothingapp.data.StaticClothingItemProvider;
import com.example.clothingapp.fragments.FilterBottomSheet;
import com.example.clothingapp.fragments.SortBottomSheet;
import com.example.clothingapp.listeners.RecycleViewClickListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.Serializable;

public class ListActivity extends AppCompatActivity implements RecycleViewClickListener<ClothingItem> {

    public static final String INTENT_PROVIDER_KEY = "clothingProvider";

    private static class ViewHolder {

        public final RecyclerView items;
        public final Button filterButton, sortButton;
        public final FilterBottomSheet filterBottomSheet;
        public final SortBottomSheet sortBottomSheet;
        public ViewHolder(Activity activity) {
            items = activity.findViewById(R.id.list_items);
            filterButton = activity.findViewById(R.id.list_filter_button);
            sortButton = activity.findViewById(R.id.list_sort_button);

            filterBottomSheet = new FilterBottomSheet();
            sortBottomSheet = new SortBottomSheet();
        }

    }
    private ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        var intent = getIntent();
        IProvider<ClothingItem> provider = (IProvider<ClothingItem>) intent.getSerializableExtra(INTENT_PROVIDER_KEY);

        if(provider == null) {
            provider = new NullProvider();
            // Warn if no provider is sent
            Log.w(getPackageName(), "No provider passed to ListActivity");
        };

        vh = new ViewHolder(this);

        var adapter = new CardListAdapter(provider, R.layout.component_item_card);
        adapter.setListener(this);
        vh.items.setAdapter(adapter);
        vh.items.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Buttons
        vh.filterButton.setOnClickListener(x -> this.onFilterButtonClicked(x));
        vh.sortButton.setOnClickListener(x -> this.onSortButtonClicked(x));
    }
    @Override
    public void onItemClick(ClothingItem item, View itemView, int position) {
        var intent = new Intent(this, ClothingItemActivity.class);
        intent.putExtra(ClothingItemActivity.INTENT_CLOTHING_ITEM_KEY, item);

        startActivity(intent);
    }

    private void onFilterButtonClicked(View v) {
        vh.filterBottomSheet.show(getSupportFragmentManager(), FilterBottomSheet.TAG);
    }

    private void onSortButtonClicked(View v) {
        vh.sortBottomSheet.show(getSupportFragmentManager(), SortBottomSheet.TAG);
    }

}