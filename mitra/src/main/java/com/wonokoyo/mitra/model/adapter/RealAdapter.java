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
import com.wonokoyo.mitra.model.Real;
import com.wonokoyo.mitra.model.RealWithDetail;

import java.util.ArrayList;
import java.util.List;

public class RealAdapter extends RecyclerView.Adapter<RealAdapter.RealAdapterHolder> {

    private List<RealWithDetail> data;
    private Context context;

    public RealAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public RealAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_do, parent, false);
        return new RealAdapter.RealAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RealAdapterHolder holder, final int position) {
        Real real = data.get(position).getReal();

        holder.cvReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog();
                dialog.initDialogDetailReal(context, data.get(position), (WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
            }
        });
        holder.tvRit.setText(real.getRit());
        holder.tvDo.setText(real.getNo_do());
        holder.tvSj.setText(real.getNo_sj());
        holder.tvMitra.setText(real.getMitra());
        holder.tvKandang.setText(real.getKandang());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<RealWithDetail> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public class RealAdapterHolder extends RecyclerView.ViewHolder {
        private CardView cvReal;
        private TextView tvRit;
        private TextView tvDo;
        private TextView tvSj;
        private TextView tvMitra;
        private TextView tvKandang;

        public RealAdapterHolder(View view) {
            super(view);

            cvReal = view.findViewById(R.id.cv_do);
            tvRit = view.findViewById(R.id.tvRit);
            tvDo = view.findViewById(R.id.tvDo);
            tvSj = view.findViewById(R.id.tvSj);
            tvMitra = view.findViewById(R.id.tvMitra);
            tvKandang = view.findViewById(R.id.tvKandang);
        }
    }
}
