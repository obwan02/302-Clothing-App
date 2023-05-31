package com.example.clothingapp.data;

import java.util.Optional;

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

    public static Optional<ClothesSize> parseString(String string) {
        string = string.strip();
        String twochar = string.substring(0, 2 > string.length() ? string.length() : 2);
        String onechar = string.substring(0, 1 > string.length() ? string.length() : 1);


        if(twochar.equalsIgnoreCase("XS")) {
            return Optional.of(XS);
        } else if(onechar.equalsIgnoreCase("S")) {
            return Optional.of(SMALL);
        } else if(onechar.equalsIgnoreCase("M")) {
            return Optional.of(MEDIUM);
        } else if(onechar.equalsIgnoreCase("L")) {
            return Optional.of(LARGE);
        } else if(twochar.equalsIgnoreCase("XL")) {
            return Optional.of(XL);
        }

        return Optional.empty();
    }
}
