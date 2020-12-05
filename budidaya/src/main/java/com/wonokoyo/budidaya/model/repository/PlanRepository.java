package com.wonokoyo.budidaya.model.repository;

import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.serveraccess.RetrofitInstance;

import java.util.List;

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
        Call<ResponseBody> call = RetrofitInstance.getBdyService().getPlanByDate(start, end);
        call.enqueue(listener);
    }
}
