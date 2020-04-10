package com.mobile.bitgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout c1;
    Timer timer;
    Animation downtoup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c1 = findViewById(R.id.c1);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        c1.setAnimation(downtoup);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, Landing.class));
                Animatoo.animateCard(MainActivity.this);
                finish();
            }
        }, 3000);



    }




}
