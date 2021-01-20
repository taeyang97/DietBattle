package com.example.ditebattle;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String id;
    public String password;
    public String name;
    public String nickname;
    public Integer age;
    public String phone;
    public Double weight;
    public Double height;
    public Double bmi;
    public Integer total_point;
    public Integer current_point;
    public String gender;

    public User(String id, String password, String name, String nickname, int age, String phone, double weight ,double height, double bmi, int total_point, int current_point, String gender){
        this.id= id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.phone = phone;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.total_point = total_point;
        this.current_point = current_point;
        this.gender = gender;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("password", password);
        result.put("name",name);
        result.put("nickname",nickname);
        result.put("age",age);
        result.put("phone",phone);
        result.put("weight",weight);
        result.put("height",height);
        result.put("bmi", bmi);
        result.put("total_point",total_point);
        result.put("current_point",current_point);
        result.put("gender",gender);
        return result;
    }
}
