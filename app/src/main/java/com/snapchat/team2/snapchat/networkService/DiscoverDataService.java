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

import java.util.Arrays;
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

        String news=null;
        String tech=null;
        String bussiness=null;

        String user_id;
        SharedPreferences shared = fragment.getActivity().getSharedPreferences("snapchat_user", fragment.getActivity().MODE_PRIVATE);
        user_id=shared.getString("user_id", null);

        UserDBService userDBService = new UserDBService(DBManager.getInstance(fragment.getActivity()));
        String[] clicks = userDBService.getClicks(user_id);

        int newsDig = (Integer.parseInt(clicks[0]));
        int techDig =(Integer.parseInt(clicks[1]));
        int bussinessDig = (Integer.parseInt(clicks[2]));


        System.out.println("HereHereHereHereHereHereHereHere---Before");
        System.out.println(clicks[0]);
        System.out.println(clicks[1]);
        System.out.println(clicks[2]);

        int numb[] = {newsDig,techDig,bussinessDig};

        Arrays.sort(numb);

        if(newsDig==numb[0]){
            news = "1";
        }else if(techDig==numb[0]){
            tech = "1";
        }else{
            bussiness = "1";
        }


        if(newsDig==numb[1]){
            news = "2";
        }else if(techDig==numb[1]){
            tech = "2";
        }else{
            bussiness = "2";
        }

        if(newsDig==numb[2]){
            news = "3";
        }else if(techDig==numb[2]){
            tech = "3";
        }else{
            bussiness = "3";
        }

        System.out.println("HereHereHereHereHereHereHereHere---After");
        System.out.println(news);
        System.out.println(tech);
        System.out.println(bussiness);


//        final String news=clicks[0];
//        final String tech =clicks[1];
//        final String bussiness=clicks[2];

//        final String news="2";
//        final String tech ="2";
//        final String bussiness="2";

        final String tech_value = tech;
        final String news_value = news;
        final String bussiness_value = bussiness;




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
                params.put("tech", tech_value);
                params.put("news", news_value);
                params.put("bussiness", bussiness_value);
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
