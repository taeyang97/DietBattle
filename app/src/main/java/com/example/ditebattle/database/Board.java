package com.example.ditebattle.database;

import java.util.HashMap;
import java.util.Map;

public class Board {
    public String userID;
    public String category;
    public String written_date;
    public String modified_date;
    public String content;
    Reply[] replies;
    public Integer replyID;
    public String replyUserID;
    public String replyContent;
    public String reply_written_date;
    public String reply_modified_date;
    public Integer views;

    public Board(String userID, String category, String written_date, String modified_date, String content, Reply[] replies, Integer views){
        this.userID = userID;
        this.category = category;
        this.written_date = written_date;
        this.modified_date = modified_date;
        this.content = content;
        this.replies = replies;
        this.views = views;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("userID",userID);
        result.put("category",category);
        result.put("written_date", written_date);
        result.put("modified_date",modified_date);
        result.put("content",content);
        result.put("replies", replies);
        result.put("views",views);
        return result;
    }
}
