package com.example.ditebattle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class BattleRoom extends AppCompatActivity {

    Button battleRoomBattleFragmentBtn2 , battleRoomBattleInfoFragmentBtn2;
    FrameLayout battleRoomFragContainer2;
    BattleFragment battleFragment;
    BattleInfoFragment battleInfoFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_room);
        battleRoomBattleFragmentBtn2 = (Button)findViewById(R.id.battleRoomBattleFragmentBtn2);
        battleRoomBattleInfoFragmentBtn2 = (Button)findViewById(R.id.battleRoomBattleInfoFragmentBtn2);
        battleRoomFragContainer2 = (FrameLayout)findViewById(R.id.battleRoomFragContainer2);
        battleFragment = new BattleFragment();
        battleInfoFragment = new BattleInfoFragment();


        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.battleRoomFragContainer2, battleFragment, "myFrag").commit();

        battleRoomBattleFragmentBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.battleRoomFragContainer2, battleFragment, "myFrag").commit();
            }
        });
        battleRoomBattleInfoFragmentBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.battleRoomFragContainer2, battleInfoFragment, "myFrag").commit();
            }
        });
    }
}