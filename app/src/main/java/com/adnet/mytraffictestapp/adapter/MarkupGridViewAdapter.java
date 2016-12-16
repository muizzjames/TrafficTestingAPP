package com.adnet.mytraffictestapp.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.adnet.mytraffictestapp.R;
import com.adnet.mytraffictestapp.datatyple.Markup_Info;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MarkupGridViewAdapter extends ArrayAdapter<Markup_Info> {
	Activity context;
	int layoutResourceId;
	ArrayList<Markup_Info> data = new ArrayList<Markup_Info>();


	public MarkupGridViewAdapter(Activity context, int layoutResourceId,
								 ArrayList<Markup_Info> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new RecordHolder();

			holder.imageItem = (ImageView) row.findViewById(R.id.item_image);

			row.setTag(holder);

		} else {
			holder = (RecordHolder) row.getTag();
		}

		final Markup_Info item = data.get(position);

		holder.imageItem.setImageDrawable(loadDataFromAsset(item.ImagePath));

		row.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, item.ImageText, Toast.LENGTH_SHORT).show();
			}
		});
		return row;

	}

	static class RecordHolder {
		ImageView imageItem;

	}
	public Drawable loadDataFromAsset(String fileName) {
		try {
			InputStream ims = context.getAssets().open(fileName);
			Drawable d = Drawable.createFromStream(ims, null);
			return d;
		}
		catch(IOException ex) {
			return null;
		}
	}
}