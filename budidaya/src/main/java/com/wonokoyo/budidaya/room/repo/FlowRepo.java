package com.wonokoyo.budidaya.room.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.wonokoyo.budidaya.model.Real;
import com.wonokoyo.budidaya.model.RealWithDetail;
import com.wonokoyo.budidaya.model.Tara;
import com.wonokoyo.budidaya.model.Weigh;
import com.wonokoyo.budidaya.room.BdyDatabase;
import com.wonokoyo.budidaya.room.dao.FlowDao;

import java.util.List;

public class FlowRepo {
    private FlowDao flowDao;

    public FlowRepo(Application application) {
        flowDao = BdyDatabase.getInstance(application).flowDao();
    }

    public void saveTara(final Tara tara) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                flowDao.insert(tara);
                return null;
            }
        }.execute();
    }

    public void saveWeigh(final Weigh weigh) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                flowDao.insert(weigh);
                return null;
            }
        }.execute();
    }

    public void saveReal(final Real real) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                flowDao.insert(real);
                return null;
            }
        }.execute();
    }

    public void updateReal(final Real real) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                flowDao.update(real);
                return null;
            }
        }.execute();
    }

    public void updateAllReal(final List<Real> reals) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                flowDao.update(reals);
                return null;
            }
        }.execute();
    }

    public LiveData<List<RealWithDetail>> getAllReal() {
        return flowDao.getAllReal();
    }

    public LiveData<Integer> countReal() {
        return flowDao.getRealCount();
    }
}
