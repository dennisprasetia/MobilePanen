package com.wonokoyo.mitra.room.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.wonokoyo.mitra.model.Real;
import com.wonokoyo.mitra.model.RealWithDetail;
import com.wonokoyo.mitra.model.Tara;
import com.wonokoyo.mitra.model.Weigh;
import com.wonokoyo.mitra.room.MitraDatabase;
import com.wonokoyo.mitra.room.dao.FlowDao;

import java.util.List;

public class FlowRepo {
    private FlowDao flowDao;

    public FlowRepo(Application application) {
        flowDao = MitraDatabase.getInstance(application).flowDao();
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
