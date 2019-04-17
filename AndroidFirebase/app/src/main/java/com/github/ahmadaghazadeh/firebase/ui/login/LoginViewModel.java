package com.github.ahmadaghazadeh.firebase.ui.login;


import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import com.github.ahmadaghazadeh.firebase.app.FirebaseRoot;
import com.github.ahmadaghazadeh.firebase.data.local.pref.IAppPref;
import com.github.ahmadaghazadeh.firebase.data.model.User;
import com.github.ahmadaghazadeh.firebase.data.remote.IRepository;
import com.github.ahmadaghazadeh.firebase.old.MainActivity;
import com.github.ahmadaghazadeh.firebase.utils.base.BaseViewModel;
import com.github.ahmadaghazadeh.firebase.utils.exception.BaseException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;


public class LoginViewModel extends BaseViewModel<ILoginNavigator> {

    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> nickName = new MutableLiveData<>();
    private FirebaseAuth mAuth;

    @Inject
    public LoginViewModel() {

    }

    public void init() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Inject
    IAppPref appPref;

    @Inject
    IRepository repository;

    public void onClickLogin(){
        login(email.getValue(),password.getValue());
    }
    public void onClickSignin(){
        signin(email.getValue(),password.getValue(),nickName.getValue());
    }



    public String  putNewUser(String email, String nickName) throws BaseException {
        String userId = appPref.getUserId();
        if (userId.isEmpty()) {
            User user = new User(email, nickName);
            userId= repository.putUser(user);
        }
        return userId;
    }

    private void signin(String email, String password, String nickName) {
        getNavigator().showProgress(true);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //  FirebaseUser user = mAuth.getCurrentUser();
                            try {
                                String userId= putNewUser(email, nickName);
                                appPref.putUserId(userId);
                            } catch (BaseException e) {
                                getNavigator().handleError(e);
                            }
                            getNavigator().toast("Sign in success.");

                        } else {
                            getNavigator().handleError(task.getException());
                        }
                        getNavigator().hideProgress();
                    }
                });
    }

    private void login(String email, String password) {
        getNavigator().showProgress(true);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                             repository.userEmailListener(email, new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     //appPref.putUserId(dataSnapshot.node.getFirstChild().name.key);

                                     getNavigator().hideProgress();
                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError databaseError) {
                                     getNavigator().handleError(databaseError.toException());
                                     getNavigator().hideProgress();
                                 }
                             });

                        } else {
                            getNavigator().handleError(task.getException());
                            getNavigator().hideProgress();
                        }

                    }
                });
    }

}
