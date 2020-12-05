package com.wonokoyo.budidaya.flow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.zxing.Result;
import com.wonokoyo.budidaya.R;
import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.model.Tara;
import com.wonokoyo.budidaya.model.viewmodel.PlanViewModel;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private FrameLayout frameLayout;
    private final static int REQUEST_CAMERA_CODE = 121;

    PlanViewModel planViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scan);

        planViewModel = new PlanViewModel();
        planViewModel.init(getApplication());

        mScannerView = new ZXingScannerView(this);
        frameLayout = findViewById(R.id.flScanner);

        requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                frameLayout.addView(mScannerView);
            } else {
                Toast.makeText(this, getString(R.string.camera_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        String scan = rawResult.getText();

        planViewModel.getPlanByDoSj(scan).observe(this, new Observer<Plan>() {
            @Override
            public void onChanged(Plan plan) {
                if (plan != null) {
                    Intent intent = new Intent(ScanActivity.this, TaraActivity.class);
                    intent.putExtra("plan", plan);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.plan_not_found),
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();

                    mScannerView.resumeCameraPreview(ScanActivity.this);
                }
            }
        });
    }
}