package com.example.android.sqliteexample.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.android.sqliteexample.DBUtil.MemoDbHelper;
import com.example.android.sqliteexample.DBUtil.MemoScheme;
import com.example.android.sqliteexample.DBUtil.MemoScheme.MemoTable;

/**
 * Created by DaHoon on 2017-01-14.
 */

public class MemoContentProvider extends ContentProvider {
    public static final int TASK_ALL = 100;
    public static final int TASK_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MemoDbHelper mMemoDbHelper;

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MemoScheme.AUTHORITY, MemoScheme.PATH_TASK, TASK_ALL);
        uriMatcher.addURI(MemoScheme.AUTHORITY, MemoScheme.PATH_TASK + "/#", TASK_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mMemoDbHelper = new MemoDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mMemoDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);

        Cursor retCursor;

        switch(match){
            case TASK_ALL :
                retCursor = db.query(MemoTable.TABLE_NAME,
                                     projection,
                                     selection,
                                     selectionArgs,
                                     null,
                                     null,
                                     sortOrder);

                break;
            case TASK_WITH_ID :
                String eno = uri.getPathSegments().get(1);
                retCursor = db.query(MemoTable.TABLE_NAME,
                                        projection,
                                        MemoTable.MemoEntry._ID + " = ?",
                                        new String[]{eno},
                                        null,
                                        null,
                                        sortOrder);
                break;
            default :
                throw new UnsupportedOperationException("잘못된 경로입니다 : " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
