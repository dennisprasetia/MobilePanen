package com.wonokoyo.budidaya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.wonokoyo.budidaya.flow.RealActivity;
import com.wonokoyo.budidaya.flow.ScanActivity;
import com.wonokoyo.budidaya.helper.TimePref;
import com.wonokoyo.budidaya.model.RealWithDetail;
import com.wonokoyo.budidaya.model.viewmodel.FlowViewModel;
import com.wonokoyo.budidaya.model.viewmodel.PlanViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BudidayaActivity extends AppCompatActivity {

    private CardView cvPlan;
    private CardView cvStart;
    private CardView cvReal;
    private CardView cvSend;
    private TextView tvQuanPlan;
    private TextView tvQuanReal;

    LifecycleOwner owner;

    View view;

    TimePref pref;

    PlanViewModel planViewModel;
    FlowViewModel flowViewModel;

    Observer<List<RealWithDetail>> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_budidaya);

        pref = new TimePref(this);
        pref.saveSPString(TimePref.TM_NIK, getIntent().getStringExtra("nik"));

        planViewModel = new PlanViewModel();
        planViewModel.init(getApplication(), BudidayaActivity.this);

        flowViewModel = new FlowViewModel();
        flowViewModel.init(getApplication(), BudidayaActivity.this);

        view = findViewById(R.id.menu_bdy);

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
                Intent intent = new Intent(BudidayaActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        cvReal = findViewById(R.id.cvReal);
        cvReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BudidayaActivity.this, RealActivity.class);
                startActivity(intent);
            }
        });

        owner = this;
        observer = new Observer<List<RealWithDetail>>() {
            @Override
            public void onChanged(List<RealWithDetail> reals) {
                if (reals.size() > 0) {
                    flowViewModel.upload(reals, pref.getNik());
                    flowViewModel.getAllReal().removeObserver(observer);
                    flowViewModel.getAllReal().removeObservers(owner);
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
        };

        cvSend = findViewById(R.id.cvSend);
        cvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flowViewModel.getAllReal().observe(owner, observer);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}