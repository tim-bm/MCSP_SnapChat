package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class showPhotoActivity extends Activity {
    private ImageView showphoto ;
    private String imageId = null;
    private TextView disappear = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        showphoto = (ImageView)findViewById(R.id.showPhoto);
        String url = getIntent().getStringExtra("url");
        imageId = getIntent().getStringExtra("imageId");
        System.out.println("传来的url是"+ url);
        String newurl = url.replaceAll("localhost",this.getResources().getString(R.string.ip));
        new DownLoadImageTask(showphoto).execute(newurl);
        disappear = (TextView)findViewById(R.id.disappear);

        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                disappear.setText((millisUntilFinished / 1000)+" ");
            }
            public void onFinish() {
                disappear.setText("done!");
                finish();
            }
        }.start();

    }
    protected void onDestroy(){
        Intent intent = new Intent();
        intent.putExtra("imageId",imageId);
        setResult(0,intent);
        super.onDestroy();
    }
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }
        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }


}
