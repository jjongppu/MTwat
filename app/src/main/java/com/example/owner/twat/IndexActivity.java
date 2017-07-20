package com.example.owner.twat;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

public class IndexActivity extends AppCompatActivity {

    EditText inputId,inputPassward;
    ContentValues paramData;
    String userid,userpw;
    CookieManager cookieManager;
    int resultCookie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        cookieManager = CookieManager.getInstance();

        resultCookie = new CookieCheck(cookieManager).checkCook();
        if (resultCookie != -1){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

    }


    public void btnLogin(View v){
        inputId = (EditText)findViewById(R.id.inputId);
        inputPassward = (EditText)findViewById(R.id.inputPassward);
        paramData = new ContentValues();

        userid = inputId.getText().toString();
        userpw = inputPassward.getText().toString();

        paramData.put("userid",userid);
        paramData.put("userpw",userpw);


        try {
            JSONArray jsonArray = new HttpUtil("http://aclass0201.cafe24.com/login.do",paramData).execute().get();
            String result = jsonArray.getJSONObject(0).getString("result");
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("로그인 실패");

            dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });



            if(result.equals("fail")){
                dialog.setMessage("아이디와 비밀번호를 \r\n 확인해주세요.");
                dialog.show();

            }else if(result.equals("outIng")){
                dialog.setMessage("탈퇴가 진행중인 회원입니다. \r\n회원정보를 복구 하시겠습니까?");

                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 회원 정보 복구시키는 서블릿 연결
                        try {
                            paramData = new ContentValues();
                            paramData.put("state","return");
                            paramData.put("userid",userid);
                            JSONArray jsonArray = new HttpUtil("http://aclass0201.cafe24.com/outUser.do",paramData).execute().get();
                            String result = jsonArray.getJSONObject(0).getString("result");
                            if(result.equals("success")){
                                Toast.makeText(IndexActivity.this, "회원정보가 정상 복구 되었습니다.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(IndexActivity.this, "회원정보 복구에 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                dialog.show();

            }else if(result.equals("out")){
                dialog.setMessage("탈퇴한 회원입니다.");
                dialog.show();

            }else{
                // 로그인 성공!
                //로그인 한 아이디로 쿠키생성후 페이지이동
                CookieManager.getInstance().setCookie("http://dalgether.com", userid);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}

