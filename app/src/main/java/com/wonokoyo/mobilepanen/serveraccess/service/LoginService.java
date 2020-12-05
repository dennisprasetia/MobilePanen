package com.wonokoyo.mobilepanen.serveraccess.service;

import com.wonokoyo.mobilepanen.serveraccess.Url;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginService {
    @GET(Url.LOGIN)
    Call<ResponseBody> login(@Query("username") String username, @Query("password") String password,
                             @Query("device_id") String deviceId);

    @GET(Url.REGISTER)
    Call<ResponseBody> register(@Query("data_register") String data_register);

    @GET(Url.DATA_TIMPANEN)
    Call<ResponseBody> getDataTimpanen();
}
