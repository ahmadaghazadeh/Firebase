package com.github.ahmadaghazadeh.firebase.data.model;

public class User {
    private String email;
    private String nickName;

    public User() {
    }
    public User(String email, String nickName) {
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
}
