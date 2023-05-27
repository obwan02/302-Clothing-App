package com.example.clothingapp.data;

import java.util.List;

public class ClothingItem {

    private String name;
    // TODO: private Category category
    private float price;
    private ClothesSize size;
    private List<String> imageResourceNames;
    private String description;


    public ClothingItem(String name, float price, ClothesSize size, List<String> imageResourceNames, String description) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.imageResourceNames = imageResourceNames;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ClothesSize getSize() {
        return size;
    }

    public void setSize(ClothesSize size) {
        this.size = size;
    }

    public List<String> getImageResourceNames() {
        return imageResourceNames;
    }

    public void setImageResourceNames(List<String> imageResourceNames) {
        this.imageResourceNames = imageResourceNames;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
