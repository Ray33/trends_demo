package io.mobitech.trends.demo;

import android.app.Application;

/**
 * Created by User on 07.11.2016.
 */

public class TrendsDemoApplication extends Application {

    private static final String API_KEY = "SY1000325";

    private String mUserId;

    private static TrendsDemoApplication sInstance;

    public static TrendsDemoApplication getInstance() {
        if (sInstance == null) sInstance = new TrendsDemoApplication();
        return sInstance;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getApiKey() {
        return API_KEY;
    }

}
