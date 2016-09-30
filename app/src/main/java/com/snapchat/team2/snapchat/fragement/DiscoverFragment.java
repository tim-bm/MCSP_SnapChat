package com.snapchat.team2.snapchat.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snapchat.team2.snapchat.R;

/**
 * Created by bm on 2/09/2016.
 */

public class DiscoverFragment extends Fragment {

    private RecyclerView discoverView;
    private GridLayoutManager discoverLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.fragment_discover,container,false);

        discoverView=(RecyclerView) rootView.findViewById(R.id.discover_recycle_view);

        // Create a grid layout with 6 columns
        // (least common multiple of 2 and 3)
        discoverLayoutManager=new GridLayoutManager(this.getActivity(),6);
        discoverLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (position % 5) {
                    // first two items span 3 columns each
                    case 0:
                    case 1:
                        return 3;
                    // next 3 items span 2 columns each
                    case 2:
                    case 3:
                    case 4:
                        return 2;
                }
                throw new IllegalStateException("internal error");
            }
        });
        discoverView.setLayoutManager(discoverLayoutManager);
        return rootView;

    }
}
