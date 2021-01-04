package com.wonokoyo.mitra.model.viewmodel;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.wonokoyo.mitra.model.Real;
import com.wonokoyo.mitra.model.RealWithDetail;
import com.wonokoyo.mitra.model.Tara;
import com.wonokoyo.mitra.model.Weigh;
import com.wonokoyo.mitra.model.repository.RealRepository;
import com.wonokoyo.mitra.room.repo.FlowRepo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlowViewModel {
    private RealRepository realRepository;
    private FlowRepo flowRepo;
    private Application app;

    private ProgressDialog dialog;

    public void init(Application application, Context context) {
        realRepository = new RealRepository();
        flowRepo = new FlowRepo(application);

        this.app = application;

        dialog = new ProgressDialog(context);
        dialog.setMessage("Sedang memuat data");
        dialog.setCancelable(false);
    }

    public void saveTara(Tara tara) {
        flowRepo.saveTara(tara);
    }

    public void saveWeigh(Weigh weigh) {
        flowRepo.saveWeigh(weigh);
    }

    public void saveReal(Real real) {
        flowRepo.saveReal(real);
    }

    public void update(Real real) {
        flowRepo.updateReal(real);
    }

    public LiveData<Integer> getCountReal() {
        return flowRepo.countReal();
    }

    public LiveData<List<RealWithDetail>> getAllReal() {
        return flowRepo.getAllReal();
    }

    public void upload(final List<RealWithDetail> reals) {
        dialog.show();

        Callback<ResponseBody> listener = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        int status = jsonObject.getInt("status");
                        String message = jsonObject.getString("message");

                        if (dialog.isShowing())
                            dialog.dismiss();

                        Log.d("CEK DATA", jsonObject.toString());

                        if (status == 1) {
                            for (RealWithDetail real: reals) {
                                real.getReal().setStatus(1);

                                flowRepo.updateReal(real.getReal());
                            }
                        } else {

                        }

                        Toast toast = Toast.makeText(app.getApplicationContext(), message, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };

        realRepository.saveReal(reals, listener);
    }
}
