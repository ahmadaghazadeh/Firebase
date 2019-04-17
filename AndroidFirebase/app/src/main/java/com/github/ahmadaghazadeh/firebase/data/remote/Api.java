package com.github.ahmadaghazadeh.firebase.data.remote;

import android.content.Context;


import com.github.ahmadaghazadeh.firebase.utils.exception.ApiException;
import com.github.ahmadaghazadeh.firebase.utils.exception.ConnectionException;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;

public class Api implements IApi {

    private Retrofit retrofit;
    private Context context;

    public Api(Retrofit retrofit, Context context) {
        this.retrofit = retrofit;
        this.context = context;
    }
//
//    @Override
//    public ResultData SendQrCodeData(String code) throws ConnectionException, ApiException {
//        PostData postData=new PostData();
//        postData.Text=code;
//        Response<ResultData> response = null;
//        try {
//            response = retrofit.create(IRetrofitApi.class).SendQrCodeData(postData).execute();
//        } catch (IOException e) {
//            throw new ConnectionException(context,e);
//        }
//        if (response.isSuccessful()) return response.body();
//        throw new ApiException(context, response);
//
//
//    }
}
