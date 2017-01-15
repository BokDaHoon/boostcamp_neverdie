package com.example.android.memo;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.memo.data.MemoContract;

/**
 * Created by user on 2017-01-14.
 */

public class MemoAdaptor extends RecyclerView.Adapter<MemoAdaptor.MemoViewHolder> {

private static final String TAG = MemoAdaptor.class.getSimpleName();

    private Context mContext;
    private Cursor mCursor;
    private MemoAdaptorOnClickHandler mMemoHandler;

    public interface MemoAdaptorOnClickHandler{
        void onClick(long id);
    }

    public MemoAdaptor(Context context, MemoAdaptorOnClickHandler handler){
        mContext = context;
        mMemoHandler = handler;
    }

    @Override
    public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(
                R.layout.memo_item_list,
                parent,
                false
        );
        MemoViewHolder viewHolder = new MemoViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemoViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)) {
            return; // fail to move
        }

        String memo = mCursor.getString(
                mCursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_CONTENTS));

        String title;
        if(memo.length()<=30) {
            title = memo;
        }else if(memo.contains("\n")){
            title=memo.split("\n")[0];
        }else{
            title=memo.substring(0,30);
        }

        holder.listItemNumberView.setText(String.valueOf(position));
        holder.viewHolderText.setText(title);

        // for swipe delete
        long id =  mCursor.getLong(
                mCursor.getColumnIndex(MemoContract.MemoEntry._ID));
        holder.itemView.setTag(id);


    }

    @Override
    public int getItemCount() {
        if (mCursor==null) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor cursor){
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public class MemoViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView listItemNumberView;
        // Will display which ViewHolder is displaying this data
        TextView viewHolderText;

        public MemoViewHolder(View itemView) {
            super(itemView);
            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            viewHolderText = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mCursor.moveToPosition(position);
            long id = mCursor.getLong(mCursor.getColumnIndex(MemoContract.MemoEntry._ID));
            mMemoHandler.onClick(id);
        }
    }
}
