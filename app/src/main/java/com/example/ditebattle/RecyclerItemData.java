package com.example.ditebattle;

public class RecyclerItemData {
    String number, title, memo, userName, message;

    public RecyclerItemData() {}

    public RecyclerItemData(String number, String title, String memo) {
        this.number = number;
        this.title = title;
        this.memo = memo;
    }

    public RecyclerItemData(String userName, String message) {
        this.userName = userName;
        this.message = message;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
