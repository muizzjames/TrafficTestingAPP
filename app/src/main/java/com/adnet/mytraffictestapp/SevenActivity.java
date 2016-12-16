package com.adnet.mytraffictestapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.adnet.mytraffictestapp.adapter.FiveListViewAdapter;
import com.adnet.mytraffictestapp.adapter.SevenListViewAdapter;
import com.adnet.mytraffictestapp.datatyple.Markup_Info;

import java.util.ArrayList;

public class SevenActivity extends AppCompatActivity {

    private ListView m_lvMenu;
    ArrayList<Markup_Info> mainData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven);

        loadData();

        m_lvMenu = (ListView) findViewById(R.id.lvMenu);
        m_lvMenu.setAdapter(new SevenListViewAdapter(this, mainData));

    }
    private void loadData() {
        mainData = new ArrayList<>();
        String[] itemtext = getResources().getStringArray(R.array.seven_array);
        for (int i = 0; i < itemtext.length; i++) {
            Markup_Info data = new Markup_Info();
            data.ImagePath = "HighwayCode/a" + (i + 1) + ".png";
            data.ImageText = itemtext[i];
            mainData.add(data);
        }
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SevenActivity.this, FirstActivity.class);
        startActivity(intent);
        finish();
    }
}
