package com.github.ahmadaghazadeh.firebase.ui.login;

import android.arch.lifecycle.ViewModelProvider;


import com.github.ahmadaghazadeh.firebase.utils.base.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    ViewModelProvider.Factory getFactory(LoginViewModel model){
        return new ViewModelProviderFactory<>(model);
    }

}

