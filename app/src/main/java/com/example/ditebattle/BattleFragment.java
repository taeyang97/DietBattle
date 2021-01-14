package com.example.ditebattle;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;


public class BattleFragment extends Fragment {
    ProgressBar progressBar;
    ImageView battleFragPlayerLeft;
    ImageView battleFragPlayerRight;
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
        battleFragPlayerLeft=view.findViewById(R.id.battleFragPlayerLeft);
        battleFragPlayerRight=view.findViewById(R.id.battleFragPlayerRight);
        Glide.with(this).load(R.drawable.battle_player_left).into(battleFragPlayerLeft);
        Glide.with(this).load(R.drawable.battle_player_right).into(battleFragPlayerRight);

        return view;
    }
}