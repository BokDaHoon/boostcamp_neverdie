package com.example.android.mymemo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMemoActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextContent;

    private Button addButton;
    private Button cancelButton;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MemoSQLiteOpenHelper helper = new MemoSQLiteOpenHelper(this, null, null, 0);
        db = helper.getWritableDatabase();

        editTextTitle = (EditText) findViewById(R.id.editText);
        editTextContent = (EditText) findViewById(R.id.editText2);
        addButton = (Button) findViewById(R.id.addButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTitle.getText().length() == 0 ||
                        editTextContent.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "제목 혹은 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContentValues value = new ContentValues();
                value.put("title", editTextTitle.getText().toString());
                value.put("content", editTextContent.getText().toString());
                long id = db.insert(MemoSQLiteOpenHelper.tableName, null, value);
                Toast.makeText(getApplicationContext(), id + "번 메모가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                initEditText();
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
        editTextTitle.setText("");
        editTextContent.setText("");
    }
}
