package com.wonokoyo.budidaya.flow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wonokoyo.budidaya.R;
import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.model.Tara;
import com.wonokoyo.budidaya.model.adapter.TaraAdapter;
import com.wonokoyo.budidaya.model.viewmodel.FlowViewModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaraActivity extends AppCompatActivity {
    // variable socket timbangan
    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "192.168.100.10";

    private Thread threadReceive;

    private TextView tvSj;
    private EditText etValue;
    private Button btnRefresh;
    private Button btnSave;
    private RecyclerView rvTara;

    private int count;
    private String date;
    private Plan plan;

    private List<Tara> taras;

    LifecycleOwner owner;

    FlowViewModel flowViewModel;

    TaraAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tara);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(new Date());

        count = 0;

        flowViewModel = new FlowViewModel();
        flowViewModel.init(getApplication(), TaraActivity.this);

        adapter = new TaraAdapter();

        Intent intent = getIntent();
        plan = (Plan) intent.getSerializableExtra("plan");
        taras = new ArrayList<>();

        tvSj = findViewById(R.id.tv_no_sj);
        tvSj.setText(plan.getNo_do() + " - " + plan.getNo_sj());

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

        owner = this;
        flowViewModel.getTaraByRitAndDate(plan.getRit(), date).observe(owner, new Observer<List<Tara>>() {
            @Override
            public void onChanged(List<Tara> tara_exist) {
                adapter.updateData(tara_exist);
                count = tara_exist.size();
                taras = tara_exist;

                flowViewModel.getTaraByRitAndDate(plan.getRit(), date).removeObservers(owner);
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
                    if (socket.isConnected()) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        final String response = in.readLine();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (response != null) {
                                    Pattern pattern = Pattern.compile("[0-9]+\\.[0-9]");
                                    Matcher matcher = pattern.matcher(response);

                                    if (matcher.find()) {
                                        etValue.setError(null);
                                        etValue.setText(matcher.group());
                                    }
                                }
                            }
                        });
                    }
                    socket.close();
                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean validate() {
        if (etValue.getText().toString().equalsIgnoreCase("") || etValue.getText().toString().equalsIgnoreCase("0.0")) {
            etValue.setError("Awas Kosong");
            return false;
        }

        etValue.setError(null);
        return true;
    }

    public void save() {
        if (count < 5) {
            if (validate()) {
                count++;
                Tara tara = new Tara();
                tara.setRit(plan.getRit());
                tara.setNodo_real(plan.getNo_do());
                tara.setUrut(String.valueOf(count));
                tara.setBerat(Double.valueOf(etValue.getText().toString()));
                tara.setTgl_tara(date);

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

            if (taras.get(0).getNodo_real() != plan.getNo_do()) {
                saveTaraSameRit(taras);
            }

            if (threadReceive.isAlive())
                threadReceive.interrupt();

            Intent intent = new Intent(TaraActivity.this, WeighActivity.class);
            intent.putExtra("plan", plan);
            startActivity(intent);
            finish();
        }

        etValue.setText("");
    }

    public void saveTaraSameRit(final List<Tara> taras) {
        flowViewModel.getTaraByDo(plan.getNo_do()).observe(owner, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != 5) {
                    for (Tara t : taras) {
                        Tara new_tara = new Tara();
                        new_tara.setUrut(t.getUrut());
                        new_tara.setBerat(t.getBerat());
                        new_tara.setRit(plan.getRit());
                        new_tara.setTgl_tara(date);
                        new_tara.setNodo_real(plan.getNo_do());

                        flowViewModel.saveTara(new_tara);
                    }
                }

                flowViewModel.getTaraByDo(plan.getNo_do()).removeObservers(owner);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}