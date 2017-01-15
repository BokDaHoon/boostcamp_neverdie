package com.example.gon.memo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gon.memo.data.MemoDB;
import com.example.gon.memo.data.MemoHelper;

public class Memo_add extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    EditText et_memo_title;
    EditText et_memo_body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_add);
        et_memo_title = (EditText)findViewById(R.id.et_memo_add_title);
        et_memo_body = (EditText)findViewById(R.id.et_memo_add_body);

        Intent intent = getIntent();
        int caller = intent.getIntExtra("call", -1);
        if(caller == 1) {
            String title = intent.getStringExtra("title");
            String body = intent.getStringExtra("body");
            et_memo_title.setText(title);
            et_memo_body.setText(body);
        }
        MemoHelper memoHelper = new MemoHelper(this);
        sqLiteDatabase = memoHelper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.memo_add,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.memo_add){
            ContentValues cv = new ContentValues();
            cv.put(MemoDB.MemoTable.MEMO_BODY, et_memo_body.getText().toString());
            cv.put(MemoDB.MemoTable.MEMO_TITLE, et_memo_title.getText().toString());
            sqLiteDatabase.insert(MemoDB.MemoTable.TABLE_NAME, null, cv);
            et_memo_body.clearFocus();
            et_memo_title.getText().clear();
            et_memo_body.getText().clear();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
