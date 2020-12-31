package com.wonokoyo.mobilepanen;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.wonokoyo.mobilepanen.helper.SharedPrefManager;
import com.wonokoyo.mobilepanen.serveraccess.RetrofitInstance;
import com.wonokoyo.mobilepanen.util.BiometricCallback;
import com.wonokoyo.mobilepanen.util.BiometricManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements BiometricCallback {

    BiometricManager mBiometricManager;

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private ImageView ivFP;
    private TextView tvRegister;

    TelephonyManager manager;

    SharedPrefManager spManager;

    View view;

    private String idUser, namaUser, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        view = findViewById(R.id.login);

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        spManager = new SharedPrefManager(this);

        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.INTERNET}, 200);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    String deviceId = "";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        deviceId = manager.getImei(0);
                    } else {
                        deviceId = manager.getDeviceId();
                    }

                    loginUser(etUsername.getText().toString(), etPassword.getText().toString(), deviceId);
                }
            }
        });

        ivFP = findViewById(R.id.ivFP);
        ivFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBiometricManager = new BiometricManager.BiometricBuilder(LoginActivity.this)
                        .setTitle(getString(R.string.biometric_title))
                        .setSubtitle(getString(R.string.biometric_subtitle))
                        .setDescription(getString(R.string.biometric_description))
                        .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                        .build();

                //start authentication
                mBiometricManager.authenticate(LoginActivity.this);
            }
        });

        tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        skipLogin();
    }

    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    @Override
    public void onAuthenticationCancelled() {
        Snackbar snackbar = Snackbar.make(view, getString(R.string.biometric_cancelled), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.show();
        mBiometricManager.cancelAuthentication();
    }

    @Override
    public void onAuthenticationSuccessful() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_success), Toast.LENGTH_SHORT).show();
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            String deviceId = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                deviceId = manager.getImei(0);
            }

            loginUser("", "", deviceId);
        }
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

    }

    public void loginUser(String username, String password, String deviceId) {
        Call<ResponseBody> call = RetrofitInstance.getLoginService().login(username, password, deviceId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        int status = jsonObject.getInt("status");
                        message = jsonObject.getString("message");

                        if (status == 1) {
                            idUser = jsonObject.getString("id");
                            namaUser = jsonObject.getString("name");

                            spManager.saveSPString(SharedPrefManager.SP_USER, idUser);
                            spManager.saveSPString(SharedPrefManager.SP_NAME, namaUser);
                            spManager.saveSPBoolean(SharedPrefManager.SP_LOGIN, true);

                            resultLogin(true);
                        } else {
                            idUser = "";
                            namaUser = "";

                            resultLogin(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.getMessage().equalsIgnoreCase("timeout")) {
                    Toast.makeText(getApplicationContext(), "Waktu koneksi habis", Toast.LENGTH_SHORT).show();
                }

                if (t.getMessage().contains("failed to connect")) {
                    Toast.makeText(getApplicationContext(), "Koneksi ke server gagal", Toast.LENGTH_SHORT).show();
                }

                idUser = "";
                namaUser = "";
                message = "";
            }
        });
    }

    public void resultLogin(Boolean isLogin) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        if (isLogin) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("id", idUser);
            intent.putExtra("name", namaUser);
            startActivity(intent);
            finish();
        }
    }

    public void skipLogin() {
        if (spManager.getLogin()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("id", spManager.getUser());
            intent.putExtra("name", spManager.getName());
            startActivity(intent);
            finish();
        }
    }
}