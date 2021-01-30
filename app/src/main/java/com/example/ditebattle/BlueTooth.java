package com.example.ditebattle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BlueTooth extends AppCompatActivity {
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
    int readBufferPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 블루투스 지원하는지 체크하는 메소드
    void checkBluetooth() {
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter(); // 어댑터를 반환받는다.
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
