package com.wonokoyo.budidaya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wonokoyo.budidaya.flow.PlanActivity;
import com.wonokoyo.budidaya.flow.ScanActivity;
import com.wonokoyo.budidaya.model.viewmodel.PlanViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BudidayaActivity extends AppCompatActivity {

    private CardView cvPlan;
    private CardView cvStart;
    private CardView cvReal;
    private CardView cvSend;

    View view;

    PlanViewModel planViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_budidaya);

        planViewModel = new PlanViewModel();

        view = findViewById(R.id.menu_bdy);

        cvPlan = findViewById(R.id.cvPlan);
        cvPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String start = sdf.format(new Date());

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                Date tomorrow = calendar.getTime();
                String end = sdf.format(tomorrow);

                planViewModel.init(getApplication());
                planViewModel.syncPlan(end, start, view);
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
    }
}