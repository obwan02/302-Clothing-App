package com.example.clothingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clothingapp.R;
import com.example.clothingapp.activities.CartActivity;
import com.example.clothingapp.activities.CheckOutActivity;
import com.example.clothingapp.activities.MainActivity;
import com.example.clothingapp.activities.SavedActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationBar extends Fragment implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNav;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.frag_navigation, container, false);

        bottomNav = view.findViewById(R.id.bottom_navigation);

        var highlightId = getActivity().getIntent().getIntExtra("nav_id", R.id.menu_nav_home);
        bottomNav.setSelectedItemId(highlightId);
        bottomNav.setOnItemSelectedListener(this);




        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(this.getContext(), MainActivity.class);
        intent.putExtra("nav_id", item.getItemId());
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);

        if(item.getItemId() == R.id.menu_nav_home) {
            intent.setClass(this.getContext(), MainActivity.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.menu_nav_saved) {
            intent.setClass(this.getContext(), SavedActivity.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.menu_nav_cart) {
            intent.setClass(this.getContext(), CartActivity.class);
            startActivity(intent);
        }

        return true;
    }
}
