package com.example.ditebattle;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ditebattle.database.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText ninkname, weight, height, age;
    Button NavStartBtn;
    RadioButton reg_Woman_btn, reg_Man_btn;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDBReference = FirebaseDatabase.getInstance().getReference();
    HashMap<String, Object> childUpdates = new HashMap<>();
    Map<String, Object> userValue = null;
    User userInfo = null;
    RadioGroup radioGroup;
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ninkname = (EditText) findViewById(R.id.reg_Id_Edt);
        weight = (EditText) findViewById(R.id.reg_Weight_Edt);
        height = (EditText) findViewById(R.id.reg_Height_Edt);
        age = (EditText) findViewById(R.id.reg_Age_Edt);
        NavStartBtn = (Button) findViewById(R.id.NavStartBtn);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        NavStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(ninkname.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주십시오", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(weight.getText().toString())){
                    Toast.makeText(getApplicationContext(),"몸무게를 입력해주십시오",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(age.getText().toString())){
                    Toast.makeText(getApplicationContext(),"나이를 입력해주십시오",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(height.getText().toString())){
                    Toast.makeText(getApplicationContext(),"키를 입력해주십시오",Toast.LENGTH_SHORT).show();
                }else {
                    userInfo = new User(
                            user.getEmail(),
                            ninkname.getText().toString(),
                            Integer.parseInt(age.getText().toString()),
                            Double.parseDouble(weight.getText().toString()),
                            Double.parseDouble(height.getText().toString()),
                            33.4,
                            0,
                            0,
                            "남자",
                            1
                    );
                    userValue = userInfo.toMap();
                    childUpdates.put("/User/" + user.getUid(), userValue);
                    mDBReference.updateChildren(childUpdates);

                    Toast.makeText(getApplicationContext(), "successfully signed in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if (i == R.id.reg_Man_Btn) {
                gender="남자";
            } else if (i == R.id.reg_Woman_btn) {
                gender="여자";
            };
        }
    };
}
