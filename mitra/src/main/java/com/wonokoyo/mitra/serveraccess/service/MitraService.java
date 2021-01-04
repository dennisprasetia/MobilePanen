package com.wonokoyo.mitra.serveraccess.service;

import com.wonokoyo.mitra.serveraccess.Url;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MitraService {
    @GET(Url.GET_PLAN)
    Call<ResponseBody> getPlanByDate(@Query("start") String start, @Query("end") String end);

    @GET(Url.SAVE_DATA)
    Call<ResponseBody> saveDataReal(@Query("data") String data);
}
