package com.example.ditebattle;

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

public class BattleRoom extends AppCompatActivity {
    ImageView battleRoomIvMission, battleRoomIvCaht, battleRoomIvPoint,
            ivMissionExit, ivMissionCheckBox1, ivMissionCheckBox2,
            ivMissionCheckBox3, ivMissionCheckBox4, ivMissionCheckBox5,
            ivChatingExit , ivChatingBuy1, ivChatingBuy2, ivChatingBuy3,
            ivPointExit;
    ImageView battleRoomBattleFragmentBtn2 , battleRoomBattleInfoFragmentBtn2;
    FrameLayout battleRoomFragContainer2;
    BattleFragment battleFragment;
    BattleInfoFragment battleInfoFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    Dialog missiondialog, chatingdialog, pointdialog;
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
        battleRoomBattleInfoFragmentBtn2 = (ImageView)findViewById(R.id.battleRoomBattleInfoFragmentBtn2);

        battleFragment = new BattleFragment();
        battleInfoFragment = new BattleInfoFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.battleRoomFragContainer2, battleFragment, "myFrag").commit();

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

        battleRoomIvCaht.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        battleRoomIvCaht.setBackgroundResource(R.drawable.layoutborderbuttonclick);

                    case MotionEvent.ACTION_CANCEL:

                        break;
                    case MotionEvent.ACTION_UP:
                        battleRoomIvCaht.setBackgroundResource(R.drawable.layoutborderbutton);
                        chatingdialog = new Dialog(BattleRoom.this);
                        chatingdialog.setContentView(R.layout.activity_battle_roomchatingdialog);

                        ivChatingExit = (ImageView)chatingdialog.findViewById(R.id.ivChatingExit);
                        ivChatingBuy1 = (ImageView)chatingdialog.findViewById(R.id.ivChatingBuy1);
                        ivChatingBuy2 = (ImageView)chatingdialog.findViewById(R.id.ivChatingBuy2);
                        ivChatingBuy3 = (ImageView)chatingdialog.findViewById(R.id.ivChatingBuy3);

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

                        ivPointExit = (ImageView)pointdialog.findViewById(R.id.ivPointExit);

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
                battleRoomBattleInfoFragmentBtn2.setImageResource(R.drawable.battlgrapbtn);
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
}