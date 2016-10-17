package com.snapchat.team2.snapchat;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.snapchat.team2.snapchat.fragement.CameraGalleryFragment;
import com.snapchat.team2.snapchat.fragement.MemoryGalleryFragment;

public class MemoryActivity extends FragmentActivity {

    private ViewPager memoryPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);



        memoryPager =(ViewPager)findViewById(R.id.memory_pager);
        pagerAdapter=new MemoryPagerAdapter(getSupportFragmentManager());
        memoryPager.setAdapter(pagerAdapter);
        memoryPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            public void onPageSelected(int position) {
                // When swiping between pages, select the
                // corresponding tab.
                getActionBar().setSelectedNavigationItem(position);
            }
        });

        //set action bar
        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);

        android.app.ActionBar.TabListener tabListener = new android.app.ActionBar.TabListener() {
            @Override
            public void onTabSelected(android.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                memoryPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(android.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            }
            @Override
            public void onTabReselected(android.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };
        actionBar.addTab(actionBar.newTab().setText("From Meomry").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("From Camera roll").setTabListener(tabListener));
    }


    class MemoryPagerAdapter extends FragmentStatePagerAdapter{

        public MemoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MemoryGalleryFragment();
                case 1:
                    return new CameraGalleryFragment();
                default:
                    //error happens
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
