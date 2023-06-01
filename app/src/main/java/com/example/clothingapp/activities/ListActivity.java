package com.example.clothingapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Toast;

import com.example.clothingapp.R;
import com.example.clothingapp.adapters.CardListAdapter;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.Gender;
import com.example.clothingapp.data.IProvider;
import com.example.clothingapp.data.JSONClothingProvider;
import com.example.clothingapp.data.NullProvider;
import com.example.clothingapp.data.StaticClothingItemProvider;
import com.example.clothingapp.fragments.FilterBottomSheet;
import com.example.clothingapp.fragments.SortBottomSheet;
import com.example.clothingapp.listeners.RecycleViewClickListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;

import java.io.Serializable;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ListActivity extends AppCompatActivity implements RecycleViewClickListener<ClothingItem>, SearchView.OnQueryTextListener {

    public static final String INTENT_CATEGORY_FILTER = "clothingCategory";
    public static final String INTENT_SEARCH_FILTER = "catgeorySearch";
    private IProvider<ClothingItem> allItems;

    private static class ViewHolder {

        public final SearchView searchView;
        public final RecyclerView items;
        public final Button filterButton, sortButton;
        public final FilterBottomSheet filterBottomSheet;
        public final SortBottomSheet sortBottomSheet;

        public ViewHolder(Activity activity) {
            items = activity.findViewById(R.id.list_items);
            filterButton = activity.findViewById(R.id.list_filter_button);
            sortButton = activity.findViewById(R.id.list_sort_button);
            searchView = activity.findViewById(R.id.list_search_view);

            filterBottomSheet = new FilterBottomSheet();
            sortBottomSheet = new SortBottomSheet();
        }

    }
    private ViewHolder vh;
    private IProvider<ClothingItem> provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup transitions
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new android.transition.Slide());
        setContentView(R.layout.activity_list);

        vh = new ViewHolder(this);
        allItems = new JSONClothingProvider(this);

        var adapter = new CardListAdapter(this, provider, R.layout.component_item_card);
        adapter.setListener(this);
        vh.items.setAdapter(adapter);
        vh.items.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Buttons
        vh.filterButton.setOnClickListener(x -> this.onFilterButtonClicked(x));
        vh.sortButton.setOnClickListener(x -> this.onSortButtonClicked(x));

        // Searching
        vh.searchView.setOnQueryTextListener(this);
        String search = getIntent().getStringExtra(INTENT_SEARCH_FILTER);
        if(search != null) {
            vh.searchView.setQuery(search, true);
        }

        // Filtering
        vh.filterBottomSheet.setOnOptionChangedCallback(options -> setFilters(vh.searchView.getQuery().toString(), options));
        initProviders();
    }

    private void initProviders() {
        var intent = getIntent();
        String category = intent.getStringExtra(INTENT_CATEGORY_FILTER);
        if(category != null) {
            // If we are in a category, we restrict searches to that category
            allItems = allItems.filter(x -> x.getCategory().equalsIgnoreCase(category));
        }
        provider = allItems;

        setFilters(vh.searchView.getQuery().toString(), vh.filterBottomSheet.getFilterOptions());
    }
    @Override
    public void onItemClick(ClothingItem item, View itemView, int position) {
        var intent = new Intent(this, ClothingItemActivity.class);
        intent.putExtra(ClothingItemActivity.INTENT_CLOTHING_ITEM_KEY, item);

        var transition = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                        this,
                        itemView.findViewWithTag(getResources().getString(R.string.card_list_adapter_image_tag)),
                        ClothingItemActivity.TRANSITION_SHARED_IMAGE_NAME);

        startActivity(intent, transition.toBundle());
    }

    private void onFilterButtonClicked(View v) {
        Bundle b = new Bundle();
        b.putSerializable(FilterBottomSheet.BUNDLE_PROVIDER_KEY, provider);
        vh.filterBottomSheet.setArguments(b);
        vh.filterBottomSheet.show(getSupportFragmentManager(), FilterBottomSheet.TAG);
    }

    private void onSortButtonClicked(View v) {
        vh.sortBottomSheet.show(getSupportFragmentManager(), SortBottomSheet.TAG);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        setFilters(newText, vh.filterBottomSheet.getFilterOptions());
        return true;
    }

    private void setFilters(@Nullable String search, @Nullable FilterBottomSheet.FilterOptions filterOptions) {
        final Pattern regex;
        if(search != null) {
            regex = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
        } else {
            regex = null;
        }

        // Do filtering
        provider = allItems.filter(item -> {
            boolean result = true;

            if(filterOptions != null) {
                result = result && (item.getGender() == Gender.MALE && filterOptions.maleClothes) ||
                                   (item.getGender() == Gender.FEMALE && filterOptions.femaleClothes);
                result = result && filterOptions.sizes.get(item.getSize());
            }

            if(regex != null) {
                result = result && regex.matcher(item.getName()).find();
            }

            return result;
        });

        var adapter = (CardListAdapter) vh.items.getAdapter();
        adapter.setProvider(provider);
        adapter.notifyDataSetChanged();
    }
}