package io.mobitech.trends.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Viacheslav Titov on 07.11.2016.
 */

public class SharedPreferencesMapper {

    private static final String SHR_PREF_NEED_USER = "SHR_PREF_NEED_USER";

    public static boolean needUsedUserId(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(SHR_PREF_NEED_USER, false);
    }

    public static void switchUsedUserId(Activity activity, boolean useUserId) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(SHR_PREF_NEED_USER, useUserId);
        editor.commit();
    }

}
