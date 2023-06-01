package com.example.clothingapp.data;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public interface IProvider<T> extends Serializable {

    int getCount();
    T getItem(int index);

    @NonNull
    default Iterable<T> iter() {
        return () -> new Iterator<T>() {
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

    default IProvider<T> sort(Comparator<T> comparator) {
        List<T> list = IProvider.toList(this);
        list.sort(comparator);
        return IProvider.fromList(list);
    }

    default IProvider<T> filter(Predicate<T> filter) {
        int filterCount = 0;

        for (int i = 0; i < getCount(); i++) {
            if(filter.test(getItem(i))) {
                filterCount++;
            }
        }

        final List<Integer> indexMap = new ArrayList<>(filterCount);
        for (int i = 0; i < getCount(); i++) {
            if(filter.test(getItem(i))) {
                indexMap.add(i);
            }
        }

        // Satisfy the compiler
        final int count = filterCount;
        return new IProvider<T>() {

            @Override
            public int getCount() {
                return count;
            }

            @Override
            public T getItem(int index) {
                int mappedIndex = indexMap.get(index);
                return IProvider.this.getItem(mappedIndex);
            }
        };
    }

    default IProvider<T> slice(int startIndex, int endIndex) {
        // TODO: Error checking

        return new IProvider<T>() {
            @Override
            public int getCount() {
                return endIndex - startIndex;
            }

            @Override
            public T getItem(int index) {
                return IProvider.this.getItem(index - startIndex);
            }
        };
    }

    static <Y> List<Y> toList(IProvider<Y> provider) {
        var list = new ArrayList<Y>(provider.getCount());
        for (int i = 0; i < provider.getCount(); i++) {
            list.add(provider.getItem(i));
        }

        return list;
    }

    static <Y> IProvider<Y> fromList(List<Y> list) {
        final var readOnly = Collections.unmodifiableList(list);
        return new IProvider<Y>() {
            @Override
            public int getCount() {
                return readOnly.size();
            }

            @Override
            public Y getItem(int index) {
                return readOnly.get(index);
            }
        };
    }
}
