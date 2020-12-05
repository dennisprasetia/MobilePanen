package com.wonokoyo.budidaya.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wonokoyo.budidaya.R;
import com.wonokoyo.budidaya.model.Tara;

import java.util.ArrayList;
import java.util.List;

public class TaraAdapter extends RecyclerView.Adapter<TaraAdapter.TaraAdapterHolder> {

    private List<Tara> data;

    public TaraAdapter() {
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public TaraAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_tara, parent, false);
        return new TaraAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaraAdapterHolder holder, int position) {
        Tara tara = data.get(position);

        holder.tvSeq.setText(tara.getUrut());
        holder.tvValue.setText(String.valueOf(tara.getBerat()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateData(List<Tara> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public class TaraAdapterHolder extends RecyclerView.ViewHolder {
        private TextView tvSeq;
        private TextView tvValue;

        public TaraAdapterHolder(View view) {
            super(view);

            tvSeq = view.findViewById(R.id.tvSeq);
            tvValue = view.findViewById(R.id.tvValue);
        }
    }
}
