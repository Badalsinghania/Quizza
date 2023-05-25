package com.english.quizza;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };thread.start();
    }
}
/*
xml animatin
com.airbnb.lottie.LottieAnimationView
        android:id="@+id/animation_view"
        android:layout_width="402dp"
        android:layout_height="719dp"
        android:layout_marginStart="6dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="3dp"
        android:layout_marginBottom="5dp"
        android:fitsSystemWindows="true"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:lottie_autoPlay="true"
        app:lottie_fileName="hello_world.json"
        app:lottie_loop="true"
        app:lottie_rawRes="@raw/splash" />
 */