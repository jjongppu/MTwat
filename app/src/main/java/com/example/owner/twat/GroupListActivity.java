package com.example.owner.twat;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class GroupListActivity extends AppCompatActivity {

    CookieManager cookieManager;
    ListView groupListView;
    AdapterGroupList ag;
    ContentValues paramData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        groupListView = (ListView)findViewById(R.id.groupListView);
        ag = new AdapterGroupList(this);

        cookieManager = CookieManager.getInstance();


        new CookieCheck(cookieManager).checkCook();
        String id = cookieManager.getCookie("http://dalgether.com");
        Log.i("----------------------------------------",id);


        try {
            paramData = new ContentValues();
            paramData.put("login",id.trim());
            paramData.put("kind",3);
            JSONArray jsonArray = new HttpUtil("http://dalgether.com/groupList.do",paramData).execute().get();
            String result = jsonArray.getJSONObject(0).getString("group_id");
            if(result.equals("noGroup")){
                ag.addItem(new CalgatherVO(0,"","","",0,null));
                Log.i("Json group","no---");
            }else{
                Log.i("Json group","yes---");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject cv = (JSONObject) jsonArray.get(i);
                    final CalgatherVO cvs = new CalgatherVO();

////                    URL url = new URL(src);
//                    HttpURLConnection connection;
//                    connection = (HttpURLConnection) url.openConnection();
//                    connection.setDoInput(true);
//                    connection.connect();
//                    InputStream input = connection.getInputStream();
//                    Bitmap bm = BitmapFactory.decodeStream(input);
//




                    cvs.setGroup_id(cv.getInt("group_id"));
                    cvs.setGroup_count(cv.getInt("group_count"));
                    cvs.setGroup_master(cv.getString("group_master"));
                    cvs.setGroup_master_name(cv.getString("group_master_name"));
                    cvs.setGroup_name(cv.getString("group_name"));
                    cvs.setGroup_img("http://dalgether.com/"+cv.getString("group_img"));


                    ag.addItem(cvs);


                }

            }

            groupListView.setAdapter(ag);

            groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //클릭한 아이템 몇번째인지 보고 그아이탬의 pk 값 가지고 넘어가야함
//                    int groupPk = cv.getGroup_id();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

//class ImgThread implements Runnable{
//
//    @Override
//    public void run() {
//        GroupListActivity.
//
//    }
//}