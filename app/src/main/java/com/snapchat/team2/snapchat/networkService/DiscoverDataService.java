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
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customAdapter.DiscoverStoryDerpAdapter;
import com.snapchat.team2.snapchat.customAdapter.SubStoryDerpAdapter;
import com.snapchat.team2.snapchat.dbHelper.DBManager;
import com.snapchat.team2.snapchat.dbService.UserDBService;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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


        String tech=null;
        String news=null;
        String bussiness=null;

        String user_id;
        SharedPreferences shared = fragment.getActivity().getSharedPreferences("snapchat_user", fragment.getActivity().MODE_PRIVATE);
        user_id=shared.getString("user_id", null);

        UserDBService userDBService = new UserDBService(DBManager.getInstance(fragment.getActivity()));
        String[] clicks = userDBService.getClicks(user_id);

        int techDig = (Integer.parseInt(clicks[0]));
        int newsDig =(Integer.parseInt(clicks[1]));
        int bussinessDig = (Integer.parseInt(clicks[2]));


        //sorting
        Map<String, Integer> unsortMap = new HashMap<String, Integer>();

        unsortMap.put("tech", techDig);
        unsortMap.put("news", newsDig);
        unsortMap.put("bussiness", bussinessDig);

        Map<String, Integer> sortedMap = sortByValue(unsortMap);

        String min = (String) sortedMap.keySet().toArray()[0];//min
        String med = (String) sortedMap.keySet().toArray()[1];
        String max = (String) sortedMap.keySet().toArray()[2];//max



        if(min.equals("news")){
            news = "1";
        }else if(med.equals("news")){
            news = "2";
        }else if(max.equals("news")){
            news = "3";
        }

        if(min.equals("tech")){
            tech = "1";
        }else if(med.equals("tech")){
            tech = "2";
        }else if(max.equals("tech")){
            tech = "3";
        }

        if(min.equals("bussiness")){
            bussiness = "1";
        }else if(med.equals("bussiness")){
            bussiness = "2";
        }else if(max.equals("bussiness")){
            bussiness = "3";
        }


        final String tech_value = tech;
        final String news_value = news;
        final String bussiness_value = bussiness;




        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL_base,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (flag==1){
                            List<DiscoverStoryListItem> listData = makeDiscoverAdapterModel(response);

                            final SubStoryDerpAdapter adapter = new SubStoryDerpAdapter(listData,fragment.getActivity(),ip);

                            adapter.setItemClickCallback((SubStoryDerpAdapter.ItemClickCallback) fragment);
                            recyclerView.setAdapter(adapter);

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



    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
