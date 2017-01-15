package com.example.gon.memo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 김장민 on 2017-01-15.
 */

public class MemoHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "memo.db";
    private static final int DATABASE_VERSION = 2;

    public MemoHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + MemoDB.MemoTable.TABLE_NAME + "( "
                + MemoDB.MemoTable._ID + " integer primary key autoincrement, "
                + MemoDB.MemoTable.MEMO_TITLE + " text not null, "
                +MemoDB.MemoTable.MEMO_BODY +" text not null"+");";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MemoDB.MemoTable.TABLE_NAME);
        onCreate(db);
    }
}
