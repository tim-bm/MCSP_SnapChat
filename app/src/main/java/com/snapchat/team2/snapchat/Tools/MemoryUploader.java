package com.snapchat.team2.snapchat.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.fragement.CameraGalleryFragment;
import com.snapchat.team2.snapchat.networkService.PhotoNetService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bm on 16/10/2016.
 */

public class MemoryUploader {



    public static void uploadPhoto(String photoPath, Activity activity,boolean ifCompressed){


        String receiverId ="-1";
        String category="2";
        //get send id from share preferences
        SharedPreferences shared = activity.getSharedPreferences("snapchat_user", MODE_PRIVATE);
        String sendId=shared.getString("user_id", null);

        //read photo from path

        File photoFile=new File(photoPath);

        String ImageDate = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
        String filenameOnServer="IMG_"+ImageDate+"---memory"+".jpg";

        //send photo
        RequestQueue rq= Volley.newRequestQueue(activity);
        PhotoNetService p=new PhotoNetService(rq);
        if(photoFile.exists()){

            Bitmap photo;
            if(ifCompressed){
                //decode and resize Bitmap
                BitmapFactory.Options resizeoption=new BitmapFactory.Options();
                resizeoption.inSampleSize=3;

                photo= BitmapFactory.decodeFile(photoFile.getAbsolutePath(),resizeoption);
            }else{
                photo= BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            }
//            //decode and resize Bitmap
//            BitmapFactory.Options resizeoption=new BitmapFactory.Options();
//            resizeoption.inSampleSize=3;
//
//            Bitmap photo= BitmapFactory.decodeFile(photoFile.getAbsolutePath(),resizeoption);
            p.postPhoto(activity,activity.getString(R.string.serverAddress)+"upload/photo",
                    photo,filenameOnServer,sendId,receiverId,category);
        }



    }

}
