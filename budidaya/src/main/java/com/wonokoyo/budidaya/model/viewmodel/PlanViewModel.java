package com.wonokoyo.budidaya.model.viewmodel;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.wonokoyo.budidaya.flow.PlanActivity;
import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.model.repository.PlanRepository;
import com.wonokoyo.budidaya.room.repo.PlanRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanViewModel {
    private PlanRepository planRepository;
    private PlanRepo planRepo;
    private Application app;

    private ProgressDialog dialog;

    public void init(Application application, Context context) {
        planRepository = PlanRepository.getInstance();
        planRepo = new PlanRepo(application);

        this.app = application;

        dialog = new ProgressDialog(context);
        dialog.setMessage("Sedang memuat data");
        dialog.setCancelable(false);
    }

    public LiveData<Integer> getCountPlan() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = sdf.format(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String end = sdf.format(tomorrow);

        return planRepo.countPlan(start, end);
    }

    public void syncPlan(final View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = sdf.format(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String end = sdf.format(tomorrow);

        dialog.show();

        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");

                        if (dialog.isShowing())
                            dialog.dismiss();

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
