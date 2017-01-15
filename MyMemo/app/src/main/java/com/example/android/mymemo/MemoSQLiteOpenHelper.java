package com.example.android.mymemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jr on 2017-01-15.
 */

public class MemoSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String dbName = "mymemo.db";
    private static final int dbVersion = 1;
    public static final String tableName = "mymemo";

    public MemoSQLiteOpenHelper(Context context, String name,
                                SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + tableName + " ("
                + "_id integer primary key autoincrement, "
                + "title text not null, "
                + "content text not null" + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
