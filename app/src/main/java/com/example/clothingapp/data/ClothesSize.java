package com.example.clothingapp.data;

public enum ClothesSize {
    XS(0),
    SMALL(1),
    MEDIUM(2),
    LARGE(3),
    XL(4);

    public final int size;

    private ClothesSize(int size) {
        this.size = size;
    }
}
