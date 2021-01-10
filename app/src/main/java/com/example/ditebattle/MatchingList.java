package com.example.ditebattle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MatchingList extends AppCompatActivity {

    Button btn1,btn2,btn3, btnMatchingListRoomMakeMake, btnMatchingListRoomMakeCancel;
    EditText etMatchingListRoomMakeTitle, etMatchingListRoomMakeWeight;
    LinearLayout LayoutmatchingListRoomMakeMan, LayoutmatchingListRoomMakeGirl,
            LayoutmatchingListRoomMakeTop, LayoutmatchingListRoomMakeMiddle,
            LayoutmatchingListRoomMakeBottom;
    ArrayList<ItemData> items = new ArrayList<>();
    RecyclerView rView1;
    RecyclerAdapter rAdapter;
    Context context;
    Dialog roomMakeDialog, roomSearchDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchinglist);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);

        btn1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                btn1.setBackground(getDrawable(R.drawable.buttonclick));
                roomMakeDialog = new Dialog(MatchingList.this);
                roomMakeDialog.setContentView(R.layout.matchinglistroommakedialog);

                btnMatchingListRoomMakeMake = (Button)roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeMake);
                btnMatchingListRoomMakeCancel = (Button)roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeCancle);
                etMatchingListRoomMakeTitle = (EditText) roomMakeDialog.findViewById(R.id.etMatchingListRoomMakeTitle);
                etMatchingListRoomMakeWeight = (EditText) roomMakeDialog.findViewById(R.id.etMatchingListRoomMakeWeight);
                LayoutmatchingListRoomMakeMan = (LinearLayout) roomMakeDialog.findViewById(R.id.LayoutmatchingListRoomMakeMan);
                LayoutmatchingListRoomMakeGirl = (LinearLayout) roomMakeDialog.findViewById(R.id.LayoutmatchingListRoomMakeGirl);
                LayoutmatchingListRoomMakeTop= (LinearLayout) roomMakeDialog.findViewById(R.id.LayoutMatchingListRoomMakeTop);
                LayoutmatchingListRoomMakeMiddle = (LinearLayout) roomMakeDialog.findViewById(R.id.LayoutMatchingListRoomMakeMiddle);
                LayoutmatchingListRoomMakeBottom = (LinearLayout) roomMakeDialog.findViewById(R.id.LayoutMatchingListRoomMakeBottom);

                roomMakeDialog.show();
                roomMakeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                btnMatchingListRoomMakeMake.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        int i=1;
                        LayoutmatchingListRoomMakeMan.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {

                            }
                        });
                        LayoutmatchingListRoomMakeGirl.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {

                            }
                        });
                        LayoutmatchingListRoomMakeTop.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {

                            }
                        });
                        LayoutmatchingListRoomMakeMiddle.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {

                            }
                        });
                        LayoutmatchingListRoomMakeBottom.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {

                            }
                        });
                        items.add(i,new ItemData(i+1,etMatchingListRoomMakeTitle.getText().toString(),
                                ));
                        i++;
                    }
                });
                btnMatchingListRoomMakeCancel.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        roomMakeDialog.dismiss();
                    }
                });

            }
        });

        btn2.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                roomSearchDialog = new Dialog(MatchingList.this);
                roomSearchDialog.setContentView(R.layout.matchinglistroomsearchdialog);

                roomSearchDialog.show();
                roomSearchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });

        btn3.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

            }
        });

        rView1 = (RecyclerView)findViewById(R.id.rView1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false);
        rView1.setLayoutManager(layoutManager);

        items.add(0, new ItemData("1","고수방","여/65kg/고수"));
        items.add(1, new ItemData("2","초보만 들어오세요","남/88kg/중"));
        items.add(2, new ItemData("3","의지 있는 사람만","남/95kg/중"));
        items.add(3, new ItemData("4","패작방","남/185kg/하"));
        items.add(4, new ItemData("5","아무나 들어오세요","여/75kg/하"));
        rAdapter = new RecyclerAdapter(items);
        rView1.setAdapter(rAdapter);

    }
}