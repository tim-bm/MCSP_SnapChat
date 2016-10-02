package com.snapchat.team2.snapchat.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

import com.snapchat.team2.snapchat.ListAdapterDataModel.Friend;
import com.snapchat.team2.snapchat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xu on 2016/10/1.
 */
public class ChatFriendListAdapter extends BaseAdapter{

    private List<Friend> ls;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Friend item = ls.get(position);
        int type = getItemViewType(position);

        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;

        if(convertView ==null){
            switch (type){
                case ITEM_TYPE_1:
                    holder1 = new ViewHolder1();
                    convertView = inflater .inflate(R.layout.chat_friend_list_item,null);
                    holder1.t_initial_1=(TextView)convertView.findViewById(R.id.chat_friend_letter);
                    holder1.t_name_1 = (TextView)convertView.findViewById(R.id.chat_friend_name);
                    holder1.t_initial_1.setText(item.getInitial_letter());
                    holder1.t_name_1.setText(item.getName());
                    convertView.setTag(holder1);
                    break;
                case ITEM_TYPE_2:
                    holder2 = new ViewHolder2();
                    convertView = inflater.inflate((R.layout.chat_frind_list_item_2),null);
                    holder2.t_name_2 = (TextView)convertView.findViewById(R.id.chat_friend_name_2);
                    holder2.t_name_2.setText(item.getName());
                    convertView.setTag(holder2);
                    break;
                default:
                    break;
            }
        }
        else{
            switch (type){
                case ITEM_TYPE_1:
                    holder1 = (ViewHolder1)convertView.getTag();
                    holder1.t_initial_1.setText(item.getInitial_letter());
                    holder1.t_name_1.setText(item.getName());
                    break;
                case ITEM_TYPE_2:
                    holder2 = (ViewHolder2)convertView.getTag();
                    holder2.t_name_2.setText(item.getName());
                    break;
                default:
                    break;
            }
        }
        return  convertView;


    }

    public int getItemViewType(int position){
        return ls.get(position).getItem_type();
    }

    //constructor
    public ChatFriendListAdapter(Context context,List<Friend> list){
        this.ls=list;
        //this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.inflater =LayoutInflater.from(context);
    }


    class ViewHolder1{
        TextView t_initial_1;
        TextView t_name_1;
    }
    class ViewHolder2{
        TextView t_name_2;
    }

}
