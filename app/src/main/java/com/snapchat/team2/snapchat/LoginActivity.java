package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class LoginActivity extends Activity {
    private String user_id = null;
    private SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getApplicationContext().getSharedPreferences("snapchat_user",MODE_PRIVATE);

        //let it login now
       /* Editor editor =sharedPreferences.edit();
        editor.putString("user_id","1");
        editor.commit();*/
        
    }

}
