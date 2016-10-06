package com.snapchat.team2.snapchat.networkService;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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
import com.snapchat.team2.snapchat.dbHelper.DBManager;
import com.snapchat.team2.snapchat.dbModel.User;
import com.snapchat.team2.snapchat.dbService.UserDBService;

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
    public void getFriends(final Activity activity, final ListView listView, final EditText search_view) {

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

                        Toast.makeText(activity.getApplication(), "Connect Server successfully: " + response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                UserDBService userDBService = new UserDBService(DBManager.getInstance(activity));
                //current user is user_id, get all of his freinds from Local database
                System.out.println("data service 查询数据库");
                List<User> friends_from_db = userDBService.getFriendsByUserId(user_id);

                System.out.println("返回数据");
                for (User u:friends_from_db){
                    System.out.println("name is"+u.getName());
                }
                List<Friend> friendList =makeFriendsAdapterModel(friends_from_db);



                /*List<Friend> list = new ArrayList<Friend>();
                list.add(new Friend("A","Afsdfs",1,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("A","asrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("B","bsrgsddg",1,"4etef3r43t3"));
                list.add(new Friend("B","bsrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("B","bsrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("B","bsrgsddg",2,"4etef3r43t3"));
                list.add(new Friend("B","bsrgsddg",2,"4etef3r43t3"));*/
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
                Toast.makeText(activity.getApplication(), "Failed in connecting server: show fake data ", Toast.LENGTH_LONG).show();
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
    //from a list of db_model to adpater model
    private List<Friend> makeFriendsAdapterModel(List<User> friends_from_db){
        List<Friend> friends = new ArrayList<Friend>();
        Collections.sort(friends_from_db,new ComparatorUserFromDB());

        for(int i=0;i<friends_from_db.size();i++){
            System.out.println("name is " + friends_from_db.get(i).getName());
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