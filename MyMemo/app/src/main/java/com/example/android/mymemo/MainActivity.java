package com.example.android.mymemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MemoViewAdapter.MemoViewOnClickHandler {

    private static final int MY_LOADER_ID = 123;

    private MemoViewAdapter adapter;
    private SQLiteDatabase db;

    private TextView textView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        MemoSQLiteOpenHelper helper = new MemoSQLiteOpenHelper(this, null, null, 0);
        db = helper.getReadableDatabase();

        List<OneMemo> list = getMemoList();
        adapter = new MemoViewAdapter(list, this);

        if(adapter.getItemCount() != 0) {
            textView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<OneMemo> list = getMemoList();
        adapter = new MemoViewAdapter(list, this);

        if(adapter.getItemCount() != 0) {
            textView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.action_add) {
            Intent intent = new Intent(this, AddMemoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<OneMemo> getMemoList() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + MemoSQLiteOpenHelper.tableName, null);
        List<OneMemo> list = new ArrayList<>();

        while(cursor.moveToNext()) {
            OneMemo item = new OneMemo();
            item.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            item.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            item.setContent(cursor.getString(cursor.getColumnIndex("content")));
            list.add(item);
            //Log.d("Test", item.getId() + ", " + item.getTitle());
        }

        return list;
    }

    @Override
    public void onClick(OneMemo item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", Integer.toString(item.getId()));
        intent.putExtra("title", item.getTitle());
        intent.putExtra("content", item.getContent());
        startActivity(intent);
    }

}
