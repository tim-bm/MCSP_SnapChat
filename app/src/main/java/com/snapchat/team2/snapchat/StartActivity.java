package com.snapchat.team2.snapchat;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.snapchat.team2.snapchat.Tools.ActManager;

public class StartActivity extends Activity {
    private Button login_button;
    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActManager.getAppManager().addActivity(this);
        ActManager.getAppManager().finishActivity(MainActivity.class);

        setContentView(R.layout.activity_start);
        initViews();
        //forbid the back button
        addListeners();
    }

    private void initViews(){
        login_button = (Button)findViewById(R.id.button_login);
        register_button=(Button)findViewById(R.id.button_register);
    }
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {

            ActManager.getAppManager().AppExit(this);
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

    private void addListeners(){
        login_button .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,LoginActivity.class));
            }
        });
    }


}
