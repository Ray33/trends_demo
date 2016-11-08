package io.mobitech.trends.demo.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.mobitech.trends.demo.R;
import io.mobitech.trends.demo.api.ApiManager;
import io.mobitech.trends.demo.ui.adapters.TrendsRecyclerViewAdapter;
import io.mobitech.trends.demo.ui.interfaces.OnChangeFragmentListener;
import io.mobitech.trends.demo.ui.interfaces.OnTrendsClickListener;
import io.mobitech.trends.demo.ui.interfaces.OnTrendsDeleteClickListener;

/**
 * Created by Viacheslav Titov on 08.11.2016.
 */

public class TrendingKeywordsFragment extends Fragment {

    public static final String TAG = TrendingKeywordsFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private OnChangeFragmentListener mOnChangeFragmentListener;

    public static TrendingKeywordsFragment newInstance() {
        TrendingKeywordsFragment fragment = new TrendingKeywordsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trending_list, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.trendsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnChangeFragmentListener = (OnChangeFragmentListener) getActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateData();
    }

    private void updateData() {
        AsyncTask<Void, Void, List<String>> task = new AsyncTask<Void, Void, List<String>>() {

            @Override
            protected List<String> doInBackground(Void... voids) {
                return ApiManager.getTrendingKeywords(null);
            }

            @Override
            protected void onPostExecute(List<String> result) {
                updateUI(result);
            }
        };
        task.execute();
    }

    private void updateUI(List<String> trendingList) {
        TrendsRecyclerViewAdapter adapter = new TrendsRecyclerViewAdapter(trendingList, new OnTrendsClickListener() {
            @Override
            public void onTrendsClick(String trendKeyword) {
                if (mOnChangeFragmentListener != null) {
                    mOnChangeFragmentListener.onFragmentChange(HtmlKeywordsFragment.TAG, new Object[]{trendKeyword});
                }
            }
        }, new OnTrendsDeleteClickListener() {
            @Override
            public void onTrendsDelete(String trendsKeyword) {
                showDialogDeleteTrendingKeyword(trendsKeyword);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    private void showDialogDeleteTrendingKeyword(final String trendsKeyword) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setTitle(R.string.confirmation);
        String message = String.format(getString(R.string.dialog_delete_trends_keywords), trendsKeyword);
        alertBuilder.setMessage(message);
        alertBuilder.setNegativeButton(R.string.no, null);
        alertBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteTrendingKeyword(trendsKeyword);
            }
        });
        alertBuilder.create().show();
    }

    private void deleteTrendingKeyword(final String trendsKeyword) {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                return ApiManager.deleteTrendingKeyword(trendsKeyword);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    updateData();
                }
            }
        };
        task.execute();
    }
}
