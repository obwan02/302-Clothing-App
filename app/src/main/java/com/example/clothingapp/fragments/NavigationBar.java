package com.example.clothingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.app.ActivityOptions;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.clothingapp.R;
import com.example.clothingapp.activities.CartActivity;
import com.example.clothingapp.activities.CheckOutActivity;
import com.example.clothingapp.activities.ClothingItemActivity;
import com.example.clothingapp.activities.ListActivity;
import com.example.clothingapp.activities.MainActivity;
import com.example.clothingapp.activities.SavedActivity;
import com.example.clothingapp.activities.ThankYouActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.List;

public class NavigationBar extends Fragment implements NavigationBarView.OnItemSelectedListener {

    private static final HashMap<Class<? extends FragmentActivity>, Integer> activityNavBarMap = new HashMap<>();
    @IdRes
    private static int lastMenuId = R.id.menu_nav_home;

    // Initialise the map here
    static {
        activityNavBarMap.put(CartActivity.class, R.id.menu_nav_cart);
        activityNavBarMap.put(ListActivity.class, R.id.menu_nav_home);
        activityNavBarMap.put(MainActivity.class, R.id.menu_nav_home);
        activityNavBarMap.put(SavedActivity.class, R.id.menu_nav_saved);
    }

    private BottomNavigationView bottomNav;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.frag_navigation, container, false);

        bottomNav = view.findViewById(R.id.bottom_navigation);

        var navBarId = activityNavBarMap.get(requireActivity().getClass());
        Log.d("NavBar", String.format("Class: %s", requireActivity().getClass()));
        if(navBarId == null) {
            Log.d("NavBar", "Class was null");
            navBarId = lastMenuId;
        }

        bottomNav.setSelectedItemId(navBarId);
        bottomNav.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Class<? extends FragmentActivity> target = MainActivity.class;

        if(item.getItemId() == R.id.menu_nav_home) {
            target = MainActivity.class;
        } else if(item.getItemId() == R.id.menu_nav_saved) {
            target = SavedActivity.class;
        } else if(item.getItemId() == R.id.menu_nav_cart) {
            target = CartActivity.class;
        }

        // Hold the last menu id, if we're going to a
        // page that isn't directly accessible from the
        // nav bar. This makes it so that the app remembers
        // from what page you came.
        if(activityNavBarMap.containsValue(item.getItemId())) {
            lastMenuId = item.getItemId();
        }

        Intent intent = new Intent(this.getContext(), target);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
        return true;
    }
}
