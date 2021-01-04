package com.wonokoyo.mitra.model.repository;

import com.wonokoyo.mitra.serveraccess.RetrofitInstance;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class PlanRepository {
    private static PlanRepository planRepository;

    public static PlanRepository getInstance() {
        if (planRepository == null)
            planRepository = new PlanRepository();

        return planRepository;
    }

    public void getPlansByDate(String start, String end, Callback<ResponseBody> listener) {
        Call<ResponseBody> call = RetrofitInstance.getMitraService().getPlanByDate(start, end);
        call.enqueue(listener);
    }
}
