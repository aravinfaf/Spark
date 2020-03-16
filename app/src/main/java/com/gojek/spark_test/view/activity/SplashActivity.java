package com.gojek.spark_test.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gojek.spark_test.R;
import com.gojek.spark_test.databinding.ActivitySplashBinding;
import com.gojek.spark_test.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    SessionManager sessionManager;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    Animation anim;
    ImageView imageView;
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash);

        sessionManager=new SessionManager(SplashActivity.this);
        anim= AnimationUtils.loadAnimation(this,R.anim.fade_in);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        binding.IV.startAnimation(anim);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                if(sessionManager.isLoggedIn()){
                    Intent i=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else {
                    Intent i=new Intent(SplashActivity.this,GmailActivity.class);
                    startActivity(i);
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
