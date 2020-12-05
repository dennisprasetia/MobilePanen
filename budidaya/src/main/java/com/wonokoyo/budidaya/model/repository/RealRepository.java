package com.wonokoyo.budidaya.model.repository;

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

    public void saveTara() {

    }

    public void saveWeigh() {

    }
}
