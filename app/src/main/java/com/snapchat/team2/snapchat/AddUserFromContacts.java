package com.snapchat.team2.snapchat;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.snapchat.team2.snapchat.networkService.UserDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddUserFromContacts extends Activity {


    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
     };
    /** show user name **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /** show phone **/
    private static final int PHONES_NUMBER_INDEX = 1;

    private List<Map<String,Object>> pairs = new ArrayList<Map<String,Object>>();

    private ListView constact_list;
    private ImageView back;

    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_from_contacts);
        SharedPreferences shared = getSharedPreferences("snapchat_user", MODE_PRIVATE);
        this.user_id=shared.getString("user_id", null);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initViews();
        getPhoneContacts();
        addListeners();

    }
    private void initViews(){
        constact_list =(ListView) findViewById(R.id.contact_list);
        back = (ImageView)findViewById(R.id.add_user_from_contacts_back);

    }
    private void addListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        constact_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone = (String)(pairs.get(position).get("phone"));
                addFriendByPhone(phone);
            }
        });
    }

    private void getPhoneContacts(){
        ContentResolver resolver = getContentResolver();
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PHONES_PROJECTION,null,null,null);
        if(phoneCursor!=null){
            while (phoneCursor.moveToNext()){
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                String phoneNumber = phoneCursor.getString (PHONES_NUMBER_INDEX);
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("name",contactName);
                map.put("phone",phoneNumber);
                pairs.add(map);
            }
            phoneCursor.close();
        }
        constact_list.setAdapter(new SimpleAdapter(this,pairs,R.layout.add_user_from_contacts_item,
                new String[]{"name","phone"},new int[]{R.id.contact_name,R.id.contact_phone}));
        //setAdapter
    }

    private void addFriendByPhone(String phone){
        RequestQueue rq = Volley.newRequestQueue(this);
        UserDataService uds = new UserDataService(rq,user_id);
        uds.addFriendbyPhone(this,phone);
    }

}
