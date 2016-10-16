package com.snapchat.team2.snapchat.networkService;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.snapchat.team2.snapchat.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kun on 10/11/2016.
 */
public class SubUpdateDataService {

    private String requestURL = null;

    private RequestQueue requestQueue;


    public SubUpdateDataService(RequestQueue requestQueue){
        this.requestQueue=requestQueue;
    }

    //set the display view as parameters
    public void getDiscover(final Fragment fragment, final String requestURL, final RecyclerView recyclerView , final String storyId){

        String requestURL_base = fragment.getActivity().getResources().getString(R.string.serverAddress) + requestURL;

        final String user_id;
        SharedPreferences shared = fragment.getActivity().getSharedPreferences("snapchat_user", fragment.getActivity().MODE_PRIVATE);
        user_id=shared.getString("user_id", null);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL_base,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fragment.getActivity().getApplication(),"Failed in connecting server: ",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                // setting post
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", user_id);
                params.put("storyId", storyId);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }



}
