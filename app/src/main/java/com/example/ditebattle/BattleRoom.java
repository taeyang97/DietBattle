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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ditebattle.database.Battle;
import com.example.ditebattle.database.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BattleRoom extends AppCompatActivity {
    ImageView battleRoomIvMission, battleRoomIvCaht, battleRoomIvPoint,
            ivMissionExit, ivMissionCheckBox1, ivMissionCheckBox2,
            ivMissionCheckBox3, ivMissionCheckBox4, ivMissionCheckBox5,
            ivChatingExit, ivChatingBuy1, ivChatingBuy2, ivChatingBuy3,
            ivPointExit;
    ImageView battleRoomBattleFragmentBtn2, battleRoomBattleInfoFragmentBtn2;
    //    FrameLayout battleRoomFragContainer2;
    BattleFragment battleFragment;
    BattleInfoFragment battleInfoFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    Dialog missiondialog, chatingdialog, pointdialog;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
//    Query userbyUid = databaseReference.child("User").child(user.getUid());
    ArrayList<String> myUserDb = new ArrayList<String>();
    ArrayList<String> myBattleDb = new ArrayList<String>();
    Boolean firstLogin = true;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle_room);
        ActionBar ac = getSupportActionBar();
        ac.hide();
        battleRoomIvMission = (ImageView) findViewById(R.id.battleRoomIvMission);
        battleRoomIvCaht = (ImageView) findViewById(R.id.battleRoomIvCaht);
        battleRoomIvPoint = (ImageView) findViewById(R.id.battleRoomIvPoint);
        battleRoomBattleFragmentBtn2 = (ImageView) findViewById(R.id.battleRoomBattleFragmentBtn2);
        battleRoomBattleInfoFragmentBtn2 = (ImageView) findViewById(R.id.battleRoomBattleInfoFragmentBtn2);
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

                        ivMissionExit = (ImageView) missiondialog.findViewById(R.id.ivMissionExit);
                        ivMissionCheckBox1 = (ImageView) missiondialog.findViewById(R.id.ivMissionCheckBox1);
                        ivMissionCheckBox2 = (ImageView) missiondialog.findViewById(R.id.ivMissionCheckBox2);
                        ivMissionCheckBox3 = (ImageView) missiondialog.findViewById(R.id.ivMissionCheckBox3);
                        ivMissionCheckBox4 = (ImageView) missiondialog.findViewById(R.id.ivMissionCheckBox4);
                        ivMissionCheckBox5 = (ImageView) missiondialog.findViewById(R.id.ivMissionCheckBox5);

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
                        ivMissionCheckBox1.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                ivMissionCheckBox1.setImageResource(R.drawable.checkbox2);
                            }
                        });
                        ivMissionCheckBox2.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                ivMissionCheckBox2.setImageResource(R.drawable.checkbox2);
                            }
                        });
                        ivMissionCheckBox3.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                ivMissionCheckBox3.setImageResource(R.drawable.checkbox2);
                            }
                        });
                        ivMissionCheckBox4.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                ivMissionCheckBox4.setImageResource(R.drawable.checkbox2);
                            }
                        });
                        ivMissionCheckBox5.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                ivMissionCheckBox5.setImageResource(R.drawable.checkbox2);
                            }
                        });
                        break;
                }
                return true;
            }
        });

        // 채팅 버튼 클릭 시
        battleRoomIvCaht.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        battleRoomIvCaht.setBackgroundResource(R.drawable.layoutborderbuttonclick);
                        break;
                    case MotionEvent.ACTION_UP:
                    
                        battleRoomIvCaht.setBackgroundResource(R.drawable.layoutborderbutton);
                        chatingdialog = new Dialog(BattleRoom.this);
                        chatingdialog.setContentView(R.layout.activity_battle_roomchatingdialog);
                        ivChatingExit = (ImageView) chatingdialog.findViewById(R.id.ivChatingExit);
                        ivChatingBuy1 = (ImageView) chatingdialog.findViewById(R.id.ivChatingBuy1);
                        ivChatingBuy2 = (ImageView) chatingdialog.findViewById(R.id.ivChatingBuy2);
                        ivChatingBuy3 = (ImageView) chatingdialog.findViewById(R.id.ivChatingBuy3);
                        chatingdialog.show();
                        chatingdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        ivChatingExit.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        ivChatingExit.setImageResource(R.drawable.exit2);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        ivChatingExit.setImageResource(R.drawable.exit);
                                        chatingdialog.dismiss();
                                        break;
                                }
                                return true;
                            }
                        });
                        ivChatingBuy1.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        ivChatingBuy1.setImageResource(R.drawable.buyimage2);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        ivChatingBuy1.setImageResource(R.drawable.buyimage);
                                        break;
                                }
                                return true;
                            }
                        });
                        ivChatingBuy2.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        ivChatingBuy2.setImageResource(R.drawable.buyimage2);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        ivChatingBuy2.setImageResource(R.drawable.buyimage);
                                        break;
                                }
                                return true;
                            }
                        });
                        ivChatingBuy3.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                switch (motionEvent.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        ivChatingBuy3.setImageResource(R.drawable.buyimage2);
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        ivChatingBuy3.setImageResource(R.drawable.buyimage);
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

        // 포인트 버튼 클릭 시
        battleRoomIvPoint.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        battleRoomIvPoint.setBackgroundResource(R.drawable.layoutborderbuttonclick);
                        break;
                    case MotionEvent.ACTION_UP:
                        battleRoomIvPoint.setBackgroundResource(R.drawable.layoutborderbutton);
                        pointdialog = new Dialog(BattleRoom.this);
                        pointdialog.setContentView(R.layout.activity_battle_roompointdialog);

                        ivPointExit = (ImageView) pointdialog.findViewById(R.id.ivPointExit);

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
//        ref.removeEventListener(battleValueEventListener);
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
                Battle get3 = snapshot.getValue(Battle.class);
                String[] battle_get = {get3.master, get3.guest, String.valueOf(get3.finish_time), String.valueOf(get3.masterHP), String.valueOf(get3.guestHP)};
                for (int i = 0; i < battle_get.length; i++) {
                    myBattleDb.add(battle_get[i]);
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
   ArrayList<String> deliverUser(){
      return myUserDb;
    };
   ArrayList<String> deliverBattle(){
      return myBattleDb;
    };
//    void ReadBattleDB() {
//        battleValueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Battle get = snapshot.getValue(Battle.class);
//                String[] info = {get.master, get.guest, String.valueOf(get.finish_time), String.valueOf(get.masterHP), String.valueOf(get.guestHP)};
//                for (int i = 1; i < info.length; i++) {
//                    myBattleDb.add(info[i]);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        };
//        if(myUserDb.get(11)!=null) {
//            ref = databaseReference.child("Battle").child(myUserDb.get(11));
//            ref.addValueEventListener(battleValueEventListener);
//        }
//    }
}