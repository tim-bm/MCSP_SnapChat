package com.snapchat.team2.snapchat.networkService;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import com.snapchat.team2.snapchat.ListAdapterDataModel.ChatMessage;
import com.snapchat.team2.snapchat.ListAdapterDataModel.Friend;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customAdapter.ChatFriendListAdapter;
import com.snapchat.team2.snapchat.customAdapter.ChatListAdapter;
import com.snapchat.team2.snapchat.dataJsonModel.GetChatResonseModel;
import com.snapchat.team2.snapchat.dataJsonModel.userModel;
import com.snapchat.team2.snapchat.dbHelper.DBManager;
import com.snapchat.team2.snapchat.dbModel.User;
import com.snapchat.team2.snapchat.dbService.UserDBService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xu on 2016/10/5.
 */
public class UserDataService {


    private String requestURL_base = null;
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
    public void getFriends(final Activity activity, final ListView listView, final EditText search_view) {
        this.requestURL_base = activity.getResources().getString(R.string.serverAddress);
        requestURL=requestURL_base+"user/friends/"+user_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //deal with data
                        List<Friend> friendList = makeFriendsAdapterModel(response);
                        final ChatFriendListAdapter adapter = new ChatFriendListAdapter(activity,friendList);
                        listView.setAdapter(adapter);
                        search_view.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                adapter.getFilter().filter(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                        //makeSearchable(friendList,search_view,listView,activity,adapter);

                        Toast.makeText(activity.getApplication(), "Connect Server successfully: " + user_id, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                UserDBService userDBService = new UserDBService(DBManager.getInstance(activity));
                //current user is user_id, get all of his freinds from Local database

                List<User> friends_from_db = userDBService.getFriendsByUserId(user_id);

                List<Friend> friendList =makeFriendsAdapterModel(friends_from_db);

                final ChatFriendListAdapter adapter = new ChatFriendListAdapter(activity,friendList);
                listView.setAdapter(adapter);
                //makeSearchable(list,search_view,listView,activity,adapter);
                search_view.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                Toast.makeText(activity.getApplication(), "Failed in connecting server: show local data", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);

    }

    public void setReceiverName(Activity activity, final TextView v, String receiverId){
        this.requestURL_base = activity.getResources().getString(R.string.serverAddress);
        requestURL=requestURL_base+"user/"+receiverId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //deal with data
                userModel userFromJson = getUserModelFromJson(response);
                String user_name = userFromJson.getName();
                v.setText(user_name);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                v.setText("bot");
            }
        });
        requestQueue.add(stringRequest);

    }

    public void sendTextMessage(final Activity activity,final ListView listview,final ChatListAdapter cla, final String receiver_id, final String content){
        this.requestURL_base = activity.getResources().getString(R.string.serverAddress);
        requestURL=requestURL_base+"chat/send";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //deal with response
                Gson gson = new Gson();
                Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
                Map<String,String> result_map = gson.fromJson(response, stringStringMap);
                String status = result_map.get("status");
                String info = result_map.get("info");

                if(status.equals("1")){
                    ChatMessage chatMessage = new ChatMessage(content,1);
                    cla.addone(chatMessage);
                    listview.setSelection(listview.getAdapter().getCount()-1);
                }
                else{
                    Toast.makeText(activity.getApplication(),"send messgae fail",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ChatMessage chatMessage = new ChatMessage("network connect fail!",1);
                cla.addone(chatMessage);
                listview.setSelection(listview.getAdapter().getCount()-1);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // setting post
                Map<String, String> params = new HashMap<String, String>();
                params.put("from",user_id);
                params.put("to", receiver_id);
                params.put("message", content);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    //from json string to adapter model
    private List<Friend> makeFriendsAdapterModel(String jsonString) {
        Gson gson = new Gson();
        List<Friend> friends = new ArrayList<Friend>();
        List<userModel> users = gson.fromJson(jsonString, new TypeToken<List<userModel>>() {
        }.getType());
        // users sort by initial letter
        Collections.sort(users, new ComparatorUserFromJson());
        //construct firends list for adapters
        for(int i=0;i<users.size();i++){

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



    //from a list of db_model to adpater model
    private List<Friend> makeFriendsAdapterModel(List<User> friends_from_db){
        List<Friend> friends = new ArrayList<Friend>();
        Collections.sort(friends_from_db,new ComparatorUserFromDB());

        for(int i=0;i<friends_from_db.size();i++){

            if(i==0){
                Friend friend = new Friend(Character.toString(friends_from_db.get(i).getName().charAt(0)),friends_from_db.get(i).getName(),1,friends_from_db.get(i).getUserId()+"");
                friends.add(friend);
                continue;
            }
            if(Character.toString(friends_from_db.get(i).getName().charAt(0)).equals(Character.toString(friends_from_db.get(i-1).getName().charAt(0)))){
                Friend friend = new Friend(Character.toString(friends_from_db.get(i).getName().charAt(0)),friends_from_db.get(i).getName(),2,friends_from_db.get(i).getUserId()+"");
                friends.add(friend);
            }else{
                Friend friend = new Friend(Character.toString(friends_from_db.get(i).getName().charAt(0)),friends_from_db.get(i).getName(),1,friends_from_db.get(i).getUserId()+"");
                friends.add(friend);
            }
        }
        return friends;
    }

    private userModel getUserModelFromJson(String j_string){
        List<userModel> users = new Gson().fromJson(j_string,new TypeToken<List<userModel>>() {
        }.getType());
        userModel u =users.get(0);
        return u;
    }

    public void getChatToUser(Activity activity, final Message msg,final String opponeng_id){

        this.requestURL_base = activity.getResources().getString(R.string.serverAddress);
        requestURL=requestURL_base+"chatToUser2/"+opponeng_id+"/"+user_id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //deal with data
                System.out.println("返回的报文是: "+response);
                if(response.startsWith("[")){
                    Bundle b = new Bundle();
                    b.putBoolean("network",true);
                    b.putBoolean("new",true);
                    b.putString("data",response);
                    msg.setData(b);
                    msg.sendToTarget();
                    return;
                }else if(response.startsWith("{")){
                    /*List<GetChatResonseModel> results = new Gson().fromJson(response , new TypeToken<List<GetChatResonseModel>>() {
                    }.getType());*/
                    Bundle b = new Bundle();
                    b.putBoolean("network",true);
                    b.putBoolean("new",false);

                    msg.setData(b);
                    msg.sendToTarget();
                    return;
                }
                else{
                    System.out.println("错误");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Bundle b = new Bundle();
                b.putBoolean("network",false);
                msg.setData(b);
                msg.sendToTarget();
            }
        });
        requestQueue.add(stringRequest);

    }

    private class ComparatorUserFromJson implements Comparator {
        public int compare(Object arg0, Object arg1) {
            userModel user0 = (userModel) arg0;
            userModel user1 = (userModel) arg1;
            return Character.toString(user0.getName().charAt(0)).compareTo(Character.toString(user1.getName().charAt(0)));

        }
    }

    private class ComparatorUserFromDB implements Comparator{

        @Override
        public int compare(Object o1, Object o2) {
            User user0 = (User) o1;
            User user1 = (User) o2;
            return  Character.toString(user0.getName().charAt(0)).compareTo(Character.toString(user1.getName().charAt(0)));
        }
    }



}