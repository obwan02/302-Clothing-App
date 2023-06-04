package com.example.clothingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.clothingapp.R;
import com.example.clothingapp.data.ClothingItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Comparator;
import java.util.function.Consumer;

public class SortBottomSheet extends BottomSheetDialogFragment {

    public static final String TAG = "SortBottomSheet";

    private RadioGroup options;
    private SortOptions currentSort = SortOptions.HOTTEST_NEW_ITEMS;
    private Consumer<SortOptions> callback;

    public enum SortOptions {
        PRICE_HIGHEST_LOWEST,
        PRICE_LOWEST_HIGHEST,
        HOTTEST_NEW_ITEMS,
        ALWAYS_SELLING,
        BEST_VALUE;

        public Comparator<ClothingItem> getComparator() {
            switch(this) {
                case PRICE_HIGHEST_LOWEST -> {
                    return (x, y) -> (int) (100 * (y.getPrice() - x.getPrice()));
                }

                case PRICE_LOWEST_HIGHEST -> {
                    return (x, y) -> (int) (100 * (x.getPrice() - y.getPrice()));
                }

                default -> {
                    return (x, y) -> 0;
                }
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_sort_list, container, false);
        options = v.findViewById(R.id.sort_radio_group);
        options.setOnCheckedChangeListener((group, checkedId) -> this.onCheckedChanged(group, checkedId));
        return v;
    }

    private void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.sort_radio_high_low) {
            currentSort = SortOptions.PRICE_HIGHEST_LOWEST;
        } else if(checkedId == R.id.sort_radio_low_high) {
            currentSort = SortOptions.PRICE_LOWEST_HIGHEST;
        } else if(checkedId == R.id.sort_radio_hot) {
            currentSort = SortOptions.HOTTEST_NEW_ITEMS;
        } else if(checkedId == R.id.sort_radio_always_selling) {
            currentSort = SortOptions.ALWAYS_SELLING;
        } else if(checkedId == R.id.sort_radio_value) {
            currentSort = SortOptions.ALWAYS_SELLING;
        }

        callback.accept(currentSort);
    }

    public void setOnOptionChangedCallback(Consumer<SortOptions> sortOptionsCallback) {
        callback = sortOptionsCallback;
    }

    public SortOptions getSortOptions() {
        return currentSort;
    }
}
