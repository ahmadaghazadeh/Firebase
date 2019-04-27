package com.github.ahmadaghazadeh.firebase.utils.base;

import android.arch.lifecycle.ViewModel;


import com.github.ahmadaghazadeh.firebase.utils.common.RunnableIn;
import com.github.ahmadaghazadeh.firebase.utils.common.RunnableMethod;
import com.github.ahmadaghazadeh.firebase.utils.common.RunnableModel;
import com.github.ahmadaghazadeh.firebase.utils.common.SimpleAsyncTask;
import com.google.firebase.auth.FirebaseUser;

import java.lang.ref.WeakReference;


public abstract class BaseViewModel<N extends INavigator> extends ViewModel {

    private WeakReference<N> mNavigator;

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public void runDialogAsyncTask(RunnableMethod method, RunnableIn post) {

        RunnableMethod pre = (param, onProgressUpdate) -> {
            getNavigator().showProgress(false);
            return null;
        };

        RunnableIn<RunnableModel> postInternal = new RunnableIn<RunnableModel>() {
            @Override
            public void run(RunnableModel param) {
                if (param.getException() != null) {
                    getNavigator().handleError(param.getException());
                }
                if(post!=null){
                    post.run(param);
                }
                getNavigator().hideProgress();
            }

            @Override
            public void onError(Exception ex) {

            }
        };


        SimpleAsyncTask simpleAsyncTask = new SimpleAsyncTask(pre, method, postInternal);
        simpleAsyncTask.execute();
    }

    public void runAsyncTask(RunnableMethod method, RunnableIn post) {
        RunnableIn<RunnableModel> postInternal =new RunnableIn<RunnableModel>() {
            @Override
            public void run(RunnableModel param) {
                if (param.getException() != null) {
                    getNavigator().toast(param.getException().toString());
                }
                if(post!=null){
                    post.run(param);

                }
            }

            @Override
            public void onError(Exception ex) {

            }
        };

        SimpleAsyncTask simpleAsyncTask = new SimpleAsyncTask(null, method, postInternal);
        simpleAsyncTask.execute();
    }

}
