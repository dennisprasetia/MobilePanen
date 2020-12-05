package com.wonokoyo.mobilepanen.serveraccess;

import com.wonokoyo.mobilepanen.serveraccess.service.LoginService;
import com.wonokoyo.mobilepanen.util.UnsafeOkHttpClient;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;

    static OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Url.BASE_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static LoginService getLoginService() {
        return RetrofitInstance.getRetrofit().create(LoginService.class);
    }
}
