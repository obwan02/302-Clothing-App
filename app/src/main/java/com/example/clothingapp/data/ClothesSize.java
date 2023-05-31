package com.example.clothingapp.data;

public enum ClothesSize {
    XS(0),
    SMALL(1),
    MEDIUM(2),
    LARGE(3),
    XL(4);

    public final int size;

    ClothesSize(int size) {
        this.size = size;
    }

    public String getDisplayDescription() {
        switch (this) {
            case XS:
                return "≤XS (EXTRA SMALL + SMALLER)";
            case SMALL:
                return " S (SMALL)";
            case MEDIUM:
                return " M (MEDIUM)";
            case LARGE:
                return " L (LARGE)";
            case XL:
                return "≥XL (EXTRA LARGE + LARGER)";
        }

        return null;
    }
}
