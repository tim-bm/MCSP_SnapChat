package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.snapchat.team2.snapchat.Tools.ActManager;
import com.snapchat.team2.snapchat.dbHelper.DBManager;
import com.snapchat.team2.snapchat.dbModel.User;
import com.snapchat.team2.snapchat.dbService.UserDBService;
import com.snapchat.team2.snapchat.fragement.CameraFragment;
import com.snapchat.team2.snapchat.fragement.ChatFragment;
import com.snapchat.team2.snapchat.fragement.DiscoverFragment;
import com.snapchat.team2.snapchat.fragement.StroyFragment;
import com.snapchat.team2.snapchat.networkService.MsgFromIndex;

public class MainActivity extends FragmentActivity {

    private static final int NUM_FRAMES = 4;
    private ViewPager mainPage;
    private PagerAdapter pagerAdapter;
    private SharedPreferences sharedPreferences = null;
    private String user_id = null;
    private ImageView chatSwitch;
    private ImageView cameraSwitch;
    private ImageView storySwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getApplicationContext().getSharedPreferences("snapchat_user", MODE_PRIVATE);
        String user_id_from_login = null;
        user_id_from_login = getIntent().getStringExtra("user_id");
        //tell if the last activity is Login Activity
        if (user_id_from_login != null) {
            System.out.println("from log in activity");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_id", user_id_from_login);
            editor.commit();
            makeActivityAtBottom();
        } else {
            System.out.println("not from login activity");
        }
        //let log off
        //sharedPreferences.edit().clear().commit();

        if (isLogin()) {
            System.out.println("current user id is : " + sharedPreferences.getString("user_id", null));
            this.user_id = sharedPreferences.getString("user_id", null);
            makeActivityAtBottom();

        } else {
            System.out.println("no user id find ,need to login first ");
            //add this activity to the activity stack , so that user can close app in login activity
            makeActivityAtBottom();
            startActivity(new Intent(MainActivity.this, StartActivity.class));
        }

        //use default pageAdapter
        mainPage = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mainPage.setAdapter(pagerAdapter);

        //register button
        chatSwitch = (ImageView) this.findViewById(R.id.chat_switch_fragment);
        cameraSwitch = (ImageView) this.findViewById(R.id.camera_switch_fragment);
        storySwitch = (ImageView) this.findViewById(R.id.story_switch_fragment);

        setListenerOnButton();

        //test and usage example for DB
        //UserDBService userDBService = new UserDBService(DBManager.getInstance(MainActivity.this));
        //User user = userDBService.getUserByUserEmail("admin@snapchat.com");
        // Toast.makeText(getApplication(),"Database open: "+user.getName(),Toast.LENGTH_LONG).show();
        //test for connecting server
        //RequestQueue queue = Volley.newRequestQueue(this);
        //MsgFromIndex msgFromIndex = new MsgFromIndex(queue);
        //msgFromIndex.getMsg(this);

    }


    private void setListenerOnButton() {
        chatSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPage.setCurrentItem(0);
            }
        });
        cameraSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPage.setCurrentItem(1);
            }
        });

        storySwitch.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mainPage.setCurrentItem(2);
        }
    });
    }

    public void disableTabButtons() {
        chatSwitch.setVisibility(View.INVISIBLE);
        cameraSwitch.setVisibility(View.INVISIBLE);
        storySwitch.setVisibility(View.INVISIBLE);
    }

    public void enableTabButtons() {
        chatSwitch.setVisibility(View.VISIBLE);
        cameraSwitch.setVisibility(View.VISIBLE);
        storySwitch.setVisibility(View.VISIBLE);
    }




    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ChatFragment();
                case 1:
                    return new CameraFragment();
                case 2:
                    return new StroyFragment();
                case 3:
                    return new DiscoverFragment();
                default:
                    //error happens
                    return null;
            }

        }

        @Override
        public int getCount() {
            return NUM_FRAMES;
        }
    }

    private boolean isLogin() {
        String id = sharedPreferences.getString("user_id", null);
        if (id != null) {
            return true;
        }
        return false;
    }

    private void makeActivityAtBottom(){

        ActManager.getAppManager().finishActivity(LoginActivity.class);
        ActManager.getAppManager().finishActivity(StartActivity.class);
        ActManager.getAppManager().finishActivity(RegisterActivity.class);
        ActManager.getAppManager().finishActivity(RegisterActivity2.class);
        ActManager.getAppManager().addActivity(this);

    }

    @Override
    protected void onDestroy(){

        //ActManager.getAppManager().AppExit(this);
        super.onDestroy();
    }

    //github upload test


}
