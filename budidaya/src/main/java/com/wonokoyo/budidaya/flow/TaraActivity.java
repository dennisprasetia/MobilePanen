package com.wonokoyo.budidaya.flow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wonokoyo.budidaya.R;
import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.model.Tara;
import com.wonokoyo.budidaya.model.adapter.TaraAdapter;
import com.wonokoyo.budidaya.model.viewmodel.FlowViewModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TaraActivity extends AppCompatActivity {
    // variable socket timbangan
    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "192.168.100.10";

    private Thread threadReceive;

    private EditText etValue;
    private Button btnRefresh;
    private Button btnSave;
    private RecyclerView rvTara;

    private int count;
    private Plan plan;

    private List<Tara> taras;

    FlowViewModel flowViewModel;

    TaraAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tara);

        count = 0;

        flowViewModel = new FlowViewModel();
        flowViewModel.init(getApplication());

        adapter = new TaraAdapter();

        Intent intent = getIntent();
        plan = (Plan) intent.getSerializableExtra("plan");
        taras = new ArrayList<>();

        etValue = findViewById(R.id.value_tara);
        rvTara = findViewById(R.id.rv_list_tara);
        rvTara.setAdapter(adapter);

        btnRefresh = findViewById(R.id.btnRefresh);
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

        btnSave = findViewById(R.id.btnSimpan);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

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

    public void save() {
        if (count < 5) {
            if (etValue.getText().toString() != null ||
                    !etValue.getText().toString().equalsIgnoreCase("") ||
                    !etValue.getText().toString().equalsIgnoreCase("0.0")) {
                count++;
                Tara tara = new Tara();
                tara.setRit(plan.getRit());
                tara.setNodo_real(plan.getNo_do());
                tara.setUrut(String.valueOf(count));
                tara.setBerat(Double.valueOf(etValue.getText().toString()));

                taras.add(tara);
                adapter.updateData(taras);

                flowViewModel.saveTara(tara);
            }

            if (threadReceive.isAlive()) {
                threadReceive.interrupt();
            }

            threadReceive = recieve();
            threadReceive.start();
        } else {
            plan.setTaras(taras);

            Intent intent = new Intent(TaraActivity.this, WeighActivity.class);
            intent.putExtra("plan", plan);
            startActivity(intent);
        }
    }
}