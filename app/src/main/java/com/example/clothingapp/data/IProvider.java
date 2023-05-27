package com.example.clothingapp.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public abstract class IProvider<T> implements Iterable<T> {

    public abstract int getCount();
    public abstract T getItem(int index);

    @NonNull
    @Override
    public final Iterator<T> iterator() {
        return new Iterator<T>() {
            final int count = getCount();
            int index = 0;
            @Override
            public boolean hasNext() {
                return index != count;
            }

            @Override
            public T next() {
                return getItem(index++);
            }
        };
    }

    public static <Y> IProvider<Y> filter(IProvider<Y> input, Predicate<Y> filter) {
        int filterCount = 0;

        for (int i = 0; i < input.getCount(); i++) {
            if(filter.test(input.getItem(i))) {
                filterCount++;
            }
        }

        final List<Integer> indexMap = new ArrayList<>(filterCount);
        for (int i = 0; i < input.getCount(); i++) {
            if(filter.test(input.getItem(i))) {
                indexMap.add(i);
            }
        }

        // Satisfy the compiler
        final int count = filterCount;
        return new IProvider<Y>() {

            @Override
            public int getCount() {
                return count;
            }

            @Override
            public Y getItem(int index) {
                int mappedIndex = indexMap.get(index);
                return input.getItem(mappedIndex);
            }
        };
    }
}
