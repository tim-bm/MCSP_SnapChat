package com.snapchat.team2.snapchat.networkService;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by bm on 1/10/2016.
 */

public class MsgFromIndex {

    //Important note: modify the address of your own ip of XAMPP and shut down the windows firewall
    private static final String requestURL="http://10.0.0.120:8001/snapchat_server/";

    private RequestQueue requestQueue;
    private String result;

    public MsgFromIndex(RequestQueue requestQueue){
        this.requestQueue=requestQueue;
    }

    //set the display view as parameters
    public void getMsg(final Activity activity){

            StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(activity.getApplication(),"Connect Server successfully: "+response,Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplication(),"Failed in connecting server: ",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);

    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
