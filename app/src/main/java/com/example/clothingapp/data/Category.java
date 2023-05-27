package com.example.clothingapp.data;

import java.util.stream.Stream;

public class Category {

    private String name;
    private IProvider<ClothingItem> items;


    public Category(String name, IProvider<ClothingItem> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IProvider<ClothingItem> getItems() {
        return items;
    }

    public void setItems(IProvider<ClothingItem> items) {
        this.items = items;
    }
}
