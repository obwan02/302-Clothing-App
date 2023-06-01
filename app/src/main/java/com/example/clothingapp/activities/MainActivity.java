package com.example.clothingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.clothingapp.R;
import com.example.clothingapp.adapters.CategoryAdapter;
import com.example.clothingapp.adapters.CardListAdapter;
import com.example.clothingapp.data.Category;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.GenerativeCategoryProvider;
import com.example.clothingapp.data.IProvider;
import com.example.clothingapp.data.JSONClothingProvider;
import com.example.clothingapp.data.StaticClothingItemProvider;
import com.example.clothingapp.listeners.RecycleViewClickListener;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

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
    private RecycleViewClickListener<Category> categoryListener = (cat, view, pos) -> this.onCategoryClicked(cat, view, pos);

    private IProvider<ClothingItem> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new android.transition.Fade());
        setContentView(R.layout.activity_main);

        vh = new ViewHolder(this);
        allItems = new JSONClothingProvider(this);

        // TODO: change this
        IProvider<ClothingItem> trendingItems = allItems.slice(0, 5);
        IProvider<Category> categories = new GenerativeCategoryProvider(allItems);

        var trendingAdapter = new CardListAdapter(this, trendingItems, R.layout.component_trending_card);
        trendingAdapter.setListener(this.trendingListener);
        vh.trending.setAdapter(trendingAdapter);
        vh.trending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        var categoryAdapter = new CategoryAdapter(categories);
        categoryAdapter.setListener(this.categoryListener);
        vh.categories.setAdapter(categoryAdapter);
        var layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        vh.categories.setLayoutManager(layout);

        vh.searchBar.setOnQueryTextListener(this);
    }

    private void onTrendingItemClicked(ClothingItem item, View itemView, int position) {
        var intent = new Intent(this, ClothingItemActivity.class);
        intent.putExtra(ClothingItemActivity.INTENT_CLOTHING_ITEM_KEY, item);

        // Animation
        var transition = ActivityOptionsCompat
                                            .makeSceneTransitionAnimation(
                                                    this,
                                                    itemView.findViewById(R.id.trending_card_image),
                                                    ClothingItemActivity.TRANSITION_SHARED_IMAGE_NAME);
        startActivity(intent, transition.toBundle());
    }

    private void onCategoryClicked(Category cat, View itemView, int position) {
        var intent = new Intent(this, ListActivity.class);
        intent.putExtra(ListActivity.INTENT_CATEGORY_FILTER, cat.getName());
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        var intent = new Intent(this, ListActivity.class);
        intent.putExtra(ListActivity.INTENT_SEARCH_FILTER, vh.searchBar.getQuery().toString());
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


}
