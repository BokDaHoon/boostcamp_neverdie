package com.example.android.memo;

import android.content.ContentValues;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.android.memo.data.MemoContract;
import com.example.android.memo.data.MemoDbHelper;

public class AddMemoActivity extends AppCompatActivity {

    private EditText mMemoEdit;

    private MemoDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mMemoEdit = (EditText) findViewById(R.id.et_memo);
        mDbHelper = new MemoDbHelper(this);

    }

    public void insertMemo(){
        ContentValues cv = new ContentValues();
        String memoText = mMemoEdit.getText().toString();
        cv.put(MemoContract.MemoEntry.COLUMN_CONTENTS, memoText);
        if(mDbHelper.insertMemo(cv) <= 0){
            return;
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_add:
                insertMemo();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
