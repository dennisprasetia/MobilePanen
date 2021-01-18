package com.wonokoyo.mobilepanen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.wonokoyo.mobilepanen.model.User;
import com.wonokoyo.mobilepanen.serveraccess.RetrofitInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView etName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirm;
    private EditText etId;
    private EditText etTelephone;
    private Button btnRegister;

    ProgressDialog pd;

    View view;

    TelephonyManager manager;

    private String[] daftarNamaTimpanen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pd = new ProgressDialog(this);
        pd.setMessage("Sedang memproses");
        pd.setCancelable(false);

        view = findViewById(R.id.register);

        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        pullDataTimpanen();

        etName = findViewById(R.id.nama_lengkap);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        etTelephone = findViewById(R.id.telephone);
        etId = findViewById(R.id.device_id);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                etId.setText(manager.getImei(0).toString());
//            }
//        }
        etId.setText(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

        etConfirm = findViewById(R.id.conf_password);
        etConfirm.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((keyCode > KeyEvent.KEYCODE_0 && keyCode < KeyEvent.KEYCODE_Z) || keyCode == KeyEvent.KEYCODE_DEL)
                    return cekPassword();

                return false;
            }
        });

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();

                User user = new User();
                user.setName(etName.getText().toString());
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.setDeviceId(etId.getText().toString());
                user.setTelephone(etTelephone.getText().toString());

                register(user);
            }
        });
    }

    public void register(User user) {
        if (validateField()) {
            Call<ResponseBody> callResponse = RetrofitInstance.getLoginService().register(new Gson().toJson(user));
            callResponse.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");

                            if (pd.isShowing()) {
                                pd.dismiss();
                                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();

                                if (status == 1) {
                                    finish();
                                } else if (status == 2) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Detail Hubungi Admin",
                                            Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0,0);
                                    toast.show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Snackbar.make(view, "Tidak bisa terhubung", Snackbar.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (t.getMessage().equalsIgnoreCase("timeout")) {
                        Snackbar.make(view, "Waktu koneksi habis", Snackbar.LENGTH_SHORT).show();
                    }

                    if (t.getMessage().contains("failed to connect")) {
                        Snackbar.make(view, "Koneksi ke server gagal", Snackbar.LENGTH_SHORT).show();
                    }

                    pd.dismiss();
                }
            });
        }
    }

    public boolean validateField() {
        String name = etName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirm = etConfirm.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Harap isi nama");
            etName.requestFocus();
            return false;
        }
        if (username.isEmpty()) {
            etUsername.setError("Harap isi username");
            etUsername.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Harap isi password");
            etPassword.requestFocus();
            return false;
        }
        if (confirm.isEmpty()) {
            etConfirm.setError("Harap isi konfirmasi");
            etConfirm.requestFocus();
            return false;
        }
        if (!confirm.equals(password)) {
            etConfirm.setError("Password tidak sama");
            etConfirm.requestFocus();
            return false;
        }

        return true;
    }

    public boolean cekPassword() {
        String password = etPassword.getText().toString().trim();
        String confirm = etConfirm.getText().toString().trim();

        if (!password.equals(confirm)) {
            etConfirm.setError("Password harus sama !");
            return false;
        }
        return true;
    }

    public void pullDataTimpanen() {
        Call<ResponseBody> ambilDaftarSopir = RetrofitInstance.getLoginService().getDataTimpanen();
        ambilDaftarSopir.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("content");

                        if (jsonArray.length() > 0) {
                            daftarNamaTimpanen = new String[jsonArray.length()];
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject item = jsonArray.getJSONObject(a);

                                daftarNamaTimpanen[a] = item.getString("timpanen");
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,
                                android.R.layout.simple_list_item_1, daftarNamaTimpanen);
                        etName.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("CEK", "GAGAL");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (t.getMessage().equalsIgnoreCase("timeout")) {
                    Snackbar.make(view, "Waktu koneksi habis", Snackbar.LENGTH_SHORT).show();
                }

                if (t.getMessage().contains("failed to connect")) {
                    Snackbar.make(view, "Koneksi ke server gagal", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}