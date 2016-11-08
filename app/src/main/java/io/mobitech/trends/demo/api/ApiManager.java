package io.mobitech.trends.demo.api;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.mobitech.trends.demo.BuildConfig;
import io.mobitech.trends.demo.TrendsDemoApplication;

/**
 * Created by Viacheslav Titov on 07.11.2016.
 */

public class ApiManager {

    private static final String LOG = ApiManager.class.getPackage() + "." + ApiManager.class.getSimpleName();

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String ACCEPT_LANGUAGE = "en-US,en;q=0.5";

    private static final String URL_BASE = "http://trends-vertx.mobitech-search.xyz/";
    private static final String URL_TRENDING_LIST = "mobitech/trends/?p_key=%s&user_id=%s&p_id=%s&c=%s";
    private static final String URL_TRENDING_DELETE = "mobitech/trends/delete/?p_key=%s&user_id=%s&p_id=%s&keywords=%s";

    private static String getTrendsUrl(String country) {
        String url = URL_BASE + URL_TRENDING_LIST;
        return String.format(url, TrendsDemoApplication.getInstance().getApiKey(),
                TrendsDemoApplication.getInstance().getUserId(),
                "search_sdk", TextUtils.isEmpty(country) ? "US" : country);
    }

    private static String getTrendDeleteUrl(String keywords) throws UnsupportedEncodingException {
        String url =  URL_BASE + URL_TRENDING_DELETE;
        return String.format(url, TrendsDemoApplication.getInstance().getApiKey(),
                TrendsDemoApplication.getInstance().getUserId(), "search_sdk",
                URLEncoder.encode(keywords, "UTF-8"));
    }

    public static List<String> getTrendingKeywords(String country) {
        Log.d(LOG, "------- getTrendingKeywords -------");
        List<String> result = new ArrayList<>();
        try {
            HttpURLConnection connection = prepareHttpGetConnection(getTrendsUrl(country));
            Log.d(LOG, connection.getURL().toString());

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
        } catch (IOException | JSONException e) {
            Log.e(LOG, e.getMessage());
        }
        return result;
    }

    public static boolean deleteTrendingKeyword(String keywords) {
        Log.d(LOG, "------- deleteTrendingKeyword -------");
        try {
            HttpURLConnection connection = prepareHttpGetConnection(getTrendDeleteUrl(keywords));
            Log.d(LOG, connection.getURL().toString());

            final int responseCode = connection.getResponseCode();
            Log.d(LOG, "response code: " + responseCode);
            String responseBody;
            if (200 <= responseCode && responseCode <= 299) {
                responseBody = getStringFromInputStream(connection.getInputStream());
            } else {
                responseBody = getStringFromInputStream(connection.getErrorStream());
                Log.e(LOG, responseBody);
                return false;
            }
            if (BuildConfig.DEBUG) {
                Log.d(LOG, responseBody);
            }
        } catch (IOException e) {
            Log.e(LOG, e.getMessage());
            return false;
        }
        return true;
    }

    private static HttpURLConnection prepareHttpGetConnection(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
        return connection;
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
