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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snapchat.team2.snapchat.ListAdapterDataModel.MemoryStoryListItem;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customAdapter.MemoryStoryDerpAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kun on 10/11/2016.
 */
public class MemoryStoryService {

    private String requestURL = null;

    private RequestQueue requestQueue;


    public MemoryStoryService(RequestQueue requestQueue){
        this.requestQueue=requestQueue;
    }

    //set the display view as parameters
    public void getDiscover(final Fragment fragment, final String requestURL, final RecyclerView recyclerView ,final String ip){

        String requestURL_base = fragment.getActivity().getResources().getString(R.string.serverAddress) + requestURL;

        final String user_id;
        SharedPreferences shared = fragment.getActivity().getSharedPreferences("snapchat_user", fragment.getActivity().MODE_PRIVATE);
        user_id=shared.getString("user_id", null);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL_base,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        List<MemoryStoryListItem> listData = makeMemoryAdapterModel(response);

                        final MemoryStoryDerpAdapter adapter = new MemoryStoryDerpAdapter(listData,fragment.getActivity(),ip);

                        adapter.setItemClickCallback((MemoryStoryDerpAdapter.ItemClickCallback) fragment);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fragment.getActivity().getApplication(),"Failed in connecting server: ",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // setting post
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", user_id);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    //from json string to adapter model


    private List<MemoryStoryListItem> makeMemoryAdapterModel(String jsonString) {

        Gson gson = new Gson();
        List<MemoryStoryListItem> StoryListItems = null;
        if (jsonString .startsWith("[")) {
            StoryListItems = gson.fromJson(jsonString, new TypeToken<List<MemoryStoryListItem>>() {
            }.getType());
        }else{
            String default_sub = "[ { \"id\": \"0\", \"userId\": \"0\", \"photoContent\": \"http://localhost:8001/snapchat_server/public/photo_upload/test.JPEG\", \"to\": \"-1\", \"category\": \"2\", \"ifSend\": \"-1\" ,\"name\": \"NULL\"}]";
            StoryListItems = gson.fromJson(default_sub, new TypeToken<List<MemoryStoryListItem>>() {
            }.getType());
        }

        return StoryListItems;
    }
}
