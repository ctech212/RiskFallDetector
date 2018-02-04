package com.riskfall.detector;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
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

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.exception.SaripaarViolationException;
import com.riskfall.detector.apihelper.BaseApiService;
import com.riskfall.detector.apihelper.ResponseApi;
import com.riskfall.detector.apihelper.SharedPrefManager;
import com.riskfall.detector.apihelper.UtilsApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Year;
import java.util.Calendar;
import java.util.List;



public class Assesment extends AppCompatActivity implements Validator.ValidationListener{
    @NotEmpty
    @BindView(R.id.idperawat)
    EditText idperawat;
    @NotEmpty
    @BindView(R.id.no_medis)
    EditText no_medis;
    @NotEmpty
    @BindView(R.id.p_name)
    EditText p_name;

    @NotEmpty
    @BindView(R.id.p_age)
    EditText p_age;

    @NotEmpty
    @BindView(R.id.p_gender)
    EditText p_gender;
    @NotEmpty
    @BindView(R.id.p_diagnosis)
    EditText p_diagnosis;
    @NotEmpty
    @BindView(R.id.p_class)
    EditText p_class;
    @NotEmpty
    @BindView(R.id.p_room)
    EditText p_room;
    @BindView(R.id.btn_cal)
    ImageView buttonCal;

    @BindView(R.id.start)
    Button btnRegister;
    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;

    int usia;
    Validator validator;
    public static int NO_REKAM_MEDIS;
    public static int KAMAR;
    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            usia = getUsia(year, month, dayOfMonth);
            p_age.setText(dayOfMonth + "-" + month + "-" + year);
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_assesment);

        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);

        sharedPrefManager = new SharedPrefManager(this);
        if (!sharedPrefManager.getSP_idPerawat().equalsIgnoreCase("")){
            idperawat.setText(sharedPrefManager.getSP_idPerawat());
        }
        mContext = this;
        mApiService = UtilsApi.getAPIService();


        buttonCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(Assesment.this, listener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_WEEK));
                dialog.show();
            }

        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();

            }
        });
    }

    public int getUsia(int y, int m, int d) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(y, m, d);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return new Integer(age);
    }


    private void requestRegister() {
        mApiService.registerRequest(no_medis.getText().toString(), idperawat.getText().toString(), p_name.getText().toString(), String.valueOf(usia), p_gender.getText().toString(), p_diagnosis.getText().toString(), p_class.getText().toString(), p_room.getText().toString())
                .enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                        if (response.isSuccessful()) {
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();

                            if (response.body().success) {
                                NO_REKAM_MEDIS = Integer.parseInt(no_medis.getText().toString());
                                KAMAR=Integer.parseInt(p_room.getText().toString());
                                Toast.makeText(mContext, "BERHASIL REGISTRASI", Toast.LENGTH_SHORT).show();
                                if (usia > 66) {
                                    startActivity(new Intent(mContext, Lansia_assesment.class).putExtra("no_rekam_medis",no_medis.getText().toString()));
                                }else if (usia>18) {
                                    startActivity(new Intent(mContext, Parent_assesment.class).putExtra("no_rekam_medis",no_medis.getText().toString()).putExtra("kamar",p_room.getText().toString()));
                                }
                                else {
                                    startActivity(new Intent(mContext, pediatri_assesment.class).putExtra("no_rekam_medis",no_medis.getText().toString()));
                                }
                                finish();
                            } else {
//                                    String error_message = jsonRESULTS.getString("error_msg");
                                Toast.makeText(mContext, "gagal", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.i("debug", "onResponse: REGISTRASI GAGAL");
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseApi> call, Throwable t) {
                        loading.dismiss();
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onValidationSucceeded() {
        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
        requestRegister();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors){
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText){
                EditText et = (EditText) view;
                et.setError(message);
            }
        }
    }
}


