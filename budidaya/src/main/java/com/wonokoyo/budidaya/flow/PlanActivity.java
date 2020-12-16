package com.wonokoyo.budidaya.flow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wonokoyo.budidaya.R;
import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.model.adapter.PlanAdapter;
import com.wonokoyo.budidaya.model.viewmodel.PlanViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private RecyclerView rvDo;

    private PlanAdapter adapter;

    PlanViewModel planViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_plan);

        planViewModel = new PlanViewModel();
        planViewModel.init(getApplication());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = sdf.format(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        String end = sdf.format(tomorrow);

        adapter = new PlanAdapter();

        rvDo = findViewById(R.id.rv_list_do);
        rvDo.setAdapter(adapter);

        planViewModel.listDataPlan(start, end).observe(this, new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> plans) {
                if (plans.size() > 0) {
                    adapter.updateData(plans);
                }
            }
        });
    }
}