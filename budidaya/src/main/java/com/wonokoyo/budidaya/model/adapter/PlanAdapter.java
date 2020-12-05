package com.wonokoyo.budidaya.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.budidaya.R;
import com.wonokoyo.budidaya.model.Plan;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanAdapterHolder> {

    private List<Plan> data;

    public PlanAdapter() {
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public PlanAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_plan_do, parent, false);
        return new PlanAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapterHolder holder, int position) {
        Plan plan = data.get(position);

        holder.tvRit.setText(plan.getRit());
        holder.tvDo.setText(plan.getNo_do());
        holder.tvSj.setText(plan.getNo_sj());
        holder.tvMitra.setText(plan.getMitra());
        holder.tvKandang.setText(plan.getKandang());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<Plan> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public class PlanAdapterHolder extends RecyclerView.ViewHolder {
        private TextView tvRit;
        private TextView tvDo;
        private TextView tvSj;
        private TextView tvMitra;
        private TextView tvKandang;

        public PlanAdapterHolder(View view) {
            super(view);

            tvRit = view.findViewById(R.id.tvRit);
            tvDo = view.findViewById(R.id.tvDo);
            tvSj = view.findViewById(R.id.tvSj);
            tvMitra = view.findViewById(R.id.tvMitra);
            tvKandang = view.findViewById(R.id.tvKandang);
        }
    }
}
