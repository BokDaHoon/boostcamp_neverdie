package com.example.daisy.simplememo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.daisy.simplememo.data.DBHelper;

public class MainActivity extends AppCompatActivity implements MemoAdapter.ListItemClickListener {
    private MemoAdapter mAdapter;
    private RecyclerView mMemoList;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbHelper = new DBHelper(this);
        Cursor cursor = mDbHelper.getAllMemos();
        mMemoList = (RecyclerView) findViewById(R.id.rv_memo);
        mMemoList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MemoAdapter(this, cursor, this);
        mMemoList.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAddMemoIntent = new Intent(MainActivity.this, AddMemoActivity.class);
                startActivity(startAddMemoIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex , int clickedMemoId) {
        Intent startDetailMemoActivity = new Intent(MainActivity.this, DetailMemoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("memoId", clickedMemoId);
        startDetailMemoActivity.putExtras(bundle);
        startActivity(startDetailMemoActivity);
    }
}
