package com.github.ahmadaghazadeh.firebase.data.remote.database;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.ahmadaghazadeh.firebase.R;
import com.github.ahmadaghazadeh.firebase.app.C;
import com.github.ahmadaghazadeh.firebase.app.FirebaseRoot;
import com.github.ahmadaghazadeh.firebase.data.model.User;
import com.github.ahmadaghazadeh.firebase.utils.common.RunnableIn;
import com.github.ahmadaghazadeh.firebase.utils.exception.BaseException;
import com.github.ahmadaghazadeh.firebase.utils.exception.DatabaseException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;

public class Persist implements IPersist {

    private Context context;
    FirebaseDatabase database;
    public Persist(Context context) {
        this.context = context;
         FirebaseApp userInfoFireBase = FirebaseApp.getInstance("base-userinfo");
         database = FirebaseDatabase.getInstance(userInfoFireBase);
    }


    @Override
    public void   putUser(User user) throws BaseException {

        DatabaseReference myRef = database.getReference(FirebaseRoot.users);
        myRef.child(user.getUid()).setValue(user);
    }


    @Override
    public void userEmailListener(String email, RunnableIn<User> runnable) {
        DatabaseReference myRef = database.getReference(FirebaseRoot.users);
        Query queryRef = myRef.orderByChild("email").equalTo(email);
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildren().iterator().next().exists()){
                    runnable.run(dataSnapshot.getChildren().iterator().next().getValue(User.class));
                }else{
                    runnable.onError(new Exception("Not found item"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                runnable.onError(databaseError.toException());
            }
        });
    }
}
