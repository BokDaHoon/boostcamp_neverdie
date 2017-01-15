package com.example.gon.memo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gon.memo.data.MemoDB;
import com.example.gon.memo.data.MemoHelper;

public class MainActivity extends AppCompatActivity implements MemoAdapter.MemoOnclickHandler{

    private MemoAdapter memoAdapter;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView;

        recyclerView = (RecyclerView)this.findViewById(R.id.rv_memo_title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        MemoHelper helper = new MemoHelper(this);
        sqLiteDatabase = helper.getReadableDatabase();

        Cursor cursor = getAllMemo();

        memoAdapter = new MemoAdapter(this, cursor, this);
        recyclerView.setAdapter(memoAdapter);
        Log.v("onCreate", "onCreateFinish");
    }
    @Override
    public void onClick(long id, String title, String body){
        Intent intent = new Intent(this, Memo_detail.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("body",body);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.memo_menu, menu);
        Log.v("onCreate","onCreateOptionFinish");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_add){
            Intent intent = new Intent(this, Memo_add.class);
            intent.putExtra("call", 0);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Cursor getAllMemo(){
        return sqLiteDatabase.query(
                MemoDB.MemoTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }

}
