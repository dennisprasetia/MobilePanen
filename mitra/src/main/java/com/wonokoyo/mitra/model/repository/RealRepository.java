package com.wonokoyo.mitra.model.repository;

import com.google.gson.Gson;
import com.wonokoyo.mitra.model.RealWithDetail;
import com.wonokoyo.mitra.serveraccess.RetrofitInstance;

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

    public void saveReal(List<RealWithDetail> reals, Callback<ResponseBody> listener) {
        Call<ResponseBody> call = RetrofitInstance.getMitraService().saveDataReal(new Gson().toJson(reals));
        call.enqueue(listener);
    }
}
