package com.example.owner.twat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.CookieManager;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2017-07-17.
 */

public class CookieCheck
{
    CookieManager cookieManager;

    public CookieCheck(CookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }
    public int checkCook(){
        // 쿠키유무를 반환해줌 0 쿠키 존재 / -1 쿠키 없음
        int result = 0;
        if(cookieManager.getCookie("http://dalgether.com") != null){
//            ContentValues jsonParams = new ContentValues();
//
//            jsonParams.put("LoginCookie", cookieManager.getCookie("http://dalgether.com"));
//            JSONObject json = null;
//            JSONParser jParserloc = new JSONParser();
//            json = jParserloc.httpRequest("MakeSession.do", "Post", jsonParams);

            ContentValues paramData = new ContentValues();
            String id = cookieManager.getCookie("http://dalgether.com");
            paramData.put("LoginCookie", id);


            try
            {
                JSONArray jsonArray = new HttpUtil("http://aclass0201.cafe24.com/MakeSession.do", paramData).execute().get();

                Log.i("make Cookie",jsonArray.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else
        {
            // login.xml로 이동
            result =-1;

        }

        if(cookieManager.getCookie("http://dalgether.com") != null){
            Log.i("CookName",cookieManager.getCookie("http://dalgether.com"));

        }
        return result;
    }

}