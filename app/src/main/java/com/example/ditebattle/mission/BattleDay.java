package com.example.ditebattle.mission;

import java.util.HashMap;
import java.util.Map;

public class BattleDay {
    public String firstday;
    public String twoday;
    public String threeday;
    public String fourday;
    public String fiveday;
    public String sixday;
    public String lastday;

    public BattleDay() { }

    public BattleDay(String firstday, String twoday, String threeday, String fourday, String fiveday, String sixday, String lastday) {
        this.firstday = firstday;
        this.twoday = twoday;
        this.threeday = threeday;
        this.fourday = fourday;
        this.fiveday = fiveday;
        this.sixday = sixday;
        this.lastday = lastday;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("firstday", firstday);
        result.put("twoday",twoday);
        result.put("threeday",threeday);
        result.put("fourday",fourday);
        result.put("fiveday",fiveday);
        result.put("sixday",sixday);
        result.put("lastday", lastday);
        return result;
    }
}
