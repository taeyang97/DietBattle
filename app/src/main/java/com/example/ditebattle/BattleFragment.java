package com.example.ditebattle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class BattleFragment extends Fragment {

    ImageView FragBattleBackgroundImg;
    public BattleFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battle, container, false);
        // Inflate the layout for this fragment
        FragBattleBackgroundImg = view.findViewById(R.id.FragBattleBackgroundImg);

        return view;
    }
}