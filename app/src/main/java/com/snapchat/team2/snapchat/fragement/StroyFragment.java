package com.snapchat.team2.snapchat.fragement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.snapchat.team2.snapchat.ListAdapterDataModel.DiscoverStoryListItem;
import com.snapchat.team2.snapchat.MainActivity;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customAdapter.LiveStoryDerpAdapter;
import com.snapchat.team2.snapchat.customAdapter.SubStoryDerpAdapter;
import com.snapchat.team2.snapchat.networkService.DiscoverDataService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bm on 2/09/2016.
 */



public class StroyFragment extends Fragment implements SubStoryDerpAdapter.ItemClickCallback, LiveStoryDerpAdapter.ItemClickCallback {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_STORY = "EXTRA_STORY";
    private static final String EXTRA_AUTHOR = "EXTRA_AUTHOR";
    private static final String EXTRA_IMAGE = "EXTRA_IMAGE";

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private SubStoryDerpAdapter adapter1;
    private LiveStoryDerpAdapter adapter2;
    private ArrayList listData1;
    private ArrayList listData2;

    private ViewGroup rootView;
    private ImageButton button_search;
    private ImageButton button_disc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_story,container,false);



        button_disc = (ImageButton) rootView.findViewById(R.id.newDiscover);
        button_disc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MainActivity parent=(MainActivity) StroyFragment.this.getActivity();
                parent.getMainPage().setCurrentItem(3);
            }
        });

        //listData1 = (ArrayList) StoryDerpData.getListData();
        recyclerView1 = (RecyclerView) rootView.findViewById(R.id.rec_list1);
        recyclerView2 = (RecyclerView) rootView.findViewById(R.id.rec_list2);
        recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        String ip = (String) this.getActivity().getResources().getString(R.string.ip);

        //adapter1 = new SubStoryDerpAdapter(StoryDerpData.getListData(),this.getActivity(),ip);
        //adapter2 = new LiveStoryDerpAdapter(StoryDerpData.getListData(),this.getActivity(),ip);

        RequestQueue mqueue = Volley.newRequestQueue(this.getActivity());
        DiscoverDataService mservice = new DiscoverDataService(mqueue);


        String requestURL1 = "discovery/recommend";
        mservice.getDiscover(this,requestURL1,recyclerView1,1,ip);
        String requestURL2 = "discovery/recommend";
        mservice.getDiscover(this,requestURL2,recyclerView2,2,ip);

        return rootView;

    }



    @Override
    public void onItemClick(List<DiscoverStoryListItem> arrayList, int position) {

        Intent i = new Intent(rootView.getContext(), StoryDetail.class);

        Bundle extras = new Bundle();
        DiscoverStoryListItem item = arrayList.get(position);
        extras.putString(EXTRA_AUTHOR, item.getTitle());
        extras.putString(EXTRA_STORY, item.getText());
        extras.putString(EXTRA_IMAGE,item.getImage());
        i.putExtra(BUNDLE_EXTRAS, extras);

        startActivity(i);
    }

    @Override
    public void onItemLongClick(int p) {

        new AlertDialog.Builder(rootView.getContext())
                .setTitle("Subscribe")
                .setMessage("Are you sure you want to subscribe this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with subscribe

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}


