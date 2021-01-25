package com.wonokoyo.mitra.serveraccess.service;

import com.wonokoyo.mitra.serveraccess.Url;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MitraService {
    @GET(Url.GET_PLAN)
    Call<ResponseBody> getPlanByDate(@Query("start") String start, @Query("end") String end);

    @FormUrlEncoded
    @POST(Url.SAVE_DATA)
    Call<ResponseBody> saveDataReal(@Field("data") String data, @Field("nik") String nik);
}
