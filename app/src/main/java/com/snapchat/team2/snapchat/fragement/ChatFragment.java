package com.snapchat.team2.snapchat.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customView.RefreshListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by bm on 1/09/2016.
 */

public class ChatFragment extends Fragment {
    private ViewGroup rootView;
    private RefreshListView refreshListView;
    private ImageButton button_search;
    private Button button_chat;
    private ImageButton add_friend;
    private SearchView search_view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        initialView(inflater,container,savedInstanceState);
        return rootView;
    }

    private List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "G1");
        map.put("info", "google 1");
        map.put("img", R.drawable.file);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "G2");
        map.put("info", "google 2");
        map.put("img", R.drawable.file);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "G3");
        map.put("info", "google 3");
        map.put("img", R.drawable.file);
        list.add(map);

        return list;

    }

    private void initialView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //initial widgets
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_chat,container,false);
        refreshListView= (RefreshListView) rootView.findViewById(R.id.refresh_list);
        button_search=(ImageButton) rootView.findViewById(R.id.search_button);
        button_chat=(Button)rootView.findViewById(R.id.button_chat);
        add_friend=(ImageButton)rootView.findViewById(R.id.addFriend);
        search_view=(SearchView)rootView.findViewById(R.id.search_view);

        initialSetAdapter();
        initialSetListeners();
    }


    private void initialSetListeners(){
        System.out.println("设置监听器");
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show the serch textview and hide other
                button_chat.setVisibility(View.GONE);
                add_friend.setVisibility(View.GONE);
                button_search.setVisibility(View.GONE);
                search_view.setVisibility(View.VISIBLE);

                search_view.setQueryHint("search the snap");
                int search_plate_id=search_view.getContext().getResources().getIdentifier("android:id/search_plate",null,null);
                System.out.println("searchPlateId is"+ search_plate_id);



                if (search_view != null) {
                    try {
                        //--拿到字节码
                        Class<?> argClass = search_view.getClass();
                        //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                        Field ownField = argClass.getDeclaredField("mSearchPlate");
                        //--暴力反射,只有暴力反射才能拿到私有属性
                        ownField.setAccessible(true);
                        View mView = (View) ownField.get(search_view);
                        //--设置背景

                        mView.setBackgroundColor(getResources().getColor(R.color.colorChatlistHeaderRelease));    } catch (Exception e) {        e.printStackTrace();    }}
            }
        });
    }

    private void initialSetAdapter(){
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),getData(),R.layout.chat_list_item,
                new String[]{"name","info","img"},new int[]{R.id.chat_name,R.id.chat_info,R.id.chat_item_img});

        refreshListView.setAdapter(adapter);
    }
}
