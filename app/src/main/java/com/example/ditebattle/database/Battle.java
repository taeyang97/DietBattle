package com.example.ditebattle.database;

import java.util.HashMap;
import java.util.Map;

public class Battle {
    public String master;
    public String guest;
    public double finish_time;
    public Integer masterHP;
    public Integer guestHP;


    Battle(){
    }
    public Battle(String master, String guest, double finish_time, Integer masterHP, Integer guestHP){
        this.master = master;
        this.guest = guest;
        this.finish_time = finish_time;
        this.masterHP = masterHP;
        this.guestHP = guestHP;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("master", master);
        result.put("guest",guest);
        result.put("finish_tume",finish_time);
        result.put("masterHP",masterHP);
        result.put("guestHP",guestHP);
        return result;
    }
}
