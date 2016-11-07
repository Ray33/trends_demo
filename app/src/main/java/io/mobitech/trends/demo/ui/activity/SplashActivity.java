package io.mobitech.trends.demo.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import io.mobitech.trends.demo.TrendsDemoApplication;

/**
 * Created by Viacheslav Titov on 07.11.2016.
 */

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getPackage() + "." + SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler(Looper.myLooper()).post(mRunnable);
    }

    /**
     * Gets user id and sets it to @{@link TrendsDemoApplication}
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    //init Mobitech search SDK with the user's advertising ID
                    String userAdvId = null;
                    try {

                        AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(SplashActivity.this);
                        if (!adInfo.isLimitAdTrackingEnabled()) {
                            userAdvId = adInfo.getId();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                    return userAdvId;
                }

                @Override
                protected void onPostExecute(String userAdvId) {
                    TrendsDemoApplication.getInstance().setUserId(userAdvId);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(intent);
                    finish();
                }

            };
            task.execute();
        }
    };
}
