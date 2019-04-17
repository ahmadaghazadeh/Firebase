package com.github.ahmadaghazadeh.firebase.utils.common;


public interface RunnableMethod<TIN, TOUT> {
    TOUT run(TIN param, OnProgressUpdate onProgressUpdate) ;
}

