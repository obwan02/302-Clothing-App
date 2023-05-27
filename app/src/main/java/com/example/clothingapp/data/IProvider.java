package com.example.clothingapp.data;

public interface IProvider<T> {

    int getCount();
    T getItem(int index);

}
