package com.example.clothingapp.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothingapp.R;
import com.example.clothingapp.data.Category;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.IProvider;
import com.example.clothingapp.data.ImageDownloader;
import com.example.clothingapp.listeners.RecycleViewClickListener;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private IProvider<Category> categoryProvider;
    private RecycleViewClickListener<Category> listener;
    private ImageDownloader downloader = new ImageDownloader(false);

    public CategoryAdapter(IProvider<Category> categoryProvider) {
        this.categoryProvider = categoryProvider;
    }

    public void setListener(RecycleViewClickListener<Category> listener) {
        this.listener = listener;
    }

    public IProvider<Category> getProvider() {
        return categoryProvider;
    }

    public void setProvider(IProvider<Category> categoryProvider) {
        this.categoryProvider = categoryProvider;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.component_category_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category item = categoryProvider.getItem(position);

        downloader.loadSingle(item.getItems().getItem(0).getImageUrls().get(0), (bm, i) -> {
            holder.image.setImageBitmap(bm);
        });

        holder.text.setText(item.getName());

        if(listener != null) {
            holder.itemView.setOnClickListener(x -> listener.onItemClick(item, holder.itemView, position));
        }
    }

    @Override
    public int getItemCount() {
        return categoryProvider.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView image;
        public final TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.category_card_image);
            text = itemView.findViewById(R.id.category_card_text);
        }
    }
}
