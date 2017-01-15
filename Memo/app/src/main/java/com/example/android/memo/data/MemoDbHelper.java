package com.example.android.memo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2017-01-15.
 */

public class MemoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "memo.db";

    private static final int DATABASE_VERSION = 2;

    public MemoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MEMO_TABLE = "CREATE TABLE " + MemoContract.MemoEntry.TABLE_NAME + " (" +
                MemoContract.MemoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MemoContract.MemoEntry.COLUMN_CONTENTS + " TEXT NOT NULL " +
                "); ";
        db.execSQL(SQL_CREATE_MEMO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ MemoContract.MemoEntry.TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllMemoList(){
        return getReadableDatabase().query(
                MemoContract.MemoEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getMemoCursorById(long id){
        String selection  = MemoContract.MemoEntry._ID + "=?";
        String[] selectionArg = new String[] {String.valueOf(id)};
        Cursor cursor = getReadableDatabase().query(
                MemoContract.MemoEntry.TABLE_NAME,
                null,
                selection,
                selectionArg,
                null,
                null,
                null
        );

        if(cursor!=null){
            cursor.moveToFirst();
        } else {
            return null;
        }
        return cursor;
    }

    public long insertMemo(ContentValues cv){
        return getWritableDatabase().insert(
                MemoContract.MemoEntry.TABLE_NAME,
                null,
                cv
        );
    }

    public boolean updateMemo(long id, ContentValues cv){
        String selection = MemoContract.MemoEntry._ID + "=?";
        String[] selectionArg = new String[] {String.valueOf(id)};
        return getWritableDatabase().update(
                MemoContract.MemoEntry.TABLE_NAME,
                cv,
                selection,
                selectionArg
        )>0;
    }

    public int deleteMemo(long id){
        String selection = MemoContract.MemoEntry._ID + "=?";
        String[] selectionArg = new String[] {String.valueOf(id)};
        return getWritableDatabase().delete(
                MemoContract.MemoEntry.TABLE_NAME,
                selection,
                selectionArg
        );
    }


}
