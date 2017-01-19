package com.example.daisy.simplememo;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.daisy.simplememo.data.DBHelper;
import com.example.daisy.simplememo.data.Memo;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMemoActivity extends AppCompatActivity {
    @BindView(R.id.et_memo_content)
    EditText mMemoContentEditText;

    private DBHelper mDbHelper;
    private Memo mMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mDbHelper = new DBHelper(AddMemoActivity.this);
    }

    private void addToMemoList() {
        if (mMemoContentEditText.getText().length() == 0) {
            return;
        }

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy. MM. dd");
        String currentTime = simpleDateFormat.format(date).toString();

        mMemo = new Memo(mMemoContentEditText.getText().toString(), currentTime);
        mDbHelper.addNewMemo(mMemo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_memo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save_memo:
                addToMemoList();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
