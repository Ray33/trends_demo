package io.mobitech.trends.demo.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.mobitech.trends.demo.R;
import io.mobitech.trends.demo.TrendsDemoApplication;
import io.mobitech.trends.demo.api.ApiManager;
import io.mobitech.trends.demo.ui.adapters.TrendsRecyclerViewAdapter;
import io.mobitech.trends.demo.ui.interfaces.OnTrendsClickListener;
import io.mobitech.trends.demo.ui.interfaces.OnTrendsDeleteClickListener;

/**
 * Created by Viacheslav Titov on 07.11.2016.
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.trendsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    private void updateData() {
        AsyncTask<Void, Void, List<String>> task = new AsyncTask<Void, Void, List<String>>() {

            @Override
            protected List<String> doInBackground(Void... voids) {
                return ApiManager.getTrendingKeywords(TrendsDemoApplication.getInstance().getApiKey(),
                        TrendsDemoApplication.getInstance().getUserId(), null);
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

            }
        }, new OnTrendsDeleteClickListener() {
            @Override
            public void onTrendsDelete(String trendsKeyword) {

            }
        });
        mRecyclerView.setAdapter(adapter);
    }

}
