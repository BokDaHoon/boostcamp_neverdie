package com.example.android.mymemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jr on 2017-01-15.
 */

public class MemoViewAdapter extends RecyclerView.Adapter<MemoViewAdapter.MemoViewHolder> {

    private List<OneMemo> memolist;
    private final MemoViewOnClickHandler handler;

    public MemoViewAdapter(List<OneMemo> list, MemoViewOnClickHandler handler) {
        this.memolist = list;
        this.handler = handler;
    }

    @Override
    public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_list_item, parent, false);
        MemoViewHolder viewHolder = new MemoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemoViewHolder holder, int position) {
        OneMemo item = memolist.get(position);
        holder.id.setText(Integer.toString(item.getId()));
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        if(memolist == null) return 0;
        else return memolist.size();
    }

    public interface MemoViewOnClickHandler {
        public void onClick(OneMemo item);
    }

    class MemoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView id;

        public MemoViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textViewTitle);
            id = (TextView) itemView.findViewById(R.id.textViewId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            OneMemo item = memolist.get(position);
            handler.onClick(item);
        }

    }

}
