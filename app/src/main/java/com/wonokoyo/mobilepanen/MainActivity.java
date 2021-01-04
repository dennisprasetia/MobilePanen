package com.wonokoyo.mobilepanen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonokoyo.budidaya.BudidayaActivity;
import com.wonokoyo.mitra.MitraActivity;
import com.wonokoyo.mobilepanen.helper.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    private ImageView btnLogout;
    private TextView tvUser;
    private CardView cvBdy;
    private CardView cvMitra;

    SharedPrefManager spManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        spManager = new SharedPrefManager(this);

        tvUser = findViewById(R.id.tv_user);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");
        spManager.saveSPString(SharedPrefManager.SP_NAME, name);
        spManager.saveSPString(SharedPrefManager.SP_USER, id);

        tvUser.setText(name);

        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spManager.saveSPBoolean(SharedPrefManager.SP_LOGIN, false);

                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });

        cvBdy = findViewById(R.id.cvBdy);
        cvBdy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bdy = new Intent(MainActivity.this, BudidayaActivity.class);
                bdy.putExtra("nik", spManager.getUser());
                startActivity(bdy);
            }
        });

        cvMitra = findViewById(R.id.cvMitra);
        cvMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mitra = new Intent(MainActivity.this, MitraActivity.class);
                mitra.putExtra("nik", spManager.getUser());
                startActivity(mitra);
            }
        });
    }
}