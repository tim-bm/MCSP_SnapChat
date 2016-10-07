package com.snapchat.team2.snapchat.customAdapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.DialogInterface;
//import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.snapchat.team2.snapchat.R;
import java.util.ArrayList;
import java.util.List;
import com.snapchat.team2.snapchat.ListAdapterDataModel.StoryListItem;
/**
 * Created by Kun on 10/3/2016.
 */

public class StoryDerpAdapter extends RecyclerView.Adapter<StoryDerpAdapter.DerpHolder>{

    private List<StoryListItem> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;



    public interface ItemClickCallback {
        void onItemClick(int p);
        void onItemLongClicked(int p);
        void onSecondaryIconClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public StoryDerpAdapter(List<StoryListItem> listData, Context c){
        inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public StoryDerpAdapter.DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.stroy_list_item, parent, false);
        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {
        StoryListItem item = listData.get(position);
        holder.title.setText(item.getTitle());
        holder.subTitle.setText(item.getSubTitle());
    }

    public void setListData(ArrayList<StoryListItem> exerciseList) {
        this.listData.clear();
        this.listData.addAll(exerciseList);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        ImageView thumbnail;
        //ImageView secondaryIcon;
        TextView title;
        TextView subTitle;
        View container;


        public DerpHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView)itemView.findViewById(R.id.im_item_icon);
            // secondaryIcon = (ImageView)itemView.findViewById(R.id.im_item_icon_secondary);
            // secondaryIcon.setOnClickListener(this);
            subTitle = (TextView)itemView.findViewById(R.id.lbl_item_sub_title);
            title = (TextView)itemView.findViewById(R.id.lbl_item_text);
            container = (View)itemView.findViewById(R.id.cont_item_root);;
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cont_item_root){
                itemClickCallback.onItemClick(getAdapterPosition());
            } else {
                // itemClickCallback.onSecondaryIconClick(getAdapterPosition());
            }
        }


        @Override
        public boolean onLongClick(View v) {
            if (v.getId() == R.id.cont_item_root){
                itemClickCallback.onItemLongClicked(getAdapterPosition());
            }
            return false;
        }
    }
}