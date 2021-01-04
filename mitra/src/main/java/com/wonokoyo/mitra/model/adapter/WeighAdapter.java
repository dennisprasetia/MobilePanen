package com.wonokoyo.mitra.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.mitra.R;
import com.wonokoyo.mitra.model.Weigh;

import java.util.ArrayList;
import java.util.List;

public class WeighAdapter extends RecyclerView.Adapter<WeighAdapter.WeighAdapterHolder>{

    private List<Weigh> data;

    public WeighAdapter() {
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public WeighAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_tara, parent, false);
        return new WeighAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeighAdapterHolder holder, int position) {
        Weigh weigh = data.get(position);

        holder.tvSeq.setText(String.valueOf(weigh.getUrut()));
        holder.tvValue.setText(String.valueOf(weigh.getBerat()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<Weigh> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public class WeighAdapterHolder extends RecyclerView.ViewHolder {
        private TextView tvSeq;
        private TextView tvValue;

        public WeighAdapterHolder(View view) {
            super(view);

            tvSeq = view.findViewById(R.id.tvSeq);
            tvValue = view.findViewById(R.id.tvValue);
        }
    }
}
