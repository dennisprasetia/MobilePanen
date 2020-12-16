package com.wonokoyo.budidaya.flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wonokoyo.budidaya.R;
import com.wonokoyo.budidaya.helper.TimePref;
import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.model.Tara;
import com.wonokoyo.budidaya.model.Weigh;
import com.wonokoyo.budidaya.model.viewmodel.FlowViewModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeighActivity extends AppCompatActivity {
    // variable socket timbangan
    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "192.168.100.10";

    private Thread threadReceive;

    private TextView tvSeq;
    private EditText etValue;
    private EditText etQuan;
    private TextView tvTara;
    private TextView tvLeft;
    private TextView tvTonase;
    private TextView tvBb;
    private Button btnSave;
    private Button btnRefresh;
    private Button btnNext;

    private int count;
    private int tails;
    private int quan;
    private double tonase;
    private double total_nett;

    private Plan plan;

    private List<Weigh> weighs;

    FlowViewModel flowViewModel;

    TimePref timePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_weigh);

        timePref = new TimePref(this);

        Intent intent = getIntent();
        plan = (Plan) intent.getSerializableExtra("plan");
        weighs = new ArrayList<>();

        flowViewModel = new FlowViewModel();
        flowViewModel.init(getApplication());

        tvSeq = findViewById(R.id.value_seq);
        etValue = findViewById(R.id.value_weigh);
        etQuan = findViewById(R.id.value_quan);

        tvTara = findViewById(R.id.value_tara_avg);
        setTara();

        tvLeft = findViewById(R.id.value_quan_left);
        tvLeft.setText(String.valueOf(plan.getEkor()));
        tails = plan.getEkor();

        tvTonase = findViewById(R.id.value_tonase);
        tvTonase.setText(String.valueOf(plan.getBerat()));
        tonase = plan.getBerat();

        tvBb = findViewById(R.id.value_bb);

        count = 1;
        quan = 0;
        total_nett = 0.0;
        tvSeq.setText(String.valueOf(count));

        btnSave = findViewById(R.id.btnSimpanWeigh);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan.setWeighs(weighs);

                if (threadReceive.isAlive())
                    threadReceive.interrupt();

                // SET JAM SELESAI PANEN
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sdf.format(new Date());
                timePref.saveSPString(TimePref.TM_SELESAI, time);

                Intent result = new Intent(WeighActivity.this, ResultActivity.class);
                result.putExtra("plan", plan);
                startActivity(result);
                finish();
            }
        });

        btnRefresh = findViewById(R.id.btnRefreshWeigh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (threadReceive.isAlive()) {
                    threadReceive.interrupt();
                }

                threadReceive = recieve();
                threadReceive.start();
            }
        });

        btnNext = findViewById(R.id.btnLanjut);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Weigh weigh = new Weigh();
                weigh.setNodo_real(plan.getNo_do());
                weigh.setUrut(count);
                weigh.setBerat(Double.valueOf(etValue.getText().toString()));
                weigh.setEkor(Integer.valueOf(etQuan.getText().toString()));

                weighs.add(weigh);

                flowViewModel.saveWeigh(weigh);
                calcAndReset();
            }
        });

        threadReceive = recieve();
        threadReceive.start();
    }

    public void setTara() {
        double total_tara = 0.0;
        for (Tara tara : plan.getTaras()) {
            total_tara = total_tara + tara.getBerat();
        }

        double avg_tara = total_tara / 12.5;
        tvTara.setText(String.format("%.1f", avg_tara));
    }

    public void calcAndReset() {
        // CALCULATE
        quan += Integer.valueOf(etQuan.getText().toString());
        tails = tails - Integer.valueOf(etQuan.getText().toString());
        tvLeft.setText(String.valueOf(tails));

        double nett = Double.valueOf(etValue.getText().toString()) - Double.valueOf(tvTara.getText().toString());
        tonase = tonase - nett;
        total_nett += nett;
        tvTonase.setText(String.format("%.2f", tonase));

        double bb = total_nett / quan;
        tvBb.setText(String.format("%.2f", bb));

        // RESET
        count++;

        tvSeq.setText(String.valueOf(count));
        etValue.setText("");

        if (threadReceive.isAlive()) {
            threadReceive.interrupt();
        }
        threadReceive = recieve();
        threadReceive.start();

    }

    public Thread recieve() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(SERVER_IP, SERVERPORT);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    final String response = in.readLine();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response != null) {
                                String[] split = response.split(" ");
                                etValue.setText(split[split.length - 2]);
                            }
                        }
                    });

                    socket.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}