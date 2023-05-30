package com.example.clothingapp.data;

public class NullProvider extends IProvider {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int index) {
        return null;
    }
}
