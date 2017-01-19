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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailMemoActivity extends AppCompatActivity {
    private DBHelper mDbHelper;
    private Memo mMemo;

    @BindView(R.id.et_memo_detail)
    EditText mMemoDetailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_memo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mDbHelper = new DBHelper(DetailMemoActivity.this);
        int memoId = this.getIntent().getExtras().getInt("memoId"); // from MemoAdapter
        mMemo = mDbHelper.getMemoDetail(memoId);
        mMemoDetailEditText.setText(mMemo.getContent());
    }

    @OnClick(R.id.btn_modify_memo)
    public void modifyMemo() {
        String newMemo = mMemoDetailEditText.getText().toString();
        mMemo.setContent(newMemo);
        mDbHelper.modifyMemo(mMemo);
        finish();
    }

    @OnClick(R.id.btn_delete_memo)
    public void deleteMemo() {
        mDbHelper.deleteMemo(mMemo);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}