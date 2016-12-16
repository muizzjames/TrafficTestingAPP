package com.adnet.mytraffictestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.GridView;

import com.adnet.mytraffictestapp.adapter.MarkupGridViewAdapter;
import com.adnet.mytraffictestapp.config.Config;
import com.adnet.mytraffictestapp.datatyple.Markup_Info;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;


public class MarkupActivity extends AppCompatActivity {

    private GridView m_gvResult = null;

    ArrayList<Markup_Info> mainData;
    private int mMainNo = 21;


    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.markup_activity);



        mAdView = (AdView) findViewById(R.id.adView);
//        final TelephonyManager tm =(TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceid = tm.getDeviceId();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("YOUR_PHONE_HASH")
                .build();
        mAdView.loadAd(adRequest);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            mMainNo= 21;
        } else {
            mMainNo= extras.getInt(Config.MAIN_NO);
        }

        loadData();

        m_gvResult = (GridView) findViewById(R.id.gvResult);
        m_gvResult.setAdapter(new MarkupGridViewAdapter(this,R.layout.markup_item, mainData));

    }
    private void loadData(){
        mainData = new ArrayList<>();
        if (mMainNo == 21){
            String[] strTooltip = getResources().getStringArray(R.array.a_array);
            for (int i  = 0; i < 18; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "Icon/A/"+(i+1)+"a.png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }
        if (mMainNo == 22){
            String[] strTooltip = getResources().getStringArray(R.array.b_array);
            for (int i  = 0; i < 19; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "Icon/B/"+ (i+1) +"a.png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }
        if (mMainNo == 23){
            String[] strTooltip = getResources().getStringArray(R.array.c_array);
            for (int i  = 0; i < 9; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "Icon/C/"+ (i+1) +"a.png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }
        if (mMainNo == 24){
            String[] strTooltip = getResources().getStringArray(R.array.d_array);
            for (int i  = 0; i < 30; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "Icon/D/"+ (i+1) +"a.png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }
        if (mMainNo == 25){
            String[] strTooltip = getResources().getStringArray(R.array.e_array);
            for (int i  = 0; i < 6; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "Icon/E/"+ (i+1) +"a.png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MarkupActivity.this, MainActivity.class);
        intent.putExtra(Config.MAIN_NO, mMainNo);
        startActivity(intent);
        finish();
    }
}