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

    Button btn1,btn2,btn3, btnMatchingListRoomMakeMake, btnMatchingListRoomMakeCancel,
            btnMatchingListRoomMakeMan, btnMatchingListRoomMakeGirl,
            btnMatchingListRoomMakeTop, btnMatchingListRoomMakeMiddle,
            btnMatchingListRoomMakeBottom;
    EditText etMatchingListRoomMakeTitle, etMatchingListRoomMakeWeight;
    ArrayList<ItemData> items = new ArrayList<>();
    RecyclerView rView1;
    RecyclerAdapter rAdapter;
    Context context;
    Dialog roomMakeDialog, roomSearchDialog;
    String gender, grade;
    int i=2;
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
                btnMatchingListRoomMakeMan = (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeMan);
                btnMatchingListRoomMakeGirl = (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeGirl);
                btnMatchingListRoomMakeTop= (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeTop);
                btnMatchingListRoomMakeMiddle = (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeMiddle);
                btnMatchingListRoomMakeBottom = (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeBottom);

                roomMakeDialog.show();
                roomMakeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                btnMatchingListRoomMakeMan.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        gender="남";
                    }
                });
                btnMatchingListRoomMakeGirl.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        gender="여";
                    }
                });
                btnMatchingListRoomMakeTop.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        grade="상";
                    }
                });
                btnMatchingListRoomMakeMiddle.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        grade="중";
                    }
                });
                btnMatchingListRoomMakeBottom.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        grade="하";
                    }
                });

                btnMatchingListRoomMakeMake.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {

                        items.add(i,new ItemData(String.valueOf(i+1),etMatchingListRoomMakeTitle.getText().toString(),
                                gender + "/" + etMatchingListRoomMakeWeight.getText().toString() +
                                 "/" + grade));
                        i++;
                        rAdapter.notifyDataSetChanged();
                        roomMakeDialog.dismiss();
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

        rAdapter = new RecyclerAdapter(items);
        rView1.setAdapter(rAdapter);

    }
}