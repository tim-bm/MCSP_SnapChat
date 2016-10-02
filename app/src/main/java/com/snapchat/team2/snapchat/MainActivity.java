package com.snapchat.team2.snapchat;

import android.app.Activity;
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
import com.snapchat.team2.snapchat.dbHelper.DBManager;
import com.snapchat.team2.snapchat.dbModel.User;
import com.snapchat.team2.snapchat.dbService.UserDBService;
import com.snapchat.team2.snapchat.fragement.CameraFragment;
import com.snapchat.team2.snapchat.fragement.ChatFragment;
import com.snapchat.team2.snapchat.fragement.DiscoverFragment;
import com.snapchat.team2.snapchat.fragement.StroyFragment;
import com.snapchat.team2.snapchat.networkService.MsgFromIndex;

public class MainActivity extends FragmentActivity {

    private static final int NUM_FRAMES=4;
    private ViewPager mainPage;
    private PagerAdapter pagerAdapter;

    private ImageView chatSwitch;
    private ImageView cameraSwitch;
    private ImageView storySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //use default pageAdapter
        mainPage=(ViewPager) findViewById(R.id.pager);
        pagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mainPage.setAdapter(pagerAdapter);

        //register button
        chatSwitch=(ImageView)this.findViewById(R.id.chat_switch_fragment);
        cameraSwitch=(ImageView)this.findViewById(R.id.camera_switch_fragment);
        storySwitch=(ImageView)this.findViewById(R.id.story_switch_fragment);

        setListenerOnButton();

        //test and usage example for DB
        UserDBService userDBService=new UserDBService(DBManager.getInstance(MainActivity.this));
        User user=userDBService.getUserByUserEmail("admin@snapchat.com");
       // Toast.makeText(getApplication(),"Database open: "+user.getName(),Toast.LENGTH_LONG).show();
        //test for connecting server

        RequestQueue queue = Volley.newRequestQueue(this);
        MsgFromIndex msgFromIndex= new MsgFromIndex(queue);
        msgFromIndex.getMsg(this);
    }


    private void setListenerOnButton(){
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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
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

    //github upload test
}
