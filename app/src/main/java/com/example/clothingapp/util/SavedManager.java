package com.example.clothingapp.util;

import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.IProvider;

import java.util.ArrayList;

public class SavedManager implements IProvider<ClothingItem> {

    private ArrayList<ClothingItem> saved = new ArrayList<>();
    private static SavedManager instance = new SavedManager();

    private SavedManager() {}

    @Override
    public int getCount() {
        return saved.size();
    }

    @Override
    public ClothingItem getItem(int index) {
        return saved.get(index);
    }

    public static SavedManager getInstance() {
        return instance;
    }

    public static void addToSaved(ClothingItem item) {
        // Don't add if it already exists
        for(var x : getInstance().saved) {
            if(x.getName().equalsIgnoreCase(item.getName())) {
                return;
            }
        }

        getInstance().saved.add(item);
    }

}