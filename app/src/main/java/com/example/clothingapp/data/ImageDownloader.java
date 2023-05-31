package com.example.clothingapp.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImageDownloader {

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

            executor.execute(() -> {
                Bitmap mImage = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    mImage = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                final var image = Bitmap.createBitmap(mImage, mImage.getWidth()/4, 0, mImage.getWidth()/2, mImage.getHeight());
                handler.post(() -> callback.onComplete(image, index));

            });
        }
    }

    public void loadSingle(String url, Callback callback) {
        executor.execute(() -> {
            Bitmap mImage = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mImage = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            final var image = Bitmap.createBitmap(mImage, mImage.getWidth()/4, 0, mImage.getWidth()/2, mImage.getHeight());
            handler.post(() -> callback.onComplete(image, 0));
        });
    }
}