package com.snapchat.team2.snapchat.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snapchat.team2.snapchat.ListAdapterDataModel.StoryDerpData;
import com.snapchat.team2.snapchat.ListAdapterDataModel.DiscoverStoryListItem;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customAdapter.DiscoverStoryDerpAdapter;

import java.util.ArrayList;

/**
 * Created by bm on 2/09/2016.
 */

public class DiscoverFragment extends Fragment implements DiscoverStoryDerpAdapter.ItemClickCallback {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_STORY = "EXTRA_STORY";
    private static final String EXTRA_AUTHOR = "EXTRA_AUTHOR";

    private RecyclerView recyclerView;
    private DiscoverStoryDerpAdapter adapter;
    private ArrayList listData;

    private ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_discover,container,false);
        listData = (ArrayList) StoryDerpData.getListData();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rec_list1);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        adapter = new DiscoverStoryDerpAdapter(StoryDerpData.getListData(),this.getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setItemClickCallback(this);
        adapter.notifyDataSetChanged();
        return rootView;

    }

    @Override
    public void onItemClick(int p) {
        DiscoverStoryListItem item = (DiscoverStoryListItem) listData.get(p);

        Intent i = new Intent(rootView.getContext(), StoryDetail.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_AUTHOR, item.getTitle());
        extras.putString(EXTRA_STORY, item.getText());
        i.putExtra(BUNDLE_EXTRAS, extras);

        startActivity(i);
    }

    @Override
    public void onItemLongClick(int p) {

    }
}
