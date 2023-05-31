package com.example.clothingapp.data;

import java.io.Serializable;
import java.util.List;

public class ClothingItem implements Serializable {

    private String name;
    // TODO: private Category category
    private float price;
    private ClothesSize size;
    private List<String> imageUrls;
    private String description;
    // NOTE(spec): Change from original spec here
    private String category;

    private Gender gender;


    public ClothingItem(String name, float price, ClothesSize size, List<String> imageUrls, String description, String category, Gender gender) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.imageUrls = imageUrls;
        this.description = description;
        this.category = category;
        this.gender = gender;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Gender getGender() { return gender; }

    public void setGender(Gender gender) { this.gender = gender; }
}
