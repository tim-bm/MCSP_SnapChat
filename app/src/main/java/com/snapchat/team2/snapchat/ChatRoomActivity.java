package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.app.Notification;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snapchat.team2.snapchat.ListAdapterDataModel.ChatMessage;
import com.snapchat.team2.snapchat.customAdapter.ChatListAdapter;
import com.snapchat.team2.snapchat.dataJsonModel.GetChatResonseModel;
import com.snapchat.team2.snapchat.networkService.ChatService;
import com.snapchat.team2.snapchat.networkService.UserDataService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends Activity {
    private String receiver_id;
    private String user_id;
    private ListView chatList;
    private ChatListAdapter adapter;
    private List<ChatMessage> cms;
    private UserDataService uds;

    private TextView showReceiver;
    private EditText editText;

    private final Handler handler = new Handler(){
        public void handleMessage(Message msg){
            Bundle bundle=msg.getData();
            if(bundle.getBoolean("network")){
                if(bundle.getBoolean("new")){
                    System.out.println("检测到新的聊天");
                    String data_string = bundle.getString("data");

                    for(ChatMessage c:convertoChatMessage(data_string)){
                        adapter.addone(c);
                    }
                    adapter.notifyDataSetChanged();
                    chatList.setSelection(chatList.getAdapter().getCount()-1);
                }
                else{
                    System.out.println("网络连通，但没有新的聊天");
                }
            }
            else{
                System.out.println("网络未联通");
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroom);
        //get data from database
        //get the receivers information from the user id of the receriver


        receiver_id = getIntent().getStringExtra("receiver_id");
        SharedPreferences shared = getSharedPreferences("snapchat_user", MODE_PRIVATE);
        user_id=shared.getString("user_id", null);
        //uds = new UserDataService(user_id);
        //get currennt user from session
        //cms = new ArrayList<ChatMessage>();
        //adapter = new ChatListAdapter(this,cms);
        System.out.println("receiver_id is"+receiver_id);
        initViews();
        updateChatList();
        addListeners();
        startCheckNewChatThread();
    }

    private void initViews(){
        showReceiver =(TextView)findViewById(R.id.show_receiver);
        showReceiverData(receiver_id);
        chatList = (ListView)findViewById(R.id.message_list);
        chatList.setDivider(null);
        editText = (EditText)findViewById(R.id.message_editor);
    }

    private void showReceiverData(String receiver_id){
        RequestQueue rq = Volley.newRequestQueue(this);
        uds = new UserDataService(rq,user_id);
        uds.setReceiverName(this,showReceiver,receiver_id);
    }

    private void updateChatList(){
        cms =new ArrayList<ChatMessage>();

        adapter = new ChatListAdapter(this,cms);
        chatList.setAdapter(adapter);
    }

    private void startCheckNewChatThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开启线程");
                while(true){
                    try {
                        requestNewChat();
                        Thread.sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void requestNewChat(){
        String me = user_id;
        String opponent_id = receiver_id;
        RequestQueue rq = Volley .newRequestQueue(this);
        UserDataService uds = new UserDataService(rq,user_id);
        Message msg = new Message();
        msg.setTarget(handler);
        //Bundle b = new Bundle();
        //b.putString("xuzhe","hahahah");
        //msg.setData(b);
        //msg.sendToTarget();
        uds.getChatToUser(this,msg);
    }

    private void sendTextMessage(String receiver_id,String content){
        RequestQueue rq = Volley.newRequestQueue(this);

        UserDataService uds = new UserDataService(rq,user_id);
        uds.sendTextMessage(this,chatList,adapter,receiver_id,content);


    }


    private void addListeners(){
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    //Toast.makeText(getApplicationContext(), editText.getText(), Toast.LENGTH_SHORT).show();
                    sendTextMessage(receiver_id,editText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private List<ChatMessage> convertoChatMessage(String data_string){
        List<GetChatResonseModel> newchats = new Gson().fromJson(data_string , new TypeToken<List<GetChatResonseModel>>() {
        }.getType());
        List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
        for(GetChatResonseModel chatModel:newchats){
            chatMessages.add(new ChatMessage(chatModel.getContent(),2));
        }
        return chatMessages;
    }
}
