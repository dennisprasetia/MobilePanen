package com.wonokoyo.mitra.flow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.mitra.R;
import com.wonokoyo.mitra.helper.TimePref;
import com.wonokoyo.mitra.model.Plan;
import com.wonokoyo.mitra.model.Real;
import com.wonokoyo.mitra.model.Tara;
import com.wonokoyo.mitra.model.Weigh;
import com.wonokoyo.mitra.model.adapter.WeighAdapter;
import com.wonokoyo.mitra.model.viewmodel.FlowViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private TextView tvDo;
    private TextView tvSj;
    private TextView tvRit;
    private TextView tvMulai;
    private TextView tvSelesai;
    private TextView tvBruto;
    private TextView tvTara;
    private TextView tvNetto;
    private TextView tvEkor;
    private TextView tvBb;
    private Button btnOk;
    private RecyclerView rvWeigh;

    private Plan plan;

    FlowViewModel flowViewModel;

    TimePref timePref;

    WeighAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        plan = (Plan) getIntent().getSerializableExtra("plan");

        timePref = new TimePref(this);

        flowViewModel = new FlowViewModel();
        flowViewModel.init(getApplication(), ResultActivity.this);

        tvDo = findViewById(R.id.no_do);
        tvDo.setText(plan.getNo_do());

        tvSj = findViewById(R.id.no_sj);
        tvSj.setText(plan.getNo_sj());

        tvRit = findViewById(R.id.real_rit);
        tvRit.setText(plan.getRit());

        tvMulai = findViewById(R.id.real_mulai);
        tvMulai.setText(timePref.getMulai());

        tvSelesai = findViewById(R.id.real_selesai);
        tvSelesai.setText(timePref.getSelesai());

        tvBruto = findViewById(R.id.real_bruto);
        tvTara = findViewById(R.id.real_total_tara);
        tvNetto = findViewById(R.id.real_netto);
        tvEkor = findViewById(R.id.real_ekor);
        tvBb = findViewById(R.id.real_bb);

        adapter = new WeighAdapter();
        rvWeigh = findViewById(R.id.rv_list_weigh);
        rvWeigh.setAdapter(adapter);

        adapter.updateData(plan.getWeighs());

        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        calcWeighAndSave();
    }

    public double calcTara() {
        double total = 0.0;
        List<Tara> taras = plan.getTaras();

        for (Tara tara: taras) {
            total += tara.getBerat();
        }

        double tara_avg = total / 1;

        return tara_avg;
    }

    public void calcWeighAndSave() {
        // CALCULATE
        String tara_avg = String.format("%.1f", calcTara());
        List<Weigh> weighs = plan.getWeighs();

        double total_tandu = Double.valueOf(tara_avg) * weighs.size();

        double bruto = 0.0;
        int quan = 0;
        for (Weigh weigh: weighs) {
            bruto += weigh.getBerat();
            quan += weigh.getEkor();
        }

        double netto = bruto - total_tandu;
        double bb_avg = netto / quan;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        tvBruto.setText(String.format("%.1f", bruto));
        tvTara.setText(String.format("%.1f", total_tandu));
        tvNetto.setText(String.format("%.1f", netto));
        tvEkor.setText(String.valueOf(quan));
        tvBb.setText(String.format("%.2f", bb_avg));

        // SAVE REAL
        Real real = new Real();
        real.setNo_do(plan.getNo_do());
        real.setNo_sj(plan.getNo_sj());
        real.setRit(plan.getRit());
        real.setKandang(plan.getKandang());
        real.setMitra(plan.getMitra());
        real.setBruto(bruto);
        real.setTara_tandu(Double.valueOf(tara_avg));
        real.setTotal_tandu(total_tandu);
        real.setBb(bb_avg);
        real.setNetto(netto);
        real.setEkor(quan);
        real.setTgl_realisasi(today);
        real.setMulai_panen(timePref.getMulai());
        real.setSelesai_panen(timePref.getSelesai());
        real.setTaras(plan.getTaras());
        real.setWeighs(plan.getWeighs());
        real.setStatus(0);

        flowViewModel.saveReal(real);
    }
}