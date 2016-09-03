package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.snapchat.team2.snapchat.fragement.CameraFragment;
import com.snapchat.team2.snapchat.fragement.ChatFragment;
import com.snapchat.team2.snapchat.fragement.DiscoverFragment;
import com.snapchat.team2.snapchat.fragement.StroyFragment;

public class MainActivity extends FragmentActivity {

    private static final int NUM_FRAMES=4;
    private ViewPager mainPage;
    private PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //use default pageAdapter
        mainPage=(ViewPager) findViewById(R.id.pager);
        pagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mainPage.setAdapter(pagerAdapter);

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
}
