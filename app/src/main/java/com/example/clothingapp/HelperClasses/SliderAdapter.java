package com.example.clothingapp.HelperClasses;

import android.content.Context;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.example.clothingapp.R;

public class SliderAdapter extends RecyclerView.Adapter {

    private Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    static final int images[] = {
            R.drawable.men_or_women,
            R.drawable.cloth_type,
            R.drawable.sit_back_and_relax
    };

    static final int headings[] = {
            R.string.first_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title

    };

    static final int descriptions[] = {
            R.string.first_slide_desc,
            R.string.second_slide_desc,
            R.string.third_slide_desc
    };

    private static class SlideViewHolder extends RecyclerView.ViewHolder {
        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void update(int imageId, int headingId, int descId) {
            ImageView imageView = this.itemView.findViewById(R.id.slider_image);
            TextView heading = this.itemView.findViewById(R.id.slider_heading);
            TextView desc = this.itemView.findViewById(R.id.slider_desc);

            imageView.setImageResource(imageId);
            heading.setText(headingId);
            desc.setText(descId);
        }

        public static SlideViewHolder inflateFromXML(@NonNull ViewGroup parent, Context context) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slides_layout, parent, false);
            return new SlideViewHolder(view);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SlideViewHolder viewHolder = SlideViewHolder.inflateFromXML(parent, this.context);
        viewHolder.update(images[0], headings[0], descriptions[0]);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SlideViewHolder viewHolder = (SlideViewHolder) holder;
        viewHolder.update(images[position], headings[position], descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
