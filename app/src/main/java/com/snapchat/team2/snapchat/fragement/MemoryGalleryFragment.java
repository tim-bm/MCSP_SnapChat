package com.snapchat.team2.snapchat.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customAdapter.MemoryGridviewAdapter;

/**
 * Created by bm on 14/10/2016.
 */

public class MemoryGalleryFragment extends Fragment {
    private ViewGroup rootView;
    private GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_memory_gallery,container,false);


        gridView=(GridView)rootView.findViewById(R.id.gridview_memory);
        gridView.setAdapter(new MemoryGridviewAdapter(this.getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
//                Toast.makeText(HelloGridView.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
