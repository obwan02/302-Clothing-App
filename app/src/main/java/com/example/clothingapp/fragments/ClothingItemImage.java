package com.example.clothingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clothingapp.R;

public class ClothingItemImage extends Fragment {

    public static final String BUNDLE_IMAGE_ID_KEY = "clothing_item_id";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_clothing_item_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        int imageId = args.getInt("clothing_item_id");

        ImageView imageView = view.findViewById(R.id.clothing_item_image);
        imageView.setImageResource(imageId);
    }
}
