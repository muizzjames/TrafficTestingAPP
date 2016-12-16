package com.adnet.mytraffictestapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.adnet.mytraffictestapp.datatyple.Level_Info;

import java.util.ArrayList;
import java.util.List;

public class DatabaseProgressHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ProgressDB";
    //table name
    public static final String TABLE_PROGRESS = "Progress_Table";

    public DatabaseProgressHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createEnquiry = "CREATE TABLE " + TABLE_PROGRESS + "(KEY_ID integer primary key autoincrement,"
                    + "MainLevel INTEGER, SubLevel INTEGER, ProblemNo INTEGER, Answer INTEGER )";
        db.execSQL(createEnquiry);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS);
        onCreate(db);
    }

    public void onDeleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS);
        onCreate(db);
    }
    public void addLevelState(Level_Info enq) {
        Log.d("ADD_DATA", "==" + "Success!");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MainLevel", enq.get_MainLevel());
        values.put("SubLevel", enq.get_SubLevel());
        values.put("ProblemNo", enq.get_ProblemNo());
        values.put("Answer", enq.get_Answer());
        // Inserting Row
        db.insert(TABLE_PROGRESS, null, values);
        db.close(); // Closing database connection
    }

    public List<Level_Info> getAllProgressInfo() {
        List<Level_Info> contactList = new ArrayList<Level_Info>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROGRESS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Level_Info qu = new Level_Info();
                qu.set_MainLevel(cursor.getInt(1));
                qu.set_SubLevel(cursor.getInt(2));
                qu.set_ProblemNo(cursor.getInt(3));
                qu.set_Answer(cursor.getInt(4));
                contactList.add(qu);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }
}