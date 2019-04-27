package com.github.ahmadaghazadeh.firebase.data.model;

public class User {
    private String uid;
    private String email;
    private String nickName;
    private int xp=11;

    public User() {
    }
    public User(String uid, String email, String nickName) {
        this.uid = uid;
        this.email = email;
        this.nickName = nickName;
    }

    private Advertise advertise;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Advertise getAdvertise() {
        return advertise;
    }

    public void setAdvertise(Advertise advertise) {
        this.advertise = advertise;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
