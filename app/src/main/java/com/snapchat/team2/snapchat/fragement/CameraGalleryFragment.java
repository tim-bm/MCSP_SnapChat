package com.snapchat.team2.snapchat.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snapchat.team2.snapchat.R;

/**
 * Created by bm on 14/10/2016.
 */

public class CameraGalleryFragment extends Fragment {
    private ViewGroup rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_camera_gallery,container,false);

        return rootView;
    }
}
