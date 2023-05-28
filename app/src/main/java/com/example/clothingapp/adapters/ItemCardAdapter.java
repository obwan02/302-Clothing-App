package com.example.clothingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothingapp.R;
import com.example.clothingapp.activities.MainActivity;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.IProvider;
import com.example.clothingapp.listeners.RecycleViewClickListener;

public class ItemCardAdapter extends RecyclerView.Adapter<ItemCardAdapter.CardViewHolder> {

    private IProvider<ClothingItem> itemProvider;
    private RecycleViewClickListener<ClothingItem> listener;

    public ItemCardAdapter(IProvider<ClothingItem> itemProvider, RecycleViewClickListener<ClothingItem> listener) {
        this.itemProvider = itemProvider;
        this.listener = listener;
    }

    public void updateProvider(@NonNull IProvider<ClothingItem> provider) {
        // TODO: Tell android we have updated the underlying data
        this.itemProvider = provider;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var context = parent.getContext();
        var layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        var view = layoutInflater.inflate(R.layout.component_item_card, parent, false);
        return  new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        ClothingItem item = itemProvider.getItem(position);

        int imageResourceId = holder.image
                                    .getContext()
                                    .getResources()
                                    .getIdentifier(item.getImageResourceNames().stream().findFirst().orElse(""),
                                                   "drawable", "app");
        if(imageResourceId == 0) {
            imageResourceId = R.drawable.sherpa_jacket_brown;
        }

        holder.image.setImageResource(imageResourceId);
        holder.text.setText(item.getName());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item, holder.itemView, position));
    }

    @Override
    public int getItemCount() {
        return itemProvider.getCount();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public final ImageView image;
        public final TextView text;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_card_image);
            text = itemView.findViewById(R.id.item_card_text);
        }

    }
}
