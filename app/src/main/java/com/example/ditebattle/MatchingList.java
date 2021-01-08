package com.example.ditebattle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class MatchingList extends AppCompatActivity {

    Button btn1,btn2,btn3;
    public static ArrayList<ItemData> items = new ArrayList<>();
    public static RecyclerView rView1;
    public static RecyclerAdapter rAdapter;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchinglist);

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        rView1 = (RecyclerView)findViewById(R.id.rView1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false);
        rView1.setLayoutManager(layoutManager);
        items.add(0, new ItemData("1","고수방","여/65kg/고수"));
        rAdapter = new RecyclerAdapter(context,items);
        rView1.setAdapter(rAdapter);

    }
}