package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.snapchat.team2.snapchat.Tools.ActManager;

public class UserInfoActivity extends Activity {
    private AlertDialog.Builder builder;
    private ImageButton sign_out=null;
    private SharedPreferences sharedPreferences = null;

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
    }
    private void addListeners(){
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("登出");
                showSimpleDialog(v);
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
