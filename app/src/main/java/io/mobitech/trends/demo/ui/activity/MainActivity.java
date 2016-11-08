package io.mobitech.trends.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import io.mobitech.trends.demo.R;
import io.mobitech.trends.demo.ui.fragments.HtmlKeywordsFragment;
import io.mobitech.trends.demo.ui.fragments.SettingsFragment;
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
        } else if (fragmentTag.equals(SettingsFragment.TAG)) {
            ft.add(R.id.activity_main, SettingsFragment.newInstance(), SettingsFragment.TAG);
            ft.addToBackStack(SettingsFragment.TAG);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            onFragmentChange(SettingsFragment.TAG, null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
