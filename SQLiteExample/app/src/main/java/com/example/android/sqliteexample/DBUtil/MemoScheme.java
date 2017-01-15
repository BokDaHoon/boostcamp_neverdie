package com.example.android.sqliteexample.DBUtil;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by DaHoon on 2017-01-13.
 */

public class MemoScheme {

    public static final String AUTHORITY = "com.example.android.sqliteexample";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASK = "task";
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASK).build();

    public static final class MemoTable {

        public static final String TABLE_NAME = "MEMO";

        public static final class MemoEntry implements BaseColumns {

            public static final String _ID = "_id";
            public static final String COLUMN_TITLE = "title";
            public static final String COLUMN_CONTENT = "content";

        }
    }

}
