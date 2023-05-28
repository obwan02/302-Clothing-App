package com.example.clothingapp.listeners;

import android.view.View;

public interface RecycleViewClickListener<T> {
    void onItemClick(T item, View itemView, int position);
}
