package com.adnet.mytraffictestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.adnet.mytraffictestapp.adapter.ResultGridViewAdapter;
import com.adnet.mytraffictestapp.config.Config;
import com.adnet.mytraffictestapp.database.DatabaseProgressHandler;
import com.adnet.mytraffictestapp.database.MyDatabase;
import com.adnet.mytraffictestapp.datatyple.Level_Info;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;


public class ResultActivity extends AppCompatActivity {


    private TextView m_tvTitle= null;

    private GridView m_gvResult = null;
    private ArrayList<Boolean> resultData;

    private Button m_btnOkay;
    private Button m_btnCancel;

    private MyDatabase mainDB;
    ArrayList<Level_Info> mainData;
    ArrayList<Level_Info> progressData;


    private int mMainNo = 1;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

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

        loadData();

        initData();

        m_tvTitle = (TextView) findViewById(R.id.tvResult);
        m_tvTitle.setText(""+correctCount()+"/40 Result");

        m_gvResult = (GridView) findViewById(R.id.gvResult);
        m_gvResult.setAdapter(new ResultGridViewAdapter(this,R.layout.result_item, resultData, mMainNo));


        m_btnCancel = (Button) findViewById(R.id.btnCancel);
        m_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.putExtra(Config.MAIN_NO, mMainNo);
                startActivity(intent);
                finish();
            }
        });
        m_btnOkay = (Button) findViewById(R.id.btnAnswer);
        m_btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, TestActivity.class);
                intent.putExtra(Config.MAIN_NO, mMainNo);
                startActivity(intent);
                finish();

            }
        });

    }
    private int correctCount(){
        int count = 0;
        for (int i = 0 ; i < resultData.size(); i++){
            if (resultData.get(i).booleanValue() == true){
                count++;
            }
        }
        return count;
    }
    private void loadData(){
        mainDB = new MyDatabase(this);
        mainData = (ArrayList) mainDB.getLevelInfo("" + mMainNo);

        final DatabaseProgressHandler progressDB = new DatabaseProgressHandler(this);

        progressData = (ArrayList<Level_Info>) progressDB.getAllProgressInfo();
    }
    private void initData(){
        resultData = new ArrayList<Boolean>();
        for (int i = 1; i < 41; i ++){
            if (compareAnswer(i) == true){
                resultData.add(Boolean.TRUE);
            }else
                resultData.add(Boolean.FALSE);
        }
    }
    private boolean compareAnswer(int SubNo){
        int nCounter = getProblemCounter(mainData, SubNo);
        int nCompareResult = 0;
        for (int i = 0; i < nCounter; i++ ){
            if (getAnswer(progressData, SubNo, i+1) == 5){// not progress state
                return false;
            }
            if (getAnswer(mainData, SubNo, i + 1) == getAnswer(progressData, SubNo, i+1)){
                nCompareResult++;
            }
        }
        if (nCompareResult == nCounter)
            return true;
        else
            return false;
    }
    private int getAnswer(ArrayList<Level_Info> data, int SubNo, int ProblemNo){
        for (int i = 0; i < data.size(); i++){
            if (data.get(i).get_SubLevel() == SubNo && data.get(i).get_ProblemNo() == ProblemNo){
                return data.get(i).get_Answer();
            }
        }
        return 5;
    }
    private int getProblemCounter( ArrayList<Level_Info> data,int SubNo){
        int count = 0;
        for (int i = 0; i < data.size(); i++){
            if (data.get(i).get_SubLevel() == SubNo){
                count++;
            }
        }
        return count;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        intent.putExtra(Config.MAIN_NO, mMainNo);
        startActivity(intent);
        finish();
    }
}