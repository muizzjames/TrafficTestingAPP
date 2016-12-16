package com.adnet.mytraffictestapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.adnet.mytraffictestapp.MainActivity;
import com.adnet.mytraffictestapp.R;

public class MenuListViewAdapter extends BaseAdapter {

    String[] marrList;
    private LayoutInflater layoutInflater;
    private MainActivity mAcitivity;

    public MenuListViewAdapter(MainActivity context, String[] listData) {
        this.marrList = listData;
        layoutInflater = LayoutInflater.from(context);
        mAcitivity = context;
    }

    public int getCount() {
        return marrList.length;
    }
    @Override
    public Object getItem(int position) {
        return marrList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.menu_item, null);
            holder = new ViewHolder();
            holder.button = (Button) convertView.findViewById(R.id.buttonMenu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String newsItem = marrList[position];

        holder.button.setText(newsItem);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAcitivity.showInterstitial(position +1);
            }
        });
        return convertView;
    }
    static class ViewHolder {
        Button button;
    }
}
