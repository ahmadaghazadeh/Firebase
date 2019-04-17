package com.github.ahmadaghazadeh.firebase.ui.login;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.github.ahmadaghazadeh.firebase.R;
import com.github.ahmadaghazadeh.firebase.databinding.ActivityLoginBinding;
import com.github.ahmadaghazadeh.firebase.utils.base.activity.BaseActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel>
        implements ILoginNavigator  {

    @Inject
    ViewModelProvider.Factory factory;

    public static Intent newInstance(Context context) {
        return new Intent(context, LoginActivity.class);
    }


    @Override
    public LoginViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mViewModel.setNavigator(this);
            FirebaseApp.initializeApp(this);

            mViewModel.init();

    }


}
