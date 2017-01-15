package com.example.android.sqliteexample.DBUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.sqliteexample.DBUtil.MemoScheme.MemoTable;

/**
 * Created by DaHoon on 2017-01-13.
 */

public class MemoDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "memo.db";
    public static final int DATABASE_VERSION = 2;

    public MemoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder queryString = new StringBuilder();

        queryString.append("CREATE TABLE ").append(MemoTable.TABLE_NAME).append("(")
                .append(MemoTable.MemoEntry._ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(MemoTable.MemoEntry.COLUMN_TITLE).append(" TEXT, ")
                .append(MemoTable.MemoEntry.COLUMN_CONTENT).append(" TEXT")
                .append(");");

        db.execSQL(queryString.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MemoTable.TABLE_NAME);
        onCreate(db);
    }
}
