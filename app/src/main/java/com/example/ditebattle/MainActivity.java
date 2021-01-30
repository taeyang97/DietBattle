package com.example.ditebattle;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    TextView main_nav_btn_kal, main_nav_btn_battle, main_nav_btn_board, nav_logout, NavTvUserID, NavTvUserLV;
    private AppBarConfiguration mAppBarConfiguration;
    ImageView mainHomeIvCheck, mainHomeIvMission, mainHomeIvMyInfo, home_iv_Mission_Exit, NavTvUserIcon,home_iv_Weight_Iv;
    Dialog missionDialog,weightDialog;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    HashMap<String, Object> childUpdates = new HashMap<>();
    Map<String, Object> userValue = null;
    User userInfo = null;
    String battle;
    TextView main_Point_Tv,main_Lv_Tv,main_Exp_Tv, home_iv_Weight_Tv;
    BluetoothAdapter bluetoothAdapter;
    static final int REQUEST_ENABLE_BT=10;
    int pairedDeviceCount=0; // 연결된 장치 갯수
    Set<BluetoothDevice> deviceSet; // 연결된 장치 목록
    BluetoothDevice remoteDevice; // 연결해서 사용할 장치 이름
    BluetoothSocket socket=null; // 블루투스와 연결할 소켓
    OutputStream outputStream=null;
    InputStream inputStream=null;
    Thread workerThread=null;
    String strDelimiter="\n";
    char charDelimiter='\n';
    byte[] readBuffer;
    int readBufferPosition, max=0;
    Button home_iv_Weight_Btn;

    @SuppressLint("ClickableViewAccessibility")
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
        main_Lv_Tv = (TextView)findViewById(R.id.main_Lv_Tv);
        main_Point_Tv = (TextView)findViewById(R.id.main_Point_Tv);
        main_Exp_Tv = (TextView)findViewById(R.id.main_Exp_Tv);

        readDB();
        main_nav_btn_battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(battle.equals("false")) {
                    intent = new Intent(MainActivity.this, Matching.class);
                }else{
                    intent = new Intent(MainActivity.this, BattleRoom.class);
                }
                startActivity(intent);

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
        mainHomeIvCheck.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mainHomeIvCheck.setBackgroundResource(R.drawable.layoutborderbuttonclick);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mainHomeIvCheck.setBackgroundResource(R.drawable.layoutborderbutton);
                        break;
                    case MotionEvent.ACTION_UP:
                        mainHomeIvCheck.setBackgroundResource(R.drawable.layoutborderbutton);
                        weightDialog = new Dialog(MainActivity.this);
                        weightDialog.setContentView(R.layout.activity_main_homeweightcheck);
                        home_iv_Weight_Btn =(Button) weightDialog.findViewById(R.id.home_iv_Weight_Btn);
                        home_iv_Weight_Tv = (TextView) weightDialog.findViewById(R.id.home_iv_Weight_Tv);
                        home_iv_Weight_Iv = (ImageView)weightDialog.findViewById(R.id.home_iv_Weight_Iv);
                        weightDialog.show();

                        home_iv_Weight_Btn.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                checkBluetooth();
                            }
                        });

                        home_iv_Weight_Iv.setOnClickListener(new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                home_iv_Weight_Iv.setImageResource(R.drawable.exit2);
                                weightDialog.dismiss();
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
                String[] info = {get.email, get.nickname, String.valueOf(get.age), String.valueOf(get.weight), String.valueOf(get.height), String.valueOf(get.bmi), String.valueOf(get.total_point), String.valueOf(get.current_point), get.gender,get.battle};
                NavTvUserID.setText(info[1]);
                NavTvUserLV.setText("Lv : " + Integer.parseInt(info[6]) / 500);
                battle = get.battle;
                main_Lv_Tv.setText("LV " +(Integer.parseInt(info[6]) / 500));
                main_Exp_Tv.setText((Integer.parseInt(info[6]) % 500)+"/500");
                main_Point_Tv.setText(info[7]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void checkBluetooth() {
        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter(); // 어댑터를 반환받는다.
        if(bluetoothAdapter==null){
            // 장치가 블루투스를 지원하지 않는 경우
            // 방법1 => finish(); // 앱 종료
            // 방법2 => Toast로 표시
            // 방법3 => dialog로 표시
            finish();
        }else {
            // 장치가 블루투스를 지원하는 경우
            if(!bluetoothAdapter.isEnabled()){
                // 블루투스가 꺼져있는 경우
                Intent enableBTintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBTintent,REQUEST_ENABLE_BT);
            } else {
                // 블루투스가 켜져있는 경우
                selectDevice();
            }
        }
    }
    //블루투스 장치를 선택하는 메소드(연결 된 장치중에서 선택
    void selectDevice() {
        deviceSet = bluetoothAdapter.getBondedDevices();
        pairedDeviceCount=deviceSet.size(); // 연결된 장치들의 갯수
        if(pairedDeviceCount==0){
            showToast("페어링된 장치가 하나도 없습니다.");
        }else {
            // 다이얼로그로 연결된 장치중에서 선택하는 코드
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("블루투스 장치 선택");
            List<String> listItems = new ArrayList<String>();
            for(BluetoothDevice device:deviceSet){
                listItems.add(device.getName());
            }
            listItems.add("취소");
            CharSequence items[]=listItems.toArray(new CharSequence[listItems.size()]);
            // 동적 배열을 일반 배열로 변경
            builder.setItems(items, new DialogInterface.OnClickListener() {
                // .setItems()는 일반 배열밖에 못가져와서 동적 배열을 일반 배열로 바꿔주었다.
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which==pairedDeviceCount) {
                        showToast("취소를 선택하였습니다.");
                    }else {
                        //연결할 장치를 선택할 경우( 선택한 장치와 연결을 시도함)
                        connectToSelectedDevice(items[which].toString());
                    }
                }
            });
            builder.setCancelable(false); // 뒤로가기 버튼 사용 금지
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }

    //블루투스 장치와의 연결
    void connectToSelectedDevice(String selectedDeviceName) {
        remoteDevice=getDeviceFromBondedList(selectedDeviceName);
        // 아두이노 식별 번호
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        try {
            //socket에 uuid를 호출
            socket=remoteDevice.createRfcommSocketToServiceRecord(uuid);
            socket.connect(); // 두 기기의 연결 완료
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            beginListenForData();
        }catch (Exception e){
            showToast("해당 기기와 연결할 수 없습니다.");
        }
    }
    //데이터 수신 준비 및 처리
    void beginListenForData() {
        Handler handler=new Handler();
        readBuffer=new byte[1024]; // 수신 버퍼
        readBufferPosition=0; // 버퍼 내 수신 문자 저장 위치
        //문자열 수신 쓰레드
        workerThread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                    try {
                        int bytesAvailable=inputStream.available(); // 수신 데이터 확인
                        if(bytesAvailable > 0){
                            byte[] paketBytes=new byte[bytesAvailable];
                            inputStream.read(paketBytes);
                            for(int i=0; i<bytesAvailable; i++) {
                                byte b=paketBytes[i];
                                if(b==charDelimiter){
                                    byte[] encodedBytes=new byte[readBufferPosition];
                                    System.arraycopy(readBuffer,0,encodedBytes,
                                            0,encodedBytes.length);
                                    final String data=new String(encodedBytes,"US-ASCII");
                                    readBufferPosition=0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //수신된 문자열 데이터에 대한 처리 작업
                                            if(max<Integer.parseInt(data)) {
                                                max=Integer.parseInt(data);
                                            }
                                                home_iv_Weight_Tv.setText(max+" kg");
                                            DatabaseReference mDBReference = FirebaseDatabase.getInstance().getReference();
                                            mDBReference.child("User").child(user.getUid()).child("weight").setValue(max);
                                        }
                                    });

                                }else {
                                    readBuffer[readBufferPosition++]=b;
                                }
                            }
                        }
                    }catch (IOException e){
                        showToast("데이터 수신 중 오류가 발생했습니다.");
                    }
                }
            }
        });
        workerThread.start();
    }
    //데이터 송신
    void sendData(String msg) {
        msg+=strDelimiter; //문자열 종료 표시
        try {
            outputStream.write(msg.getBytes()); // 아두이노로 문자열 전송
        }catch (Exception e){
            showToast("문자열 전송 도중에 에러가 발생했습니다.");
        }
    }

    //블루투스 소켓 닫기 및 데이터 수신 쓰레드 종료
    @Override
    protected void onDestroy() {
        // 앱이 종료되기 전의 생명주기 메소드
        try{
            workerThread.interrupt();
            inputStream.close();
            outputStream.close();
            socket.close();
        }catch (Exception e){
            showToast("종료 중에 에러 발생");
        }
        super.onDestroy();
    }

    //연결된 블루투스 장치를 이름으로 찾기
    BluetoothDevice getDeviceFromBondedList(String name) {
        BluetoothDevice selectedDevice=null;
        for(BluetoothDevice device:deviceSet) {
            if(name.equals(device.getName())){
                selectedDevice=device;
                break;
            }
        }
        return selectedDevice;
    }

    void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                //블루투스 장치 활성화 여부
                if(resultCode==RESULT_OK) { // 블루투스를 사용할 것? 예
                    // 장치 선택
                    selectDevice();
                } else if(resultCode==RESULT_CANCELED) { // 블루투스를 사용할 것? 아니요
                    // 방법1 => finish(); // 앱 종료
                    // 방법2 => Toast로 표시
                    // 방법3 => dialog로 표시
                    finish();
                }
                break;
        }
    }

}