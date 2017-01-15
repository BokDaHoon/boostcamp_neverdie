package com.example.gon.memo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gon.memo.data.MemoDB;
import com.example.gon.memo.data.MemoHelper;

public class Memo_detail extends AppCompatActivity {
    TextView tv_memo_detail_title;
    TextView tv_memo_detail_body;
    Button bt_memo_detail_delete;
    Button bt_memo_detail_change;
    long id;
    String title;
    String body;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail);
        Intent getIntent = getIntent();
        id = getIntent.getLongExtra("id",0);
        title = getIntent.getStringExtra("title");
        body = getIntent.getStringExtra("body");

        MemoHelper helper = new MemoHelper(this);
        sqLiteDatabase = helper.getWritableDatabase();

        tv_memo_detail_title = (TextView)findViewById(R.id.tv_memo_detail_title);
        tv_memo_detail_body = (TextView)findViewById(R.id.tv_memo_detail_body);
        tv_memo_detail_title.setText(title);
        tv_memo_detail_body.setText(body);
        bt_memo_detail_change = (Button)findViewById(R.id.bt_memo_detail_change);
        bt_memo_detail_delete = (Button)findViewById(R.id.bt_memo_detail_delete);
        bt_memo_detail_change.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                change();
            }
        });
        bt_memo_detail_delete.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                delete();
            }
        });

    }

    public void delete(){
        sqLiteDatabase.delete(MemoDB.MemoTable.TABLE_NAME, MemoDB.MemoTable._ID + " = "+ id, null);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void change(){
        Intent intent = new Intent(this, Memo_add.class);
        intent.putExtra("call",1);
        intent.putExtra("title",title);
        intent.putExtra("body",body);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
