package com.adnet.mytraffictestapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.adnet.mytraffictestapp.adapter.MenuListViewAdapter;
import com.adnet.mytraffictestapp.config.Config;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class FirstActivity extends AppCompatActivity {


    private LinearLayout mllTestDrive;
    private LinearLayout mllTraffiicSign;
    private LinearLayout mllHighwayCode;

    private Button mButton1;

    private  Button mButton2;

    private Button mButton3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        mButton1 = (Button) findViewById(R.id.buttonOne);

        mButton2 = (Button) findViewById(R.id.buttonTwo);
        mButton3 = (Button) findViewById(R.id.buttonThree);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, FiveActivity.class);
                startActivity(intent);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, SevenActivity.class);
                startActivity(intent);
            }
        });
    }
}
