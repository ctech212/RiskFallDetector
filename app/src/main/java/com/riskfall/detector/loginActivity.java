package com.riskfall.detector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.riskfall.detector.apihelper.BaseApiService;
import com.riskfall.detector.apihelper.ResponseApi;
import com.riskfall.detector.apihelper.SharedPrefManager;
import com.riskfall.detector.apihelper.UtilsApi;
import com.riskfall.detector.apihelper.model.Perawat;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;


public class loginActivity extends AppCompatActivity {
    @BindView(R.id.etusername)
    EditText etusername;
    @BindView(R.id.etpassword)
    EditText etpassword;
    @BindView(R.id.btn)
    Button btn;
    ProgressDialog loading;

    Context mContext;
    BaseApiService mApiService;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().hide();

        ButterKnife.bind(this);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestLogin();
            }

        });

        if (sharedPrefManager.getSPSudahLogin()) {
            startActivity(new Intent(loginActivity.this, Mainmenu.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

    }

    private void requestLogin() {
        mApiService.loginRequest(etusername.getText().toString(), etpassword.getText().toString())
                .enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseApi> call, Response<ResponseApi> response) {
//                        if (response.isSuccessful()){
                        loading.dismiss();
                        String text = response.body().toString();
                        if (response.body().success) {
                            try {
                                Perawat p = response.body().perawat;
                                // Jika login berhasil maka data nama yang ada di response API
                                // akan diparsing ke activity selanjutnya.
                                Toast.makeText(mContext, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
                                //String nama = jsonRESULTS.getJSONObject("user").getString("name");
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, p.nama);
                                sharedPrefManager.saveSPString(SharedPrefManager.SP_idPerawat, p.idperawat);
//                                    // Shared Pref ini berfungsi untuk menjadi trigger session login
                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                            }catch (Exception e){
                                Log.e("error",e+"");

                            }
                            startActivity(new Intent(mContext, Mainmenu.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();

                        } else {
                            // Jika login gagal
//                                    String error_message = jsonRESULTS.getString("error_msg");
                            Toast.makeText(mContext, response.body().data.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
//

                    @Override
                    public void onFailure(retrofit2.Call<ResponseApi> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }

}
