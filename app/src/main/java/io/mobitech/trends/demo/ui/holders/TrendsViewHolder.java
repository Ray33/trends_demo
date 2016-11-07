package io.mobitech.trends.demo.ui.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import io.mobitech.trends.demo.ui.interfaces.OnTrendsClickListener;
import io.mobitech.trends.demo.ui.interfaces.OnTrendsDeleteClickListener;

/**
 * Created by Viacheslav Titov on 07.11.2016.
 */

public class TrendsViewHolder extends RecyclerView.ViewHolder {

    private OnTrendsClickListener mOnTrendsClickListener;
    private OnTrendsDeleteClickListener mOnTrendsDeleteClickListener;

    public TrendsViewHolder(View itemView, OnTrendsClickListener onTrendsClickListener,
                            OnTrendsDeleteClickListener onTrendsDeleteClickListener) {
        super(itemView);
        mOnTrendsClickListener = onTrendsClickListener;
        mOnTrendsDeleteClickListener = onTrendsDeleteClickListener;
    }

    public void bind(final String trendingKeyword) {
        ((TextView) itemView).setText(trendingKeyword);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnTrendsClickListener.onTrendsClick(trendingKeyword);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnTrendsDeleteClickListener.onTrendsDelete(trendingKeyword);
                return true;
            }
        });
    }
}
