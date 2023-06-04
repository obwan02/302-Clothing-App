package com.example.clothingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.clothingapp.R;
import com.example.clothingapp.adapters.CardListAdapter;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.util.CartManager;

public class CartActivity extends AppCompatActivity {

    private static class ViewHolder {
        public final RecyclerView savedItems;
        public final TextView price;
        public final Button checkoutButton, clearButton;

        ViewHolder(Activity a) {
            savedItems = a.findViewById(R.id.cart_items);
            price = a.findViewById(R.id.cart_total_price);
            checkoutButton = a.findViewById(R.id.cart_checkout_button);
            clearButton = a.findViewById(R.id.cart_clear);
        }
    }

    private ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        vh = new ViewHolder(this);

        var adapter = new CardListAdapter(this, CartManager.getInstance(), R.layout.component_item_card);
        vh.savedItems.setAdapter(adapter);
        vh.savedItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.setListener((item, view, pos) -> this.onItemClicked(item, view, pos));

        vh.price.setText(String.format("TOTAL: $%.2f", CartManager.getTotal()));

        vh.checkoutButton.setOnClickListener(v -> this.onCheckoutClicked(v));
        vh.clearButton.setOnClickListener(v -> {
            CartManager.clear();
            adapter.notifyDataSetChanged();
            vh.price.setText("TOTAL: $0.00");
        });
    }

    private void onItemClicked(ClothingItem item, View itemView, int position) {
        var intent = new Intent(this, ClothingItemActivity.class);
        intent.putExtra(ClothingItemActivity.INTENT_CLOTHING_ITEM_KEY, item);

        var transition = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                        this,
                        itemView.findViewWithTag(getResources().getString(R.string.card_list_adapter_image_tag)),
                        ClothingItemActivity.TRANSITION_SHARED_IMAGE_NAME);

        startActivity(intent, transition.toBundle());
    }

    private void onCheckoutClicked(View v) {
        var intent = new Intent(this, CheckOutActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}