package com.snapchat.team2.snapchat.fragement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.snapchat.team2.snapchat.ListAdapterDataModel.DiscoverStoryListItem;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customAdapter.DiscoverStoryDerpAdapter;
import com.snapchat.team2.snapchat.dbHelper.DBManager;
import com.snapchat.team2.snapchat.dbService.UserDBService;
import com.snapchat.team2.snapchat.networkService.DiscoverDataService;

import java.util.ArrayList;
import java.util.List;

//import android.support.v7.app.AlertDialog;

/**
 * Created by bm on 2/09/2016.
 */

public class DiscoverFragment extends Fragment implements DiscoverStoryDerpAdapter.ItemClickCallback {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_STORY = "EXTRA_STORY";
    private static final String EXTRA_AUTHOR = "EXTRA_AUTHOR";
    private static final String EXTRA_IMAGE = "EXTRA_IMAGE";

    private static final String news_clicks = "0";
    private static final String tech_clicks = "0";
    private static final String buss_clicks = "0";


    private RecyclerView recyclerView;
    private DiscoverStoryDerpAdapter adapter;
    private ArrayList listData;

    private ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_discover,container,false);
        //listData = (ArrayList) StoryDerpData.getListData();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rec_list1);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //adapter = new DiscoverStoryDerpAdapter(StoryDerpData.getListData(),this.getActivity());
        String ip = (String) this.getActivity().getResources().getString(R.string.ip);
        RequestQueue mqueue = Volley.newRequestQueue(this.getActivity());
        DiscoverDataService mservice = new DiscoverDataService(mqueue);

        String requestURL1 = "discovery/recommend";
        mservice.getDiscover(this,requestURL1,recyclerView,3,ip);

        return rootView;

    }

    @Override
    public void onItemClick(List<DiscoverStoryListItem> listData, int adapterPosition) {

        String user_id;
        SharedPreferences shared = this.getActivity().getSharedPreferences("snapchat_user", this.getActivity().MODE_PRIVATE);
        user_id=shared.getString("user_id", null);

        UserDBService userDBService = new UserDBService(DBManager.getInstance(this.getActivity()));
        String[] clicks = userDBService.getClicks(user_id);

        String nclicks = String.valueOf((Integer.parseInt(clicks[0])));
        String tclicks = String.valueOf((Integer.parseInt(clicks[1])));
        String bclicks = String.valueOf((Integer.parseInt(clicks[2])));

        DiscoverStoryListItem item = listData.get(adapterPosition);

        System.out.println("++++++++++++++++++++++++++++++++++/////////////////////////");
        System.out.println(clicks[0]);
        System.out.println(clicks[1]);
        System.out.println(clicks[2]);
        System.out.println("categoryId-categoryId");
        System.out.println(item.getCategoryId());
        String categoryId = item.getCategoryId();
        System.out.println("categoryId-categoryId");

        //1 tech 2 news 3 buss
        if(Integer.parseInt(categoryId) == 1){
            nclicks = String.valueOf((Integer.parseInt(clicks[1])+ 1));
        }else if(Integer.parseInt(categoryId) == 2){
            tclicks = String.valueOf((Integer.parseInt(clicks[0]) + 1));
        }else if(Integer.parseInt(categoryId) == 3){
            bclicks = String.valueOf((Integer.parseInt(clicks[2])+ 1));
        }

        userDBService.setClicks(nclicks,tclicks,bclicks,user_id);

        Intent i = new Intent(rootView.getContext(), StoryDetail.class);
        Bundle extras = new Bundle();
        extras.putString(EXTRA_AUTHOR, item.getTitle());
        extras.putString(EXTRA_IMAGE,item.getImage());
        extras.putString(EXTRA_STORY, item.getText());
        i.putExtra(BUNDLE_EXTRAS, extras);
        startActivity(i);
    }

    @Override
    public void onItemLongClick(int p) {

    }
}
