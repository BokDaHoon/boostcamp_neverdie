package com.example.daisy.simplememo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daisy.simplememo.data.DBHelper;
import com.example.daisy.simplememo.data.Memo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMemoActivity extends AppCompatActivity {
    //private SQLiteDatabase mDb;
    private DBHelper mDbHelper;
    private EditText mMemoContentEditText;
    private Button mAddMemoButton;
    private Memo mMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        mDbHelper = new DBHelper(AddMemoActivity.this);
        mMemoContentEditText = (EditText) findViewById(R.id.et_memo_content);
        mAddMemoButton = (Button) findViewById(R.id.btn_add_memo);
        mAddMemoButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                addToWaitlist(view);
                // returns back to MainActivity
                finish();
            }
        });
    }
    private void addToWaitlist(View view) {
        if (mMemoContentEditText.getText().length() == 0 ) {
            return;
        }

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy. MM. dd");
        String currentTime = simpleDateFormat.format(date).toString();

        mMemo = new Memo(mMemoContentEditText.getText().toString(), currentTime);
        mDbHelper.addNewMemo(mMemo);

        //clear UI text fields
        //mMemoContentEditText.clearFocus();
        //mMemoContentEditText.getText().clear();
    }
}
