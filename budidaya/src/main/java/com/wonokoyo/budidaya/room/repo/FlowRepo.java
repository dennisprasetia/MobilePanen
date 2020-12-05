package com.wonokoyo.budidaya.room.repo;

import android.app.Application;
import android.os.AsyncTask;

import com.wonokoyo.budidaya.model.Tara;
import com.wonokoyo.budidaya.model.Weigh;
import com.wonokoyo.budidaya.room.BdyDatabase;
import com.wonokoyo.budidaya.room.dao.FlowDao;

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
}
