package com.snapchat.team2.snapchat;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewChatActivity extends Activity {
    private TextView chatFriendSearch;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_friends);

        initialViews();
        initAdapter();

    }

    private void initialViews(){
        listView=(ListView) findViewById(R.id.chat_friend_list);
    }

    private void initListeners(){

    }

    private void initAdapter(){
        SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.chat_friend_list_item,
                new String[]{"initial","name"},new int[]{R.id.chat_friend_letter,R.id.chat_friend_name});

        listView.setAdapter(adapter);

    }


    private List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("initial", "A");
        map.put("name", "A_user_1");

        list.add(map);


        map.put("Initial", "A");
        map.put("name", "A_user_2");

        list.add(map);

        map.put("Initial", "A");
        map.put("name", "A_user_3");

        list.add(map);
        return list;

    }

}
