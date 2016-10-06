package com.snapchat.team2.snapchat.customAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.Filter;
import android.widget.Filterable;
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
public class ChatFriendListAdapter extends BaseAdapter implements Filterable{

    private List<Friend> ls;
    private List<Friend> mStringFilterList;
    private LayoutInflater inflater;
    private final int ITEM_TYPE_1=1;
    private final int ITEM_TYPE_2=2;
    private ValueFilter valueFilter;


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
        convertView = null;

        if(convertView ==null){
            switch (type){
                case ITEM_TYPE_1:
                    convertView = inflater .inflate(R.layout.chat_friend_list_item,null);
                    holder1 = new ViewHolder1(convertView);
                    //holder1.t_initial_1=(TextView)convertView.findViewById(R.id.chat_friend_letter);
                    //holder1.t_name_1 = (TextView)convertView.findViewById(R.id.chat_friend_name);
                    holder1.setT_initial_1(item.getInitial_letter());
                    holder1.setT_name_1(item.getName());
                    convertView.setTag(holder1);
                    break;
                case ITEM_TYPE_2:
                    convertView = inflater.inflate((R.layout.chat_frind_list_item_2),null);
                    holder2 = new ViewHolder2(convertView);
                    holder2.setT_name_2(item.getName());
                    //holder2.t_name_2 = (TextView)convertView.findViewById(R.id.chat_friend_name_2);
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
                    holder1.setT_initial_1(item.getInitial_letter());
                    holder1.setT_name_1(item.getName());
                    break;
                case ITEM_TYPE_2:

                    holder2 = (ViewHolder2) convertView.getTag();
                    holder2.setT_name_2(item.getName());
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

        this.inflater =LayoutInflater.from(context);
        this.mStringFilterList = list;
        getFilter();
    }

    public Filter getFilter(){
        if(valueFilter==null){
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    class ViewHolder1 extends RecyclerView.ViewHolder{
        TextView t_initial_1;
        TextView t_name_1;

        public ViewHolder1(View itemView) {
            super(itemView);
            t_initial_1 = (TextView)itemView.findViewById(R.id.chat_friend_letter);
            t_name_1 = (TextView)itemView.findViewById(R.id.chat_friend_name);
        }

        public TextView getT_initial_1() {
            return t_initial_1;
        }

        public void setT_initial_1(String text) {
            this.t_initial_1.setText(text);
        }

        public TextView getT_name_1() {
            return t_name_1;
        }

        public void setT_name_1(String text) {
            this.t_name_1.setText(text);
        }
    }
    class ViewHolder2 extends RecyclerView.ViewHolder{
        TextView t_name_2;

        public ViewHolder2(View itemView) {
            super(itemView);
            t_name_2 = (TextView)itemView.findViewById(R.id.chat_friend_name_2);
        }

        public TextView getT_name_2() {
            return t_name_2;
        }

        public void setT_name_2(String text) {
            this.t_name_2.setText(text);
        }
    }

    private class ValueFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results =new FilterResults();
            if(constraint!= null && constraint.length()>0){
                List<Friend> filterList =new ArrayList<Friend>();
                for(int i=0;i<mStringFilterList.size();i++){
                    Friend f = mStringFilterList.get(i);
                    String[] parts = f.getName().split(" ");
                    for(String part:parts){
                        if(part.toLowerCase().startsWith(constraint.toString().toLowerCase())){
                            Friend friend =new Friend();
                            friend.setUser_id(f.getUser_id());
                            friend.setInitial_letter(f.getInitial_letter());
                            friend.setName(f.getName());
                            filterList.add(friend);
                            break;
                        }
                    }
                }
                for(int i=0;i<filterList.size();i++){
                    Friend f = filterList.get(i);
                    if(i==0){
                        f.setItem_type(1);
                        continue;
                    }
                    if(f.getInitial_letter().equals(filterList.get(i-1).getInitial_letter())) {
                        f.setItem_type(2);
                    }else{
                        f.setItem_type(1);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            }
            else{
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ls =(List<Friend>) results.values;
            notifyDataSetChanged();
        }
    }





}
