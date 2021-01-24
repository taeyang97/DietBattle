package com.example.ditebattle.database;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String email;
    private String nickname;
    private Integer age;
    private Double weight;
    private Double height;
    private Double bmi;
    private Integer total_point;
    private Integer current_point;
    private String gender;

    public User() { }

    public User(String email){
        this.email= email;
    }

    public User(Integer age, String email){
        this.age= age;
        this.email=email;
    }

    public User(String id, String nickname, Integer age, Double weight , Double height,
                Double bmi, Integer total_point, Integer current_point, String gender){
        this.email= id;
        this.nickname = nickname;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.total_point = total_point;
        this.current_point = current_point;
        this.gender = gender;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("nickname",nickname);
        result.put("age",age);
        result.put("weight",weight);
        result.put("height",height);
        result.put("bmi", bmi);
        result.put("total_point",total_point);
        result.put("current_point",current_point);
        result.put("gender",gender);
        return result;
    }
}
