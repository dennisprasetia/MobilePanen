package com.wonokoyo.mitra.flow;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.mitra.R;
import com.wonokoyo.mitra.model.RealWithDetail;
import com.wonokoyo.mitra.model.adapter.RealAdapter;
import com.wonokoyo.mitra.model.viewmodel.FlowViewModel;

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
        flowViewModel.init(getApplication(), RealActivity.this);

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