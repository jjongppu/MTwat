package com.example.owner.twat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Owner on 2017-07-18.
 */

public class GroupItem extends LinearLayout {
    ImageView groupImage;
    TextView groupListTitle,groupListName,groupListUserCount;

    public GroupItem(Context c) {
        super(c);
        // 인플레이션 담당하는 메서드
        init(c);
    }

    public GroupItem(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public void init(Context c){
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.group_list_item,this,true);

        groupImage = (ImageView) findViewById(R.id.groupImage);
        groupListTitle = (TextView) findViewById(R.id.groupListTitle);
        groupListName = (TextView) findViewById(R.id.groupListName);
        groupListUserCount = (TextView) findViewById(R.id.groupListUserCount);

    }

    public void setGroupImage(final String imgUrl) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imgUrl);
                    URLConnection conn = url.openConnection();
                    conn.connect();

                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                    Bitmap bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    groupImage.setImageBitmap(bm);
                    Log.i("Image Thread","!");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public void setGroupListTitle(String groupListTitle) {
        this.groupListTitle.setText(groupListTitle);
    }

    public void setGroupListName(String groupListName) {
        this.groupListName.setText(groupListName);
    }

    public void setGroupListUserCount(String groupListUserCount) {
        this.groupListUserCount.setText(groupListUserCount);
    }
}
