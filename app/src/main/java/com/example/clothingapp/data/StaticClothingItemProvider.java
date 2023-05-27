package com.example.clothingapp.data;

import java.util.ArrayList;

public class StaticClothingItemProvider extends IProvider<ClothingItem> {

    private static ClothingItem[] items = {
            new ClothingItem("Oversize White Hoodie",
                        10.00f,
                             ClothesSize.LARGE,
                             new ArrayList<>(),
                             "An oversize white hoodie for men or women",
                             "Hoodies"),

            new ClothingItem("Dad Jeans - Black",
                    11.00f,
                    ClothesSize.MEDIUM,
                    new ArrayList<>(),
                    "A pair of black dad jeans - perfect for a baggy look",
                    "Pants"),

            new ClothingItem("Blue Shirt",
                    1.99f,
                    ClothesSize.XS,
                    new ArrayList<>(),
                    "A simple blue T-Shirt, with a crew neck",
                    "Shirts"),

            new ClothingItem("Tweed Coat",
                    29.99f,
                    ClothesSize.SMALL,
                    new ArrayList<>(),
                    "A tweed coat",
                    "Coats"),
    };

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public ClothingItem getItem(int index) {
        return items[index];
    }
}
