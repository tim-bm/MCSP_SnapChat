package com.snapchat.team2.snapchat.fragement;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customView.DrawFreehandView;

import java.io.IOException;

/**
 * Created by bm on 2/09/2016.
 *
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
    ImageView flashBtn;

    ImageView cancelBtn;

    //drawing view
    DrawFreehandView freehandView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_camera,container,false);

        surfaceView=(SurfaceView) rootView.findViewById(R.id.cameraSurface);

        freehandView=(DrawFreehandView) rootView.findViewById(R.id.freehandDraw);


        //Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.

        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.addCallback(this);
      //  surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        initializeButtons();
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
                initialTakePhoto();
            }
        });

        //flash button
        flashBtn=(ImageView) rootView.findViewById(R.id.camera_flash);

        // initialise flash option for camera
        flashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialFlashOption();
            }
        });

        //cancel button
        cancelBtn=(ImageView) rootView.findViewById(R.id.photo_cancel);

        //listener
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialCancelPhoto();
            }
        });

    }

    private void initialTakePhoto(){
        camera.takePicture(new Camera.ShutterCallback() {
            @Override
            public void onShutter() {

            }
        },null,null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                photo= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                camera.stopPreview();

                //disable buttons
                takePhoto.setVisibility(View.INVISIBLE);
                flashBtn.setVisibility(View.INVISIBLE);

                //enable cancel button
                cancelBtn.setVisibility(View.VISIBLE);

                //refresh the view(to force a view to draw)
                surfaceView.invalidate();

            }
        });
    }

    private void initialFlashOption(){
        String currentFlashMode=parameters.getFlashMode();
        if(currentFlashMode.equals(Camera.Parameters.FLASH_MODE_OFF)){
            //flash can be turned on
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            camera.setParameters(parameters);
            //change icon
            flashBtn.setImageResource(R.drawable.ic_image_flash_off);
        }else if (currentFlashMode.equals(Camera.Parameters.FLASH_MODE_ON)){
            //flash can be turned off
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            //change icon
            flashBtn.setImageResource(R.drawable.ic_image_flash_on);
        }
    }

    private void initialCancelPhoto(){
        //restart preview
        camera.startPreview();
        //disable button
        cancelBtn.setVisibility(View.INVISIBLE);
        //emable button
        flashBtn.setVisibility(View.VISIBLE);
        takePhoto.setVisibility(View.VISIBLE);
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
