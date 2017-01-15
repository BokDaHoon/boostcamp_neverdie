package com.example.gon.memo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gon.memo.data.MemoDB;

/**
 * Created by 김장민 on 2017-01-15.
 */

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.Holder> {

    private Cursor mcursor;
    private Context mcontext;
    private MemoOnclickHandler mmemoOnclickHandler;

    public interface MemoOnclickHandler{
        void onClick(long id, String title, String body);
    }

    public MemoAdapter(Context context, Cursor cursor, MemoOnclickHandler memoOnclickHandler){
        Log.i("Memo_Adapter","constructure");
        this.mcontext = context;
        this.mcursor = cursor;
        this.mmemoOnclickHandler = memoOnclickHandler;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("Memo_Adapter","onCreateViewHolder");
        int layout_id = R.layout.memo_summary;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(layout_id, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        Log.i("Memo_Adapter","onBindViewHolder");
        if(!mcursor.moveToPosition(position))
            return;

        String title = mcursor.getString(mcursor.getColumnIndex(MemoDB.MemoTable.MEMO_TITLE));
        Log.i("onBindViewHolder","title = "+title);
        holder.tv_memo_title.setText(title);
        holder.tv_memo_title.setTag(mcursor.getLong(mcursor.getColumnIndex(MemoDB.MemoTable._ID)));

    }

    @Override
    public int getItemCount() {
        Log.i("Memo_Adapter",mcursor.getCount()+"");
        return mcursor.getCount();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_memo_title;

        public Holder(final View itemView) {
            super(itemView);
            tv_memo_title = (TextView) itemView.findViewById(R.id.tv_memo_title);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            if(getAdapterPosition() != RecyclerView.NO_POSITION){
                long id = mcursor.getLong(mcursor.getColumnIndex(MemoDB.MemoTable._ID));
                String title = mcursor.getString(mcursor.getColumnIndex(MemoDB.MemoTable.MEMO_TITLE));
                String body = mcursor.getString(mcursor.getColumnIndex(MemoDB.MemoTable.MEMO_BODY));
                mmemoOnclickHandler.onClick(id, title, body);
            }
        }
    }
}
