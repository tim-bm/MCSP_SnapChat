package com.snapchat.team2.snapchat.networkService;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.snapchat.team2.snapchat.ListAdapterDataModel.Friend;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customAdapter.ChatFriendListAdapter;
import com.snapchat.team2.snapchat.dataJsonModel.userModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by xu on 2016/10/5.
 */
public class UserDataService {


    private static final String requestURL_base = "http://10.0.0.120:8001/snapchat_server/public/user/friends/";
    private String requestURL = null;

    private RequestQueue requestQueue;
    private String result;
    private String user_id;

    public UserDataService(RequestQueue requestQueue, String user_id) {

        this.requestQueue = requestQueue;
        this.user_id = user_id;
        this.requestURL = requestURL_base + user_id;
    }

    //set the display view as parameters
    public void getFriends(final Activity activity, final ListView listView) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //deal with data
                        List<Friend> friendList = makeFriendsAdapterModel(response);
                        listView.setAdapter(new ChatFriendListAdapter(activity,friendList));
                        Toast.makeText(activity.getApplication(), "Connect Server successfully: " + response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplication(), "Failed in connecting server: ", Toast.LENGTH_LONG).show();
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

    private List<Friend> makeFriendsAdapterModel(String jsonString) {
        Gson gson = new Gson();
        List<Friend> friends = new ArrayList<Friend>();
        List<userModel> users = gson.fromJson(jsonString, new TypeToken<List<userModel>>() {
        }.getType());
        // users sort by initial letter
        Collections.sort(users, new ComparatorUser());
        //construct firends list for adapters
        for(int i=0;i<users.size();i++){
            System.out.println("name is " + users.get(i).getName());
            if(i==0){
                Friend friend = new Friend(Character.toString(users.get(i).getName().charAt(0)),users.get(i).getName(),1,users.get(i).getId());
                friends.add(friend);
                continue;
            }
            if(Character.toString(users.get(i).getName().charAt(0)).equals(Character.toString(users.get(i-1).getName().charAt(0)))){
                Friend friend = new Friend(Character.toString(users.get(i).getName().charAt(0)),users.get(i).getName(),2,users.get(i).getId());
                friends.add(friend);
            }else{
                Friend friend = new Friend(Character.toString(users.get(i).getName().charAt(0)),users.get(i).getName(),1,users.get(i).getId());
                friends.add(friend);
            }
        }
        return friends;
    }

    private class ComparatorUser implements Comparator {
        public int compare(Object arg0, Object arg1) {
            userModel user0 = (userModel) arg0;
            userModel user1 = (userModel) arg1;
            return Character.toString(user0.getName().charAt(0)).compareTo(Character.toString(user1.getName().charAt(0)));

        }
    }
}