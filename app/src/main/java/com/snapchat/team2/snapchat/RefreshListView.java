package com.snapchat.team2.snapchat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

/**
 * Created by xu on 2016/9/14.
 */
public class RefreshListView extends ListView{

    View header; //header layout
    public RefreshListView(Context context) {
        super(context);

        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);

    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }
    /**
     * Init this view ,addding top layout into listview
     * Created by xu
     */
    private void initView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        header=inflater.inflate(R.layout.header_layout,null);
        System.out.println("add top to listview");
        this.addHeaderView(header);
    }

}
