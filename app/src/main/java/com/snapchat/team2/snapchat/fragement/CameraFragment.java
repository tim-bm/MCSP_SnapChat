package com.snapchat.team2.snapchat.fragement;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.snapchat.team2.snapchat.R;

import java.io.IOException;

/**
 * Created by bm on 2/09/2016.
 */

public class CameraFragment extends Fragment implements SurfaceHolder.Callback{


    ViewGroup rootView;

    //sufaceView to preview camera
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    //camera
    Camera camera;
    Camera.Parameters parameters;
    int facing=Camera.CameraInfo.CAMERA_FACING_BACK;
    Bitmap photo;
    //use imageView as button

    ImageView switchCamera;
    ImageView takePhoto;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_camera,container,false);

        surfaceView=(SurfaceView) rootView.findViewById(R.id.cameraSurface);

        initializeButtons();

        //Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.

        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
      //  surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        return rootView;

    }

    public void initializeButtons(){
        switchCamera=(ImageView) rootView.findViewById(R.id.switch_back_front);
        switchCamera.setOnClickListener(new SwitchButtonListener());

        //initialise button and set listener for taking photo
        takePhoto=(ImageView) rootView.findViewById(R.id.camera_take_photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {

                    }
                },null,null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                        photo= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        camera.stopPreview();

                    }
                });
            }
        });



    }

    @Override
    public void onPause(){
        super.onPause();

        //Release the camera when swiping to another frame
        //to prevent duplicated camera id that causes exception
       releaseCamera();

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {


        openCamera();
//        camera  = Camera.open(facing);
//        parameters = camera.getParameters();
//        camera.setParameters(parameters);
//        camera.setDisplayOrientation(90);
//        try {
//            camera.setPreviewDisplay(surfaceHolder);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        camera.startPreview();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    private void openCamera(){
        camera  = Camera.open(facing);
        parameters = camera.getParameters();
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();
    }

    private void releaseCamera(){
        camera.setParameters(parameters);
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    class SwitchButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            if(camera!=null){
                releaseCamera();
            }
            if(facing==Camera.CameraInfo.CAMERA_FACING_BACK){
                facing= Camera.CameraInfo.CAMERA_FACING_FRONT;
            }else{
                facing=Camera.CameraInfo.CAMERA_FACING_BACK;
            }

            openCamera();
        }
    }
}
