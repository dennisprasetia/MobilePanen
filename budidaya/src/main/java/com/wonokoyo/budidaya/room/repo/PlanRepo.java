package com.wonokoyo.budidaya.room.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.room.BdyDatabase;
import com.wonokoyo.budidaya.room.dao.PlanDao;

import java.util.List;

public class PlanRepo {
    private PlanDao planDao;

    public PlanRepo(Application application) {
        planDao = BdyDatabase.getInstance(application).planDao();
    }

    public void savePlan(final Plan plan) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                planDao.insert(plan);
                return null;
            }
        }.execute();
    }

    public void saveAllPlan(final List<Plan> plans) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                planDao.insert(plans);
                return null;
            }
        }.execute();
    }

    public void updateDoc(final Plan plan) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                planDao.update(plan);
                return null;
            }
        }.execute();
    }

    public LiveData<List<Plan>> getPlans(String start, String end) {
        return planDao.getPlansByDate(start, end);
    }

    public LiveData<Plan> getPlanByDoSj(String no_dosj) {
        return planDao.getPlanByDoSj(no_dosj);
    }

    public LiveData<Integer> countPlan(String start, String end) {
        return planDao.getCountPlan(start, end);
    }
}
