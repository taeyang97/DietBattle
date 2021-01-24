package com.example.ditebattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ditebattle.database.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText ninkname,weight,height,age;
    Button test;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDBReference = FirebaseDatabase.getInstance().getReference();
    HashMap<String, Object> childUpdates = new HashMap<>();
    Map<String, Object> userValue = null;
    User userInfo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ninkname =(EditText)findViewById(R.id.reg_Id_Edt);
        weight = (EditText)findViewById(R.id.reg_Weight_Edt);
        height=(EditText)findViewById(R.id.reg_Height_Edt);
        age = (EditText)findViewById(R.id.reg_Age_Edt);
        test = (Button) findViewById(R.id.reg_Man_Btn);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfo = new User(
                        user.getEmail(),
                        ninkname.getText().toString(),
                        Integer.parseInt(age.getText().toString()),
                        Double.parseDouble(weight.getText().toString()),
                        Double.parseDouble(height.getText().toString()),
                        33.4,
                        0,
                        0,
                        "남"
                        );
                userValue = userInfo.toMap();
                childUpdates.put("/User/" + user.getUid(), userValue);
                mDBReference.updateChildren(childUpdates);
                
                Toast.makeText(getApplicationContext(), "successfully signed in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}