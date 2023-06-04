package com.example.clothingapp.util;

import com.example.clothingapp.data.ClothingItem;
import com.example.clothingapp.data.IProvider;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CartManager implements IProvider<ClothingItem> {
    private static CartManager instance = new CartManager();

    private ArrayList<ClothingItem> cart = new ArrayList<>();

    private CartManager() {}

    @Override
    public int getCount() {
        return cart.size();
    }

    @Override
    public ClothingItem getItem(int index) {
        return cart.get(index);
    }

    public static CartManager getInstance() {
        return instance;
    }
    public static float getTotal() {
        return getInstance().cart.stream()
                .map(x -> x.getPrice())
                .reduce(0.0f, (a, b) -> a + b);
    }

    public static void addToCart(ClothingItem item) {
        instance.cart.add(item);
    }

    public static void clear() { instance.cart.clear(); }
}
