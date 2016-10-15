package com.snapchat.team2.snapchat.Tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;


/**
 * Created by bm on 15/10/2016.
 */

public class AsyncImageLoaderFromFile extends AsyncTask<String,Void,Bitmap> {

    private ImageView imageView;
    private String filePath;

    public AsyncImageLoaderFromFile(ImageView imageView, String filePath){
        this.imageView=imageView;
        this.filePath=filePath;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        //image path
        String path=strings[0];
        //resize the orginal to 1/4 to display in the grid
        BitmapFactory.Options resizeoption=new BitmapFactory.Options();
        resizeoption.outHeight=400;
        resizeoption.outWidth=200;
        resizeoption.inSampleSize=5;
        Bitmap bitmap= BitmapFactory.decodeFile(path,resizeoption);

        return bitmap;
    }

    //get Bitmap from doInBackground then update it
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);



    }


}
