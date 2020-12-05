package com.wonokoyo.budidaya.model.viewmodel;

import android.app.Application;

import com.wonokoyo.budidaya.model.Tara;
import com.wonokoyo.budidaya.model.Weigh;
import com.wonokoyo.budidaya.model.repository.PlanRepository;
import com.wonokoyo.budidaya.room.repo.FlowRepo;
import com.wonokoyo.budidaya.room.repo.PlanRepo;

public class FlowViewModel {
    private FlowRepo flowRepo;

    public void init(Application application) {
        flowRepo = new FlowRepo(application);
    }

    public void saveTara(Tara tara) {
        flowRepo.saveTara(tara);
    }

    public void saveWeigh(Weigh weigh) {
        flowRepo.saveWeigh(weigh);
    }
}
