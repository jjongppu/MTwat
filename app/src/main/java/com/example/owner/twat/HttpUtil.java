package com.example.owner.twat;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.CookieManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Owner on 2017-07-17.
 */

public class HttpUtil extends AsyncTask<String,Void,JSONArray> {
    private String url;
    private ContentValues values;


    public HttpUtil(String url, ContentValues values) {

        this.url = url;
        this.values = values;

    }


    @Override
    protected JSONArray doInBackground(String... strings) {
        String result; // 요청 결과를 저장할 변수.
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
        JSONArray jsonArray = null;
        Log.e("json",result);
        try {
            jsonArray = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

}
