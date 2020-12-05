package com.wonokoyo.mobilepanen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wonokoyo.budidaya.BudidayaActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvUser;
    private CardView cvBdy;
    private CardView cvMitra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        tvUser = findViewById(R.id.tvUser);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        tvUser.setText(name);

        cvBdy = findViewById(R.id.cvBdy);
        cvBdy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bdy = new Intent(MainActivity.this, BudidayaActivity.class);
                startActivity(bdy);
            }
        });
    }
}