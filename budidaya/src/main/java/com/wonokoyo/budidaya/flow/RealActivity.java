package com.wonokoyo.budidaya.flow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wonokoyo.budidaya.R;
import com.wonokoyo.budidaya.model.RealWithDetail;
import com.wonokoyo.budidaya.model.adapter.PlanAdapter;
import com.wonokoyo.budidaya.model.adapter.RealAdapter;
import com.wonokoyo.budidaya.model.viewmodel.FlowViewModel;

import java.util.List;

public class RealActivity extends AppCompatActivity {

    private RecyclerView rvReal;
    private TextView tvAlert;

    private RealAdapter adapter;

    FlowViewModel flowViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_real);

        flowViewModel = new FlowViewModel();
        flowViewModel.init(getApplication());

        adapter = new RealAdapter(RealActivity.this);

        rvReal = findViewById(R.id.rv_list_real);
        rvReal.setAdapter(adapter);

        tvAlert = findViewById(R.id.tvAlertReal);

        flowViewModel.getAllReal().observe(this, new Observer<List<RealWithDetail>>() {
            @Override
            public void onChanged(List<RealWithDetail> realWithDetails) {
                if (realWithDetails.size() > 0) {
                    adapter.updateData(realWithDetails);
                } else {
                    tvAlert.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}