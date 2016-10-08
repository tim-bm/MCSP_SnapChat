package com.snapchat.team2.snapchat.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.snapchat.team2.snapchat.ListAdapterDataModel.StoryDerpData;
import com.snapchat.team2.snapchat.ListAdapterDataModel.StoryListItem;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customAdapter.StoryDerpAdapter;
import java.util.ArrayList;

/**
 * Created by bm on 2/09/2016.
 */



public class StroyFragment extends Fragment implements StoryDerpAdapter.ItemClickCallback {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_STORY = "EXTRA_STORY";
    private static final String EXTRA_AUTHOR = "EXTRA_AUTHOR";

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private StoryDerpAdapter adapter;
    private ArrayList listData;

    private ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_story,container,false);

//        discoverView=(RecyclerView) rootView.findViewById(R.id.discover_recycle_view);
//
//        // Create a grid layout with 6 columns
//        // (least common multiple of 2 and 3)
//        discoverLayoutManager=new GridLayoutManager(this.getActivity(),6);
//        discoverLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                switch (position % 5) {
//                    // first two items span 3 columns each
//                    case 0:
//                    case 1:
//                        return 3;
//                    // next 3 items span 2 columns each
//                    case 2:
//                    case 3:
//                    case 4:
//                        return 2;
//                }
//                throw new IllegalStateException("internal error");
//            }
//        });
//        discoverView.setLayoutManager(discoverLayoutManager);


        listData = (ArrayList) StoryDerpData.getListData();

        recyclerView1 = (RecyclerView) rootView.findViewById(R.id.rec_list1);
        recyclerView2 = (RecyclerView) rootView.findViewById(R.id.rec_list2);
        recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));




        adapter = new StoryDerpAdapter(StoryDerpData.getListData(),this.getActivity());
        recyclerView1.setAdapter(adapter);
        recyclerView2.setAdapter(adapter);
        adapter.setItemClickCallback(this);
        return rootView;

    }


    @Override
    public void onItemClick(int p) {
        StoryListItem item = (StoryListItem) listData.get(p);

        Intent i = new Intent(rootView.getContext(), StoryDetail.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_AUTHOR, item.getTitle());
        extras.putString(EXTRA_STORY, item.getSubTitle());
        i.putExtra(BUNDLE_EXTRAS, extras);

        startActivity(i);
    }

    @Override
    public void onItemLongClicked(int p) {

    }

    @Override
    public void onSecondaryIconClick(int p) {
        StoryListItem item = (StoryListItem) listData.get(p);
        //update our data
        if (item.isFavourite()){
            item.setFavourite(false);
        } else {
            item.setFavourite(true);
        }
        //pass new data to adapter and update
        adapter.setListData(listData);
        adapter.notifyDataSetChanged();
    }
}


