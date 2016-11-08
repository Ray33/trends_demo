package io.mobitech.trends.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import io.mobitech.trends.demo.R;
import io.mobitech.trends.demo.ui.fragments.HtmlKeywordsFragment;
import io.mobitech.trends.demo.ui.fragments.TrendingKeywordsFragment;
import io.mobitech.trends.demo.ui.interfaces.OnChangeFragmentListener;

/**
 * Created by Viacheslav Titov on 07.11.2016.
 */
public class MainActivity extends AppCompatActivity implements OnChangeFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main, TrendingKeywordsFragment.newInstance(), TrendingKeywordsFragment.TAG);
        ft.commit();
    }

    @Override
    public void onFragmentChange(String fragmentTag, Object[] data) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragmentTag.equals(HtmlKeywordsFragment.TAG)) {
            ft.add(R.id.activity_main, HtmlKeywordsFragment.newInstance((String) data[0]), HtmlKeywordsFragment.TAG);
            ft.addToBackStack(HtmlKeywordsFragment.TAG);
        }
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }
}
