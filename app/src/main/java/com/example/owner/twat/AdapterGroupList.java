package com.example.owner.twat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Owner on 2017-07-18.
 */

public class AdapterGroupList extends BaseAdapter {
    ArrayList<CalgatherVO> items = new ArrayList<CalgatherVO>();
    Context c;

    public AdapterGroupList(Context c){
        this.c = c;
    }

    public void  addItem(CalgatherVO calgatherVO){
        items.add(calgatherVO);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GroupItem gi = new GroupItem(c);
        CalgatherVO cv = items.get(i);


        gi.setGroupImage(cv.getGroup_img());
        gi.setGroupListName(cv.getGroup_master_name());
        gi.setGroupListTitle(cv.getGroup_name());
        gi.setGroupListUserCount("님 외"+cv.getGroup_count()+"명 참여중");

        return gi;
    }
}
