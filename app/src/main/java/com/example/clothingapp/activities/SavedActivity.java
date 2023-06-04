package com.example.clothingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.clothingapp.R;
import com.example.clothingapp.adapters.CardListAdapter;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.listeners.RecycleViewClickListener;
import com.example.clothingapp.util.SavedManager;

public class SavedActivity extends AppCompatActivity implements RecycleViewClickListener<ClothingItem> {


    private static class ViewHolder {
        public final RecyclerView savedItems;
        public final TextView nothingSavedText;
        ViewHolder(Activity a) {
            savedItems = a.findViewById(R.id.saved_items);
            nothingSavedText = a.findViewById(R.id.saved_nothing_saved_text);
        }
    }

    private ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        vh = new ViewHolder(this);

        var adapter = new CardListAdapter(this, SavedManager.getInstance(), R.layout.component_item_card);
        vh.savedItems.setAdapter(adapter);
        vh.savedItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.setListener(this);

        if(SavedManager.getInstance().getCount() > 0) {
            vh.savedItems.setVisibility(View.VISIBLE);
            vh.nothingSavedText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(ClothingItem item, View itemView, int position) {
        var intent = new Intent(this, ClothingItemActivity.class);
        intent.putExtra(ClothingItemActivity.INTENT_CLOTHING_ITEM_KEY, item);

        var transition = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                        this,
                        itemView.findViewWithTag(getResources().getString(R.string.card_list_adapter_image_tag)),
                        ClothingItemActivity.TRANSITION_SHARED_IMAGE_NAME);

        startActivity(intent, transition.toBundle());
    }
}
