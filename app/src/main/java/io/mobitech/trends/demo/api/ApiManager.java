package io.mobitech.trends.demo.api;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.mobitech.trends.demo.BuildConfig;

/**
 * Created by Viacheslav Titov on 07.11.2016.
 */

public class ApiManager {

    private static final String LOG = ApiManager.class.getPackage() + "." + ApiManager.class.getSimpleName();

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String URL_BASE = "http://trends-vertx.mobitech-search.xyz/";
    private static final String URL_TRENDING_LIST = "mobitech/trends";

    private static String getTrendsUrl() {
        return URL_BASE + URL_TRENDING_LIST;
    }

    public static List<String> getTrendingKeywords(String publishKey, String userId, String country) {
        Log.d(LOG, "------- getTrendingKeywords -------");
        List<String> result = new ArrayList<>();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(getTrendsUrl()).openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            connection.addRequestProperty("p_key", publishKey);
            connection.addRequestProperty("user_id", userId);
            connection.addRequestProperty("p_id", "search_sdk");
            connection.addRequestProperty("c", TextUtils.isEmpty(country) ? "US" : country);

            final int responseCode = connection.getResponseCode();
            Log.d(LOG, "response code: " + responseCode);
            String responseBody;
            if (200 <= responseCode && responseCode <= 299) {
                responseBody = getStringFromInputStream(connection.getInputStream());
            } else {
                responseBody = getStringFromInputStream(connection.getErrorStream());
                Log.e(LOG, responseBody);
                return result;
            }
            if (BuildConfig.DEBUG) {
                Log.d(LOG, responseBody);
            }
            result = JsonParserManager.getTrendingKeywords(responseBody);
        } catch (IOException e) {
            Log.e(LOG, e.getMessage());
        } catch (JSONException e) {
            Log.e(LOG, e.getMessage());
        }
        return result;
    }

    private static String getStringFromInputStream(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader((is)));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
            return sb.toString();
        }
        return sb.toString();
    }
}
