package com.github.ahmadaghazadeh.firebase.ui.login;


import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.github.ahmadaghazadeh.firebase.data.local.pref.IAppPref;
import com.github.ahmadaghazadeh.firebase.data.model.User;
import com.github.ahmadaghazadeh.firebase.data.remote.IRepository;
import com.github.ahmadaghazadeh.firebase.utils.base.BaseViewModel;
import com.github.ahmadaghazadeh.firebase.utils.common.RunnableIn;
import com.github.ahmadaghazadeh.firebase.utils.exception.BaseException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class LoginViewModel extends BaseViewModel<ILoginNavigator> {

    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> nickName = new MutableLiveData<>();
    public MutableLiveData<String> phone = new MutableLiveData<>();
    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Inject
    public LoginViewModel() {

    }

    public void init() {
        FirebaseApp userInfoFireBase = FirebaseApp.getInstance("base-userinfo");
          db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance(userInfoFireBase);
        email.setValue("Ahmad.aghazadeh.a@gmail.com");
        password.setValue("asd123");
        nickName.setValue("Ahmad Aghazadeh");
    }

    @Inject
    IAppPref appPref;

    @Inject
    IRepository repository;

    public void onClickLogin() {
        login(email.getValue(), password.getValue());
    }

    public void onClickCrash() {
        Crashlytics.getInstance().crash();
    }

    public void onClickSignin() {
        signin(email.getValue(), password.getValue(), nickName.getValue());
    }

    public void onClickClear() {
        appPref.putUserId("");
    }

    public void onClickLoginGoogle() {
        getNavigator().googleSignIn();
    }

    public void onClickFirebaseInvites() {
        getNavigator().FirebaseInvites();
    }

    public void onClickSignOutGoogle() {
        getNavigator().signOutGoogle();

    }


    public void putNewUser(String uid,String email, String nickName) throws BaseException {
        User user = new User(uid, email, nickName);
        repository.putUser(user);
    }

    private void signin(String email, String password, String nickName) {
        getNavigator().showProgress(true);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //
                            try {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {

                                    putNewUser(user.getUid(),user.getEmail(), nickName);
                                    appPref.putUserId(user.getUid());
                                    getNavigator().registerFCM();
                                }
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
                            repository.userEmailListener(email, new RunnableIn<User>() {
                                @Override
                                public void run(User param) {
                                    appPref.putUserId(param.getUid());
                                    getNavigator().hideProgress();
                                }

                                @Override
                                public void onError(Exception ex) {
                                    getNavigator().handleError(ex);
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

    public void SignInGoogleAuto(FirebaseUser currentUser) {

    }

    public void checkNewUser(String email, String displayName) {
        getNavigator().showProgress(true);

        repository.userEmailListener(email, new RunnableIn<User>() {
            @Override
            public void run(User param) {
                try {
                    putNewUser(param.getUid(),param.getEmail(), displayName);
                    appPref.putUserId(param.getUid());
                    getNavigator().registerFCM();
                } catch (BaseException e) {
                    getNavigator().handleError(e);
                }
            }

            @Override
            public void onError(Exception ex) {
                getNavigator().handleError(ex);
                getNavigator().hideProgress();
            }
        });

    }

    public void onClickFireStore(){
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FireStore", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FireStore", "Error adding document", e);
                    }
                });
    }
}
