package com.example.clothingapp.data;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class JSONClothingProvider implements IProvider<ClothingItem>, Serializable {
    private ArrayList<ClothingItem> parsedItems;

    public JSONClothingProvider(Context context) {
        parsedItems = new ArrayList<>();

        String json = null;
        try {
            json = loadJSONString(context);
            loadFromJsonString(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCount() {
        return parsedItems.size();
    }

    @Override
    public ClothingItem getItem(int index) {
        return parsedItems.get(index);
    }

    private String loadJSONString(Context context) throws IOException {
        String json = null;

        InputStream is = context.getAssets().open("combo.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        return json;
    }

    private void loadFromJsonString(String json) throws JSONException {
        JSONArray jsonItems = new JSONArray(json);

        for(int i = 0; i < jsonItems.length(); i++) {
            JSONObject jsonItem = jsonItems.getJSONObject(i);

            ClothesSize size = ClothesSize.parseString(jsonItem.getString("size")).get();

            ClothingItem item = new ClothingItem(
                    jsonItem.getString("title"),
                    jsonItem.getInt("price"),
                    size,
                    convertImagesArray(jsonItem.getJSONArray("images")),
                    jsonItem.getString("description"),
                    jsonItem.getString("category").toUpperCase()
            );

            parsedItems.add(item);
        }
    }

    private static List<String> convertImagesArray(JSONArray array) throws JSONException {
        ArrayList<String> result = new ArrayList<>(array.length());
        for(int i = 0; i < array.length(); i++) {
            result.add("https:" + array.getString(i));
        }

        return result;
    }

}
