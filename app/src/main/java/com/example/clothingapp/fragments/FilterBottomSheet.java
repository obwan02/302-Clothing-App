package com.example.clothingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.clothingapp.R;
import com.example.clothingapp.data.ClothesSize;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.IProvider;
import com.example.clothingapp.data.JSONClothingProvider;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterBottomSheet extends BottomSheetDialogFragment {

    private static class ViewHolder {

        public final ChipGroup genderSelection;
        public final Chip femaleChip, maleChip;
        public final LinearLayout checkBoxesLayout;
        public final RangeSlider priceSlider;

        ViewHolder(View view) {
            genderSelection = view.findViewById(R.id.filter_gender_group);
            femaleChip = view.findViewById(R.id.filter_chip_female);
            maleChip = view.findViewById(R.id.filter_chip_male);
            checkBoxesLayout = view.findViewById(R.id.filter_checkbox_layout);
            priceSlider = view.findViewById(R.id.filter_price_slider);
            priceSlider.setValues(0.1f, 0.9f);
        }
    }

    public interface OptionsChangedCallback {
        void onOptionsChanged(FilterOptions options);
    }

    public static class FilterOptions {
        public final boolean maleClothes, femaleClothes;
        public final EnumMap<ClothesSize, Boolean> sizes;

        private FilterOptions(boolean maleClothes, boolean femaleClothes, EnumMap<ClothesSize, Boolean> sizes) {
            this.maleClothes = maleClothes;
            this.femaleClothes = femaleClothes;
            this.sizes = sizes;
        }
    };
    public static final String TAG = "FilterBottomSheet";
    public static final String BUNDLE_PROVIDER_KEY = "filter_items";

    private ViewHolder vh;
    // Outside viewholder because they are instantiated at runtime
    private EnumMap<ClothesSize, CheckBox> checkboxes = new EnumMap<>(ClothesSize.class);
    private OptionsChangedCallback onOptionChangedCallback;

    public FilterOptions getFilterOptions() {
        if(vh != null) {
            EnumMap<ClothesSize, Boolean> sizes = new EnumMap<>(ClothesSize.class);
            for(var size : checkboxes.keySet()) {
                sizes.put(size, checkboxes.get(size).isChecked());
            }

            return new FilterOptions(vh.maleChip.isChecked(), vh.femaleChip.isChecked(), sizes);
        }

        return null;
    }

    public void setOnOptionChangedCallback(OptionsChangedCallback onOptionChangedCallback) {
        this.onOptionChangedCallback = onOptionChangedCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_filter_list, container, false);
        vh = new ViewHolder(v);
        vh.femaleChip.setOnCheckedChangeListener((view, isChecked) -> this.onChipChanged(view, isChecked));
        vh.maleChip.setOnCheckedChangeListener((view, isChecked) -> this.onChipChanged(view, isChecked));

        Bundle bundle = getArguments();
        IProvider<ClothingItem> items = (IProvider<ClothingItem>) bundle.getSerializable(BUNDLE_PROVIDER_KEY);
        

        for(var size : ClothesSize.values()) {
            ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.component_filter_checkbox, null);

            var checkbox = (CheckBox) vg.findViewById(R.id.filter_checkbox);
            checkbox.setText(size.getDisplayDescription());
            checkbox.setChecked(true);
            checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> this.onRadioChanged(buttonView, isChecked, size));

            // TODO: Dynamically update with filtering
            int count = items.filter(x -> x.getSize().equals(size)).getCount();
            var countTextView = (TextView) vg.findViewById(R.id.filter_checkbox_count);
            countTextView.setText(String.format("%d", count));

            vh.checkBoxesLayout.addView(vg);
            checkboxes.put(size, checkbox);
        }
        return v;
    }

    private void onChipChanged(CompoundButton buttonView, boolean isChecked) {
        Chip chip = (Chip) buttonView;

        // Make sure at least one chip is selected
        if(!vh.femaleChip.isChecked() && !vh.maleChip.isChecked()) {
            chip.setChecked(true);
            return;
        }

        chip.setCheckedIconVisible(isChecked);
        chip.setChipIconVisible(!isChecked);

        if(onOptionChangedCallback != null) {
            onOptionChangedCallback.onOptionsChanged(getFilterOptions());
        }
    }

    private void onRadioChanged(CompoundButton view, boolean isChecked, ClothesSize size) {
        if(onOptionChangedCallback != null) {
            onOptionChangedCallback.onOptionsChanged(getFilterOptions());
        }
    }
}
