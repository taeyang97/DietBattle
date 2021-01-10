package com.example.ditebattle;

public class ItemData {
    String number, title, memo;


    public ItemData(String number, String title, String memo) {
        this.number = number;
        this.title = title;
        this.memo = memo;

    }

    public String getImage() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getMemo() {
        return memo;
    }
}
