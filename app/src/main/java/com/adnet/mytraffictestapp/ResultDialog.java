package com.adnet.mytraffictestapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultDialog extends DialogFragment {
	public Button btnYes,btnNo;

	private Button btnShare;
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
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
		dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		return dialog;
	}
	public interface YesNoDialogListener {		
		void onFinishYesNoDialog(int state);
	}

	//---empty constructor required
	public ResultDialog(){

	}

	//---set the title of the dialog window---
	public void setDialogTitle(String title) {
		DialogBoxTitle= title;
	}
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState ) {
		
		View view= inflater.inflate(R.layout.yes_no_dialogfragment, container);


		m_tvResultShow = (TextView)view.findViewById(R.id.tvResultText);
		m_tvResultShow.setText(DialogBoxTitle);

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
		mAdView = (AdView) view.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("YOUR_PHONE_HASH")
				.build();
		mAdView.loadAd(adRequest);


		String str = DialogBoxTitle.substring(0, DialogBoxTitle.indexOf("/"));
		if (Integer.valueOf(str) > 30){
			imageView.setImageResource(R.drawable.smile);
		}else{
			imageView.setImageResource(R.drawable.angry);
		}
		//---get the Button views---
		btnYes = (Button) view.findViewById(R.id.btnYes);
		btnNo = (Button) view.findViewById(R.id.btnNo);
		btnShare = (Button) view.findViewById(R.id.btnShare);
		// Button listener 
		btnYes.setOnClickListener(btnListener);
		btnNo.setOnClickListener(btnListener);
		btnShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Uri bmpUri = getLocalBitmapUri(imageView);
				String text = "Result:" + DialogBoxTitle;
				// Create share intent as described above
				Intent shareIntent = new Intent();
				shareIntent.setAction(Intent.ACTION_SEND);
				shareIntent.putExtra(Intent.EXTRA_TEXT, text);
				shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
				shareIntent.setType("image/*");
				// Attach share event to the menu item provider
				startActivity(Intent.createChooser(shareIntent, "Share image using"));
			}
		});
		
		return view;
	}
	public Uri getLocalBitmapUri(ImageView imageView) {
		Drawable drawable = imageView.getDrawable();
		Bitmap bmp = null;
		if (drawable instanceof BitmapDrawable){
			bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		} else {
			return null;
		}
		// Store image to default external storage directory
		Uri bmpUri = null;
		try {
			File file =  new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.close();
			bmpUri = Uri.fromFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmpUri;
	}
	
	//---create an anonymous class to act as a button click listener
	private OnClickListener btnListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			int state = 0;
			//---gets the calling activity---
			YesNoDialogListener activity = (YesNoDialogListener) getActivity();
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
