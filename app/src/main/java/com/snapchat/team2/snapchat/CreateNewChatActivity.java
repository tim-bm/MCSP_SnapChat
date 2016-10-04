package com.snapchat.team2.snapchat;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.snapchat.team2.snapchat.ListAdapterDataModel.Friend;
import com.snapchat.team2.snapchat.customAdapter.ChatFriendListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewChatActivity extends Activity {

    private TextView chatFriendSearch;
    private ListView listView;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_friends);
        initialViews();
        initAdapter();
        initListeners();

    }

    private void initialViews(){
        listView=(ListView) findViewById(R.id.chat_friend_list);
        chatFriendSearch=(TextView)findViewById(R.id.search_friend);
        back = (ImageButton)findViewById(R.id.chat_friend_back);

    }

    private void initListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(CreateNewChatActivity.this,"你单击的是第"+(position+1)+"条数据",Toast.LENGTH_SHORT).show();
                //获得receiver 的id
                Friend f = (Friend) listView.getItemAtPosition(position);
                String user_id = f.getUser_id();


                //to send activity
                Intent intent = new Intent(CreateNewChatActivity.this,SendMessageActivity.class);
                intent.putExtra("receiver_id",user_id);
                startActivity(intent);
            }
        });
    }
    private void initAdapter(){
        ChatFriendListAdapter adapter = new ChatFriendListAdapter(getApplicationContext(),getData());
        listView.setAdapter(adapter);
    }

    private List<Friend> getData(){

        List<Friend> list = new ArrayList<Friend>();
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
        list.add(new Friend("B","bsrgsddg",2,"4etef3r43t3"));
        return list;
    }
}
