package com.github.ahmadaghazadeh.firebase.utils.base;

import android.support.annotation.StringRes;

public interface INavigator {

    void handleError(Throwable throwable);

    void showProgress(Boolean flag);

    void setProgress(String message, int progress);

    void hideProgress();

    void toast(@StringRes int resId);

    void toast(String string);

    void hideKeyboard();

}
