package com.example.mynews.controller;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.test.espresso.IdlingResource;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApiFragment extends Fragment {


    public ApiFragment() {
        // Required empty public constructor
    }

    public static IdlingResource getCount() {
        return null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

}
