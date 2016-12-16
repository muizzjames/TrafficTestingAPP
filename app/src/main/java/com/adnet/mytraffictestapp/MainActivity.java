package com.adnet.mytraffictestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.adnet.mytraffictestapp.adapter.MenuListViewAdapter;
import com.adnet.mytraffictestapp.config.Config;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private GridView m_lvMenu;
    public String[] mainData;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    private int mPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = (AdView) findViewById(R.id.adView);
//        final TelephonyManager tm =(TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceid = tm.getDeviceId();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("YOUR_PHONE_HASH")
                .build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                startLevel();
            }
        });

        mainData = getResources().getStringArray(R.array.main_menu);
        m_lvMenu = (GridView)findViewById(R.id.lvMenu);
        m_lvMenu.setAdapter(new MenuListViewAdapter(this, mainData));

    }
    private void requestNewInterstitial() {
//        final TelephonyManager tm1 =(TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceid1 = tm1.getDeviceId();
        AdRequest adRequest1 = new AdRequest.Builder()
                .addTestDevice("YOUR_PHONE_HASH")
                .build();

        mInterstitialAd.loadAd(adRequest1);
    }
    public void showInterstitial(int position) {
        mPosition = position;
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();

        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            if (position < 21){
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                intent.putExtra(Config.MAIN_NO, position);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(MainActivity.this, MarkupActivity.class);
                intent.putExtra(Config.MAIN_NO, position);
                startActivity(intent);
                finish();
            }

        }
    }
    public void startLevel(){
        if (mPosition < 21){
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            intent.putExtra(Config.MAIN_NO, mPosition);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(MainActivity.this, MarkupActivity.class);
            intent.putExtra(Config.MAIN_NO, mPosition);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, FirstActivity.class);
        startActivity(intent);
        finish();
    }
}
