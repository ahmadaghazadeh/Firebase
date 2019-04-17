package com.github.ahmadaghazadeh.firebase.data.remote.database;

import android.content.Context;

import com.github.ahmadaghazadeh.firebase.R;
import com.github.ahmadaghazadeh.firebase.app.FirebaseRoot;
import com.github.ahmadaghazadeh.firebase.data.model.User;
import com.github.ahmadaghazadeh.firebase.utils.exception.BaseException;
import com.github.ahmadaghazadeh.firebase.utils.exception.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;

public class Persist implements IPersist {

    private Context context;

    public Persist(Context context) {
        this.context = context;
    }


    @Override
    public String  putUser(User user) throws BaseException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseRoot.users);
        String userId = myRef.push().getKey();
        if (userId != null) {
            myRef.child(userId).setValue(user);
        } else {
            throw new DatabaseException(context, context.getString(R.string.error_book_not_found));
        }
        return userId;
    }

    @Override
    public void userEmailListener(String email, ValueEventListener valueEventListener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseRoot.users);
        Query queryRef = myRef.orderByChild("email").equalTo(email);
        queryRef.addValueEventListener(valueEventListener);
    }
}
