package com.example.clothingapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.IProvider;

public class ItemCardAdapter extends RecyclerView.Adapter<ItemCardAdapter.CardViewHolder> {

    private IProvider<ClothingItem> itemProvider;

    ItemCardAdapter(IProvider<ClothingItem> itemProvider) {

    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
