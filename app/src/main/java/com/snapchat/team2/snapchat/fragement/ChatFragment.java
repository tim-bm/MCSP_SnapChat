package com.snapchat.team2.snapchat.fragement;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.snapchat.team2.snapchat.CreateNewChatActivity;
import com.snapchat.team2.snapchat.MainActivity;
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

    private SearchView search_view;
    private ImageButton newChat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        initialView(inflater,container,savedInstanceState);
        initialSetAdapter();
        initialSetListeners();
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

        search_view=(SearchView)rootView.findViewById(R.id.search_view);
        newChat=(ImageButton) rootView.findViewById(R.id.newChat);
    }


    private void initialSetListeners(){
        System.out.println("设置监听器");
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show the serch textview and hide other
                button_chat.setVisibility(View.GONE);
                newChat.setVisibility(View.GONE);
                button_search.setVisibility(View.GONE);
                search_view.setVisibility(View.VISIBLE);

                search_view.setQueryHint("search the snap");
                int search_plate_id=search_view.getContext().getResources().getIdentifier("android:id/search_plate",null,null);
                System.out.println("searchPlateId is"+ search_plate_id);
                search_view.setSubmitButtonEnabled(false);

                int id=search_view.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
                TextView t=(TextView) search_view.findViewById(id);

                int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
                ImageView magImage = (ImageView) search_view.findViewById(magId);
                magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

                System.out.println("text is "+t.getText());
                t.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus==false){
                            System.out.println("lost focus");
                            button_chat.setVisibility(View.VISIBLE);
                            newChat.setVisibility(View.VISIBLE);
                            button_search.setVisibility(View.VISIBLE);
                            search_view.setVisibility(View.GONE);
                        }
                    }
                });
                t.setTextColor(getResources().getColor(R.color.colorChatlistHeaderRelease));
                t.setHintTextColor(getResources().getColor(R.color.colorChatlistHeaderRelease));
                t.requestFocus();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(t,0);
            }
        });

        button_search.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_UP){
                    //关闭软键盘
                    System.out.println("clost the input");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(button_search.getWindowToken(), 0);
                }
                return false;
            }
        });

        newChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CreateNewChatActivity.class));
            }
        });
    }

    private void initialSetAdapter(){
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),getData(),R.layout.chat_list_item,
                new String[]{"name","info","img"},new int[]{R.id.chat_name,R.id.chat_info,R.id.chat_item_img});

        refreshListView.setAdapter(adapter);
    }


}
