package com.adnet.mytraffictestapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;

import com.adnet.mytraffictestapp.adapter.FiveListViewAdapter;
import com.adnet.mytraffictestapp.config.Config;
import com.adnet.mytraffictestapp.datatyple.Markup_Info;

import java.util.ArrayList;

public class WebActivity extends AppCompatActivity {

    private int mMainNo = 1;
    private TextView txtTitle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            mMainNo= 1;
        } else {
            mMainNo= extras.getInt(Config.MAIN_NO);
        }

        Resources res = getResources();
        String[] myTitle = res.getStringArray(R.array.highwaycode_array);

        WebView wv;
        wv = (WebView) findViewById(R.id.webView);
        wv.loadUrl("file:///android_asset/HighwayCode/a"+mMainNo+"/a"+mMainNo+".html");   // now it will not fail here

        txtTitle = (TextView) findViewById(R.id.textViewTitle);
        txtTitle.setText(myTitle[mMainNo]);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WebActivity.this, SevenActivity.class);
        startActivity(intent);
        finish();
    }
}
