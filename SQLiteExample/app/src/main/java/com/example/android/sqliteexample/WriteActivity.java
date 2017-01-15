package com.example.android.sqliteexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.android.sqliteexample.DBUtil.MemoDbHelper;
import com.example.android.sqliteexample.DBUtil.MemoScheme;
import com.example.android.sqliteexample.DBUtil.MemoScheme.MemoTable;
import android.support.v7.app.ActionBar;

public class WriteActivity extends AppCompatActivity {

    private EditText mEditTxtTitle;
    private EditText mEditTxtContent;
    private SQLiteDatabase mDb;
    private static String mFlag;
    private static String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        mEditTxtTitle = (EditText) findViewById(R.id.edit_title);
        mEditTxtContent = (EditText) findViewById(R.id.edit_content);

        MemoDbHelper empDb = new MemoDbHelper(this);
        mDb = empDb.getWritableDatabase();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent.hasExtra("modify")){
            mFlag = "modify";
            mEditTxtTitle.setText(intent.getStringExtra("title"));
            mEditTxtContent.setText(intent.getStringExtra("content"));
            mId = intent.getStringExtra("_id");
        }else{
            mFlag = "insert";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_memo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.add_memo:
                saveMemo();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void saveMemo(){
        ContentValues cv = new ContentValues();
        String title = mEditTxtTitle.getText().toString();
        String content = mEditTxtContent.getText().toString();

        cv.put(MemoTable.MemoEntry.COLUMN_TITLE, title);
        cv.put(MemoTable.MemoEntry.COLUMN_CONTENT, content);

        if(mFlag.equals("modify")){
            mDb.update(MemoTable.TABLE_NAME, cv, MemoTable.MemoEntry._ID + " = ?", new String[]{mId});
        }else{
            mDb.insert(MemoTable.TABLE_NAME, null, cv);
        }

    }


}
