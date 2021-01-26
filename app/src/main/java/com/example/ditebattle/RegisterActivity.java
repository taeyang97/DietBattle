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
    EditText ninknameEdt, weightEdt, heightEdt, ageEdt;
    Button NavStartBtn;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDBReference = FirebaseDatabase.getInstance().getReference();
    HashMap<String, Object> childUpdates = new HashMap<>();
    Map<String, Object> userValue = null;
    User userInfo = null;
    RadioGroup radioGroup;
    String gender="남자";
    double weight,height;
    double bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ninknameEdt = (EditText) findViewById(R.id.reg_Id_Edt);
        weightEdt = (EditText) findViewById(R.id.reg_Weight_Edt);
        heightEdt = (EditText) findViewById(R.id.reg_Height_Edt);
        ageEdt = (EditText) findViewById(R.id.reg_Age_Edt);
        NavStartBtn = (Button) findViewById(R.id.NavStartBtn);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.reg_Man_Btn :
                        gender="남자";
                        break;
                    case R.id.reg_Woman_btn :
                        gender="여자";
                        break;
                }
            }
        });

        NavStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(ninknameEdt.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주십시오", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(weightEdt.getText().toString())){
                    Toast.makeText(getApplicationContext(),"몸무게를 입력해주십시오",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(ageEdt.getText().toString())){
                    Toast.makeText(getApplicationContext(),"나이를 입력해주십시오",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(heightEdt.getText().toString())){
                    Toast.makeText(getApplicationContext(),"키를 입력해주십시오",Toast.LENGTH_SHORT).show();
                }else {
                    weight = Double.parseDouble(weightEdt.getText().toString());
                    height = Double.parseDouble(heightEdt.getText().toString());
                    bmi= Double.parseDouble(String.format("%.2f",((weight/(height*height))*10000)));
                    userInfo = new User(
                            user.getEmail(),
                            ninknameEdt.getText().toString(),
                            Integer.parseInt(ageEdt.getText().toString()),
                            Double.parseDouble(weightEdt.getText().toString()),
                            Double.parseDouble(heightEdt.getText().toString()),
                            bmi,
                            0,
                            0,
                            gender,
                            1,
                            "false"
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
}
