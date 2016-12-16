package com.adnet.mytraffictestapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adnet.mytraffictestapp.R;
import com.adnet.mytraffictestapp.WebActivity;
import com.adnet.mytraffictestapp.config.Config;
import com.adnet.mytraffictestapp.datatyple.Markup_Info;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SevenListViewAdapter extends BaseAdapter {

    ArrayList<Markup_Info> mainData;
    private LayoutInflater layoutInflater;
    private Activity mAcitivity;

    public SevenListViewAdapter(Activity context, ArrayList<Markup_Info> listData) {
        this.mainData = listData;
        layoutInflater = LayoutInflater.from(context);
        mAcitivity = context;
    }

    public int getCount() {
        return mainData.size();
    }
    @Override
    public Object getItem(int position) {
        return mainData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.seven_item, null);
            holder = new ViewHolder();
            holder.imageview = (ImageView) convertView.findViewById(R.id.item_image);
            holder.textview = (TextView) convertView.findViewById(R.id.item_textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Markup_Info newsItem = mainData.get(position);

        holder.textview.setText(newsItem.ImageText);
        holder.imageview.setImageDrawable(loadDataFromAsset(newsItem.ImagePath));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mAcitivity, WebActivity.class);
                intent.putExtra(Config.MAIN_NO, position + 1);
                mAcitivity.startActivity(intent);
                mAcitivity.finish();
            }
        });
        return convertView;
    }
    static class ViewHolder {
        ImageView imageview;
        TextView textview;
    }
    public Drawable loadDataFromAsset(String fileName) {
        try {
            InputStream ims = mAcitivity.getAssets().open(fileName);
            Drawable d = Drawable.createFromStream(ims, null);
            return d;
        }
        catch(IOException ex) {
            return null;
        }
    }
}
