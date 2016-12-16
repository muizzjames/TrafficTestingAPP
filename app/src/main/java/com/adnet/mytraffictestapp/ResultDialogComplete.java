package com.adnet.mytraffictestapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ResultDialogComplete extends DialogFragment {
	public Button btnYes;
	static String DialogBoxTitle;

	private TextView m_tvResultShow;
	private ImageView imageView;

	private AdView mAdView;

	@Override
	public Dialog onCreateDialog(final Bundle savedInstanceState) {
		RelativeLayout root = new RelativeLayout(getActivity());
		root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(root);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		return dialog;
	}

	//---empty constructor required
	public ResultDialogComplete(){

	}

	//---set the title of the dialog window---
	public void setDialogTitle(String title) {
		DialogBoxTitle= title;
	}
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState ) {
		
		View view= inflater.inflate(R.layout.complete_dialogfragment, container);


		m_tvResultShow = (TextView)view.findViewById(R.id.tvResultText);
		m_tvResultShow.setText(DialogBoxTitle);

		mAdView = (AdView) view.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("YOUR_PHONE_HASH")
				.build();
		mAdView.loadAd(adRequest);

		imageView = (ImageView) view.findViewById(R.id.resultImage);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.angry);
				SharePhoto photo = new SharePhoto.Builder()
						.setBitmap(image)
						.build();
				SharePhotoContent content = new SharePhotoContent.Builder()
						.addPhoto(photo)
						.build();
				ShareDialog shareDialog = new ShareDialog(getActivity());
				shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
			}
		});

		String str = DialogBoxTitle.substring(0, DialogBoxTitle.indexOf("/"));
		if (Integer.valueOf(str) > 30){
			imageView.setImageResource(R.drawable.smile);
		}else{
			imageView.setImageResource(R.drawable.angry);
		}
		//---get the Button views---
		btnYes = (Button) view.findViewById(R.id.btnYes);

		
		// Button listener 
		btnYes.setOnClickListener(btnListener);

		
		//---set the title for the dialog
//		getDialog().setTitle(DialogBoxTitle);

		
		return view;
	}
	
	//---create an anonymous class to act as a button click listener
	private OnClickListener btnListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			int state = 0;
			//---gets the calling activity---
			ResultDialog.YesNoDialogListener activity = (ResultDialog.YesNoDialogListener) getActivity();
			if (((Button) v).getText().toString().equals("Correction")){
				state = 1;
			}else if(((Button) v).getText().toString().equals("Next Level")) {
				state = 2;
			}

			activity.onFinishYesNoDialog(state);
			//---dismiss the alert---
			dismiss();
		}
	};
}
