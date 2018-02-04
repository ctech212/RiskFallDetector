package com.riskfall.detector;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.riskfall.detector.apihelper.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Mainmenu extends Activity {
//    ImageButton assesment,history,help;
    @BindView(R.id.nameShow) TextView nameShow;
    @BindView(R.id.btnLogout) Button btnLogout;
    @BindView(R.id.assesmentbtn) ImageButton assesmentbtn;
    @BindView(R.id.helpbtn) ImageButton helpbtn;
    @BindView(R.id.historybtn) ImageButton historybtn;
    @BindView(R.id.reassess) ImageButton reassess;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mainmenu);

        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);

        nameShow.setText(sharedPrefManager.getSPNama());



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(Mainmenu.this, loginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });


        assesmentbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent1= new Intent(Mainmenu.this,Assesment.class);
            startActivity(intent1);
        }
    });

        reassess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(Mainmenu.this,ReassesActivity.class);
                startActivity(intent1);
            }
        });


        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2= new Intent(Mainmenu.this,Result.class);
                startActivity(intent2);
            }
        });

    helpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3= new Intent(Mainmenu.this,Help.class);
                startActivity(intent3);
            }
        });
    }


}
