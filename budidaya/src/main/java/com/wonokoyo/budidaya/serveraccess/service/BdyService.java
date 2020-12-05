package com.wonokoyo.budidaya.serveraccess.service;

import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.serveraccess.Url;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BdyService {
    @GET(Url.GET_PLAN)
    Call<ResponseBody> getPlanByDate(@Query("start") String start, @Query("end") String end);
}
