package com.example.clothingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.clothingapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SortBottomSheet extends BottomSheetDialogFragment {

    public static final String TAG = "SortBottomSheet";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_sort_list, container, false);
    }
}
