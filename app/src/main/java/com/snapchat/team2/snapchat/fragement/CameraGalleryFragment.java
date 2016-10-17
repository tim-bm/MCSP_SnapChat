package com.snapchat.team2.snapchat.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;

import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.Tools.MemoryUploader;
import com.snapchat.team2.snapchat.customAdapter.CameraGalleryGridAdapter;

/**
 * Created by bm on 14/10/2016.
 */

public class CameraGalleryFragment extends Fragment implements PopupMenu.OnMenuItemClickListener{
    private ViewGroup rootView;
    private GridView gridView;
    private CameraGalleryGridAdapter gAdapter;
    private String uploadPhotoPath="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_camera_gallery,container,false);


        gridView=(GridView)rootView.findViewById(R.id.gridview_cameraRoll);
        gAdapter= new CameraGalleryGridAdapter(CameraGalleryFragment.this.getActivity());
        gridView.setAdapter(gAdapter);


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                CameraGalleryFragment.this.uploadPhotoPath=gAdapter.getRollImages()[position];
                //popup menu
                PopupMenu popup=new PopupMenu(CameraGalleryFragment.this.getActivity(),view);
                MenuInflater inflater=popup.getMenuInflater();
                popup.setOnMenuItemClickListener(CameraGalleryFragment.this);
                inflater.inflate(R.menu.grid_camnera_popup,popup.getMenu());
                popup.show();

                return true;
            }

        });



        return rootView;
    }

//    public void uploadCameraPhotoAsMemory(String photoPath){
//        String receiverId ="-1";
//        String category="2";
//        //get send id from share preferences
//        SharedPreferences shared = CameraGalleryFragment.this.getActivity().getSharedPreferences("snapchat_user", MODE_PRIVATE);
//        String sendId=shared.getString("user_id", null);
//
//        //read photo from path
//
//        File photoFile=new File(photoPath);
//
//        String ImageDate = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
//        String filenameOnServer="IMG_"+ImageDate+"---memory"+".jpg";
//
//        //send photo
//        RequestQueue rq= Volley.newRequestQueue(CameraGalleryFragment.this.getActivity());
//        PhotoNetService p=new PhotoNetService(rq);
//        if(photoFile.exists()){
//            //decode and resize Bitmap
//            BitmapFactory.Options resizeoption=new BitmapFactory.Options();
//            resizeoption.inSampleSize=3;
//
//            Bitmap photo= BitmapFactory.decodeFile(photoFile.getAbsolutePath(),resizeoption);
//            p.postPhoto(CameraGalleryFragment.this.getActivity(),CameraGalleryFragment.this.getActivity().getString(R.string.serverAddress)+"upload/photo",
//                    photo,filenameOnServer,sendId,receiverId,category);
//        }
//    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.popup_item_cameraRoll_memory:

//                this.uploadCameraPhotoAsMemory(uploadPhotoPath);
                MemoryUploader.uploadPhoto(uploadPhotoPath,CameraGalleryFragment.this.getActivity(),true);
                return true;

            default:
                return false;
        }
    }


}
