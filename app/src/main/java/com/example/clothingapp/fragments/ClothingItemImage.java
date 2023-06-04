package com.example.clothingapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clothingapp.R;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.ImageDownloader;
import com.example.clothingapp.util.SavedManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

import java.util.function.Consumer;

public class ClothingItemImage extends Fragment {

    public static final String BUNDLE_IMAGE_URL_KEY = "clothing_item_url";
    public static final String BUNDLE_ITEM_KEY = "clothing_item";


    private ImageDownloader downloader;
    private String sharedTransitionInName;

    public void setDownloader(ImageDownloader downloader) {
        this.downloader = downloader;
    }

    public void setSharedItemTransitionName(String name) {
        sharedTransitionInName = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_clothing_item_image, container, false);
        if(sharedTransitionInName != null) {
            v.findViewById(R.id.clothing_item_image).setTransitionName(sharedTransitionInName);
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String url = args.getString(BUNDLE_IMAGE_URL_KEY);

        ImageView imageView = view.findViewById(R.id.clothing_item_image);
        TextView loadingText = view.findViewById(R.id.clothing_item_loading_text);

        if(downloader == null) {
            downloader = new ImageDownloader(true);
        }

        downloader.loadSingle(url, (bitmap, i) -> {
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
            loadingText.setVisibility(View.GONE);
        });

        final ClothingItem item = (ClothingItem) args.getSerializable(BUNDLE_ITEM_KEY);
    }

}
