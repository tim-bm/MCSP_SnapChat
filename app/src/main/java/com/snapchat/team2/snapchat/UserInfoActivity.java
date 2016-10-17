package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.snapchat.team2.snapchat.Tools.ActManager;

public class UserInfoActivity extends Activity {
    private AlertDialog.Builder builder;
    private ImageButton sign_out=null;
    private SharedPreferences sharedPreferences = null;
    private View add_freind = null;

    private View myFriends = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        sharedPreferences = getApplicationContext().getSharedPreferences("snapchat_user", MODE_PRIVATE);
        ActManager.getAppManager().addActivity(this);
        initViews();
        addListeners();
    }
    private void initViews(){
        sign_out = (ImageButton)findViewById(R.id.sign_out);
        add_freind =(View)findViewById(R.id.add_friends);
        myFriends = (View)findViewById(R.id.my_friends);

    }
    private void addListeners(){
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("登出");
                showSimpleDialog(v);
            }
        });
        add_freind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserInfoActivity.this,AddFriendActivity.class));
            }
        });

        myFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserInfoActivity.this,CreateNewChatActivity.class));
            }
        });
    }

    private void showSimpleDialog(View view) {
        builder=new AlertDialog.Builder(this);
        builder.setMessage("Sign out for sure?");
        builder.setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getApplicationContext(),"sign out",Toast.LENGTH_SHORT).show();
                signOut();
            }
        });
        builder.setNegativeButton("stay here", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getApplicationContext(),"stay here", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private void signOut(){
        sharedPreferences.edit().clear().commit();
        finish();
    }
}
