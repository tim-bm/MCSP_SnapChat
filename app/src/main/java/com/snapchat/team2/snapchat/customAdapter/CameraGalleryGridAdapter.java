package com.snapchat.team2.snapchat.customAdapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.snapchat.team2.snapchat.Tools.AsyncImageLoaderFromFile;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by bm on 16/10/2016.
 */

public class CameraGalleryGridAdapter extends BaseAdapter {


    private Context mContext;
    private String[] rollImages;
    private File memoryImageDir=new File(Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_PICTURES),"SnapChat222");



    public CameraGalleryGridAdapter(Context context){
        mContext=context;
        readFromCameraRoll();

    }


    private void readFromCameraRoll(){
        {
            ArrayList<String> list=new ArrayList<String>();

            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            ContentResolver contentResolver = mContext.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor == null || cursor.getCount() <= 0) {
                return;
            }

            while (cursor.moveToNext())
            {
                int index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String path = cursor.getString(index);
                File file = new File(path);
                if (file.exists())
                {
                    list.add(path);

                }
            }

            String[] type=new String [list.size()];
            rollImages=list.toArray(type);

        }
    }

    @Override
    public int getCount() {
        return rollImages.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ImageView imageView;
        if (view == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);

            imageView.setLayoutParams(new GridView.LayoutParams(200, 400));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) view    ;
        }


        if(memoryImageDir.length()!=0){
            //load image asynchronisedly
//            AsyncImageLoaderFromFile loader=new AsyncImageLoaderFromFile(imageView,
//                    memoryImageDir.getAbsolutePath()+"/"+memoryImages[position]);
//            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,memoryImageDir.getAbsolutePath()+"/"+memoryImages[position]);

            AsyncImageLoaderFromFile loader=new AsyncImageLoaderFromFile(imageView,rollImages[position]);
            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,rollImages[position]);

        }

        return imageView;
    }


    public String[] getRollImages() {
        return rollImages;
    }
}
