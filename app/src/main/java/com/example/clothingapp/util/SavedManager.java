package com.example.clothingapp.util;

import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.IProvider;

import java.util.ArrayList;
import java.util.function.Consumer;

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
        if(!isSaved(item)) {
            getInstance().saved.add(item);
        }
    }

    public static boolean removeFromSaved(ClothingItem item) {
        return getInstance().saved.remove(item);
    }

    public static void toggleSaved(ClothingItem item) {
        if(isSaved(item)) {
            removeFromSaved(item);
        } else {
            addToSaved(item);
        }
    }

    public static boolean isSaved(ClothingItem item) {
        return getInstance().saved.stream().anyMatch(x -> x.getName().equalsIgnoreCase(item.getName()));
    }
}