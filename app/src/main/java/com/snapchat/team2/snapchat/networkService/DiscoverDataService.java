package com.snapchat.team2.snapchat.networkService;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snapchat.team2.snapchat.ListAdapterDataModel.DiscoverStoryListItem;
import com.snapchat.team2.snapchat.ListAdapterDataModel.Friend;
import com.snapchat.team2.snapchat.ListAdapterDataModel.StoryDerpData;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customAdapter.DiscoverStoryDerpAdapter;
import com.snapchat.team2.snapchat.customAdapter.LiveStoryDerpAdapter;
import com.snapchat.team2.snapchat.customAdapter.SubStoryDerpAdapter;
import com.snapchat.team2.snapchat.dataJsonModel.userModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kun on 10/11/2016.
 */
public class DiscoverDataService {

    private String requestURL = null;

    private RequestQueue requestQueue;
    private int tech =2;
    private int bussiness=2;
    private int news=2;


    public DiscoverDataService(RequestQueue requestQueue){
        this.requestQueue=requestQueue;
    }

    //set the display view as parameters
    public void getDiscover(final Fragment fragment, final String requestURL, final RecyclerView recyclerView ,final int flag){

        String requestURL_base = fragment.getActivity().getResources().getString(R.string.serverAddress) + requestURL;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL_base,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(fragment.getActivity().getApplication(),"Response!!!!!!",Toast.LENGTH_LONG).show();
                        if (flag==1){

                            List<DiscoverStoryListItem> listData = makeDiscoverAdapterModel(response);
                            String url =listData.get(0).getImage();
                            System.out.println("aaaaa: "+response);
//                            Toast.makeText(fragment.getActivity().getApplication(),url,Toast.LENGTH_LONG).show();
                            final SubStoryDerpAdapter adapter = new SubStoryDerpAdapter(listData,fragment.getActivity());

                            adapter.setItemClickCallback((SubStoryDerpAdapter.ItemClickCallback) fragment);
                            recyclerView.setAdapter(adapter);
                        }else if(flag==2) {
                            List<DiscoverStoryListItem> listData = makeDiscoverAdapterModel(response);

                            final LiveStoryDerpAdapter adapter = new LiveStoryDerpAdapter(listData,fragment.getActivity());

                            adapter.setItemClickCallback((LiveStoryDerpAdapter.ItemClickCallback) fragment);
                            recyclerView.setAdapter(adapter);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(fragment.getActivity().getApplication(),"Failed in connecting server: ",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // setting post
                Map<String, String> params = new HashMap<String, String>();
                params.put("tech", tech+"");
                params.put("news", news+"");
                params.put("bussiness", bussiness+"");
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    //from json string to adapter model
    private List<DiscoverStoryListItem> makeDiscoverAdapterModel(String jsonString) {
        Gson gson = new Gson();
        List<DiscoverStoryListItem> StoryListItems = gson.fromJson(jsonString, new TypeToken<List<DiscoverStoryListItem>>() {
        }.getType());

        return StoryListItems;
    }
}
