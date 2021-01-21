package com.example.ditebattle.database;

import java.util.HashMap;
import java.util.Map;

public class Reply {
    public Integer id;
    public String userID;
    public String content;
    public String written_date;
    public String modified_date;

    public Reply(Integer id, String userID, String content, String written_date, String modified_date){
        this.id = id;
        this.userID = userID;
        this.content = content;
        this.written_date = written_date;
        this.modified_date = modified_date;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("id",id);
        result.put("userID",userID);
        result.put("content",content);
        result.put("written_date",written_date);
        result.put("modified_date",modified_date);
        return result;
    }
}
