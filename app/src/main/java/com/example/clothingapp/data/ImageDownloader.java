package com.example.clothingapp.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImageDownloader {

    private static LruCache<String, Bitmap> imageCache = new LruCache<>(300);

    public interface Callback {
        void onComplete(Bitmap result, int index);
    }
    private final Executor executor;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public ImageDownloader(boolean singleImage) {
        if(singleImage) {
            executor = Executors.newSingleThreadExecutor();
        } else {
            executor = Executors.newCachedThreadPool();
        }
    }

    public void load(List<String> urls, Callback callback) {

        for(int i = 0; i < urls.size(); i++) {
            final String url = urls.get(i);
            final int index = i;

            loadSingleInternal(url, index, callback);
        }
    }

    public void loadSingle(String url, Callback callback) {
        loadSingleInternal(url, 0, callback);
    }

    private void loadSingleInternal(String url, int index, Callback callback) {
        executor.execute(() -> {

            // Check cache
            Bitmap cached = imageCache.get(url);
            if(cached != null) {
                handler.post(() -> callback.onComplete(cached, index));
            }

            Bitmap mImage = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mImage = BitmapFactory.decodeStream(in);

                final var image = Bitmap.createBitmap(mImage, mImage.getWidth()/4, 0, mImage.getWidth()/2, mImage.getHeight());
                imageCache.put(url, image);
                handler.post(() -> callback.onComplete(image, index));
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
        });
    }
}