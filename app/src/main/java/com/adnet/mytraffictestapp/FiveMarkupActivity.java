package com.adnet.mytraffictestapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

import com.adnet.mytraffictestapp.adapter.MarkupGridViewAdapter;
import com.adnet.mytraffictestapp.config.Config;
import com.adnet.mytraffictestapp.datatyple.Markup_Info;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;


public class FiveMarkupActivity extends AppCompatActivity {

    private GridView m_gvResult = null;
    private TextView m_txtTitle = null;

    ArrayList<Markup_Info> mainData;
    private int mMainNo = 1;


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
            mMainNo= 1;
        } else {
            mMainNo= extras.getInt(Config.MAIN_NO);
        }
        Resources res = getResources();
        String[] myTitle = res.getStringArray(R.array.trafficsign_array);
        loadData();

        m_gvResult = (GridView) findViewById(R.id.gvResult);
        m_gvResult.setAdapter(new MarkupGridViewAdapter(this,R.layout.markup_item, mainData));

        m_txtTitle = (TextView) findViewById(R.id.textviewFiveMarkupTitle);
        m_txtTitle.setText(myTitle[mMainNo]);

    }
    private void loadData(){
        mainData = new ArrayList<>();
        if (mMainNo == 1){
            String[] strTooltip = getResources().getStringArray(R.array.trafficsigns_a1_array);
            for (int i  = 0; i < strTooltip.length; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "trafficsigns/a1/a ("+(i+1)+").png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }
        if (mMainNo == 2){
            String[] strTooltip = getResources().getStringArray(R.array.trafficsigns_a2_array);
            for (int i  = 0; i < strTooltip.length; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "trafficsigns/a2/a ("+(i+1)+").png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }
        if (mMainNo == 3){
            String[] strTooltip = getResources().getStringArray(R.array.trafficsigns_a3_array);
            for (int i  = 0; i < strTooltip.length; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "trafficsigns/a3/a ("+(i+1)+").png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }
        if (mMainNo == 4){
            String[] strTooltip = getResources().getStringArray(R.array.trafficsigns_a4_array);
            for (int i  = 0; i < strTooltip.length; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "trafficsigns/a4/a ("+(i+1)+").png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }
        if (mMainNo == 5){
            String[] strTooltip = getResources().getStringArray(R.array.trafficsigns_a5_array);
            for (int i  = 0; i < strTooltip.length; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "trafficsigns/a5/a ("+(i+1)+").png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }
        if (mMainNo == 6){
            String[] strTooltip = getResources().getStringArray(R.array.trafficsigns_a6_array);
            for (int i  = 0; i < strTooltip.length; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "trafficsigns/a6/a ("+(i+1)+").png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }
        if (mMainNo == 7){
            String[] strTooltip = getResources().getStringArray(R.array.trafficsigns_a7_array);
            for (int i  = 0; i < strTooltip.length; i++){
                Markup_Info data = new Markup_Info();
                data.ImagePath =  "trafficsigns/s7/a ("+(i+1)+").png";
                data.ImageText = strTooltip[i];
                mainData.add(data);
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FiveMarkupActivity.this, FiveActivity.class);
        intent.putExtra(Config.MAIN_NO, mMainNo);
        startActivity(intent);
        finish();
    }
}