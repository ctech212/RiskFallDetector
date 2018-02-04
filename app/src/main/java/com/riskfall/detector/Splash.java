package com.riskfall.detector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        iv=(ImageView) findViewById(R.id.iv);
        Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.mytransation);
        final Intent i=new Intent(this,loginActivity.class);
//        final Intent i=new Intent(this,loginActivity.class);
        iv.startAnimation(myAnim);
        Thread timer =new Thread(){
            public void run(){
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
            timer.start();
    }
}
