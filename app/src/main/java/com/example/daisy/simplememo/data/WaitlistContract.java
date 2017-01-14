package com.example.daisy.simplememo.data;

import android.provider.BaseColumns;

/**
 * Created by Daisy on 2017-01-14.
 */

public class WaitlistContract {
    public static final class WaitlistEntry implements BaseColumns{
        public static final String TABLE_NAME = "memo";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_CONTENT="content";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
