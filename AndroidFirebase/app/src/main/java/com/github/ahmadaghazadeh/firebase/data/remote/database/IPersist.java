package com.github.ahmadaghazadeh.firebase.data.remote.database;


import com.github.ahmadaghazadeh.firebase.data.model.User;
import com.github.ahmadaghazadeh.firebase.utils.common.RunnableIn;
import com.github.ahmadaghazadeh.firebase.utils.exception.BaseException;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;


public interface IPersist {
    public void putUser(User user) throws BaseException;
    public void userEmailListener(String email, RunnableIn<User> user) ;
}
