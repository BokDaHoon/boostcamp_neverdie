package com.example.daisy.simplememo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daisy on 2017-01-14.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "memo.db";
    public static final int DATABASE_VERSION = 2;

    public DBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + MemoDBContract.MemoDBEntry.TABLE_NAME + " (" +
                MemoDBContract.MemoDBEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MemoDBContract.MemoDBEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
                MemoDBContract.MemoDBEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MemoDBContract.MemoDBEntry.TABLE_NAME);
        onCreate(db);
    }

    /*Query Methods*/
    /**
     * Get ALL Memo list
     * */
    public Cursor getAllMemos() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(MemoDBContract.MemoDBEntry.TABLE_NAME,
                new String[] {MemoDBContract.MemoDBEntry.COLUMN_ID,MemoDBContract.MemoDBEntry.COLUMN_CONTENT, MemoDBContract.MemoDBEntry.COLUMN_TIMESTAMP },
                null, null, null, null,
                MemoDBContract.MemoDBEntry.COLUMN_ID + " DESC");
    }

    /**
     * Return a Memo that has id as its _id
     *
     * @param id
     * @return Memo
     */
    public Memo getMemoDetail(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(MemoDBContract.MemoDBEntry.TABLE_NAME,
                new String[] {MemoDBContract.MemoDBEntry.COLUMN_ID,MemoDBContract.MemoDBEntry.COLUMN_CONTENT, MemoDBContract.MemoDBEntry.COLUMN_TIMESTAMP },
                MemoDBContract.MemoDBEntry.COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Memo memo = new Memo(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return memo;
    }

    /**
     * Add new memo
     *
     * @param memo
     * @return long row ID newly inserted
     */
    public long addNewMemo(Memo memo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MemoDBContract.MemoDBEntry.COLUMN_CONTENT, memo.getContent());
        cv.put(MemoDBContract.MemoDBEntry.COLUMN_TIMESTAMP, memo.getTimestamp());
        return db.insert(MemoDBContract.MemoDBEntry.TABLE_NAME, null, cv);
    }

    /**
     * Modify Memo
     *
     * @param memo
     * @return boolean return true if it success to modify
     */
    public boolean modifyMemo(Memo memo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MemoDBContract.MemoDBEntry.COLUMN_CONTENT, memo.getContent());
        return db.update(MemoDBContract.MemoDBEntry.TABLE_NAME, values, MemoDBContract.MemoDBEntry.COLUMN_ID + " = ?",
                new String[] { String.valueOf(memo.get_id()) }) >0 ;
    }

    /**
     * Delete Memo
     * @param memo
     */
    public void deleteMemo(Memo memo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MemoDBContract.MemoDBEntry.TABLE_NAME, MemoDBContract.MemoDBEntry.COLUMN_ID + " = ?",
                new String[] { String.valueOf(memo.get_id()) });
        db.close();
    }

    /**
     * return the number of Memos
     *
     * @return int the number of rows in the table
     */
    public int getMemosCount() {
        String countQuery = "SELECT  * FROM " + MemoDBContract.MemoDBEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
}
