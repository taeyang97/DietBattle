package com.example.ditebattle.mission;

import java.util.HashMap;
import java.util.Map;

public class DayMission {
    public String mission1;
    public String mission2;
    public String mission3;
    public String mission4;
    public String mission5;


    public DayMission(){

    }

    public DayMission(String mission1, String mission2, String mission3, String mission4, String mission5){
        this.mission1 = mission1;
        this.mission2 = mission2;
        this.mission3 = mission3;
        this.mission4 = mission4;
        this.mission5 = mission5;

    }

    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("mission1", mission1);
        result.put("mission2", mission2);
        result.put("mission3", mission3);
        result.put("mission4", mission4);
        result.put("mission5", mission5);
        return result;
    }
}
