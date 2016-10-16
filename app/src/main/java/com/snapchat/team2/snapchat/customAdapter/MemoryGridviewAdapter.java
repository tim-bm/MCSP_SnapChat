package com.snapchat.team2.snapchat.customAdapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.Tools.AsyncImageLoaderFromFile;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * Created by bm on 15/10/2016.
 */

public class MemoryGridviewAdapter extends BaseAdapter {

    private Context mContext;
    private String[] memoryImages;
    private File memoryImageDir=new File(Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_PICTURES),"SnapChat222");



    public MemoryGridviewAdapter(Context context){
        mContext=context;
        readMemoryImages();

    }

    private void readMemoryImages(){
        File dir = memoryImageDir;
        //it just the name of image
        memoryImages=dir.list(new FilenameFilter() {
            File f;
            @Override
            public boolean accept(File file, String name) {
                if(name.endsWith(".jpg")) {
                    return true;
                }
                f = new File(file.getAbsolutePath()+"/"+name);
                return f.isDirectory();
            }
        });

    }


    @Override
    public int getCount() {
        return memoryImages.length;
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
        AsyncImageLoaderFromFile loader=new AsyncImageLoaderFromFile(imageView,
                memoryImageDir.getAbsolutePath()+"/"+memoryImages[position]);
            loader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,memoryImageDir.getAbsolutePath()+"/"+memoryImages[position]);
//        loader.execute(memoryImageDir.getAbsolutePath()+"/"+memoryImages[position]);

        }
//            imageView.setImageBitmap(BitmapFactory.decodeFile(memoryImageDir.getAbsolutePath()+"/"+memoryImages[position]));


//        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    public String[] getMemoryImages() {
        return memoryImages;
    }

//    private Integer[] mThumbIds={
//            R.drawable.ic_circled_timer,R.drawable.ic_action_discover,
//            R.drawable.ic_image_flash_on, R.drawable.ic_image_flash_on,
//            R.color.red
//    };
}
