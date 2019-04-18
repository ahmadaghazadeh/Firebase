package com.github.ahmadaghazadeh.firebase.ui.login;


import com.github.ahmadaghazadeh.firebase.utils.base.activity.IActivityNavigator;

public interface ILoginNavigator extends IActivityNavigator {

    public void googleSignIn();
    public void signOutGoogle();

    void FirebaseInvites();
    void registerFCM();
}
