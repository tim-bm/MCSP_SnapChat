package com.snapchat.team2.snapchat.customAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.snapchat.team2.snapchat.ListAdapterDataModel.DiscoverStoryListItem;
import com.snapchat.team2.snapchat.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

//import android.support.v7.app.AlertDialog;
/**
 * Created by Kun on 10/3/2016.
 */

public class SubStoryDerpAdapter extends RecyclerView.Adapter<SubStoryDerpAdapter.DerpHolder>{

    private final String ip;
    private List<DiscoverStoryListItem> listData;
    private LayoutInflater inflater;
    private ItemClickCallback itemClickCallback;
    private DiscoverStoryListItem item;


    public interface ItemClickCallback {
        void onItemClick(List<DiscoverStoryListItem> arrayList,int position);
        void onItemLongClick(int p,List<DiscoverStoryListItem> arrayList);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public SubStoryDerpAdapter(List<DiscoverStoryListItem> listData, Context c, String ip){
        inflater = LayoutInflater.from(c);
        this.listData = listData;
        this.ip = ip;
    }

    @Override
    public SubStoryDerpAdapter.DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.story_list_item, parent, false);
        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {
        item = listData.get(position);
        holder.text.setText(item.getTitle());
        String url = item.getImage();

        url = url.replace("localhost",ip);
        new ImageLoadTask(url, holder.image ).execute();    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView image;
        TextView text;
        View container;


        public DerpHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.im_item_pic);
            text = (TextView)itemView.findViewById(R.id.lbl_item_text);
            container = (View)itemView.findViewById(R.id.cont_item_root);;
            container.setOnClickListener(this);
            container.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cont_item_root){
                itemClickCallback.onItemClick( listData,getAdapterPosition());
            } else {
            }
        }


        @Override
        public boolean onLongClick(View v) {
            if (v.getId() == R.id.cont_item_root){
                itemClickCallback.onItemLongClick(getAdapterPosition(),listData);

            } else {
            }
            return true;
        }
    }
}