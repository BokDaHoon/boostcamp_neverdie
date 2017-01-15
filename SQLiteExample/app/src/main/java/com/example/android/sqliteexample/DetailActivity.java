package com.example.android.sqliteexample;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sqliteexample.DBUtil.MemoDbHelper;
import com.example.android.sqliteexample.DBUtil.MemoScheme;

public class DetailActivity extends AppCompatActivity {

    private static SQLiteDatabase mSQLiteDatabase;
    private static Cursor mCursor;
    private TextView mTitle;
    private TextView mContent;
    private static String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        MemoDbHelper memoDbHelper = new MemoDbHelper(this);
        mSQLiteDatabase =  memoDbHelper.getWritableDatabase();

        Intent intent = getIntent();
        mId = intent.getStringExtra("id");

        mTitle = (TextView) findViewById(R.id.detail_memo_title);
        mContent = (TextView) findViewById(R.id.detail_memo_content);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_memo_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;

            case R.id.modify_memo:
                Intent modifyIntent = new Intent(this, WriteActivity.class);

                modifyIntent.putExtra("modify", "modify");
                modifyIntent.putExtra("title", mTitle.getText().toString());
                modifyIntent.putExtra("content", mContent.getText().toString());
                modifyIntent.putExtra("_id", mId);

                startActivity(modifyIntent);

                break;

            case R.id.delete_memo:

                mSQLiteDatabase.delete(MemoScheme.MemoTable.TABLE_NAME,
                                       MemoScheme.MemoTable.MemoEntry._ID + " = ?",
                                       new String[]{mId});

                Toast.makeText(this, "삭제되었습니다!", Toast.LENGTH_SHORT).show();
                finish();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setItem(){
        mCursor = getContentResolver().query(MemoScheme.CONTENT_URI,
                null,
                MemoScheme.MemoTable.MemoEntry._ID + " = ?",
                new String[]{mId},
                MemoScheme.MemoTable.MemoEntry._ID);


        mCursor.moveToNext();

        mTitle.setText(mCursor.getString(mCursor.getColumnIndex(MemoScheme.MemoTable.MemoEntry.COLUMN_TITLE)));
        mContent.setText(mCursor.getString(mCursor.getColumnIndex(MemoScheme.MemoTable.MemoEntry.COLUMN_CONTENT)));
    }

}
