package com.example.gon.memo.data;

import android.provider.BaseColumns;

/**
 * Created by 김장민 on 2017-01-15.
 */

public class MemoDB {
    public static final class MemoTable implements BaseColumns{
        public static final String TABLE_NAME = "memo";
        public static final String MEMO_TITLE="memo_title";
        public static final String MEMO_BODY="memo_body";
    }
}
