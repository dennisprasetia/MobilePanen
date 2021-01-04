package com.wonokoyo.mitra.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.mitra.R;
import com.wonokoyo.mitra.model.Plan;
import com.wonokoyo.mitra.model.Real;
import com.wonokoyo.mitra.model.RealWithDetail;
import com.wonokoyo.mitra.model.Tara;
import com.wonokoyo.mitra.model.Weigh;
import com.wonokoyo.mitra.model.adapter.TaraAdapter;
import com.wonokoyo.mitra.model.adapter.WeighAdapter;

import java.util.List;

public class CustomDialog {
    public void initDialogDetailPlan(Context context, Plan plan, WindowManager manager) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.detail_plan, null);
        builder.setView(view);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();

        TextView tvDo = view.findViewById(R.id.tv_det_plan_do);
        TextView tvSj = view.findViewById(R.id.tv_det_plan_sj);
        TextView tvRit = view.findViewById(R.id.tv_det_plan_rit);
        TextView tvTon = view.findViewById(R.id.tv_det_plan_tonnage);
        TextView tvQuan = view.findViewById(R.id.tv_det_plan_quan);
        TextView tvDep = view.findViewById(R.id.tv_det_plan_depart);
        TextView tvArrFarm = view.findViewById(R.id.tv_det_plan_arrive_farm);
        TextView tvStart = view.findViewById(R.id.tv_det_plan_start);
        TextView tvEnd = view.findViewById(R.id.tv_det_plan_end);
        TextView tvArrRpa = view.findViewById(R.id.tv_det_plan_arrive_rpa);
        TextView tvSlice = view.findViewById(R.id.tv_det_plan_slice_time);

        tvDo.setText(plan.getNo_do());
        tvSj.setText(plan.getNo_sj());
        tvRit.setText(plan.getRit());
        tvTon.setText(String.valueOf(plan.getBerat()));
        tvQuan.setText(String.valueOf(plan.getEkor()));
        tvDep.setText(plan.getBerangkat());
        tvArrFarm.setText(plan.getTiba_farm());
        tvStart.setText(plan.getMulai_panen());
        tvEnd.setText(plan.getSelesai_panen());
        tvArrRpa.setText(plan.getTiba_rpa());
        tvSlice.setText(plan.getPotong());

        dialog.show();

        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        int width = (int) (metrics.widthPixels * 0.95f);
        int height = (int) (metrics.heightPixels * 0.65f);
        dialog.getWindow().setLayout(width, height);
    }

    public void initDialogDetailReal(Context context, RealWithDetail realWithDetail, WindowManager manager) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.detail_real, null);
        builder.setView(view);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();

        Real real = realWithDetail.getReal();
        List<Tara> taras = realWithDetail.getTaras();
        List<Weigh> weighs = realWithDetail.getWeighs();

        TextView tvDo = view.findViewById(R.id.tv_det_real_do);
        TextView tvSj = view.findViewById(R.id.tv_det_real_sj);
        TextView tvRit = view.findViewById(R.id.tv_det_real_rit);
        TextView tvStart = view.findViewById(R.id.tv_det_real_start);
        TextView tvEnd = view.findViewById(R.id.tv_det_real_end);
        TextView tvBb = view.findViewById(R.id.tv_det_real_bb);
        TextView tvNetto = view.findViewById(R.id.tv_det_real_netto);
        TextView tvQuan = view.findViewById(R.id.tv_det_real_quan);
        TextView tvBruto = view.findViewById(R.id.tv_det_real_bruto);
        TextView tvTara = view.findViewById(R.id.tv_det_real_tara);
        TextView tvTotalTara = view.findViewById(R.id.tv_det_real_total);
        RecyclerView rvTara = view.findViewById(R.id.rv_det_list_tara);
        RecyclerView rvWeigh = view.findViewById(R.id.rv_det_list_weigh);

        TaraAdapter taraAdapter = new TaraAdapter();
        rvTara.setAdapter(taraAdapter);

        WeighAdapter weighAdapter = new WeighAdapter();
        rvWeigh.setAdapter(weighAdapter);

        tvDo.setText(real.getNo_do());
        tvSj.setText(real.getNo_sj());
        tvRit.setText(real.getRit());
        tvStart.setText(real.getMulai_panen());
        tvEnd.setText(real.getSelesai_panen());
        tvBb.setText(String.format("%.2f", real.getBb()));
        tvNetto.setText(String.format("%.1f", real.getNetto()));
        tvQuan.setText(String.valueOf(real.getEkor()));
        tvBruto.setText(String.format("%.1f", real.getBruto()));
        tvTara.setText(String.valueOf(real.getTara_tandu()));
        tvTotalTara.setText(String.valueOf(real.getTotal_tandu()));

        taraAdapter.updateData(taras);
        weighAdapter.updateData(weighs);

        dialog.show();

        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        int width = (int) (metrics.widthPixels * 0.95f);
        int height = (int) (metrics.heightPixels * 0.65f);
        dialog.getWindow().setLayout(width, height);
    }
}
