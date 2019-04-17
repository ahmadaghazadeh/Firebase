package com.github.ahmadaghazadeh.firebase.di;


import android.app.Application;
import android.content.Context;


import com.github.ahmadaghazadeh.firebase.app.C;
import com.github.ahmadaghazadeh.firebase.data.local.pref.IAppPref;
import com.github.ahmadaghazadeh.firebase.data.remote.Api;
import com.github.ahmadaghazadeh.firebase.data.remote.IApi;
import com.github.ahmadaghazadeh.firebase.data.remote.IRepository;
import com.github.ahmadaghazadeh.firebase.data.remote.Repository;
import com.github.ahmadaghazadeh.firebase.data.remote.database.IPersist;
import com.github.ahmadaghazadeh.firebase.data.remote.database.Persist;
import com.github.ahmadaghazadeh.firebase.utils.common.CommonUtils;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public abstract class AppModule {

    @Provides
    @Named("serverUrl")
    public static String getBaseUrl(IAppPref appPref) {
        return C.UrlApi;
    }

    @Singleton
    @Provides
    public static CommonUtils providesCommonUtils(Context context) {
        return new CommonUtils(context);
    }

    @Binds
    @Singleton
    abstract Context getContext(Application application);


    @Singleton
    @Provides
    public static IApi provideRetrofitApi(Retrofit retrofit, Context context) {
        return new Api(retrofit,context);
    }

    @Singleton
    @Provides
    public static IPersist providePersist(Context context) {
        return new Persist(context);
    }

    @Provides
    @Singleton
    public static IRepository provideRepository(IApi api, IPersist persist) {
        return new Repository(api,persist);
    }


}
