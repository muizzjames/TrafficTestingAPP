package com.adnet.mytraffictestapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adnet.mytraffictestapp.AnswerActivity;
import com.adnet.mytraffictestapp.ResultActivity;
import com.adnet.mytraffictestapp.config.Config;
import com.adnet.mytraffictestapp.R;

import java.util.ArrayList;

public class ResultGridViewAdapter extends ArrayAdapter<Boolean> {
	ResultActivity context;
	int layoutResourceId;
	ArrayList<Boolean> data = new ArrayList<Boolean>();
	int mMainNo;

	public ResultGridViewAdapter(ResultActivity context, int layoutResourceId,
								 ArrayList<Boolean> data, int MainLevel) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.mMainNo = MainLevel;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final int nPos = position;
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();

			holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
			holder.imageItem = (ImageView) row.findViewById(R.id.item_image);

			row.setTag(holder);

		} else {
			holder = (RecordHolder) row.getTag();
		}

		final Boolean item = data.get(position);
		holder.txtTitle.setText("" + (position + 1));
		if (item == Boolean.TRUE){
			holder.imageItem.setImageResource(R.drawable.ic_check);
		}else{
			holder.imageItem.setImageResource(R.drawable.ic_uncheck);
		}


		row.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, AnswerActivity.class);
				intent.putExtra(Config.MAIN_NO, mMainNo);
				intent.putExtra(Config.SUB_NO, nPos + 1);
				context.startActivity(intent);
			}
		});
		return row;

	}

	static class RecordHolder {
		TextView txtTitle;
		ImageView imageItem;

	}
}