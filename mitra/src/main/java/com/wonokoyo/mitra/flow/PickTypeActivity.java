package com.wonokoyo.mitra.flow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.wonokoyo.mitra.R;
import com.wonokoyo.mitra.model.Plan;

public class PickTypeActivity extends AppCompatActivity {

    private Spinner cmbPick;
    private Button btnLanjut;

    private Plan plan;

    int max_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pick_type);

        plan = (Plan) getIntent().getSerializableExtra("plan");

        cmbPick = findViewById(R.id.cmb_pick);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                new String[] { "Pilih Tipe", "TANDU", "KERANJANG" });
        cmbPick.setAdapter(adapter);

        cmbPick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tipe = cmbPick.getItemAtPosition(i).toString();
                if (tipe == "TANDU") {
                    max_count = 1;
                }

                if (tipe == "KERANJANG") {
                    max_count = 5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnLanjut = findViewById(R.id.btn_next);
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickTypeActivity.this, TaraActivity.class);
                intent.putExtra("count", max_count);
                intent.putExtra("plan", plan);
                startActivity(intent);
                finish();
            }
        });
    }
}