package com.example.ditebattle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ditebattle.database.Battle;
import com.example.ditebattle.database.Chating;
import com.example.ditebattle.database.User;
import com.example.ditebattle.mission.BattleDay;
import com.example.ditebattle.mission.ExerciseRoutine;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BattleRoom extends AppCompatActivity {
    ImageView battleRoomIvMission, battleRoomIvChat, battleRoomIvPoint,
            ivMissionExit, ivPointExit, ivPointBuy1, ivPointBuy2, ivPointBuy3,
            ivChatingExit;
    ImageView[] ivMissionCheckBox = new ImageView[5];
    TextView[] tvMission = new TextView[5];
    int CheckBox[] = {R.id.ivMissionCheckBox1, R.id.ivMissionCheckBox2, R.id.ivMissionCheckBox3,
            R.id.ivMissionCheckBox4, R.id.ivMissionCheckBox5};
    int tv[] = {R.id.tvMission1, R.id.tvMission2, R.id.tvMission3, R.id.tvMission4, R.id.tvMission5};
    ImageView battleRoomBattleFragmentBtn2, battleRoomBattleInfoFragmentBtn2;
    //    FrameLayout battleRoomFragContainer2;
    ListView lvChating;
    EditText edtChating;
    TextView battleRoomPointTv, battleRoomDateTv;
    Button btnChating;
    BattleFragment battleFragment;
    BattleInfoFragment battleInfoFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    Dialog missiondialog, pointdialog, chatdialog;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ValueEventListener userValueEventListener, battleValueEventListener;
//    Query userbyUid = databaseReference.child("User").child(user.getUid());
    ArrayList<String> myUserDb = new ArrayList<String>();
    ArrayList<String> myBattleDb = new ArrayList<String>();
    ArrayList<Object> secondBattle = new ArrayList<Object>();
    Boolean firstLogin = true;
    DatabaseReference ref;
    long now = System.currentTimeMillis();
    Date mDate = new Date(now);
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
    String getTime = simpleDate.format(mDate);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_room);
        ActionBar ac = getSupportActionBar();
        ac.hide();
        battleRoomDateTv = (TextView) findViewById(R.id.battleRoomDateTv);
        battleRoomPointTv = (TextView) findViewById(R.id.battleRoomPointTv);
        battleRoomIvMission = (ImageView) findViewById(R.id.battleRoomIvMission);
        battleRoomIvChat = (ImageView) findViewById(R.id.battleRoomIvChat);
        battleRoomIvPoint = (ImageView) findViewById(R.id.battleRoomIvPoint);
        battleRoomBattleFragmentBtn2 = (ImageView) findViewById(R.id.battleRoomBattleFragmentBtn2);
        battleRoomBattleInfoFragmentBtn2 = (ImageView) findViewById(R.id.battleRoomBattleInfoFragmentBtn2);

        battleRoomDateTv.setText(getTime);
        battleFragment = new BattleFragment();
        battleInfoFragment = new BattleInfoFragment();
        fragmentTransaction = fragmentManager.beginTransaction();

        firstReadDB();
        // 미션 버튼 클릭 시
        battleRoomIvMission.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        battleRoomIvMission.setBackgroundResource(R.drawable.layoutborderbuttonclick);
                        break;
                    case MotionEvent.ACTION_UP:
                        battleRoomIvMission.setBackgroundResource(R.drawable.layoutborderbutton);
                        missiondialog = new Dialog(BattleRoom.this);
                        missiondialog.setContentView(R.layout.activity_battle_roommissiondialog);

                        for(int i=0; i<ivMissionCheckBox.length; i++){
                            ivMissionCheckBox[i] = (ImageView) missiondialog.findViewById(CheckBox[i]);
                            tvMission[i] = (TextView) missiondialog.findViewById(tv[i]);
                        }

                        ivMissionExit = (ImageView) missiondialog.findViewById(R.id.ivMissionExit);

                        mission();

                        missiondialog.show();
                        missiondialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        ivMissionExit.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        ivMissionExit.setImageResource(R.drawable.exit2);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        ivMissionExit.setImageResource(R.drawable.exit);
                                        missiondialog.dismiss();
                                        break;
                                }
                                return true;
                            }
                        });
                        ivMissionExit.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                ivMissionExit.setImageDrawable(getResources().getDrawable(R.drawable.exit2));
                                missiondialog.dismiss();
                            }
                        });
                        for(int i=0; i<ivMissionCheckBox.length; i++){
                            int index = i;
                            ivMissionCheckBox[index].setOnClickListener(new OnSingleClickListener() {
                                @Override
                                public void onSingleClick(View v) {
                                    ivMissionCheckBox[index].setImageResource(R.drawable.checkbox2);
                                }
                            });
                        }
                        break;
                }
                return true;
            }
        });

        // 포인트 버튼 클릭 시
        battleRoomIvPoint.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        battleRoomIvPoint.setBackgroundResource(R.drawable.layoutborderbuttonclick);
                        break;
                    case MotionEvent.ACTION_UP:
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Battle").child(myUserDb.get(10));
                        ref.child("guestHP").setValue((int)(Math.random()*400));
                        ref.child("masterHP").setValue((int)(Math.random()*400));

                        battleRoomIvPoint.setBackgroundResource(R.drawable.layoutborderbutton);
                        pointdialog = new Dialog(BattleRoom.this);
                        pointdialog.setContentView(R.layout.activity_battle_roompointdialog);
                        ivPointExit = (ImageView) pointdialog.findViewById(R.id.ivPointExit);
                        ivPointBuy1 = (ImageView) pointdialog.findViewById(R.id.ivPointBuy1);
                        ivPointBuy2 = (ImageView) pointdialog.findViewById(R.id.ivPointBuy2);
                        ivPointBuy3 = (ImageView) pointdialog.findViewById(R.id.ivPointBuy3);
                        pointdialog.show();
                        pointdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        ivPointExit.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        ivPointExit.setImageResource(R.drawable.exit2);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        ivPointExit.setImageResource(R.drawable.exit);
                                        pointdialog.dismiss();
                                        break;
                                }
                                return true;
                            }
                        });
                        ivPointBuy1.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        ivPointBuy1.setImageResource(R.drawable.buyimage2);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        ivPointBuy1.setImageResource(R.drawable.buyimage);
                                        break;
                                }
                                return true;
                            }
                        });
                        ivPointBuy2.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        ivPointBuy2.setImageResource(R.drawable.buyimage2);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        ivPointBuy2.setImageResource(R.drawable.buyimage);
                                        break;
                                }
                                return true;
                            }
                        });
                        ivPointBuy3.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        ivPointBuy3.setImageResource(R.drawable.buyimage2);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        ivPointBuy3.setImageResource(R.drawable.buyimage);
                                        break;
                                }
                                return true;
                            }
                        });
                        break;
                }
                return true;
            }
        });

        // 채팅 버튼 클릭 시
        battleRoomIvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        battleRoomIvChat.setBackgroundResource(R.drawable.layoutborderbuttonclick);
                        break;
                    case MotionEvent.ACTION_UP:
                        battleRoomIvChat.setBackgroundResource(R.drawable.layoutborderbutton);
                        chatdialog = new Dialog(BattleRoom.this);
                        chatdialog.setContentView(R.layout.activity_battle_roomchatdialog);

                        ivChatingExit = (ImageView) chatdialog.findViewById(R.id.ivChatingExit);
                        lvChating = (ListView) chatdialog.findViewById(R.id.lvChating);
                        edtChating = (EditText) chatdialog.findViewById(R.id.edtChating);
                        btnChating = (Button)chatdialog.findViewById(R.id.btnChating);

                        openChat();

                        chatdialog.show();
                        chatdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        btnChating.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                Chating chat = new Chating(user.getEmail(),
                                        edtChating.getText().toString()); // RecyclerItemData를 이용하여 데이터를 묶는다.
                                databaseReference.child("Battle").child(myUserDb.get(10)).child("chating").push().setValue(chat); // 데이터 푸쉬
                                edtChating.setText(""); //입력창 초기화
                            }
                        });

                        ivChatingExit.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        ivChatingExit.setImageResource(R.drawable.exit2);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        ivChatingExit.setImageResource(R.drawable.exit);
                                        chatdialog.dismiss();
                                        break;
                                }
                                return true;
                            }
                        });
                        break;
                }
                return true;
            }
        });

        battleRoomBattleFragmentBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.battleRoomFragContainer2, battleFragment, "myFrag").commit();
                battleRoomBattleFragmentBtn2.setImageResource(R.drawable.battlefragbtnclick);
                battleRoomBattleInfoFragmentBtn2.setImageResource(R.drawable.battlgrapbtn2);
            }
        });
        battleRoomBattleInfoFragmentBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.battleRoomFragContainer2, battleInfoFragment, "myFrag").commit();
                battleRoomBattleFragmentBtn2.setImageResource(R.drawable.battlfragbtn);
                battleRoomBattleInfoFragmentBtn2.setImageResource(R.drawable.battlegrapbtnclick);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    void firstReadDB() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (firstLogin) {
                    User get = snapshot.child("User").child(user.getUid()).getValue(User.class);
                    String[] user = {get.email, get.nickname, String.valueOf(get.age), String.valueOf(get.weight), String.valueOf(get.height), String.valueOf(get.bmi),
                            String.valueOf(get.total_point), String.valueOf(get.current_point), get.gender, String.valueOf(get.flag), get.battle};
                    for (int i=0; i < user.length; i++) {
                        myUserDb.add(user[i]);
                    }
                }
                Battle get2 = snapshot.child("Battle").child(myUserDb.get(10)).getValue(Battle.class);
                String[] battle = {get2.master, get2.guest, String.valueOf(get2.finish_time), String.valueOf(get2.masterHP), String.valueOf(get2.guestHP)};
                for (int i = 0; i < battle.length; i++) {
                    myBattleDb.add(battle[i]);
                }
                if(firstLogin) {
                    long t = System.currentTimeMillis() / 1000;
                    long totalSec = Long.parseLong(myBattleDb.get(2)) - t;
                    int currentday = (int)(totalSec/86400);
                    battleRoomPointTv.setText(myUserDb.get(7));
                    fragmentTransaction.replace(R.id.battleRoomFragContainer2, battleFragment, "myFrag").commit();
                    firstLogin = false;
                    readBattle();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void readBattle(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Battle").child(myUserDb.get(10));
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().equals("guestHP")){
                    myBattleDb.set(4,String.valueOf(snapshot.getValue()));
                }else if(snapshot.getKey().equals("masterHP")){
                    myBattleDb.set(3,String.valueOf(snapshot.getValue()));
                }
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        Chating chat = dataSnapshot.getValue(Chating.class);
        adapter.add(chat.getUserName() + " : " + chat.getMessage());
    }

    private void removeMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        Chating chat = dataSnapshot.getValue(Chating.class);
        adapter.remove(chat.getUserName() + " : " + chat.getMessage());
    }

    private void openChat() {
        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        lvChating.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("Battle").child(myUserDb.get(10)).child("chating").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addMessage(dataSnapshot, adapter);
                Log.e("LOG", "s:" + s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeMessage(dataSnapshot, adapter);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
   ArrayList<String> deliverUser(){
      return myUserDb;
    };
   ArrayList<String> deliverBattle(){
      return myBattleDb;
    };

    protected void mission() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 배틀 정보 가져오기
                Battle get2 = snapshot.child("Battle").child(myUserDb.get(10)).getValue(Battle.class);
                String[] battle = {get2.master, get2.guest, String.valueOf(get2.finish_time),
                        String.valueOf(get2.masterHP), String.valueOf(get2.guestHP), get2.grade};

                BattleDay battleDay = snapshot.child("Battle").child(myUserDb.get(10)).child("days").getValue(BattleDay.class);
                String[] days = {battleDay.firstday, battleDay.twoday, battleDay.threeday, battleDay.fourday,
                        battleDay.fiveday, battleDay.sixday, battleDay.lastday};

                long t = System.currentTimeMillis()/1000 + 604800; // 대결 끝나는 시간
                long firstday = Long.parseLong(battle[2])-604800;
                long twoday = Long.parseLong(battle[2])-518400;
                long threeday = Long.parseLong(battle[2])-432000;
                long fourday = Long.parseLong(battle[2])-345600;
                long fiveday = Long.parseLong(battle[2])-259200;
                long sixday = Long.parseLong(battle[2])-172800;
                long lastday = Long.parseLong(battle[2])-86400;

                int random = (int)(Math.random()*2);



                // 신체 부위 가져오기
                String[] bodypartsStr = {"chest", "core", "fullbody", "legs"};
                // 운동 종목 가져오기
                String[][] Exercise = {{"declinepush", "tripush"},{"regraise", "twistplank"},
                        {"burpee", "mountainclimer"},{"lunge", "squat"}};

                // 일곱 번째 날
                if(t>lastday && days[6].equals("true")){

                    for(int i=0; i<bodypartsStr.length; i++){

                        // 운동 갯수 가져오기
                        ExerciseRoutine routine= snapshot.child("misson").child(battle[5]).child(bodypartsStr[i])
                                .child(Exercise[i][random]).getValue(ExerciseRoutine.class);
                        String[] ExerciseRoutineInt = {String.valueOf(routine.reps), String.valueOf(routine.set),
                                String.valueOf(routine.total), routine.name};

                        tvMission[i].setText(ExerciseRoutineInt[3] + " " + ExerciseRoutineInt[0]+"개 "+ExerciseRoutineInt[1]+"셋트 하기");
                    }

                    // 여섯 번째 날
                } else if(t>sixday){

                    // 다섯 번째 날
                } else if(t>fiveday){

                    // 네 번째 날
                } else if(t>fourday){

                    // 세 번째 날
                } else if(t>threeday){

                    // 두 번째 날
                } else if(t>twoday){

                    // 첫 번째 날
                } else if(t>firstday){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}