package com.example.clothingapp.data;

import java.util.Optional;

public enum Gender {
    MALE,
    FEMALE;

    public static Optional<Gender> parseString(String string) {
        string = string.strip();
        if(string.equalsIgnoreCase("male")) {
            return Optional.of(MALE);
        } else if(string.equalsIgnoreCase("female")) {
            return Optional.of(FEMALE);
        }

        return Optional.empty();
    }
}
