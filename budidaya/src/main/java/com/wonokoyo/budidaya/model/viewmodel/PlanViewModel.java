package com.wonokoyo.budidaya.model.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.wonokoyo.budidaya.BudidayaActivity;
import com.wonokoyo.budidaya.flow.PlanActivity;
import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.model.repository.PlanRepository;
import com.wonokoyo.budidaya.room.repo.PlanRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanViewModel {
    private PlanRepository planRepository;
    private PlanRepo planRepo;
    private Application app;

    public void init(Application application) {
        planRepository = PlanRepository.getInstance();
        planRepo = new PlanRepo(application);

        this.app = application;
    }

    public void syncPlan(String start, String end, final View view) {
        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");

                        if (status == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("content");
                            List<Plan> plans = Arrays.asList(new Gson().fromJson(jsonArray.toString(), Plan[].class));

                            List<Plan> plan_bdy = new ArrayList<>();
                            for (Plan plan: plans) {
                                if (plan.getNo_do().contains("RPA"))
                                    plan_bdy.add(plan);
                            }

                            planRepo.saveAllPlan(plan_bdy);
                            Intent intent = new Intent(app.getApplicationContext(), PlanActivity.class);
                            app.startActivity(intent);
                        } else {
                            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };

        planRepository.getPlansByDate(start, end, listener);
    }

    public LiveData<List<Plan>> listDataPlan(String start, String end) {
        return planRepo.getPlans(start, end);
    }

    public LiveData<Plan> getPlanByDoSj(String no_dosj) {
        return planRepo.getPlanByDoSj(no_dosj);
    }
}
