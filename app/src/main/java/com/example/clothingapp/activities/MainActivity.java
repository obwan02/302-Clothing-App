package com.example.clothingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.clothingapp.R;
import com.example.clothingapp.adapters.CategoryAdapter;
import com.example.clothingapp.adapters.CardListAdapter;
import com.example.clothingapp.data.Category;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.GenerativeCategoryProvider;
import com.example.clothingapp.data.IProvider;
import com.example.clothingapp.data.StaticClothingItemProvider;
import com.example.clothingapp.listeners.RecycleViewClickListener;

public class MainActivity extends AppCompatActivity {

    private static class ViewHolder {
        public RecyclerView trending;
        public RecyclerView categories;
        public SearchView searchBar;

        public ViewHolder(Activity activity) {
            trending = activity.findViewById(R.id.main_trending);
            categories = activity.findViewById(R.id.main_categories);
            searchBar = activity.findViewById(R.id.main_search_bar);
        }
    }


    private ViewHolder vh;
    private RecycleViewClickListener<ClothingItem> trendingListener = (clothingItem, view, pos) -> this.onTrendingItemClicked(clothingItem, view, pos);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vh = new ViewHolder(this);

        IProvider<ClothingItem> items = new StaticClothingItemProvider();
        IProvider<Category> categories = new GenerativeCategoryProvider(items);

        var trendingAdapter = new CardListAdapter(items, R.layout.component_trending_card);
        trendingAdapter.setListener(this.trendingListener);
        vh.trending.setAdapter(trendingAdapter);
        vh.trending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        vh.categories.setAdapter(new CategoryAdapter(categories));
        var layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        vh.categories.setLayoutManager(layout);
    }

    protected void updateTrending(IProvider<ClothingItem> provider) {
        CardListAdapter adapter = (CardListAdapter) vh.trending.getAdapter();
        adapter.updateProvider(provider);
    }

    private void onTrendingItemClicked(ClothingItem item, View itemView, int position) {
        Log.i("TEST", String.format("Item name: %s, position: %d", item.getName(), position));
        startActivity(new Intent(this, ClothingItemActivity.class));
    }


}
