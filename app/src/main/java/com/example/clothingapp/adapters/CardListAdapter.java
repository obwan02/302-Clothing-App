package com.example.clothingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothingapp.R;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.IProvider;
import com.example.clothingapp.listeners.RecycleViewClickListener;

import java.util.Optional;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    private IProvider<ClothingItem> itemProvider;
    private RecycleViewClickListener<ClothingItem> listener;
    // NOTE(spec): New attribute here
    private @LayoutRes int layoutId;

    // NOTE(spec): Name changed here, and takes an extra parameter
    /**
     * Creates a new CardListAdapter.
     *
     * @param itemProvider provides the underlying data to the adapter
     * @param layoutId the id of the layout to use for each item in the RecyclerView.
     *                 The items inside each item are found using tags (see the tags resource folder)
     */
    public CardListAdapter(IProvider<ClothingItem> itemProvider, @LayoutRes int layoutId) {
        this.itemProvider = itemProvider;
        this.layoutId = layoutId;
    }

    public void setListener(RecycleViewClickListener<ClothingItem> listener) {
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

        View view = layoutInflater.inflate(layoutId, parent, false);
        return new CardViewHolder(view);
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
        holder.name.setText(item.getName());

        if(listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onItemClick(item, holder.itemView, position));
        }
    }

    @Override
    public int getItemCount() {
        return itemProvider.getCount();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public final ImageView image;
        public final TextView name;
        public final Optional<TextView> price;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            var resources = itemView.getResources();
            String imageTag = resources.getString(R.string.card_list_adapter_image_tag);
            String nameTag = resources.getString(R.string.card_list_adapter_name_tag);
            String priceTag = resources.getString(R.string.card_list_adapter_price_tag);

            image = itemView.findViewWithTag(imageTag);
            name = itemView.findViewWithTag(nameTag);
            price = Optional.ofNullable(itemView.findViewWithTag(priceTag));

            if(image == null) {
                throw new RuntimeException("Could not find view with '@string/card_list_adapter_image_tag' in provided layout");
            }

            if(name == null) {
                throw new RuntimeException("Could not find view with '@string/card_list_adapter_name_tag' in provided layout");
            }
        }

    }
}
