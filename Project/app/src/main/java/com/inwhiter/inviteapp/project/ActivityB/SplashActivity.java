package com.inwhiter.inviteapp.project.ActivityB;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.inwhiter.inviteapp.project.ActivityG.LoginActivity;
import com.inwhiter.inviteapp.project.R;

public class SplashActivity extends AppCompatActivity {

    //Imageviewler ve animasyonlara ait boolean değişkenler
    private ImageView logo,title;
    private boolean isLogoFin = false, isTitleFin = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = (ImageView) findViewById(R.id.splashLogo);
        title = (ImageView) findViewById(R.id.splashTitle);

        Animation logoAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.logo_anim);
        logo.startAnimation(logoAnim);
        Animation logoTitle = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.logo_title);
        title.startAnimation(logoTitle);

        /*
            Animasyonların bitip bitmediğini dinleyen listenerlar
         */
        logoAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                isLogoFin = true;
                animControl();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        logoTitle.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                isTitleFin = true;
                animControl();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /*
        Animasyon kontrolü yapıldı.
        Animasyonlar bittiyse giriş aktivitesine yönlendirildi.
     */
    private void animControl()
    {
        if (isLogoFin && isTitleFin)
        {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}
