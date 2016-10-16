/*
package com.snapchat.team2.snapchat.customAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.snapchat.team2.snapchat.ListAdapterDataModel.ChatMessage;
import com.snapchat.team2.snapchat.ListAdapterDataModel.MessageNotifcation;
import com.snapchat.team2.snapchat.R;

import java.util.List;

*/
/**
 * Created by xu on 2016/10/15.
 *//*

public class MessageListAdapter extends BaseAdapter{

    private List<MessageNotifcation> ls;
    private LayoutInflater inflater;
    // 1 means send 2 means accept
    private final int ITEM_TYPE_1=1;
    private final int ITEM_TYPE_2=2;

    public void addone(ChatMessage cm){
        ls.add(cm);
        notifyDataSetChanged();
    }

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

    public int getItemViewType(int position){
        return ls.get(position).getItemType();
    }

    public MessageListAdapter(Context context, List<MessageNotifcation> list){
        this.ls = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        MessageNotifcation item = ls.get(position);
        int type = getItemViewType(position);

        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        convertView = null;

        if(convertView ==null){
            switch (type){
                case ITEM_TYPE_1:
                    convertView = inflater .inflate(R.layout.chat_room_list_item_1,null);
                    holder1 = new ViewHolder1(convertView);

                    holder1.setMessage1(item.getContent());
                    convertView.setTag(holder1);
                    break;
                case ITEM_TYPE_2:
                    convertView = inflater.inflate((R.layout.chat_room_list_item_2),null);
                    holder2 = new ViewHolder2(convertView);
                    holder2.setMessage2(item.getContent());

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
                    holder1.setMessage1(item.getContent());
                    break;
                case ITEM_TYPE_2:

                    holder2 = (ViewHolder2) convertView.getTag();
                    holder2.setMessage2(item.getContent());
                    break;
                default:
                    break;
            }
        }
        return  convertView;
    }

    private class ViewHolder1 extends RecyclerView.ViewHolder{
        TextView message1;

        public ViewHolder1(View itemView) {
            super(itemView);
            message1 = (TextView)itemView.findViewById(R.id.message_text1);
        }

        public TextView getMessage1() {
            return message1;
        }

        public void setMessage1(String text) {
            if(this.message1 == null){
                System.out.println("没有找到");
            }
            this.message1.setText(text);
        }
    };
    private class ViewHolder2 extends RecyclerView.ViewHolder{
        TextView message2;

        public ViewHolder2(View itemView) {
            super(itemView);
            message2 = (TextView)itemView.findViewById(R.id.message_text2);
        }

        public TextView getMessage2() {
            return message2;
        }

        public void setMessage2(String text) {
            this.message2 .setText(text);
        }
    };


}
*/
