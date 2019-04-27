package com.github.ahmadaghazadeh.firebase.data.remote;


import com.github.ahmadaghazadeh.firebase.data.model.User;
import com.github.ahmadaghazadeh.firebase.data.remote.database.IPersist;
import com.github.ahmadaghazadeh.firebase.utils.common.RunnableIn;
import com.github.ahmadaghazadeh.firebase.utils.exception.BaseException;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;


public class Repository implements IRepository {

    private IApi api;
    private IPersist persist;

    public Repository(IApi api, IPersist persist) {
        this.api = api;
        this.persist = persist;
    }


    @Override
    public void putUser(User user) throws BaseException {
        persist.putUser(user);
    }

    @Override
    public void userEmailListener(String email, RunnableIn<User> runnableIn) {
        persist.userEmailListener(email, runnableIn);
    }


}
