package com.snapchat.team2.snapchat.fragement;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.snapchat.team2.snapchat.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kun on 10/3/2016.
 */


public class StoryDetail extends Activity {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_AUTHOR = "EXTRA_AUTHOR";
    private static final String EXTRA_STORY = "EXTRA_STORY";
    private static final String EXTRA_IMAGE = "EXTRA_IMAGE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        ((TextView)findViewById(R.id.lbl_quote_text)).setText(extras.getString(EXTRA_AUTHOR));

        String ip = (String) this.getResources().getString(R.string.ip);
        String url = extras.getString(EXTRA_IMAGE);
        url = url.replace("localhost",ip);

        new ImageLoadTask(url, ((ImageView)findViewById(R.id.lbl_quote_image))).execute();
        ((TextView)findViewById(R.id.lbl_quote_attribution)).setText(extras.getString(EXTRA_STORY));
    }



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
}
