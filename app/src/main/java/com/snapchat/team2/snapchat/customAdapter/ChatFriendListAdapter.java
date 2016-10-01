package com.snapchat.team2.snapchat.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snapchat.team2.snapchat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xu on 2016/10/1.
 */
public class ChatFriendListAdapter extends BaseAdapter{

    private List<Map<String, Object>> ls;
    private LayoutInflater inflater;
    private final int ITEM_TYPE_1=1;
    private final int ITEM_TYPE_2=2;
    @Override
    public int getCount() {
        return ls.size();
    }

    @Override
    public Object getItem(int position) {
        return ls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type=getItemViewType(position);
            switch (type){
                case ITEM_TYPE_1:
                    View view_1=inflater.inflate(R.layout.chat_friend_list_item,parent,false);

                    TextView tv_initial = (TextView)view_1.findViewById(R.id.chat_friend_letter);
                    TextView tv_name = (TextView)view_1.findViewById(R.id.chat_friend_name);

                    tv_initial.setText(ls.get(position).get("Initial").toString());
                    tv_name.setText(ls.get(position).get("name").toString());
                    return view_1;

                case ITEM_TYPE_2:
                    View view_2=inflater.inflate(R.layout.chat_frind_list_item_2,parent,false);

                    TextView tv_name_2 = (TextView)view_2.findViewById(R.id.chat_friend_name_2);

                    tv_name_2.setText(ls.get(position).get("name").toString());
                    return view_2;
                default:
                    return null;
            }

    }

    public int getItemViewType(int position){
        if(position==0){
            return ITEM_TYPE_1;
        }

        if(ls.get(position).get("Initial").toString().equals(ls.get(position-1).get("Initial").toString())){
            return ITEM_TYPE_2;
        }
        return ITEM_TYPE_1;
    }

    //constructor
    public ChatFriendListAdapter(LayoutInflater inflater,List<Map<String, Object>> list){
        this.ls=list;
        this.inflater=inflater;
    }

}
