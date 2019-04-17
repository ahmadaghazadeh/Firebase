package com.github.ahmadaghazadeh.firebase.data.local.pref;


import android.content.Context;


public class AppPref implements IAppPref {

    private static final String USER_ID = "USER_ID";


    private PreferenceManager pm;


    public AppPref(Context context) {
        this.pm = new PreferenceManager(context);
    }


    @Override
    public void putUserId(String url) {
        pm.set(USER_ID,url);
    }

    @Override
    public String getUserId() {
     return   pm.get(USER_ID,"");
    }
}
