package com.riskfall.detector;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.riskfall.detector.apihelper.BaseApiService;
import com.riskfall.detector.apihelper.ResponseApi;
import com.riskfall.detector.apihelper.UtilsApi;


public class pediatri_assesment extends AppCompatActivity {


    @BindView(R.id.buttonSubmit) Button buttonSubmit;
    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;
    int no_rekam_medis;
    int kamar;
    String ket,kondisi;
    int rekomendasi;
    int jawab1;
    int jawab2;
    int jawab3;
    int jawab4;
    int jawab5;
    int jawab6;
    int jawab7;
    int hasil;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pediatri_assesment);

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();


        no_rekam_medis = getIntent().getIntExtra("no_rekam_medis", 0);
        if (no_rekam_medis == 0) {
            no_rekam_medis = Assesment.NO_REKAM_MEDIS;
        }

        final RadioGroup soal1=(RadioGroup) findViewById(R.id.soal1);
        soal1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.soal1a:
                        jawab1 = 4;
                        break;
                    case R.id.soal1b:
                        jawab1 = 3;
                        break;
                    case R.id.soal1c:
                        jawab1 = 2;
                        break;
                    case R.id.soal1d:
                        jawab1 = 1;
                        break;
                }

            }
        });


        final RadioGroup soal2=(RadioGroup) findViewById(R.id.soal2);
        soal2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.soal2a:
                        jawab2 = 2;
                        break;
                    case R.id.soal2b:
                        jawab2 = 1;
                        break;
                }
            }
        });


        final RadioGroup soal3=(RadioGroup) findViewById(R.id.soal3);
        soal3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.soal3a:
                        jawab3 = 4;
                        break;
                    case R.id.soal3b:
                        jawab3 = 3;
                        break;
                    case R.id.soal3c:
                        jawab3 = 2;
                        break;
                    case R.id.soal3d:
                        jawab3 = 1;
                        break;
                }
            }
        });

        final RadioGroup soal4=(RadioGroup) findViewById(R.id.soal4);
        soal4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.soal4a:
                        jawab4 = 3;
                        break;
                    case R.id.soal4b:
                        jawab4 = 2;
                        break;
                    case R.id.soal4c:
                        jawab4 = 1;
                        break;
                }
            }
        });

        final RadioGroup soal5=(RadioGroup) findViewById(R.id.soal5);
        soal5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.soal5a:
                        jawab5 = 4;
                        break;
                    case R.id.soal5b:
                        jawab5 = 3;
                        break;
                    case R.id.soal5c:
                        jawab5 = 2;
                        break;
                    case R.id.soal3d:
                        jawab5 = 1;
                        break;
                }
            }
        });


        final RadioGroup soal6=(RadioGroup) findViewById(R.id.soal6);
        soal6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.soal6a:
                        jawab6 = 3;
                        break;
                    case R.id.soal6b:
                        jawab6 = 2;
                        break;
                    case R.id.soal6c:
                        jawab6 = 1;
                        break;
                }
            }
        });

        final RadioGroup soal7=(RadioGroup) findViewById(R.id.soal7);
        soal6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.soal7a:
                        jawab7 = 3;
                        break;
                    case R.id.soal7b:
                        jawab7 = 2;
                        break;
                    case R.id.soal7c:
                        jawab7 = 1;
                        break;
                }
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestJawaban();
                hasil=jawab1+jawab2+jawab3+jawab4+jawab5+jawab6+jawab7;
                if(hasil>11){
                    ket= "Tinggi";
                    rekomendasi=1;
                    kondisi= "NO";
                    kesensorRequest();
                }else{
                    ket="Rendah";
                    rekomendasi=2;
                }
                resultRequest();
            }
        });

    }

    private void requestJawaban() {
        mApiService.pediatriAssesmentRequest(String.valueOf(no_rekam_medis), String.valueOf(jawab1), String.valueOf(jawab2), String.valueOf(jawab3),String.valueOf(jawab4),String.valueOf(jawab5),String.valueOf(jawab6),String.valueOf(jawab7))
                .enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                        if (response.isSuccessful()) {
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();

                            if (response.body().success) {
                                Toast.makeText(mContext, "ASSESSMENT BERHASIL", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(mContext, Result.class));
                                finish();
                            } else {
                                Toast.makeText(mContext, "gagal", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.i("debug", "onResponse: ASSESSMENT GAGAL");
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseApi> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resultRequest(){
        mApiService.resultRequest(String.valueOf(no_rekam_medis), String.valueOf(hasil), String.valueOf(ket), String.valueOf(rekomendasi) ).enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                if (response.isSuccessful()) {
                    Log.i("debug", "onResponse: BERHASIL");
                    loading.dismiss();

                    if (response.body().success) {
                        Toast.makeText(mContext, "ASSESSMENT BERHASIL", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("debug", "onResponse: ASSESSMENT GAGAL");
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void kesensorRequest(){
        mApiService.kesensorRequest(String.valueOf(no_rekam_medis), String.valueOf(kamar), String.valueOf(kondisi)).enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                if (response.isSuccessful()) {
                    Log.i("debug", "onResponse: BERHASIL");
                    loading.dismiss();

                    if (response.body().success) {
                        Toast.makeText(mContext, "ASSESSMENT BERHASIL", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("debug", "onResponse: ASSESSMENT GAGAL");
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


}


