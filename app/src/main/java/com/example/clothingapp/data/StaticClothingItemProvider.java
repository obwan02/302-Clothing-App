package com.example.clothingapp.data;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StaticClothingItemProvider implements IProvider<ClothingItem> {

    private static ClothingItem[] items = {
            new ClothingItem("Oversize White Hoodie",
                        10.00f,
                             ClothesSize.LARGE,
                             Stream.of("sherpa_jacket_brown",
                                             "sherpa_jacket_brown",
                                             "sherpa_jacket_brown",
                                             "sherpa_jacket_brown")
                                     .collect(Collectors.toCollection(ArrayList::new)),
                             "An oversize white hoodie for men or women",
                             "Hoodies",
                                      Gender.MALE),

            new ClothingItem("Dad Jeans - Black",
                    11.00f,
                    ClothesSize.MEDIUM,
                    Stream.of("sherpa_jacket_brown",
                                    "sherpa_jacket_brown",
                                    "sherpa_jacket_brown")
                            .collect(Collectors.toCollection(ArrayList::new)),
                    "A pair of black dad jeans - perfect for a baggy look",
                    "Pants",
                             Gender.FEMALE),

            new ClothingItem("Blue Shirt",
                    1.99f,
                    ClothesSize.XS,
                    Stream.of("sherpa_jacket_brown",
                                    "sherpa_jacket_brown")
                            .collect(Collectors.toCollection(ArrayList::new)),
                    "A simple blue T-Shirt, with a crew neck",
                    "Shirts",
                            Gender.MALE),

            new ClothingItem("Tweed Coat",
                    29.99f,
                    ClothesSize.SMALL,
                    Stream.of("sherpa_jacket_brown")
                            .collect(Collectors.toCollection(ArrayList::new)),
                    "A tweed coat",
                    "Coats",
                            Gender.MALE),
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
