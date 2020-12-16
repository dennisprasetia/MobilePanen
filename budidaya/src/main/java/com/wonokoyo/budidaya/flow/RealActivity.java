package com.wonokoyo.budidaya.flow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wonokoyo.budidaya.R;

public class RealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_real);
    }
}