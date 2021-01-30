package com.example.ditebattle.database;

import java.util.HashMap;
import java.util.Map;

public class Battle {
    public String master;
    public String guest;
    public long finish_time;
    public Integer masterHP;
    public Integer guestHP;
    public String grade;
    public Integer masterDay;
    public Integer guestDay;
    public String mission1;
    public String mission2;
    public String mission3;
    public String mission4;
    public String mission5;

    Battle(){
    }

    public Battle(String master, String guest, long finish_time, Integer masterHP, Integer guestHP, String grade, Integer masterDay, Integer guestDay
                   , String mission1 , String mission2 , String mission3 , String mission4 , String mission5) {
        this.master = master;
        this.guest = guest;
        this.finish_time = finish_time;
        this.masterHP = masterHP;
        this.guestHP = guestHP;
        this.grade = grade;
        this.masterDay = masterDay;
        this.guestDay = guestDay;
        this.mission1 = mission1;
        this.mission2 = mission2;
        this.mission3 = mission3;
        this.mission4 = mission4;
        this.mission5 = mission5;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("master", master);
        result.put("guest",guest);
        result.put("finish_time",finish_time);
        result.put("masterHP",masterHP);
        result.put("guestHP",guestHP);
        result.put("grade",grade);
        result.put("masterDay",masterDay);
        result.put("guestDay",guestDay);
        result.put("mission1",mission1);
        result.put("mission2",mission2);
        result.put("mission3",mission3);
        result.put("mission4",mission4);
        result.put("mission5",mission5);
        return result;
    }
}
