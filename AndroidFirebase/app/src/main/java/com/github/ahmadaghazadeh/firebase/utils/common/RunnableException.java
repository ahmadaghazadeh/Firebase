package com.github.ahmadaghazadeh.firebase.utils.common;

public interface RunnableException<TIN> {
    void run(TIN param, Exception ex);
}

