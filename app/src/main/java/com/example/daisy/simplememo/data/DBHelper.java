package com.example.daisy.simplememo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.daisy.simplememo.data.WaitlistContract.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daisy on 2017-01-14.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "memo.db";
    public static final int DATABASE_VERSION = 1;
    public DBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table to hold waitlist data
        final String SQL_CREATE_TABLE = "CREATE TABLE " + WaitlistEntry.TABLE_NAME + " (" +
                WaitlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WaitlistEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                WaitlistEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WaitlistEntry.TABLE_NAME);
        onCreate(db);
    }

//    public List<Memo> getAllMemos() {
//        List<Memo> memoList = new ArrayList<Memo>();
//        String selectQuery = "SELECT  * FROM " + WaitlistEntry.TABLE_NAME;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Memo contact = new Memo();
//                contact.set_id(Integer.parseInt(cursor.getString(0)));
//                contact.setContent(cursor.getString(1));
//                contact.setTimestamp(cursor.getString(2));
//                memoList.add(contact);
//            } while (cursor.moveToNext());
//        }
//        return memoList;
//    }
    public Cursor getAllMemos() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(WaitlistEntry.TABLE_NAME,
                new String[] {WaitlistEntry.COLUMN_ID,WaitlistEntry.COLUMN_CONTENT, WaitlistEntry.COLUMN_TIMESTAMP },
                null, null, null, null,
                WaitlistEntry.COLUMN_TIMESTAMP + " DESC ");

    }

    public Memo getMemoDetail(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(WaitlistContract.WaitlistEntry.TABLE_NAME,
                new String[] {WaitlistEntry.COLUMN_ID,WaitlistEntry.COLUMN_CONTENT, WaitlistEntry.COLUMN_TIMESTAMP },
                WaitlistEntry.COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Memo memo = new Memo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return memo;
    }

    public long addNewMemo(Memo memo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_CONTENT, memo.getContent());
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP, memo.getTimestamp());
        return db.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
    }

    public boolean modifyMemo(Memo memo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WaitlistContract.WaitlistEntry.COLUMN_CONTENT, memo.getContent());

        // updating row
        return db.update(WaitlistEntry.TABLE_NAME, values, WaitlistEntry.COLUMN_ID + " = ?",
                new String[] { String.valueOf(memo.get_id()) }) >0 ;
    }

    public void deleteMemo(Memo memo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WaitlistEntry.TABLE_NAME, WaitlistEntry.COLUMN_ID + " = ?",
                new String[] { String.valueOf(memo.get_id()) });
        db.close();
    }

    public int getMemosCount() {
        String countQuery = "SELECT  * FROM " + WaitlistEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
}
