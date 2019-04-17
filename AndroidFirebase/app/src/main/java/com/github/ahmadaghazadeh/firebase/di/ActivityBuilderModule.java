package com.github.ahmadaghazadeh.firebase.di;



import com.github.ahmadaghazadeh.firebase.ui.login.LoginActivity;
import com.github.ahmadaghazadeh.firebase.ui.login.LoginModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {


    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity bindConnectActivity();

}
