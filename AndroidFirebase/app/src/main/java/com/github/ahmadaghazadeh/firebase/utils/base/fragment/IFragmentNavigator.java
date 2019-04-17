package com.github.ahmadaghazadeh.firebase.utils.base.fragment;


import com.github.ahmadaghazadeh.firebase.utils.base.INavigator;

public interface IFragmentNavigator extends INavigator {

    void setToolbarProgressBar(boolean isIndeterminate, int progress);
}
