package com.example.owner.twat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    CookieManager cookieManager;
    int resultCookie;
    RelativeLayout content;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // FrameLayout 에 Activity를 뿌려주기위한 인플래이터...
//        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = getLayoutInflater();

        cookieManager = CookieManager.getInstance();
        content = (RelativeLayout)findViewById(R.id.content);

        resultCookie = new CookieCheck(cookieManager).checkCook();
        if (resultCookie == -1){
            Intent intent = new Intent(getApplicationContext(),IndexActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navBtnHome:
                    //위에 뜨는 메세지
                    mTextMessage.setText("달게더");

                    return true;
                case R.id.navBtnFriend:
                    mTextMessage.setText("친구 목록");
                    return true;
                case R.id.navBtnCalender:
                    mTextMessage.setText("모임 목록");
                    new CookieCheck(cookieManager).checkCook();
//                    View view = inflater.inflate(R.layout.activity_group_list,content,false);
//                    content.addView(view);
                    inflater.inflate(R.layout.activity_group_list,content,true);

//                    content.set
                    return true;
                case R.id.navBtnSetting:
                    mTextMessage.setText("설정");
                    cookieManager.removeAllCookie();



                    return true;
            }
            return false;
        }

    };




    public void textBtn(View v){

        Intent intent = new Intent(getApplicationContext(),GroupListActivity.class);
        startActivity(intent);

    }

}
