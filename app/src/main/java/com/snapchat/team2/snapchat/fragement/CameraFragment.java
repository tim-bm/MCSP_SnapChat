package com.snapchat.team2.snapchat.fragement;


import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snapchat.team2.snapchat.CreateNewChatActivity;
import com.snapchat.team2.snapchat.MainActivity;
import com.snapchat.team2.snapchat.MemoryActivity;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.UserInfoActivity;
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
    ImageView addPicBtn;
    ImageView userInfoBtn;
    ImageView sendPhotoBtn;

    TextView timerbtn;

    CamerEditText addText;
    ImageView addEmotion;

    //drawing view
    DrawFreehandView freehandView;

    //holding canmer and drawing view
    RelativeLayout holding;

    //gesture detector to start memory activity
    GestureDetector gestureDetector;



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
        //

        gestureDetector= new GestureDetector(rootView.getContext(),new SwipeUpGesTureListener());
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                return gestureDetector.onTouchEvent(motionEvent);
            }
        });


        ;
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

        userInfoBtn = (ImageView)rootView.findViewById(R.id.main_user_activity) ;
        userInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),UserInfoActivity.class));
                //getActivity().overridePendingTransition(R.anim.activity_open,0);
            }
        });


        rootView.setOnDragListener(new EditDragListner());
       // addText.setOnDragListener(new EditDragListner());
        addText.setOnTouchListener(new EditTextListener());


        //send photo button
        sendPhotoBtn=(ImageView) rootView.findViewById(R.id.image_send);
        sendPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //test for sending photo
//                Activity parentActivity=CameraFragment.this.getActivity();
//                RequestQueue rq= Volley.newRequestQueue(parentActivity);
//                PhotoNetService p=new PhotoNetService(rq);
//                p.postPhoto(parentActivity,rootView.getContext().getString(R.string.serverAddress)+"upload/photo",
//                        CameraFragment.this.mergeSequence(),"imageFromClient.JPEG","2","3","1");
                //save the photo in the temporary directory first
                CameraFragment.this.savePhotoTemporarily(CameraFragment.this.mergeSequence());
                Intent intent =new Intent(CameraFragment.this.getActivity(), CreateNewChatActivity.class);
                intent.putExtra("FromCamera",true);
                startActivity(intent);
            }
        });

        //emotion image
        addEmotion=(ImageView)rootView.findViewById(R.id.add_emotion);
        addEmotion.setOnTouchListener(new EditTextListener());

        //add emotion button
        addPicBtn=(ImageView) rootView.findViewById(R.id.add_Pic_btn);
        addPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEmotion.setVisibility(View.VISIBLE);
                if(addEmotion.getDrawable().getConstantState()==
                        CameraFragment.this.getActivity().getResources().
                                getDrawable(android.R.color.transparent).getConstantState()){
                    addEmotion.setImageResource(R.drawable.ic_lol_emotion);
                    addEmotion.setTag(1);
                }else{
                    switch ((int)addEmotion.getTag()){
                        case 1:
                            addEmotion.setImageResource(R.drawable.ic_cool_emotion);
                            addEmotion.setTag(2);
                            break;
                        case 2:
                            addEmotion.setImageResource(R.drawable.ic_wink_emotion);
                            addEmotion.setTag(3);
                            break;
                        case 3:
                            addEmotion.setImageResource(R.drawable.ic_lol_emotion);
                            addEmotion.setTag(1);
                            break;
                    }


                }
            }
        });

        timerbtn=(TextView)rootView.findViewById(R.id.image_set_timer);


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

                //enable send button
                sendPhotoBtn.setVisibility(View.VISIBLE);

                //enable emotion button
                addPicBtn.setVisibility(View.VISIBLE);

                //enable set timer
                timerbtn.setVisibility(View.VISIBLE);
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
        savebtn.setVisibility(View.INVISIBLE);
        addPicBtn.setVisibility(View.INVISIBLE);
        //disable emotion button
        addPicBtn.setVisibility(View.INVISIBLE);
        //disable editText
        addText.setVisibility(View.INVISIBLE);
        //clear editText content
        addText.setText(null);

        addEmotion.setVisibility(View.INVISIBLE);
        //clear emotion
        addEmotion.setImageResource(android.R.color.transparent);

        //disable timer
        timerbtn.setVisibility(View.INVISIBLE);
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

        //clear context of addtext
        addText.getText().clear();
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
        if(camera!=null){
            parameters = camera.getParameters();
            camera.setParameters(parameters);
            camera.stopPreview();
            camera.release();
            camera = null;
        }

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


//            freehandView.setDrawingCacheEnabled(true);
//            drawing=Bitmap.createBitmap(freehandView.getDrawingCache());
//            Bitmap combinedImage=combineImageLayers(photo,drawing);
            Bitmap combinedImage=this.mergeSequence();
            combinedImage.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (outputStream!=null){
                try {
                    outputStream.flush();
                    outputStream.close();
                    freehandView.setDrawingCacheEnabled(false);
                    addText.setDrawingCacheEnabled(false);
                    addEmotion.setDrawingCacheEnabled(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

      // Toast.makeText(this.getActivity().getApplicationContext(),"download successfully",Toast.LENGTH_LONG).show();
        Toast.makeText(this.getActivity().getApplicationContext(),"Save "+filename+" successfully",Toast.LENGTH_LONG).show();
        //back to the camera
       // initialCancelPhoto();

    }

    public Bitmap mergeSequence(){
        freehandView.setDrawingCacheEnabled(true);
        drawing=Bitmap.createBitmap(freehandView.getDrawingCache());
        Bitmap combinedImage=combineImageLayers(photo,drawing);

        Bitmap combo2;
        //no text and it should the frame of edit text
        if(addText.getText().toString().trim().length()>0){
            //merge edittext
            addText.setDrawingCacheEnabled(true);
            Bitmap text=Bitmap.createBitmap(addText.getDrawingCache());
            combo2=this.overlayBitmap(combinedImage,text,addText.getLeft(),addText.getTop());

        }else{
            combo2=combinedImage;
        }

        //merge emotion
        addEmotion.setDrawingCacheEnabled(true);
        Bitmap emotion=Bitmap.createBitmap(addEmotion.getDrawingCache());
        Bitmap combo3=this.overlayBitmap(combo2,emotion,addEmotion.getLeft(),addEmotion.getTop());

        //clean drawing cache to avoid bugs
        freehandView.setDrawingCacheEnabled(false);
        addText.setDrawingCacheEnabled(false);
        addEmotion.setDrawingCacheEnabled(false);
//        addEmotion.setImageResource(R.color.color_transparent);
//
//        rootView.setDrawingCacheEnabled(false);
        return combo3;
    }

    //draw edit text and emotions on the background according to top/left position
    private Bitmap overlayBitmap(Bitmap back, Bitmap front,float left,float top){
        Bitmap bmOverlay = Bitmap.createBitmap(back.getWidth(), back.getHeight(), back.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(back, 0,0, null);
        canvas.drawBitmap(front, left,top, null);

        return bmOverlay;
    }

    
    /**
     * scale down the background(photo) to get a image with drawing
     * 
     */
    private Bitmap combineImageLayers(Bitmap background,Bitmap foreground){
        int width=0;
        int height=0;
        Bitmap combined;

        width=background.getWidth();
        height=background.getHeight();
        //matrix for rotation
        Matrix matrix=new Matrix();
        matrix.postRotate(90);

 //       Activity parentActivity=(MainActivity)this.getActivity();
//        width=parentActivity.getWindowManager().getDefaultDisplay().getWidth();
//        height=parentActivity.getWindowManager().getDefaultDisplay().getHeight();

        //combined=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        combined=Bitmap.createBitmap(background,0,0,width,height,matrix,true);

        //scale down
        combined=this.getResizedBitmap(combined,foreground.getHeight(),foreground.getWidth());

       // combined=background.copy(Bitmap.Config.ARGB_8888,true);
//        Matrix scale=new Matrix();
//        float scaleWidth = ((float) combined.getWidth()) / foreground.getWidth();
//        float scaleHeight = ((float) combined.getHeight()) / foreground.getHeight();
//        scale.postScale(scaleWidth,scaleHeight);
       // foreground=this.getResizedBitmap(foreground,combined.getWidth(),combined.getHeight());

        Canvas combo=new Canvas(combined);
      //  background=Bitmap.createScaledBitmap(background,width,height,true);
       // foreground=Bitmap.createScaledBitmap(background,width,height,true);
      //  combo.drawBitmap(background,0,0,null);



        combo.drawBitmap(foreground,0,0,null);

        return  combined;
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;

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

    private void savePhotoTemporarily(Bitmap tempPhoto){

        File dir = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES),"TemporaryPhoto");

        String filename="TempPhoto.JPEG";

        File mediaFile = new File(dir.getPath()+File.separator+filename);
        FileOutputStream outputStream =null;
        if (!dir.exists()){
            dir.mkdir();
        }

        try {
            outputStream = new FileOutputStream(mediaFile);

            tempPhoto.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (outputStream!=null){
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
                    //get the widget which is being dragged
                    View view=(View) event.getLocalState();
                    //set params
                    paramsBlock = new RelativeLayout.LayoutParams(view.getWidth(), view.getHeight());
                    paramsBlock.leftMargin = (int) event.getX() - (view.getWidth()/2);//- ((imageView.getWidth()/9)/2);
                    paramsBlock.topMargin = (int) event.getY() -(view.getHeight()/2);//-((imageView.getWidth()/9)/2);
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

    class SwipeUpGesTureListener extends GestureDetector.SimpleOnGestureListener{


        @Override
        public boolean onDown(MotionEvent evt){

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            float sensitvity = 50;

            if((e1.getY() - e2.getY()) > sensitvity){

//                Toast.makeText(CameraFragment.this.getActivity().getApplicationContext(),"swipe up",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(CameraFragment.this.getActivity(), MemoryActivity.class);
                CameraFragment.this.getActivity().startActivity(intent);

            }else if((e2.getY() - e1.getY()) > sensitvity){
               //swipe down

            }else{

            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
