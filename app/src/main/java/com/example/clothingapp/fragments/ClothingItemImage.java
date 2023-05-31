package com.example.clothingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clothingapp.R;
import com.example.clothingapp.data.ImageDownloader;

public class ClothingItemImage extends Fragment {

    public static final String BUNDLE_IMAGE_URL_KEY = "clothing_item_url";
    private ImageDownloader downloader;

    public void setDownloader(ImageDownloader downloader) {
        this.downloader = downloader;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_clothing_item_image, container, false);
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
    }
}
