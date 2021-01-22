package com.example.ditebattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MatchingRoom extends AppCompatActivity {

    Button matchingRoomStartBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_room);
        matchingRoomStartBtn = (Button)findViewById(R.id.matchingRoomStartBtn);

        matchingRoomStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MatchingRoom.this, BattleRoom.class);
                startActivity(intent);
            }
        });
    }
}