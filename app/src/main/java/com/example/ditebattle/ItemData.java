package com.example.ditebattle;

public class ItemData {
    String image, title, memo;


    public ItemData(String image, String title, String memo) {
        this.image = image;
        this.title = title;
        this.memo = memo;

    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getMemo() {
        return memo;
    }
}

