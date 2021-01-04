package com.wonokoyo.mitra.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.mitra.R;
import com.wonokoyo.mitra.helper.CustomDialog;
import com.wonokoyo.mitra.model.Plan;

import java.util.ArrayList;
import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanAdapterHolder> {

    private List<Plan> data;
    private Context context;

    public PlanAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public PlanAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_do, parent, false);
        return new PlanAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapterHolder holder, int position) {
        final Plan plan = data.get(position);

        holder.cvPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog();
                dialog.initDialogDetailPlan(context, plan, (WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
            }
        });
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
        private CardView cvPlan;
        private TextView tvRit;
        private TextView tvDo;
        private TextView tvSj;
        private TextView tvMitra;
        private TextView tvKandang;

        public PlanAdapterHolder(View view) {
            super(view);

            cvPlan = view.findViewById(R.id.cv_do);
            tvRit = view.findViewById(R.id.tvRit);
            tvDo = view.findViewById(R.id.tvDo);
            tvSj = view.findViewById(R.id.tvSj);
            tvMitra = view.findViewById(R.id.tvMitra);
            tvKandang = view.findViewById(R.id.tvKandang);
        }
    }
}
