package com.seals.shubham.onlinequizproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomePage extends AppCompatActivity {
    Handler hnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        hnd = new Handler();
        hnd.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(HomePage.this,LoginActivity.class);
                startActivity(i);
            }
        },1000);
    }
}
