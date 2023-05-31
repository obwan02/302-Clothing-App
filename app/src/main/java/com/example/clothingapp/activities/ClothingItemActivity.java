package com.example.clothingapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.clothingapp.R;
import com.example.clothingapp.adapters.ClothingItemImageAdapter;
import com.example.clothingapp.data.ClothesSize;
import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.Gender;

import org.w3c.dom.Text;

import java.util.List;

public class ClothingItemActivity extends AppCompatActivity {

    public static final String INTENT_CLOTHING_ITEM_KEY = "clothingItem";

    private static class ViewHolder {
        private final ViewPager2 images;
        private final TextView price;
        private final TextView title;
        private final TextView description;
        private final LinearLayout dotsLayout;

        public ViewHolder(Activity activity) {
            this.images = activity.findViewById(R.id.clothing_item_pager);
            this.price = activity.findViewById(R.id.clothing_item_price);
            this.title = activity.findViewById(R.id.clothing_item_title);
            this.description = activity.findViewById(R.id.clothing_item_description);

            this.dotsLayout = activity.findViewById(R.id.clothing_item_dots_layout);
            this.dotsLayout.removeAllViews();
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
                            "NULL",
                                     Gender.MALE);
            // Warn if no provider is sent
            Log.w(getPackageName(), "No ClothingItem passed to ClothingItemActivity");
        }

        vh.title.setText(item.getName());
        vh.price.setText(String.format("$ %.2f", item.getPrice()));
        vh.description.setText(Html.fromHtml(item.getDescription().replaceAll("\n", "\n \n"), Html.FROM_HTML_MODE_COMPACT));
        vh.images.setAdapter(new ClothingItemImageAdapter(this, item));
        initDots(item);
        vh.images.registerOnPageChangeCallback(new ImageChangedCallback());
    }

    private void initDots(ClothingItem item) {
        vh.dotsLayout.removeAllViews();

        var imageResourceIds = item.getImageUrls();

        for(var ignored : imageResourceIds) {
            vh.dotsLayout.addView(createDot());
        }
    }

    private TextView createDot() {
        TextView tv = new TextView(this);

        var params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        params.setMargins(2, 0, 2, 0);

        tv.setLayoutParams(params);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tv.setText(Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_COMPACT));

        return tv;
    }

    private TextView selectDot(TextView tv) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        return tv;
    }

    private TextView deselectDot(TextView tv) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        return tv;
    }

    private class ImageChangedCallback extends ViewPager2.OnPageChangeCallback {

        private int lastSelectedIndex = 0;

        ImageChangedCallback() {
            if(vh.dotsLayout.getChildCount() > 0) {
                selectDot((TextView) vh.dotsLayout.getChildAt(0));
            }
        }

        @Override
        public void onPageSelected(int position) {
            deselectDot((TextView) vh.dotsLayout.getChildAt(lastSelectedIndex));
            selectDot((TextView) vh.dotsLayout.getChildAt(position));
            lastSelectedIndex = position;
        }
    }
}