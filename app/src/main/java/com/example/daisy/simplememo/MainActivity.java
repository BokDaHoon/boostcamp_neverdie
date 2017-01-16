package com.example.daisy.simplememo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.daisy.simplememo.data.DBHelper;

public class MainActivity extends AppCompatActivity implements MemoAdapter.ListItemClickListener , LoaderManager.LoaderCallbacks<Cursor>{
    private MemoAdapter mAdapter;
    private RecyclerView mMemoList;
    private DBHelper mDbHelper;

    private static final int TASK_LOADER_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDbHelper = new DBHelper(this);
        mMemoList = (RecyclerView) findViewById(R.id.rv_memo);
        mMemoList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MemoAdapter(this, this);
        mMemoList.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startAddMemoIntent = new Intent(MainActivity.this, AddMemoActivity.class);
                startActivity(startAddMemoIntent);
            }
        });
        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onListItemClick(int clickedItemIndex , int clickedMemoId) {
        Intent startDetailMemoActivity = new Intent(MainActivity.this, DetailMemoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("memoId", clickedMemoId);
        startDetailMemoActivity.putExtras(bundle);
        startActivity(startDetailMemoActivity);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mTaskData = null;
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    deliverResult(mTaskData);
                } else {
                    forceLoad();
                }
            }
            @Override
            public Cursor loadInBackground() {
                try {
                    return  mDbHelper.getAllMemos();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }
}
