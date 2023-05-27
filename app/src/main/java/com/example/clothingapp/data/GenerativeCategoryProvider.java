package com.example.clothingapp.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class GenerativeCategoryProvider extends IProvider<Category> {

    private List<Category> categories;

    //NOTE(spec): Spec changed here
    public GenerativeCategoryProvider(IProvider<ClothingItem> allItems) {

        // TODO: Make category names non case-sensitive
        Set<String> categoryNames = new HashSet<>();
        for(ClothingItem item : allItems) {
            categoryNames.add(item.getCategory());
        }

        for(String category : categoryNames) {
            // Filter all items that have the same category, and assign them under the
            // current category
            categories.add(new Category(category, IProvider.filter(allItems, clothingItem -> clothingItem.getCategory().equals(category))));
        }
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int index) {
        return categories.get(index);
    }
}
