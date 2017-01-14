package com.example.android.memo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by user on 2017-01-14.
 */

public class MemoAdaptor extends RecyclerView.Adapter<MemoAdaptor.NumberViewHolder> {

private static final String TAG = MemoAdaptor.class.getSimpleName();
    private int mNumberItems;


    public MemoAdaptor(){
        //mNumberItems = numberItems;
    }
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder{


        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView listItemNumberView;
        // Will display which ViewHolder is displaying this data
        TextView viewHolderText;

        public NumberViewHolder(View itemView) {
            super(itemView);
            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            viewHolderText = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);

        }
    }
}
