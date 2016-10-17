package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snapchat.team2.snapchat.dataJsonModel.userModel;
import com.snapchat.team2.snapchat.networkService.UserDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddByUsernameActivity extends Activity {
    private String user_id = null;
    private EditText edt = null;
    private ListView showUser = null;
    private List<userModel> user_friends = new ArrayList<userModel>();
    private List<userModel> allfriends = new ArrayList<userModel>();
    private SimpleAdapter adapter = null;

    private final Handler handler = new Handler(){
        public void handleMessage(Message msg){
            Bundle bundle=msg.getData();
            if(bundle.getBoolean("network")){
                String resultJson = bundle.getString("resultJson");
                Toast.makeText(AddByUsernameActivity.this,resultJson,Toast.LENGTH_SHORT).show();
                allfriends = new Gson().fromJson( resultJson, new TypeToken<List<userModel>>() {}.getType());
                setAdapter();
            }
            else{
                Toast.makeText(AddByUsernameActivity.this,"net work fail",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private final Handler handler2 = new Handler(){
        public void handleMessage(Message msg){
            Bundle bundle=msg.getData();
            if(bundle.getBoolean("network")){
                String resultJson = bundle.getString("resultJson");
                Toast.makeText(AddByUsernameActivity.this,resultJson,Toast.LENGTH_SHORT).show();
                user_friends = new Gson().fromJson( resultJson, new TypeToken<List<userModel>>() {}.getType());
            }
            else{
                Toast.makeText(AddByUsernameActivity.this,"net work fail",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Message message ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_by_username);
        SharedPreferences shared = getSharedPreferences("snapchat_user", MODE_PRIVATE);
        this.user_id=shared.getString("user_id", null);
        getFriends();
        //get all of the user's friend
        message=Message.obtain();
        message.setTarget(handler);
        initViews();
        addListeners();

    }

    private void initViews(){
        edt = (EditText) findViewById(R.id.add_by_user_search_friend);
        showUser = (ListView)findViewById(R.id.match_keyword_users_list);
    }


    private void addListeners(){
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    showUser.setAdapter(null);
                    return;
                }
                searchUser(s.toString());
            }
        });
        showUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userModel addFriend = allfriends.get(position);
                if(addFriend.getId().equals(user_id)){
                    Toast.makeText(AddByUsernameActivity.this,"you can not add yourself! ",Toast.LENGTH_SHORT).show();
                    return;
                }
                for(userModel freind: user_friends){

                    if(addFriend.getId().equals(freind.getId())){
                        Toast.makeText(AddByUsernameActivity.this,addFriend.getName()+" is aleady your friend",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                //add friend
                addFriend(addFriend.getName());
                getFriends();

            }
        });

    }

    private void searchUser(String keyword){
        RequestQueue rq = Volley.newRequestQueue(this);
        UserDataService uds = new UserDataService(rq,user_id);
        Message m = new Message();
        m.copyFrom(message);
        m.setTarget(handler);
        uds.getSearchUser(this,keyword,m);
    }
    private void setAdapter(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(userModel u: this.allfriends){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name",u.getName());
            list.add(map);
        }
        this.showUser.setAdapter(new SimpleAdapter(this,list,R.layout.match_keyword_users_list_item,
                new String[]{"name"},new int[]{R.id.add_friends_showfriend_name}));
    }
    private void getFriends(){
        RequestQueue rq = Volley.newRequestQueue(this);
        UserDataService uds = new UserDataService(rq,user_id);
        Bundle b = new Bundle();
        Message friendListMsg = new Message();
        friendListMsg.setTarget(handler2);
        uds.getFriends(this,friendListMsg);
        if(b.getBoolean("network")){
            String result_json = b.getString("resultJson");
            List<userModel> friends = new Gson().fromJson(result_json, new TypeToken<List<userModel>>() {}.getType());
            this.user_friends = friends;
        }
        else{
            System.out.println("拉取好友信息失败");
        }
    }

    private void addFriend(String username){
        RequestQueue rq = Volley.newRequestQueue(this);
        UserDataService uds = new UserDataService(rq,user_id);
        uds.addFriend(this,username);
    }
}
