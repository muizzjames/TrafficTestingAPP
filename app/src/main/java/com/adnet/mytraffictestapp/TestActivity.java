package com.adnet.mytraffictestapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.adnet.mytraffictestapp.config.Config;
import com.adnet.mytraffictestapp.database.DatabaseProgressHandler;
import com.adnet.mytraffictestapp.database.MyDatabase;
import com.adnet.mytraffictestapp.datatyple.Level_Info;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.natasa.progressviews.LineProgressBar;
import com.natasa.progressviews.utils.ProgressLineOrientation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TestActivity extends AppCompatActivity implements ResultDialog.YesNoDialogListener{

    private TextView m_tvTitle;

    private ImageView m_imgPresentation;

    private Button m_btnOkay;
//    private Button m_btnCancel;

    private ToggleButton[] m_btnAnswer = new ToggleButton[4];
    private LineProgressBar lineProgressbar1;

    protected int progress = 0;

    static private MediaPlayer player;

    private int mMainNo = 1;
    private int mSubNo = 1;


    private MyDatabase db;
    ArrayList<Level_Info> mainData;

    ArrayList<Level_Info> progressData;

    private AdView mAdView;

    Timer timer;
    MyTimerTask myTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        mAdView = (AdView) findViewById(R.id.adView);
//        final TelephonyManager tm =(TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceid = tm.getDeviceId();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("YOUR_PHONE_HASH")
                .build();
        mAdView.loadAd(adRequest);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            mMainNo= 1;
        } else {
            mMainNo= extras.getInt(Config.MAIN_NO);
        }

        initUI();
        loadDatabase();

        startSubLevel(mSubNo);
    }
    private void loadDatabase(){
        db = new MyDatabase(this);
        mainData = (ArrayList)db.getLevelInfo(""+mMainNo);

        final DatabaseProgressHandler progressDB = new DatabaseProgressHandler(this);
        progressDB.onDeleteAllData();
    }
    private int getProblemCounter(int SubNo){
        int count = 0;
        for (int i = 0; i < mainData.size(); i++){
            if (mainData.get(i).get_SubLevel() == SubNo){
                count++;
            }
        }
        return count;
    }
    private void saveCurrentState(int Count){
        final DatabaseProgressHandler db = new DatabaseProgressHandler(this);
        for (int i  = 0 ; i < Count; i ++){

            if (m_btnAnswer[i].isChecked())
                db.addLevelState(new Level_Info(mMainNo, mSubNo, i + 1, 1, ""));
            else
                db.addLevelState(new Level_Info(mMainNo, mSubNo, i + 1, 0, ""));
        }
    }
    private void showProblemButton(int Count){
        for (int k = 0; k < 4; k++){
            m_btnAnswer[k].setVisibility(View.GONE);
        }
        for (int i = 0 ; i < Count; i ++){
            m_btnAnswer[i].setVisibility(View.VISIBLE);
            m_btnAnswer[i].setChecked(false);
        }
    }
    public void initUI(){

        m_tvTitle = (TextView)findViewById(R.id.tvTitle);

        m_imgPresentation = (ImageView) findViewById(R.id.imgPresentation);

        m_btnAnswer[0] = (ToggleButton)findViewById(R.id.btnOne);
        m_btnAnswer[1] = (ToggleButton) findViewById(R.id.btnTwo);
        m_btnAnswer[2] = (ToggleButton) findViewById(R.id.btnThree);
        m_btnAnswer[3] = (ToggleButton) findViewById(R.id.btnFour);


        m_btnOkay = (Button) findViewById(R.id.btnAnswer);
        m_btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer != null){
                    timer.cancel();
                    myTimerTask.cancel();
                }
                saveCurrentState(getProblemCounter(mSubNo));
                if (mSubNo == 40){
                    if (player != null){
                        player.stop();
                        player.release();
                        player = null;
                    }
                    showResultDialog();
                    return;
                }
                mSubNo++;
                startSubLevel(mSubNo);
            }
        });
//        m_btnCancel = (Button) findViewById(R.id.btnCancel);
//        m_btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clearButton();
//            }
//        });

        lineProgressbar1 = (LineProgressBar) findViewById(R.id.progressbar);
        lineProgressbar1.setLineOrientation(ProgressLineOrientation.HORIZONTAL);
        lineProgressbar1.setLinearGradientProgress(true);
        lineProgressbar1.setRoundEdgeProgress(true);
    }
    public void clearButton(){
        for (int k = 0; k < 4; k++){
            m_btnAnswer[k].setChecked(false);
        }
    }
    public void startSubLevel(int SubNumber){

        m_tvTitle.setText("" + SubNumber + "/" + mMainNo + " for test");

        Log.e("ERROR", "startSubLevel");
        showProblemButton(getProblemCounter(SubNumber));

        initData(mMainNo, SubNumber);
        progress = 0;
        if(timer != null){
            timer.cancel();
            myTimerTask.cancel();
        }
        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 150, 150);
    }
    public void initData(int mainNo, int subNo){
        String strImagePath = "image/s"+mainNo+"/quiz_"+mainNo+"_"+subNo+".jpg";
        loadDataFromAsset(strImagePath);

        String strSongPath = "song/s"+mainNo+ "/quiz_s_" + mainNo+"_"+subNo+".ram";
        playSound(strSongPath);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //startSubLevel(mSubNo);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(timer != null){
            timer.cancel();
        }

        if (player != null){
            player.stop();
            player.release();
            player = null;
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

    private void playSound(String fileName) {
        try {
            AssetFileDescriptor afd = getAssets().openFd(fileName);
            if (player != null){
                player.stop();
                player.release();
                player = null;
            }
            player = new MediaPlayer();
            Log.d("player", "==" + mSubNo);
            player.reset();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
            afd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if(timer != null){
            timer.cancel();
        }
        if (player != null){
            player.stop();
            player.release();
            player = null;
        }
        Intent intent = new Intent(TestActivity.this, ResultActivity.class);
        intent.putExtra(Config.MAIN_NO, mMainNo);
        startActivity(intent);

        finish();
    }

    @Override
    public void onFinishYesNoDialog(int state) {
        if(state == 1) {
            Intent intent = new Intent(TestActivity.this, ResultActivity.class);
            intent.putExtra(Config.MAIN_NO, mMainNo);
            startActivity(intent);

            finish();
        }
        if (state == 2){
            loadDatabase();
            mMainNo++;
            mSubNo = 1;
            startSubLevel(mSubNo);
        }
        if (state == 3) {
//            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
//            FileOutputStream out = new FileOutputStream(file);
////            Intent iEmail = new Intent(Intent.ACTION_SEND);
//            iEmail.setType("image/*");

//            String strImagePath = "share/angry.png";
//            File file = new File(strImagePath);
//            if (file.exists()){
//                Log.d("file", "file exists");
//            }else{
//                Log.d("file", "file don't exists");
//            }
//            Uri path = Uri.fromFile(file);

//            Drawable mDrawable =
//            Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
//            String path = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, "Image I want to share", null);
//            Uri uri = Uri.parse(path);
//            Intent shareIntent = new Intent();
//            shareIntent.setAction(Intent.ACTION_SEND);
//            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//            shareIntent.setType("image/*");
//            startActivity(Intent.createChooser(shareIntent, "Share Image"));
//
//            iEmail.putExtra(Intent.EXTRA_STREAM, path);
//
//            iEmail.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//            try {
//                startActivity(Intent.createChooser(iEmail,"Send file..."));
//            } catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(TestActivity.this,"There are no email clients installed.",Toast.LENGTH_SHORT).show();
//            }
//        }
        }
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Log.e("ERROR", "This is running----200");
            progress += 1;
            if (progress < 100)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lineProgressbar1.setProgress(progress);
                    }
                });
            if (progress == 100){

                if(timer != null){
                    timer.cancel();

                }
                if (mSubNo == 40){
                    lastCallFunction();
                    return;
                }
                saveCurrentState(getProblemCounter(mSubNo));
                mSubNo++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startSubLevel(mSubNo);
                    }
                });

            }
        }

    }
    public void lastCallFunction(){

        if(timer != null){
            timer.cancel();
            myTimerTask.cancel();
        }
        saveCurrentState(getProblemCounter(mSubNo));
        showResultDialog();
    }

    public void showResultDialog() {
        final DatabaseProgressHandler progressDB = new DatabaseProgressHandler(this);
        progressData = (ArrayList<Level_Info>) progressDB.getAllProgressInfo();
        if (mMainNo == 20){

            FragmentManager fragmentManager = getFragmentManager();
            ResultDialogComplete yesnoDialogComplete = new ResultDialogComplete();
            yesnoDialogComplete.setCancelable(false);
            yesnoDialogComplete.setDialogTitle(""+getCorrectCount()+"/40");
            yesnoDialogComplete.show(fragmentManager, "Yes/No Dialog");
        }else{
            FragmentManager fragmentManager = getFragmentManager();
            ResultDialog yesnoDialog = new ResultDialog();
            yesnoDialog.setCancelable(false);
            yesnoDialog.setDialogTitle(""+getCorrectCount()+"/40");
            yesnoDialog.show(fragmentManager, "Yes/No Dialog");
        }
    }
    private int getCorrectCount(){
        int nCount = 0;
        for (int i = 1; i < 41; i ++){
            if (compareAnswer(i) == true){
                nCount++;
            }
        }
        return nCount;
    }
    private boolean compareAnswer(int SubNo){
        int nCounter = getProblemCounter(mainData, SubNo);
        int nCompareResult = 0;
        for (int i = 0; i < nCounter; i++ ){
            if (getAnswer(progressData, SubNo, i+1) == 5){// not progress state
                return false;
            }
            if (getAnswer(mainData, SubNo, i + 1) == getAnswer(progressData, SubNo, i+1)){
                nCompareResult++;
            }
        }
        if (nCompareResult == nCounter)
            return true;
        else
            return false;
    }
    private int getProblemCounter( ArrayList<Level_Info> data,int SubNo){
        int count = 0;
        for (int i = 0; i < data.size(); i++){
            if (data.get(i).get_SubLevel() == SubNo){
                count++;
            }
        }
        return count;
    }

    private int getAnswer(ArrayList<Level_Info> data, int SubNo, int ProblemNo){
        for (int i = 0; i < data.size(); i++){
            if (data.get(i).get_SubLevel() == SubNo && data.get(i).get_ProblemNo() == ProblemNo){
                return data.get(i).get_Answer();
            }
        }
        return 5;
    }
}
