package com.snapchat.team2.snapchat.fragement;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.snapchat.team2.snapchat.MainActivity;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customView.DrawFreehandView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    Bitmap drawing;
    //use imageView as button

    ImageView switchCamera;
    ImageView takePhoto;
    ImageView flashBtn;

    ImageView cancelBtn;
    ImageView drawBtn;
    ImageView savebtn;

    //drawing view
    DrawFreehandView freehandView;

    //holding canmer and drawing view
    RelativeLayout holding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_camera,container,false);

        surfaceView=(SurfaceView) rootView.findViewById(R.id.cameraSurface);

        freehandView=(DrawFreehandView) rootView.findViewById(R.id.freehandDraw);

        holding=(RelativeLayout) rootView.findViewById(R.id.holdingView);

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

        //freehand draw button
        drawBtn=(ImageView) rootView.findViewById(R.id.photo_draw);

        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  freehandView=(DrawFreehandView) rootView.findViewById(R.id.freehandDraw);
                freehandView.setDrawable(true);
            }
        });

        //save button
        savebtn=(ImageView) rootView.findViewById(R.id.image_save);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage();
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
                switchCamera.setVisibility(View.INVISIBLE);

                //enable cancel button
                cancelBtn.setVisibility(View.VISIBLE);
                //enable draw button
                drawBtn.setVisibility(View.VISIBLE);
                //save button
                savebtn.setVisibility(View.VISIBLE);

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
        drawBtn.setVisibility(View.INVISIBLE);
        savebtn.setVisibility(View.INVISIBLE);

        //enable button
        flashBtn.setVisibility(View.VISIBLE);
        takePhoto.setVisibility(View.VISIBLE);
        switchCamera.setVisibility(View.VISIBLE);

        //disable drawing and clean canvas
        freehandView.setDrawable(false);
        freehandView.clearCanvas();
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

    public void saveImage(){
        File dir = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES),"SnapChat222");

      //  File dir=new File(Environment.getExternalStorageDirectory().toString()+"/snapchat111");
        String ImageDate = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
        File mediaFile = new File(dir.getPath()+File.separator+"IMG_"+ImageDate+".jpg");
        FileOutputStream outputStream =null;
        if (!dir.exists()){
            dir.mkdir();
            Toast.makeText(this.getActivity().getApplicationContext(),"mkdir successfully",Toast.LENGTH_LONG).show();
        }

        try {
            outputStream = new FileOutputStream(mediaFile);
//            rootView.setDrawingCacheEnabled(true);
//            photo=rootView.getDrawingCache();
            freehandView.setDrawingCacheEnabled(true);
            drawing=Bitmap.createBitmap(freehandView.getDrawingCache());
            Bitmap combinedImage=combineImageLayers(photo,drawing);
            //photo.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            combinedImage.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (outputStream!=null){
                try {
                    outputStream.flush();
                    outputStream.close();
                    rootView.setDrawingCacheEnabled(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

      // Toast.makeText(this.getActivity().getApplicationContext(),"download successfully",Toast.LENGTH_LONG).show();
        Toast.makeText(this.getActivity().getApplicationContext(),dir.getAbsolutePath(),Toast.LENGTH_LONG).show();
    }

    private Bitmap combineImageLayers(Bitmap background,Bitmap foreground){
        int width=0;
        int height=0;
        Bitmap combined;

        Activity parentActivity=(MainActivity)this.getActivity();
        width=parentActivity.getWindowManager().getDefaultDisplay().getWidth();
        height=parentActivity.getWindowManager().getDefaultDisplay().getHeight();

        combined=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas combo=new Canvas(combined);
        background=Bitmap.createScaledBitmap(background,width,height,true);
        combo.drawBitmap(background,0,0,null);
        combo.drawBitmap(foreground,0,0,null);

        return  combined;
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
