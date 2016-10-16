package com.snapchat.team2.snapchat.fragement;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snapchat.team2.snapchat.ChatRoomActivity;
import com.snapchat.team2.snapchat.CreateNewChatActivity;
import com.snapchat.team2.snapchat.ListAdapterDataModel.ChatMessage;
import com.snapchat.team2.snapchat.ListAdapterDataModel.MessageNotifcation;
import com.snapchat.team2.snapchat.MainActivity;
import com.snapchat.team2.snapchat.R;
import com.snapchat.team2.snapchat.customView.RefreshListView;
import com.snapchat.team2.snapchat.dataJsonModel.GetChatResonseModel;
import com.snapchat.team2.snapchat.dataJsonModel.GetChatResponseModelWithName;
import com.snapchat.team2.snapchat.dataJsonModel.userModel;
import com.snapchat.team2.snapchat.networkService.UserDataService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by bm on 1/09/2016.
 */

public class ChatFragment extends Fragment {
    private ViewGroup rootView;
    private RefreshListView refreshListView;
    private ImageButton button_search;
    private Button button_chat;

    private SearchView search_view;
    private ImageButton newChat;

    private boolean messageFlag;
    //private List<ChatMessage> passToChatRoom = new ArrayList<ChatMessage>();
    private String user_id;
    private List<Map<String,Object>> messageDataSource = new ArrayList<>();
    private String oppenentName = null;
    private boolean getUserLock = true;

    private Intent intent = null;
    //private SimpleAdapter adapter = null;


    private final Handler handler = new Handler(){
        public void handleMessage(Message msg){
            Bundle bundle=msg.getData();
            if(bundle.getBoolean("network")){
                if(bundle.getBoolean("new")){
                    System.out.println("检测到新的聊天");
                    String data_string = bundle.getString("data");
                    messageDataSource.addAll(converttoMessageNotification(data_string));
                    SimpleAdapter sm = new SimpleAdapter(getActivity(),messageDataSource,R.layout.message_notification_list_item,
                            new String[]{"name","notification"},new int[]{R.id.message_from,R.id.notification});
                    refreshListView.setAdapter(sm);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        user_id = getActivity().getSharedPreferences("snapchat_user",getActivity().MODE_PRIVATE).getString("user_id",null);
        intent = new Intent(this.getActivity(), ChatRoomActivity.class);
        initialView(inflater,container,savedInstanceState);
        initialSetAdapter();
        initialSetListeners();
        return rootView;
    }

    private List<Map<String, Object>> getData(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "G1");
        map.put("info", "google 1");
        map.put("img", R.drawable.file);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "G2");
        map.put("info", "google 2");
        map.put("img", R.drawable.file);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("name", "G3");
        map.put("info", "google 3");
        map.put("img", R.drawable.file);
        list.add(map);

        return list;

    }

    private void initialView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //initial widgets
        rootView=(ViewGroup)inflater.inflate(R.layout.fragment_chat,container,false);
        refreshListView= (RefreshListView) rootView.findViewById(R.id.refresh_list);
        button_search=(ImageButton) rootView.findViewById(R.id.search_button);
        button_chat=(Button)rootView.findViewById(R.id.button_chat);

        search_view=(SearchView)rootView.findViewById(R.id.search_view);
        newChat=(ImageButton) rootView.findViewById(R.id.newChat);
    }

    public void onStart(){
        super.onStart();
    }

    public void onResume(){
        System.out.println("调用onresume");
        messageDataSource.clear();
        SimpleAdapter sm = new SimpleAdapter(getActivity(),messageDataSource,R.layout.message_notification_list_item,
                new String[]{"name","notification"},new int[]{R.id.message_from,R.id.notification});
        refreshListView.setAdapter(sm);

        messageFlag = true;
        startCheckNewChatThread();
        super.onResume();
    }
    public void onPause(){
        messageFlag = false;
        super.onPause();
    }

    private void initialSetListeners(){
        System.out.println("设置监听器");
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show the serch textview and hide other
                button_chat.setVisibility(View.GONE);
                newChat.setVisibility(View.GONE);
                button_search.setVisibility(View.GONE);
                search_view.setVisibility(View.VISIBLE);

                search_view.setQueryHint("search the snap");
                int search_plate_id=search_view.getContext().getResources().getIdentifier("android:id/search_plate",null,null);
                System.out.println("searchPlateId is"+ search_plate_id);
                search_view.setSubmitButtonEnabled(false);

                int id=search_view.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
                TextView t=(TextView) search_view.findViewById(id);

                int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
                ImageView magImage = (ImageView) search_view.findViewById(magId);
                magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

                System.out.println("text is "+t.getText());
                t.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus==false){
                            System.out.println("lost focus");
                            button_chat.setVisibility(View.VISIBLE);
                            newChat.setVisibility(View.VISIBLE);
                            button_search.setVisibility(View.VISIBLE);
                            search_view.setVisibility(View.GONE);
                        }
                    }
                });
                t.setTextColor(getResources().getColor(R.color.colorChatlistHeaderRelease));
                t.setHintTextColor(getResources().getColor(R.color.colorChatlistHeaderRelease));
                t.requestFocus();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(t,0);
            }
        });

        button_search.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_UP){
                    //关闭软键盘
                    System.out.println("clost the input");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(button_search.getWindowToken(), 0);
                }
                return false;
            }
        });

        newChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CreateNewChatActivity.class));
            }
        });


        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("跳转");
                if(messageDataSource.size()==0){
                    System.out.println("跳转到匿名");
                    return;
                }
                System.out.println("messageDataSource的长度 "+messageDataSource.size());
                System.out.println("位置"+position);

                intent.putExtra("receiver_id",(String)(messageDataSource.get(position-1).get("oppoentId")));
                List<ChatMessage> sendMessages = new ArrayList<ChatMessage>();
                Bundle bundle =new Bundle();

                ArrayList<String> results = new ArrayList<String>();
                //int i= 0;
                for(Map<String,Object> map:messageDataSource){
                    if(((String)(map.get("name"))).equals((String)(messageDataSource.get(position-1).get("name")))){
                        results.add((String)(map.get("content")));
                        //i++;
                    }
                }
                Bundle b = new Bundle();

                String[] final_results = new String[results.size()];
                for(int i=0;i<results.size();i++){
                    final_results[i] = results.get(i);
                }
                b.putStringArray("initialInfo",final_results);
                intent.putExtra("initialInfo",b);
                startActivity(intent);


            }
        });
    }


    public  void startCheckNewChatThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开启线程");
                while(messageFlag){
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

    private void initialSetAdapter(){
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),getData(),R.layout.chat_list_item,
                new String[]{"name","info","img"},new int[]{R.id.chat_name,R.id.chat_info,R.id.chat_item_img});
        refreshListView.setAdapter(adapter);
    }

    private List<Map<String,Object>> converttoMessageNotification(String data_string){
        System.out.println("data string is: "+ data_string);
        List<GetChatResponseModelWithName> newchats = new Gson().fromJson(data_string , new TypeToken<List<GetChatResponseModelWithName>>(){}.getType());
        if(newchats == null){
            System.out.println("new caht is sempty");
        }
        //List<MessageNotifcation> chatMessages = new ArrayList<MessageNotifcation>();
        List<Map<String,Object>> data = new ArrayList<>();
        for(GetChatResponseModelWithName chatModel:newchats){
            //chatMessages.add(new MessageNotifcation(chatModel.getFrom(),chatModel.getContent(),1));
            Map<String,Object> map= new HashMap<String,Object>();
            map.put("oppoentId",chatModel.getFrom());
            map.put("name",chatModel.getName());
            map.put("content",chatModel.getContent());
            map.put("notification","click to chat");
            data.add(map);
        }
        return data;
    }

    private List<ChatMessage> converToChatMessage(String data_string,String opponent_user_id){
        System.out.println("data string is: "+ data_string);
        List<GetChatResonseModel> newchats = new Gson().fromJson(data_string , new TypeToken<List<GetChatResonseModel>>(){}.getType());
        if(newchats == null){
            System.out.println("new caht is sempty");
        }

        List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
        for(GetChatResonseModel chatModel:newchats){
            if(chatModel.getId().equals(opponent_user_id)){
                chatMessages.add(new ChatMessage(chatModel.getContent(),2));
            }
        }
        return chatMessages;
    }

    private void requestNewChat(){
        String me = user_id;
        //String opponent_id = receiver_id;
        RequestQueue rq = Volley.newRequestQueue(this.getActivity());
        UserDataService uds = new UserDataService(rq,user_id);
        Message msg = new Message();
        msg.setTarget(handler);
        uds.getAllChatToUser(this.getActivity(),msg);

    }


}
