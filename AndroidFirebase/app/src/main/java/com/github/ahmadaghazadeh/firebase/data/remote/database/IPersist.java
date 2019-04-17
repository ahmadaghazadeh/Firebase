package com.github.ahmadaghazadeh.firebase.data.remote.database;


import com.github.ahmadaghazadeh.firebase.data.model.User;
import com.github.ahmadaghazadeh.firebase.utils.exception.BaseException;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;


public interface IPersist {
    public String putUser(User user) throws BaseException;
    public void userEmailListener(String email,ValueEventListener valueEventListener) ;
}
