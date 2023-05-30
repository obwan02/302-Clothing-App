package com.example.clothingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clothingapp.R;
import com.example.clothingapp.data.ClothesSize;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.IProvider;
import com.example.clothingapp.data.NullProvider;

import java.util.List;

public class ClothingItemActivity extends AppCompatActivity {

    public static final String INTENT_CLOTHING_ITEM_KEY = "clothingItem";

    private static class ViewHolder {
        private final ImageView image;
        private final TextView price;
        private final TextView title;

        public ViewHolder(Activity activity) {
            this.image = activity.findViewById(R.id.clothing_item_image);
            this.price = activity.findViewById(R.id.clothing_item_price);
            this.title = activity.findViewById(R.id.clothing_item_title);
        }
    }

    private ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_item);

        vh = new ViewHolder(this);

        var intent = getIntent();
        var item = (ClothingItem) intent.getSerializableExtra(INTENT_CLOTHING_ITEM_KEY);

        if(item == null) {
            item = new ClothingItem("NOTHING",
                                -1.0f,
                                    ClothesSize.MEDIUM,
                                    List.of(),
                          "No clothing item passed to activity",
                            "NULL");
            // Warn if no provider is sent
            Log.w(getPackageName(), "No ClothingItem passed to ClothingItemActivity");
        }

        this.update(item);
    }

    private void update(ClothingItem item) {
        int imageId = this
                        .getResources()
                        .getIdentifier(item.getImageResourceNames().stream().findFirst().orElse("sherpa_jacket_brown"),
                        "drawable", getPackageName());
        vh.image.setImageResource(imageId);
        vh.title.setText(item.getName());
        vh.price.setText(String.format("$ %.2f", item.getPrice()));
    }
}