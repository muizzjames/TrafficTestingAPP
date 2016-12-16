package com.adnet.mytraffictestapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.adnet.mytraffictestapp.adapter.FiveListViewAdapter;
import com.adnet.mytraffictestapp.adapter.MenuListViewAdapter;
import com.adnet.mytraffictestapp.config.Config;
import com.adnet.mytraffictestapp.datatyple.Markup_Info;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class FiveActivity extends AppCompatActivity {

    private ListView m_lvMenu;
    ArrayList<Markup_Info> mainData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);

        loadData();

        m_lvMenu = (ListView) findViewById(R.id.lvMenu);
        m_lvMenu.setAdapter(new FiveListViewAdapter(this, mainData));

    }
    private void loadData() {
        mainData = new ArrayList<>();
        String[] itemtext = getResources().getStringArray(R.array.five_array);
        for (int i = 0; i < itemtext.length; i++) {
            Markup_Info data = new Markup_Info();
            data.ImagePath = "trafficsigns/a" + (i + 1) + ".jpg";
            data.ImageText = itemtext[i];
            mainData.add(data);
        }
    }
}
