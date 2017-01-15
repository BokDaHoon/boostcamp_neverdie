package com.example.android.memo.data;

import android.provider.BaseColumns;

/**
 * Created by user on 2017-01-15.
 */

public class MemoContract {

    public static final class MemoEntry implements BaseColumns {
        public static final String TABLE_NAME = "memo";
        public static final String COLUMN_CONTENTS = "contents";
    }
}
