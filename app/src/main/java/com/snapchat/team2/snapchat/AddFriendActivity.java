package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AddFriendActivity extends Activity {

    private ImageButton back;
    private View from_username;
    private View from_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initViews();
        addListeners();

    }
    private void initViews(){
        back = (ImageButton)findViewById(R.id.add_friend_back);
        from_username = (View)findViewById(R.id.add_by_username);
        from_contact = (View)findViewById(R.id.add_from_contact);
    }

    private void addListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        from_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddFriendActivity.this,AddByUsernameActivity.class));
            }
        });
        from_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddFriendActivity.this,AddUserFromContacts.class));
            }
        });
    }
}
