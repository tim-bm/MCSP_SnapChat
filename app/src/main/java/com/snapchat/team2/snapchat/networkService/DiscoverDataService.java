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
import com.snapchat.team2.snapchat.ListAdapterDataModel.DiscoverStoryListItem;
import com.snapchat.team2.snapchat.ListAdapterDataModel.MemoryStoryListItem;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customAdapter.DiscoverStoryDerpAdapter;
import com.snapchat.team2.snapchat.customAdapter.SubStoryDerpAdapter;
import com.snapchat.team2.snapchat.dbHelper.DBManager;
import com.snapchat.team2.snapchat.dbService.UserDBService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kun on 10/11/2016.
 */
public class DiscoverDataService {

    private String requestURL = null;

    private RequestQueue requestQueue;


    public DiscoverDataService(RequestQueue requestQueue){
        this.requestQueue=requestQueue;
    }

    //set the display view as parameters
    public void getDiscover(final Fragment fragment, final String requestURL, final RecyclerView recyclerView ,final int flag, final String ip){

        String requestURL_base = fragment.getActivity().getResources().getString(R.string.serverAddress) + requestURL;

        String user_id;
        SharedPreferences shared = fragment.getActivity().getSharedPreferences("snapchat_user", fragment.getActivity().MODE_PRIVATE);
        user_id=shared.getString("user_id", null);

        UserDBService userDBService = new UserDBService(DBManager.getInstance(fragment.getActivity()));
        String[] clicks = userDBService.getClicks(user_id);


        final String news=clicks[0];
        final String tech =clicks[1];
        final String bussiness=clicks[2];

//        final String news="2";
//        final String tech ="2";
//        final String bussiness="2";


        System.out.println();
        System.out.println(clicks[0]);
        System.out.println(clicks[1]);
        System.out.println(clicks[2]);


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
                            final SubStoryDerpAdapter adapter = new SubStoryDerpAdapter(listData,fragment.getActivity(),ip);

                            adapter.setItemClickCallback((SubStoryDerpAdapter.ItemClickCallback) fragment);
                            recyclerView.setAdapter(adapter);
//                        }else if(flag==2) {
//                            List<DiscoverStoryListItem> listData = makeDiscoverAdapterModel(response);
//
//                            final LiveStoryDerpAdapter adapter = new LiveStoryDerpAdapter(listData,fragment.getActivity(),ip);
//
//                            adapter.setItemClickCallback((LiveStoryDerpAdapter.ItemClickCallback) fragment);
//                            recyclerView.setAdapter(adapter);
                        }else if(flag==3) {
                            List<DiscoverStoryListItem> listData = makeDiscoverAdapterModel(response);

                            final DiscoverStoryDerpAdapter adapter = new DiscoverStoryDerpAdapter(listData,fragment.getActivity(),ip);

                            adapter.setItemClickCallback((DiscoverStoryDerpAdapter.ItemClickCallback) fragment);
                            recyclerView.setAdapter(adapter);

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(fragment.getActivity().getApplication(),"Failed in connecting server: ",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // setting post
                Map<String, String> params = new HashMap<String, String>();
                params.put("tech", tech);
                params.put("news", news);
                params.put("bussiness", bussiness);
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

    private List<MemoryStoryListItem> makeMemoryAdapterModel(String jsonString) {
        Gson gson = new Gson();
        List<MemoryStoryListItem> StoryListItems = gson.fromJson(jsonString, new TypeToken<List<MemoryStoryListItem>>() {
        }.getType());

        return StoryListItems;
    }
}
