package com.snapchat.team2.snapchat;

import android.os.Bundle;
import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.snapchat.team2.snapchat.ListAdapterDataModel.Friend;
import com.snapchat.team2.snapchat.customAdapter.ChatFriendListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewChatActivity extends Activity {

    private TextView chatFriendSearch;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_friends);

        initialViews();
        initAdapter();
        initListeners();


        /*for(int i=0;i<listView.getCount();i++){

            View item = getViewByPosition(i,listView);
            TextView v_letter = (TextView) item.findViewById(R.id.chat_friend_letter);
            TextView v_name = (TextView) item.findViewById(R.id.chat_friend_name);

            if(v_letter.getText().equals(currnetLetter)){
                item.performClick();

            }


        }*/
    }

    private void initialViews(){
        listView=(ListView) findViewById(R.id.chat_friend_list);
        chatFriendSearch=(TextView)findViewById(R.id.search_friend);
    }

    private void initListeners(){
    }

    private void initAdapter(){
        ChatFriendListAdapter adapter = new ChatFriendListAdapter(getApplicationContext(),getData());
        listView.setAdapter(adapter);
    }


    private List<Friend> getData(){
        List<Friend> list = new ArrayList<Friend>();
        list.add(new Friend("A","Afsdfs",1));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("A","asrgsddg",2));
        list.add(new Friend("B","bsrgsddg",1));
        list.add(new Friend("B","bsrgsddg",1));
        list.add(new Friend("B","bsrgsddg",2));
        list.add(new Friend("B","bsrgsddg",2));
        list.add(new Friend("B","bsrgsddg",2));
        return list;

    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }


    }
}
