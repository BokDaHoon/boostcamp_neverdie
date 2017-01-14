package com.example.daisy.simplememo;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daisy.simplememo.data.MemoDBContract;

/**
 * Created by Daisy on 2017-01-14.
 */

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> {

    final private ListItemClickListener mOnClickListener;
    private int mMemoItmes;

    private Cursor mCursor;
    private Context  mContext;

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex , int memoId);
    }
    public MemoAdapter(Context context, ListItemClickListener listener){
        mContext = context;
        mOnClickListener = listener;
    }
    @Override
    public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.memo_list_item , parent, false);
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemoViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        int memoId = mCursor.getInt(mCursor.getColumnIndex(MemoDBContract.MemoDBEntry.COLUMN_ID));
        String timestamp = mCursor.getString(mCursor.getColumnIndex(MemoDBContract.MemoDBEntry.COLUMN_TIMESTAMP));
        String memoContent = mCursor.getString(mCursor.getColumnIndex(MemoDBContract.MemoDBEntry.COLUMN_CONTENT));

        holder.memoIdTextView.setText(String.valueOf(memoId));
        holder.memoTimeStampTextView.setText(timestamp);
        holder.memoContentTextView.setText(memoContent);
    }

    @Override
    public int getItemCount() {
        if(mCursor ==null){
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (mCursor == newCursor) {
            return null;
        }
        Cursor currentCursor = mCursor;
        this.mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
        return currentCursor;
    }

    class MemoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView memoIdTextView;
        TextView memoTimeStampTextView;
        TextView memoContentTextView;

        public MemoViewHolder(View view){
            super(view);
            memoIdTextView = (TextView)view.findViewById(R.id.tv_memo_id);
            memoTimeStampTextView = (TextView)view.findViewById(R.id.tv_memo_timestamp);
            memoContentTextView = (TextView)view.findViewById(R.id.tv_memo_content);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int clickedPosition= getAdapterPosition();
            int clickedMemoId = Integer.parseInt(memoIdTextView.getText().toString());
            mOnClickListener.onListItemClick(clickedPosition, clickedMemoId);
        }
    }
}
