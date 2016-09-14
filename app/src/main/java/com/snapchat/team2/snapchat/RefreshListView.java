package com.snapchat.team2.snapchat;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by xu on 2016/9/14.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener{

    View header; //header layout
    private int headerHeight;// height of the top layout
    private int topItemIndex;
    private boolean isRemark; //Tell whether I touch the list when the list shows its top
    private int startY; //the Y number when pulling down
    private int state;  //tell the current status of the list view

    int scrollState; //current scroll state

    private final int NORMAL= 0;
    private final int PULL= 1;
    private final int RELEASE= 2;
    private final int REFRESHING= 3;

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
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        Log.i("tag","topHeight is "+ headerHeight);
        setTopPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);

    }

    /**
     * Tell the Listview the width and the height that the top item occupied
     * Created by xu
     */
    private void measureView(View view){
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if(p==null){
            p=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width =  ViewGroup.getChildMeasureSpec(0,0,p.width);
        int height;
        int tempHeight = p.height;
        if(tempHeight>0){
            height = MeasureSpec.makeMeasureSpec(tempHeight,MeasureSpec.EXACTLY);
        }else{
            height = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        }
        view.measure(width,height);
    }
    /**
     * Set the top padding to the header in order to hide to top
     * Created by xu
     */
    private void setTopPadding(int topPadding){
        header.setPadding(header.getPaddingLeft(),topPadding,header.getPaddingRight(),header.getPaddingBottom());
        header.invalidate();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState=scrollState;

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.topItemIndex = firstVisibleItem;

    }

    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(this.topItemIndex == 0){
                    System.out.println("Now I am touching , and the top one is the first item of the list");
                    //show the header
                    this.isRemark=true;
                    this.startY=(int)event.getY();
                    break;

                }
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("now I am moving");
                onMove(event);
                break;

            case MotionEvent.ACTION_UP:
                System.out.println("now I leave");
                break;

        }

        return true;
    }

    /**
     * Tell the action when moving
     * Created by xu
     */
    private void onMove(MotionEvent event){
        System.out.println("当前的状态是"+state);
        if(!isRemark){
            System.out.println("是否在最顶端"+isRemark);
            return;
        }
        int tempY=(int)event.getY();
        int move_distance =tempY - this.startY;
        System.out.println("移动距离:"+ move_distance);
        int topPadding = move_distance - headerHeight;
        switch (state){
            case NORMAL:
                if(move_distance>0){
                    state=PULL;
                }
                break;
            case PULL:
                setTopPadding(topPadding);
                if(move_distance>headerHeight+30 && this.scrollState==SCROLL_STATE_TOUCH_SCROLL){
                    state = RELEASE;
                }
                else if(move_distance<=0){
                    state=NORMAL;
                    isRemark=false;
                }
                break;
            case RELEASE:
                setTopPadding(topPadding);
                if(move_distance<headerHeight+30){
                    state = PULL;
                }else if(move_distance<=0){
                    state=NORMAL;
                    isRemark=false;
                }
                break;
        }


    }
}
