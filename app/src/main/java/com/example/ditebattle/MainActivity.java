package com.example.ditebattle;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ditebattle.database.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView main_nav_btn_kal, main_nav_btn_battle, main_nav_btn_board, nav_logout, NavTvUserID, NavTvUserLV;
    private AppBarConfiguration mAppBarConfiguration;
    ImageView mainHomeIvCheck, mainHomeIvMission, mainHomeIvMyInfo, home_iv_Mission_Exit, NavTvUserIcon;
    Dialog missionDialog;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDBReference = FirebaseDatabase.getInstance().getReference();
    HashMap<String, Object> childUpdates = new HashMap<>();
    Map<String, Object> userValue = null;
    User userInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home).setDrawerLayout(drawer)
                .build();
        NavigationView navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navigationView.getHeaderView(0);
        main_nav_btn_battle = (TextView) headerView.findViewById(R.id.main_nav_btn_battle);
        main_nav_btn_kal = (TextView) headerView.findViewById(R.id.main_nav_btn_kal);
        main_nav_btn_board = (TextView) headerView.findViewById(R.id.main_nav_btn_board);
        nav_logout = (TextView) headerView.findViewById(R.id.nav_logout);
        NavTvUserLV = (TextView) headerView.findViewById(R.id.NavTvUserLV);
        NavTvUserID = (TextView) headerView.findViewById(R.id.NavTvUserID);
        NavTvUserIcon = (ImageView) headerView.findViewById(R.id.NavTvUserIcon);
        mainHomeIvCheck = (ImageView) findViewById(R.id.mainHomeIvCheck);
        mainHomeIvMyInfo = (ImageView) findViewById(R.id.mainHomeIvMyInfo);
        mainHomeIvMission = (ImageView) findViewById(R.id.mainHomeIvMission);
        readDB();
        main_nav_btn_battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Matching.class);
                startActivity(intent);
                ;
            }
        });
        main_nav_btn_kal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User/" + user.getUid());
                ref.child("current_point").setValue(500);
                ref.child("total_point").setValue(500);
            }
        });
        main_nav_btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        nav_logout.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if(user!=null) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(user.getUid()).child("flag");
                    ref.setValue(0);
                }
                nav_logout.setTextColor(Color.parseColor("#99ffffff"));
                GoogleLoginActivity activity = new GoogleLoginActivity();
                activity.signOut();
                Toast.makeText(MainActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, GoogleLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mainHomeIvMission.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mainHomeIvMission.setBackgroundResource(R.drawable.layoutborderbuttonclick);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mainHomeIvMission.setBackgroundResource(R.drawable.layoutborderbutton);
                        break;
                    case MotionEvent.ACTION_UP:
                        mainHomeIvMission.setBackgroundResource(R.drawable.layoutborderbutton);
                        missionDialog = new Dialog(MainActivity.this);
                        missionDialog.setContentView(R.layout.activity_main_homemissiondialog);

                        missionDialog.show();
                        missionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        home_iv_Mission_Exit = (ImageView) missionDialog.findViewById(R.id.home_iv_Mission_Exit);

                        home_iv_Mission_Exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                missionDialog.dismiss();
                            }
                        });
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void readDB() {
        String sort_column_name = "age";
        Query sortbyUid = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());
        sortbyUid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    String key = postSnapshot.getKey();
//                    User get = postSnapshot.getValue(User.class);
//                    String[] info = {get.email, get.nickname, String.valueOf(get.age), String.valueOf(get.weight), String.valueOf(get.height), String.valueOf(get.bmi), String.valueOf(get.total_point), String.valueOf(get.current_point), get.gender};
//                    NavTvUserID.setText(info[1]);
//                }
                String key = snapshot.getKey();
                User get = snapshot.getValue(User.class);
                String[] info = {get.email, get.nickname, String.valueOf(get.age), String.valueOf(get.weight), String.valueOf(get.height), String.valueOf(get.bmi), String.valueOf(get.total_point), String.valueOf(get.current_point), get.gender};
                NavTvUserID.setText(info[0]);
                NavTvUserLV.setText("Lv : " + Integer.parseInt(info[6]) / 500);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}