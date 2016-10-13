package com.snapchat.team2.snapchat.networkService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by bm on 8/10/2016.
 */

public class PhotoNetService {

    private RequestQueue requestQueue;

    public PhotoNetService(RequestQueue rq){
        this.requestQueue=rq;
    }

    public void postPhoto(final Activity activity, String UploadURL, final Bitmap photo, final String filename,
                          final String senderId, final String receiverId, final String catergory){

    final ProgressDialog loading = ProgressDialog.show(activity,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UploadURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Toast.makeText(activity.getApplication(),"upload successfully",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error response
                loading.dismiss();
                Toast.makeText(activity.getApplication(),"upload error ",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //convert photo to string
                String image=PhotoNetService.getStringImage(photo);

                Map<String,String> params = new Hashtable<String, String>();

                params.put("photo",image);
                params.put("name",filename);
                params.put("from",senderId);
                params.put("to",receiverId);
                params.put("category",catergory);

                return params;

            }

        };

        requestQueue.add(stringRequest);

    }

    public static String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public void getPhotoToUser(int userId, String URL){

    }


    private void getImageByCategory(String imageURL,String userId){
        ImageRequest request=new ImageRequest(imageURL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

            }
        },0,0,null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }
}
