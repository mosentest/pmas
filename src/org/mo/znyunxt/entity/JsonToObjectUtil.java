package org.mo.znyunxt.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by moziqi on 2015/3/17 0017.
 */
public class JsonToObjectUtil {
    public static JSONArray getJSONArray(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray rows = jsonObject.getJSONArray("rows");
        return rows;
    }
}
