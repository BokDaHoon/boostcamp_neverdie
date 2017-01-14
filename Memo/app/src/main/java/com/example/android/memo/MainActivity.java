package com.example.android.memo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


    private MemoAdaptor mAdapter;
    private RecyclerView mMemosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMemosList = (RecyclerView) findViewById(R.id.rv_memos);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMemosList.setLayoutManager(layoutManager);
        mMemosList.setHasFixedSize(true);
        mAdapter = new MemoAdaptor();

        mMemosList.setAdapter(mAdapter);
    }
}
