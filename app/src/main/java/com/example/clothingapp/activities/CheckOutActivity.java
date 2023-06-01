package com.example.clothingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.clothingapp.R;

public class CheckOutActivity extends AppCompatActivity {

    private static class ViewHolder {
        public final Button purchaseButton;

        ViewHolder(Activity a) {
            purchaseButton = a.findViewById(R.id.checkout_purchase_button);
        }
    }

    private ViewHolder vh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        vh = new ViewHolder(this);
        vh.purchaseButton.setOnClickListener(v -> this.onPurchaseButtonClicked(v));
    }

    public void onPurchaseButtonClicked(View v) {
        var intent = new Intent(this, ThankYouActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}