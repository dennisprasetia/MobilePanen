package com.wonokoyo.mitra.flow;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.mitra.R;
import com.wonokoyo.mitra.model.Plan;
import com.wonokoyo.mitra.model.adapter.PlanAdapter;
import com.wonokoyo.mitra.model.viewmodel.PlanViewModel;

import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private TextView tvAlert;
    private RecyclerView rvDo;

    private PlanAdapter adapter;

    PlanViewModel planViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_plan);

        planViewModel = new PlanViewModel();
        planViewModel.init(getApplication(), PlanActivity.this);

        adapter = new PlanAdapter(PlanActivity.this);

        tvAlert = findViewById(R.id.tvAlertPlan);

        rvDo = findViewById(R.id.rv_list_do);
        rvDo.setAdapter(adapter);

        planViewModel.listDataPlan().observe(this, new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> plans) {
                if (plans.size() > 0) {
                    adapter.updateData(plans);
                } else {
                    tvAlert.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}