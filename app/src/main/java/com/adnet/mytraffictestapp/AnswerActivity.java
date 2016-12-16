package com.adnet.mytraffictestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.adnet.mytraffictestapp.config.Config;
import com.adnet.mytraffictestapp.database.MyDatabase;
import com.adnet.mytraffictestapp.datatyple.Level_Info;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AnswerActivity extends AppCompatActivity {
    private TextView m_tvTitle;
    private ImageView m_imgPresentation;
    private Button m_btnOkay;
    private ToggleButton[] m_btnAnswer = new ToggleButton[4];
    private int mMainNo;
    private int mSubNo;


    private MyDatabase mainDB;
    ArrayList<Level_Info> mainData;

    private AdView mAdView;

    private TextView mTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_activity);

        mAdView = (AdView) findViewById(R.id.adView);
//        final TelephonyManager tm =(TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceid = tm.getDeviceId();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("YOUR_DEVICE_HASH")
                .build();
        mAdView.loadAd(adRequest);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            mMainNo= 1;
            mSubNo = 1;
        } else {
            mMainNo= extras.getInt(Config.MAIN_NO);
            mSubNo = extras.getInt(Config.SUB_NO);
        }

        loadData();

        m_tvTitle = (TextView)findViewById(R.id.tvTitle);
        m_tvTitle.setText(""+mMainNo+" Stage!" );

        mTextDescription = (TextView)findViewById(R.id.textDescription);

        m_imgPresentation = (ImageView) findViewById(R.id.imgPresentation);

        String strImagePath = "image/s"+mMainNo+"/quiz_"+mMainNo+"_"+mSubNo+".jpg";
        loadDataFromAsset(strImagePath);


        m_btnOkay = (Button) findViewById(R.id.btnAnswer);
        m_btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnswerActivity.this, ResultActivity.class);
                intent.putExtra(Config.MAIN_NO, mMainNo);
                startActivity(intent);
                finish();
            }
        });


        m_btnAnswer[0] = (ToggleButton)findViewById(R.id.btnOne);
        m_btnAnswer[1] = (ToggleButton) findViewById(R.id.btnTwo);
        m_btnAnswer[2] = (ToggleButton) findViewById(R.id.btnThree);
        m_btnAnswer[3] = (ToggleButton) findViewById(R.id.btnFour);


        showAnswer();
    }

    private void loadData(){
        mainDB = new MyDatabase(this);
        mainData = (ArrayList) mainDB.getSubLevelInfo("" + mMainNo, "" + mSubNo);
    }
    private void showAnswer(){
        int nCount = mainData.size();
        for ( int i = 0; i < 4; i ++){
            m_btnAnswer[i].setVisibility(View.GONE);

        }
        for ( int i = 0; i < nCount; i ++){
            m_btnAnswer[i].setVisibility(View.VISIBLE);
            m_btnAnswer[i].setEnabled(false);
            if (mainData.get(i).get_Answer() == 1){
                m_btnAnswer[i].setChecked(true);
                mTextDescription.append("" + i + ". " + mainData.get(i).get_Description());
            }
            else{
                m_btnAnswer[i].setChecked(false);
                mTextDescription.append("" + i + ". " + mainData.get(i).get_Description());
            }


        }
    }
    public void loadDataFromAsset(String fileName) {
        try {
            InputStream ims = getAssets().open(fileName);
            Drawable d = Drawable.createFromStream(ims, null);
            m_imgPresentation.setImageDrawable(d);
        }
        catch(IOException ex) {
            return;
        }
    }

}
