package com.wonokoyo.mitra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.wonokoyo.mitra.flow.RealActivity;
import com.wonokoyo.mitra.flow.ScanActivity;
import com.wonokoyo.mitra.model.RealWithDetail;
import com.wonokoyo.mitra.model.viewmodel.FlowViewModel;
import com.wonokoyo.mitra.model.viewmodel.PlanViewModel;

import java.util.List;

public class MitraActivity extends AppCompatActivity {

    private CardView cvPlan;
    private CardView cvStart;
    private CardView cvReal;
    private CardView cvSend;
    private TextView tvQuanPlan;
    private TextView tvQuanReal;

    View view;

    PlanViewModel planViewModel;
    FlowViewModel flowViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mitra);

        planViewModel = new PlanViewModel();
        planViewModel.init(getApplication(), MitraActivity.this);

        flowViewModel = new FlowViewModel();
        flowViewModel.init(getApplication(), MitraActivity.this);

        view = findViewById(R.id.menu_mitra);

        tvQuanPlan = findViewById(R.id.tvQuanPlan);
        tvQuanReal = findViewById(R.id.tvQuanReal);

        planViewModel.getCountPlan().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tvQuanPlan.setText(String.valueOf(integer));
            }
        });

        flowViewModel.getCountReal().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tvQuanReal.setText(String.valueOf(integer));
            }
        });

        cvPlan = findViewById(R.id.cvPlan);
        cvPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planViewModel.syncPlan(view);
            }
        });

        cvStart = findViewById(R.id.cvStart);
        cvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MitraActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        cvReal = findViewById(R.id.cvReal);
        cvReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MitraActivity.this, RealActivity.class);
                startActivity(intent);
            }
        });

        cvSend = findViewById(R.id.cvSend);
        cvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flowViewModel.getAllReal().observe(MitraActivity.this, new Observer<List<RealWithDetail>>() {
                    @Override
                    public void onChanged(List<RealWithDetail> reals) {
                        if (reals.size() > 0) {
                            flowViewModel.upload(reals);
                            flowViewModel.getAllReal().removeObservers(MitraActivity.this);
                        } else {
                            Snackbar snackbar = Snackbar.make(view, getString(R.string.data_empty),
                                    Snackbar.LENGTH_INDEFINITE);
                            snackbar.setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }
                    }
                });
            }
        });
    }
}