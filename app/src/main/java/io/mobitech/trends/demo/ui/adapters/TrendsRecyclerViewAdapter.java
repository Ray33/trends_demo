package io.mobitech.trends.demo.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import io.mobitech.trends.demo.R;
import io.mobitech.trends.demo.ui.holders.TrendsViewHolder;
import io.mobitech.trends.demo.ui.interfaces.OnTrendsClickListener;
import io.mobitech.trends.demo.ui.interfaces.OnTrendsDeleteClickListener;

/**
 * Created by Viacheslav Titov on 07.11.2016.
 */

public class TrendsRecyclerViewAdapter extends RecyclerView.Adapter<TrendsViewHolder> {

    private OnTrendsClickListener mOnTrendsClickListener;
    private OnTrendsDeleteClickListener mOnTrendsDeleteClickListener;

    private List<String> mData;

    public TrendsRecyclerViewAdapter(List<String> trendingKeywords, OnTrendsClickListener onTrendsClickListener,
                                     OnTrendsDeleteClickListener onTrendsDeleteClickListener) {
        super();
        mData = trendingKeywords;
        mOnTrendsClickListener = onTrendsClickListener;
        mOnTrendsDeleteClickListener = onTrendsDeleteClickListener;
    }

    @Override
    public TrendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrendsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trending_keywords, parent, false),
                mOnTrendsClickListener, mOnTrendsDeleteClickListener);
    }

    @Override
    public void onBindViewHolder(TrendsViewHolder holder, int position) {
        if (mData == null || holder == null) return;
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size();
    }
}
