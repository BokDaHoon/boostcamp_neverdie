package com.example.android.memo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.memo.data.MemoContract;
import com.example.android.memo.data.MemoDbHelper;

/**
 * Created by user on 2017-01-15.
 */

public class DetailActivity extends AppCompatActivity {

    private EditText mMemoEdit;
    private Button mDeleteBtn;
    private Cursor mCursor;
    private MemoDbHelper mDbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mMemoEdit = (EditText) findViewById(R.id.et_memo);
        mDeleteBtn = (Button) findViewById(R.id.bt_delete_memo);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // get Memo from db
        long id = getIntent().getLongExtra(MemoContract.MemoEntry._ID,0);
        mDbHelper = new MemoDbHelper(this);
        mCursor = mDbHelper.getMemoCursorById(id);

        // set Memo
        String memo = mCursor.getString(mCursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_CONTENTS));
        mMemoEdit.setText(memo);


        // delete button listener
        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = getIntent().getLongExtra(MemoContract.MemoEntry._ID,0);
                if(mDbHelper.deleteMemo(id)>0){
                    finish();
                }
            }
        });
    }

    public void modifyMemo(){
        long id = getIntent().getLongExtra(MemoContract.MemoEntry._ID,0);
        ContentValues cv = new ContentValues();
        String memoText = mMemoEdit.getText().toString();
        cv.put(MemoContract.MemoEntry.COLUMN_CONTENTS, memoText);
        if(mDbHelper.updateMemo(id, cv)){
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_update:
                modifyMemo();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
