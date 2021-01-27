package com.example.ditebattle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MatchingRoom extends AppCompatActivity {

    Button matchingRoomStartBtn, matchingRoomChatBtn;
    EditText matchingRoomChatEdt;
    TextView matchingRoomNum, matchingRoomTitle, matchingRoomOption;
    ListView matchingRoomChatList;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    DatabaseReference battleRef = databaseReference.child("Battle");
    HashMap<String, Object> childUpdates = new HashMap<>();
    Map<String, Object> battleValue = null;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String number, title, memo, masterUid , guestUid, battletitle;
    Boolean master,Login=true;
    Boolean flag = false , onBattle=false;
    Battle battle;
    ValueEventListener battleValueEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_room);
        matchingRoomStartBtn = (Button) findViewById(R.id.matchingRoomStartBtn);
        matchingRoomChatBtn = (Button) findViewById(R.id.matchingRoomChatBtn);
        matchingRoomChatEdt = (EditText) findViewById(R.id.matchingRoomChatEdt);
        matchingRoomNum = (TextView) findViewById(R.id.matchingRoomNum);
        matchingRoomTitle = (TextView) findViewById(R.id.matchingRoomTitle);
        matchingRoomOption = (TextView) findViewById(R.id.matchingRoomOption);
        matchingRoomChatList = (ListView) findViewById(R.id.matchingRoomChatList);

        // 로그인 화면에서 받아온 제목, 성별, 무게, 학년 저장
        Intent intent = getIntent();
        number = intent.getStringExtra("number");
        title = intent.getStringExtra("title");
        memo = intent.getStringExtra("memo");
        master = intent.getBooleanExtra("master", false);

        readDB();
        readMaster();
        readBattle();
        matchingRoomNum.setText(number);
        matchingRoomTitle.setText(title);
        matchingRoomOption.setText(memo);

        // 받아온 데이터 저장

        if (master == true) {

            RecyclerItemData roomName = new RecyclerItemData(number, title,
                    memo, flag); // RecyclerItemData를 이용하여 데이터를 묶는다.
            databaseReference.child("chat").child(title).setValue(roomName);
            masterUid = user.getUid();
            battletitle = (""+title+memo+number);
            matchingRoomStartBtn.setText("시작");
            matchingRoomStartBtn.setEnabled(false);
        } else {
            guestUid=user.getUid();
            matchingRoomStartBtn.setText("준비대기");
        }

        // 채팅 방 입장
        openChat(title);

        // 채팅 글 보내기 버튼
        matchingRoomChatBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                RecyclerItemData chat = new RecyclerItemData(user.getEmail(),
                        matchingRoomChatEdt.getText().toString()); // RecyclerItemData를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(title).child("chating").push().setValue(chat); // 데이터 푸쉬
                matchingRoomChatEdt.setText(""); //입력창 초기화
            }
        });

        // 대결 시작 버튼 누를 시 방장과 다르게 눌리는 버튼
        matchingRoomStartBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (master == true) {
                    long t = System.currentTimeMillis()/1000;
                    onBattle=true;
                    battle = new Battle(
                            title,
                            "GuestUID",
                            (t+604800),
                            500,
                            500
                    );
                    battleValue = battle.toMap();
                    childUpdates.put("/Battle/" +title,battleValue);
                    databaseReference.updateChildren(childUpdates);
                } else {
                    if (!flag) {
                        flag = true;
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("chat").child(title).child("master");
                        ref.setValue(flag);
                    } else {
                        flag = false;
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("chat").child(title).child("master");
                        ref.setValue(flag);
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("test","onStop");
        battleRef.removeEventListener(battleValueEventListener);
    }

    private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        RecyclerItemData chat = dataSnapshot.getValue(RecyclerItemData.class);
        adapter.add(chat.getUserName() + " : " + chat.getMessage());
    }

    private void removeMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        RecyclerItemData chat = dataSnapshot.getValue(RecyclerItemData.class);
        adapter.remove(chat.getUserName() + " : " + chat.getMessage());
    }

    private void openChat(String chatName) {
        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        matchingRoomChatList.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatName).child("chating").addChildEventListener(new ChildEventListener() {
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

    void readDB() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("chat").child(title);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                if (!master && !onBattle) {
                    Toast.makeText(getApplicationContext(), "방장이 방을 나갔습니다.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void readBattle(){
        battleValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(title).getValue()!=null){
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid()).child("battle");
                    ref.setValue(title);
                    if(!master) {
                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Battle").child(title).child("guest");
                        ref2.setValue(guestUid);
                    }
                        if(Login) {
                            Login=false;
                            DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("chat").child(title);
                            ref3.removeValue();
                            Toast.makeText(getApplicationContext(), "배틀이 시작됩니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MatchingRoom.this, BattleRoom.class);
                            startActivity(intent);
                            finish();
                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        battleRef.addValueEventListener(battleValueEventListener);
    }
    void readMaster() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("chat").child(title).child("master");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean ready = Boolean.parseBoolean(String.valueOf(snapshot.getValue()));
                if (ready) {
                    if (master) {
                        matchingRoomStartBtn.setEnabled(true);
                    } else {
                        matchingRoomStartBtn.setText("준비완료");
                    }
                } else {
                    if (master) {
                        matchingRoomStartBtn.setEnabled(false);
                    } else {
                        matchingRoomStartBtn.setText("준비대기");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("채팅방을 나가시겠습니까?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if (master == true) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("chat").child(title);
                    ref.removeValue();
                    Toast.makeText(getApplicationContext(), "방을 나갔습니다.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    finish();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
