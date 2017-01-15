package com.example.android.memo;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.memo.data.MemoContract;
import com.example.android.memo.data.MemoDbHelper;

public class MainActivity extends AppCompatActivity implements
        MemoAdaptor.MemoAdaptorOnClickHandler,
        LoaderManager.LoaderCallbacks<Cursor>{

    private MemoAdaptor mAdapter;
    private RecyclerView mMemosList;
    private ProgressBar mLoadingIndicator;

    private MemoDbHelper mDbHelper;

    public static final int ID_MEMO_LOADER = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new MemoDbHelper(this);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // set RecycleView
        mMemosList = (RecyclerView) findViewById(R.id.rv_memos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMemosList.setLayoutManager(layoutManager);
        mMemosList.setHasFixedSize(true);
        mAdapter = new MemoAdaptor(this, this);
        mMemosList.setAdapter(mAdapter);


        // swipe delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long)viewHolder.itemView.getTag();
                if(mDbHelper.deleteMemo(id)>0){
                    getSupportLoaderManager().restartLoader(ID_MEMO_LOADER,null,MainActivity.this);
                }
            }
        }).attachToRecyclerView(mMemosList);


        // Floating Add Button
        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(MainActivity.this, AddMemoActivity.class);
                startActivity(addTaskIntent);
            }
        });

        //loader initialize
        getSupportLoaderManager().initLoader(ID_MEMO_LOADER,null,this);
    }


    // onClickListener for MemoAdaptorOnClickHandler
    @Override
    public void onClick(long id) {
        Intent startToDetailActivity = new Intent(MainActivity.this, DetailActivity.class);
        startToDetailActivity.putExtra(MemoContract.MemoEntry._ID, id);
        startActivity(startToDetailActivity);
    }

    public void showLoading(){
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mMemosList.setVisibility(View.INVISIBLE);
    }

    public void finishLoading(){
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMemosList.setVisibility(View.VISIBLE);
    }


    //for landscape
    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_MEMO_LOADER,null,this);
        super.onResume();
    }


    //memo data load in background
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {
            private Cursor mCursor;

            @Override
            protected void onStartLoading() {
                showLoading();

                if(mCursor==null || mCursor.getCount()==0){
                    forceLoad();
                }else{
                    deliverResult(mCursor);
                }
            }

            @Override
            public Cursor loadInBackground() {
                try{
                    return mDbHelper.getAllMemoList();
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Cursor data) {
                mCursor=data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        finishLoading();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
