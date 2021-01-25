package com.example.ditebattle;

public class RecyclerItemData {
    String number, title, memo, userName, message;
    Boolean master;

    public RecyclerItemData() {}

    public RecyclerItemData(Boolean master) {
        this.master = master;
    }

    public RecyclerItemData(String number, String title, String memo, Boolean master) {
        this.number = number;
        this.title = title;
        this.memo = memo;
        this.master = master;
    }

    public RecyclerItemData(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public Boolean getMaster() {
        return master;
    }

    public String getNumber() {
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
