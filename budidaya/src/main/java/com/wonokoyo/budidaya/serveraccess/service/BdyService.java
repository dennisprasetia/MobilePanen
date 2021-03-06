package com.wonokoyo.budidaya.serveraccess.service;

import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.serveraccess.Url;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BdyService {
    @GET(Url.GET_PLAN)
    Call<ResponseBody> getPlanByDate(@Query("start") String start, @Query("end") String end);

    @FormUrlEncoded
    @POST(Url.SAVE_DATA)
    Call<ResponseBody> saveDataReal(@Field("data") String data, @Field("nik") String nik);
}
