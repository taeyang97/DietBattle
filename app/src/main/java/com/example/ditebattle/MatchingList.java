package com.example.ditebattle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MatchingList extends AppCompatActivity {

    Button btn1,btn2,btn3, btnMatchingListRoomMakeMake, btnMatchingListRoomMakeCancel,
            btnMatchingListRoomMakeMan, btnMatchingListRoomMakeGirl,
            btnMatchingListRoomMakeTop, btnMatchingListRoomMakeMiddle,
            btnMatchingListRoomMakeBottom , testBtn;
    EditText etMatchingListRoomMakeTitle, etMatchingListRoomMakeWeight;
    ArrayList<RecyclerItemData> items = new ArrayList<>();
    RecyclerView rView1;
    RecyclerAdapter rAdapter;
    Context context;
    Dialog roomMakeDialog, roomSearchDialog;
    String gender=null, grade=null;
    CardView cvList;
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
        testBtn =(Button)findViewById(R.id.testBtn);
        cvList = (CardView)findViewById(R.id.cvList);

        // 방만들기 버튼
        btn1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                roomMakeDialog = new Dialog(MatchingList.this);
                roomMakeDialog.setContentView(R.layout.matchinglistroommakedialog);
                {
                    btnMatchingListRoomMakeMake = (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeMake);
                    btnMatchingListRoomMakeCancel = (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeCancle);
                    etMatchingListRoomMakeTitle = (EditText) roomMakeDialog.findViewById(R.id.etMatchingListRoomMakeTitle);
                    etMatchingListRoomMakeWeight = (EditText) roomMakeDialog.findViewById(R.id.etMatchingListRoomMakeWeight);
                    btnMatchingListRoomMakeMan = (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeMan);
                    btnMatchingListRoomMakeGirl = (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeGirl);
                    btnMatchingListRoomMakeTop = (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeTop);
                    btnMatchingListRoomMakeMiddle = (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeMiddle);
                    btnMatchingListRoomMakeBottom = (Button) roomMakeDialog.findViewById(R.id.btnMatchingListRoomMakeBottom);
                } // 다이럴로그 버튼 연결
                roomMakeDialog.show();
                roomMakeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                btnMatchingListRoomMakeMan.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        gender="남";
                        btnMatchingListRoomMakeMan.setSelected(true);
                        btnMatchingListRoomMakeGirl.setSelected(false);
                    }
                });
                btnMatchingListRoomMakeGirl.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        gender="여";
                        btnMatchingListRoomMakeMan.setSelected(false);
                        btnMatchingListRoomMakeGirl.setSelected(true);
                    }
                });
                btnMatchingListRoomMakeTop.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        grade="상";
                        btnMatchingListRoomMakeTop.setSelected(true);
                        btnMatchingListRoomMakeMiddle.setSelected(false);
                        btnMatchingListRoomMakeBottom.setSelected(false);
                    }
                });
                btnMatchingListRoomMakeMiddle.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        grade="중";
                        btnMatchingListRoomMakeTop.setSelected(false);
                        btnMatchingListRoomMakeMiddle.setSelected(true);
                        btnMatchingListRoomMakeBottom.setSelected(false);
                    }
                });
                btnMatchingListRoomMakeBottom.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        grade="하";
                        btnMatchingListRoomMakeTop.setSelected(false);
                        btnMatchingListRoomMakeMiddle.setSelected(false);
                        btnMatchingListRoomMakeBottom.setSelected(true);
                    }
                });

                //방 만들기 버튼
                btnMatchingListRoomMakeMake.setOnClickListener(new OnSingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        String title=etMatchingListRoomMakeTitle.getText().toString();
                        String weight=etMatchingListRoomMakeWeight.getText().toString();
                        if(title.getBytes().length <= 0){
                            Toast.makeText(getApplicationContext(),"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                        }else if(weight.getBytes().length <=0){
                            Toast.makeText(getApplicationContext(),"몸무게를 입력해주세요",Toast.LENGTH_SHORT).show();
                        }else if(grade==null){
                            Toast.makeText(getApplicationContext(),"난이도를 선택해주세요",Toast.LENGTH_SHORT).show();
                        }else {
                            items.add(i, new RecyclerItemData(String.valueOf(i + 1), title,
                                    gender + "/" + weight +
                                            "/" + grade));
                            i++;
                            rAdapter.notifyDataSetChanged();
                            roomMakeDialog.dismiss();
                        }
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

        // 방 찾기 버튼
        btn2.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                roomSearchDialog = new Dialog(MatchingList.this);
                roomSearchDialog.setContentView(R.layout.matchinglistroomsearchdialog);

                roomSearchDialog.show();
                roomSearchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });

        // 새로고침 버튼
        btn3.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

            }
        });

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MatchingList.this, BattleRoom.class);
                startActivity(intent);
            }
        });
        //리싸이클러뷰 레이아웃 매니저를 통해 형태 설정
        rView1 = (RecyclerView)findViewById(R.id.rView1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false);
        rView1.setLayoutManager(layoutManager);

        // 아이템 추가 코드
        items.add(0, new RecyclerItemData("1","고수방","여/65kg/고수"));
        items.add(1, new RecyclerItemData("2","초보만 들어오세요","남/88kg/중"));

        // 어댑터에 아이템데이터 넣어주고 리싸이클러뷰 장착
        rAdapter = new RecyclerAdapter(items);
        rView1.setAdapter(rAdapter);
    }
}