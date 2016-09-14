package com.snapchat.team2.snapchat.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.RefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bm on 1/09/2016.
 */

public class ChatFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.fragment_chat,container,false);
        RefreshListView refreshListView= (RefreshListView) rootView.findViewById(R.id.refresh_list);
        //refreshListView.removeAllViews();

        SimpleAdapter adapter = new SimpleAdapter(getActivity(),getData(),R.layout.chat_list_item,
                new String[]{"name","info","img"},new int[]{R.id.chat_name,R.id.chat_info,R.id.chat_item_img});

        refreshListView.setAdapter(adapter);


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
}
