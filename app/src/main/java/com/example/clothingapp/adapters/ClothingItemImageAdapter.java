package com.example.clothingapp.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.clothingapp.R;
import com.example.clothingapp.activities.ClothingItemActivity;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.fragments.ClothingItemImage;

public class ClothingItemImageAdapter extends FragmentStateAdapter {

    private ClothingItem clothingItem;
    private FragmentActivity activity;

    public ClothingItemImageAdapter(@NonNull FragmentActivity activity, @NonNull ClothingItem item) {
        super(activity);
        this.clothingItem = item;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new ClothingItemImage();

        Bundle args = new Bundle();

        int imageResourceId = activity
                .getResources()
                .getIdentifier(clothingItem.getImageResourceNames().get(position),
                        "drawable", activity.getPackageName());
        args.putInt(ClothingItemImage.BUNDLE_IMAGE_ID_KEY, imageResourceId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return clothingItem.getImageResourceNames().size();
    }

    public ClothingItem getClothingItem() {
        return clothingItem;
    }

    public void setClothingItem(ClothingItem clothingItem) {
        this.clothingItem = clothingItem;
        this.notifyDataSetChanged();
    }
}
