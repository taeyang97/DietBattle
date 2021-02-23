package com.example.dietbattle.database;

import java.util.HashMap;
import java.util.Map;

public class BattleEnemyDay {
    public Integer day0;
    public Integer day1;
    public Integer day2;
    public Integer day3;
    public Integer day4;
    public Integer day5;
    public Integer day6;

    public BattleEnemyDay() {}

    public BattleEnemyDay(Integer day0, Integer day1, Integer day2, Integer day3, Integer day4, Integer day5, Integer day6) {
        this.day0 = day0;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("day0", day0);
        result.put("day1", day1);
        result.put("day2", day2);
        result.put("day3", day3);
        result.put("day4", day4);
        result.put("day5", day5);
        result.put("day6", day6);
        return result;
    }
}
