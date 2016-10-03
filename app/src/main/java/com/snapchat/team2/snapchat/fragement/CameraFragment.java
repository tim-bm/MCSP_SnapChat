package com.snapchat.team2.snapchat.fragement;


import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.snapchat.team2.snapchat.MainActivity;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customView.DrawFreehandView;
import com.snapchat.team2.snapchat.customWidget.CamerEditText;

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
    ImageView addTextBtn;

    CamerEditText addText;

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

        addText=(CamerEditText) rootView.findViewById(R.id.edit_text_add);

        //add text button
        addTextBtn=(ImageView) rootView.findViewById(R.id.add_TextView);
        addTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText.setVisibility(View.VISIBLE);
                addText.requestFocus();
                MainActivity parent=(MainActivity)CameraFragment.this.getActivity();
                InputMethodManager imm = (InputMethodManager) parent.getSystemService(parent.INPUT_METHOD_SERVICE);
                imm.showSoftInput(addText, InputMethodManager.SHOW_IMPLICIT);
            }
        });


        rootView.setOnDragListener(new EditDragListner());
       // addText.setOnDragListener(new EditDragListner());
        addText.setOnTouchListener(new EditTextListener());
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

                //disable tab from activity
                MainActivity parent=(MainActivity)CameraFragment.this.getActivity();
                parent.disableTabButtons();

                //enable cancel button
                cancelBtn.setVisibility(View.VISIBLE);
                //enable draw button
                drawBtn.setVisibility(View.VISIBLE);
                //save button
                savebtn.setVisibility(View.VISIBLE);
                //add text button
                addTextBtn.setVisibility(View.VISIBLE);



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
        addTextBtn.setVisibility(View.INVISIBLE);

        //disable editText
        addText.setVisibility(View.INVISIBLE);
        //clear editText content
        addText.setText(null);

        //enable button
        flashBtn.setVisibility(View.VISIBLE);
        takePhoto.setVisibility(View.VISIBLE);
        switchCamera.setVisibility(View.VISIBLE);

        //enable tab from activity
        MainActivity parent=(MainActivity)CameraFragment.this.getActivity();
        parent.enableTabButtons();

        //disable drawing and clean canvas
        freehandView.setDrawable(false);
        freehandView.clearCanvas();

        //remove the cache from previous drawing
        if(drawing!=null){
            drawing.recycle();
            drawing=null;
        }
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
        String filename="IMG_"+ImageDate+".jpg";
        File mediaFile = new File(dir.getPath()+File.separator+filename);
        FileOutputStream outputStream =null;
        if (!dir.exists()){
            dir.mkdir();
        }

        try {
            outputStream = new FileOutputStream(mediaFile);
//            rootView.setDrawingCacheEnabled(true);
//            photo=rootView.getDrawingCache();

            freehandView.setDrawingCacheEnabled(true);

            drawing=Bitmap.createBitmap(freehandView.getDrawingCache());
            Bitmap combinedImage=combineImageLayers(photo,drawing);
            combinedImage.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (outputStream!=null){
                try {
                    outputStream.flush();
                    outputStream.close();
                    freehandView.setDrawingCacheEnabled(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

      // Toast.makeText(this.getActivity().getApplicationContext(),"download successfully",Toast.LENGTH_LONG).show();
        Toast.makeText(this.getActivity().getApplicationContext(),"Save "+filename+" successfully",Toast.LENGTH_LONG).show();
        //back to the camera
        initialCancelPhoto();

    }

    private Bitmap combineImageLayers(Bitmap background,Bitmap foreground){
        int width=0;
        int height=0;
        Bitmap combined;

        Activity parentActivity=(MainActivity)this.getActivity();
//        width=parentActivity.getWindowManager().getDefaultDisplay().getWidth();
//        height=parentActivity.getWindowManager().getDefaultDisplay().getHeight();
        width=background.getWidth();
        height=background.getHeight();

        combined=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas combo=new Canvas(combined);
        background=Bitmap.createScaledBitmap(background,width,height,true);
        combo.drawBitmap(background,0,0,null);
        combo.drawBitmap(foreground,0,0,null);

        return  combined;
    }


    public final class EditTextListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
               view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }


    class EditDragListner implements View.OnDragListener{

       private RelativeLayout.LayoutParams paramsBlock;
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    View view=(View) event.getLocalState();
                    paramsBlock = new RelativeLayout.LayoutParams(view.getWidth(), view.getHeight());
//                    paramsBlock=(RelativeLayout.LayoutParams)view.getLayoutParams();
                    paramsBlock.leftMargin = (int) event.getX() - (view.getWidth()/2);//- ((imageView.getWidth()/9)/2);
                    paramsBlock.topMargin = (int) event.getY() -(view.getHeight()/2);//-((imageView.getWidth()/9)/2);
//                    paramsBlock.leftMargin = (int) event.getX();
//                    paramsBlock.topMargin = (int) event.getY();
                    view.setLayoutParams(paramsBlock);
                    //addText.setVisibility(View.VISIBLE);
                    //v.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                  // v.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            return true;
        }
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
