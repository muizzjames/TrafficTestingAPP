package com.adnet.mytraffictestapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.EventLogTags;

import com.adnet.mytraffictestapp.database.SQLiteAssetHelper;
import com.adnet.mytraffictestapp.datatyple.Level_Info;

import java.util.ArrayList;
import java.util.List;


public class MyDatabase extends SQLiteAssetHelper {

//	private static final String DATABASE_NAME = "MyRouteDB.db";//quiz_s_0_0.ram
	private static final String DATABASE_NAME = "quiz_s_0_0.ram";
	private static final int DATABASE_VERSION = 1;
	
	public MyDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		// you can use an alternate constructor to specify a database location 
		// (such as a folder on the sd card)
		// you must ensure that this folder is available and you have permission
		// to write to it
		//super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
		
	}

	public Cursor getAllInfo() {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"MainLevel", "SubLevel","ProblemNo", "Answer", "Description"};
		String sqlTables = "Result_Table";

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, null, null,
				null, null, null);

		c.moveToFirst();
		return c;

	}

	public List<Level_Info> getLevelInfo(String  Main_Level){

		SQLiteDatabase db = this.getReadableDatabase();
		List<Level_Info> contactList = new ArrayList<Level_Info>();
		Cursor cursor = db.query("Result_Table", new String[]{"MainLevel",
						"SubLevel", "ProblemNo", "Answer", "Description"}, "MainLevel" + "=?",
				new String[]{Main_Level}, null, null, null, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Level_Info contact = new Level_Info();
				contact.set_MainLevel(cursor.getInt(0));
				contact.set_SubLevel(cursor.getInt(1));
				contact.set_ProblemNo(cursor.getInt(2));
				contact.set_Answer(cursor.getInt(3));
				contact.set_Description(cursor.getString(4));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		return contactList;
	}
	public List<Level_Info> getSubLevelInfo(String  Main_Level, String Sub_Level){

		SQLiteDatabase db = this.getReadableDatabase();
		List<Level_Info> contactList = new ArrayList<Level_Info>();
		Cursor cursor = db.query("Result_Table", new String[]{"MainLevel",
						"SubLevel", "ProblemNo", "Answer", "Description"}, "MainLevel" + "=?"+ "and SubLevel" + "=?",
				new String[]{Main_Level, Sub_Level}, null, null, null, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Level_Info contact = new Level_Info();
				contact.set_MainLevel(cursor.getInt(0));
				contact.set_SubLevel(cursor.getInt(1));
				contact.set_ProblemNo(cursor.getInt(2));
				contact.set_Answer(cursor.getInt(3));
				contact.set_Description(cursor.getString(4));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		// return contact list
		return contactList;
	}
}
