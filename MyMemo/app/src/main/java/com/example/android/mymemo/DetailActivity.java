package com.example.android.mymemo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    private EditText titleTextView;
    private EditText contentTextView;

    private Button updateButton;
    private Button deleteButton;
    private Button cancelButton;

    private String id;
    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MemoSQLiteOpenHelper helper = new MemoSQLiteOpenHelper(this, null, null, 0);
        db = helper.getWritableDatabase();

        titleTextView = (EditText) findViewById(R.id.titleTextView);
        contentTextView = (EditText) findViewById(R.id.contentTextView);
        updateButton = (Button) findViewById(R.id.updateButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        Intent intentFromMainActivity = getIntent();
        id = intentFromMainActivity.getStringExtra("id");
        title = intentFromMainActivity.getStringExtra("title");
        content = intentFromMainActivity.getStringExtra("content");

        setTitle(id + "번 메모");
        titleTextView.setText(title);
        contentTextView.setText(content);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleTextView.getText().length() == 0 ||
                        contentTextView.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "제목 혹은 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                title = titleTextView.getText().toString();
                content = contentTextView.getText().toString();

                ContentValues value = new ContentValues();
                value.put("title", title);
                value.put("content", content);
                db.update(MemoSQLiteOpenHelper.tableName, value, "_id=?", new String[]{id});
                Toast.makeText(getApplicationContext(), id + "번 메모가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                initEditText();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(MemoSQLiteOpenHelper.tableName, "_id=?", new String[]{id});
                Toast.makeText(getApplicationContext(), id + "번 메모가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initEditText();
            }
        });
    }

    private void initEditText() {
        titleTextView.setText(title);
        contentTextView.setText(content);
    }

}
