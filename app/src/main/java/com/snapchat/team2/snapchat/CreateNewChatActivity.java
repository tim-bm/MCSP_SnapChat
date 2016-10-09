package com.snapchat.team2.snapchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.snapchat.team2.snapchat.ListAdapterDataModel.Friend;
import com.snapchat.team2.snapchat.networkService.PhotoNetService;
import com.snapchat.team2.snapchat.networkService.UserDataService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNewChatActivity extends Activity {

    private TextView chatFriendSearch;
    private ListView listView;
    private ImageButton back;
    private EditText searchChatFriend;
    //default is 1. 2 comes from cameraFragment
    private boolean fromCamera=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_friends);
        initialViews();
        setData();
        initListeners();

        Bundle extrasInfo=getIntent().getExtras();
        if(extrasInfo!=null){
            fromCamera=extrasInfo.getBoolean("FromCamera");
        }

    }

    private void initialViews(){
        listView=(ListView) findViewById(R.id.chat_friend_list);
        listView.setTextFilterEnabled(true);
        back = (ImageButton)findViewById(R.id.chat_friend_back);
        searchChatFriend =(EditText)findViewById(R.id.search_friend);

    }

    private void initListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //determine this activity is used for photo or chat

                if(fromCamera){

                    //Toast.makeText(CreateNewChatActivity.this,"你单击的是第"+(position+1)+"条数据",Toast.LENGTH_SHORT).show();
                    //获得receiver 的id
                    Friend f = (Friend) listView.getItemAtPosition(position);
                    String user_id = f.getUser_id();


                    //to send activity
                    Intent intent = new Intent(CreateNewChatActivity.this,ChatRoomActivity.class);
                    intent.putExtra("receiver_id",user_id);
                    startActivity(intent);
                }else{
                    //send the photo to a friend



                    Friend f = (Friend) listView.getItemAtPosition(position);
                    String receiverId = f.getUser_id();

                    //get send id from share preferences
                    SharedPreferences shared = getSharedPreferences("snapchat_user", MODE_PRIVATE);
                    String sendId=shared.getString("user_id", null);

                    //read from temp directory
                    File dir = new File(Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_PICTURES),"TemporaryPhoto");

                    String filename="TempPhoto.JPEG";

                    File mediaFile = new File(dir.getPath()+File.separator+filename);

                    String ImageDate = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
                    String filenameOnServer="IMG_"+ImageDate+".jpg";

                    //send photo
                    RequestQueue rq= Volley.newRequestQueue(CreateNewChatActivity.this);
                    PhotoNetService p=new PhotoNetService(rq);
                    if(mediaFile.exists()){
                        Bitmap photo= BitmapFactory.decodeFile(mediaFile.getAbsolutePath());
                        p.postPhoto(CreateNewChatActivity.this,CreateNewChatActivity.this.getString(R.string.serverAddress)+"upload/photo",
                                photo,filenameOnServer,sendId,receiverId,"1");
                    }

                    //reset
                    fromCamera=false;

                    //go back to chat fragment
                    Intent intent=new Intent(CreateNewChatActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    private void setData(){
        //获得数据,向server 发送get 请求 user id is 1
        RequestQueue rq = Volley.newRequestQueue(this);
        UserDataService userDataService = new UserDataService(rq,"1");
        userDataService.getFriends(this,listView,searchChatFriend);

    }
}
