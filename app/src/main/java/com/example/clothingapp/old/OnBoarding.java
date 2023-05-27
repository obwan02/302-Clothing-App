package com.example.clothingapp.old;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clothingapp.old.SliderAdapter;
import com.example.clothingapp.R;
import com.google.android.material.color.MaterialColors;

public class OnBoarding extends AppCompatActivity {


    ViewPager2 viewPager2;
    LinearLayout dotsLayout;

    SliderAdapter sliderAdapter;
    TextView[] dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        //Hooks
        viewPager2 = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);

        //Call adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager2.setAdapter((RecyclerView.Adapter) sliderAdapter);

        addDots(0);
        viewPager2.registerOnPageChangeCallback(changeListener);
    }

    private void addDots(int position){

        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for(int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0) {
            dots[position].setTextColor(MaterialColors.getColor(dots[position], com.google.android.material.R.attr.colorTertiary));
        }

    }

    ViewPager2.OnPageChangeCallback changeListener = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}