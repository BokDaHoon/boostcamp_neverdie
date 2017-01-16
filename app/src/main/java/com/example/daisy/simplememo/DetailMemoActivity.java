package com.example.daisy.simplememo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.daisy.simplememo.data.DBHelper;
import com.example.daisy.simplememo.data.Memo;

public class DetailMemoActivity extends AppCompatActivity {
    private DBHelper mDbHelper;
    private EditText mMemoDetailEditText;
    private Button mModifyMemoButton;
    private Button mDeleteMemoButton;
    private Memo mMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_memo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDbHelper= new DBHelper(DetailMemoActivity.this);
        int memoId= this.getIntent().getExtras().getInt("memoId"); // from MemoAdapter
        mMemo =  mDbHelper.getMemoDetail(memoId);

        mMemoDetailEditText = (EditText) findViewById(R.id.et_memo_detail);
        mMemoDetailEditText.setText(mMemo.getContent());

        //Button for Modify
        mModifyMemoButton = (Button) findViewById(R.id.btn_modify_memo);
        mModifyMemoButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                String newMemo =  mMemoDetailEditText.getText().toString();
                mMemo.setContent(newMemo);
                mDbHelper.modifyMemo(mMemo);
                finish();
            }
        });
        //Button for Delete
        mDeleteMemoButton = (Button) findViewById(R.id.btn_delete_memo);
        mDeleteMemoButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                mDbHelper.deleteMemo(mMemo);
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}