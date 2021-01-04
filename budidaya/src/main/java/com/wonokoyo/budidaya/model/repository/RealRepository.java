package com.wonokoyo.budidaya.model.repository;

import com.google.gson.Gson;
import com.wonokoyo.budidaya.model.RealWithDetail;
import com.wonokoyo.budidaya.serveraccess.RetrofitInstance;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RealRepository {
    private static RealRepository realRepository;

    public static RealRepository getInstance() {
        if (realRepository == null)
            realRepository = new RealRepository();

        return realRepository;
    }

    public void saveReal(List<RealWithDetail> reals, String nik, Callback<ResponseBody> listener) {
        Call<ResponseBody> call = RetrofitInstance.getBdyService().saveDataReal(new Gson().toJson(reals), nik);
        call.enqueue(listener);
    }
}
