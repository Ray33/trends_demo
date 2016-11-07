package io.mobitech.trends.demo.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viacheslav Titov on 08.11.2016.
 */

public class JsonParserManager {

    public static List<String> getTrendingKeywords(final String jsonBody) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonBody);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            result.add(jsonObject.getString("keyword"));
        }
        return result;
    }

}
