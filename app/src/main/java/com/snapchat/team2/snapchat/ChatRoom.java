package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.os.Bundle;

public class ChatRoom extends Activity {
    private String receiver_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom);

        //get data from database
        //get the receivers information from the user id of the receriver
        receiver_id = getIntent().getStringExtra("receiver_id");

        //get currennt user from session
        System.out.println("receiver_id is"+receiver_id);
        initViews();

    }

    private void initViews(){

    }
}
