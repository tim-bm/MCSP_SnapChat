package com.snapchat.team2.snapchat.Tools;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by xu on 2016/10/7.
 */
public class ActManager {

    private static Stack<Activity> activityStack;
    private static ActManager instance;

    /**
     * single instance
     * @return
     */
    public static ActManager getAppManager() {
        if (instance == null) {
            instance = new ActManager();
        }
        return instance;
    }

    private ActManager(){
        initActivityStack();
    }


    private void initActivityStack() {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
    }

    /**
     * Add an Activity to the stack
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * get the current activity
     * @return
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * finish activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public int getSize(){
        return activityStack.size();
    }



    /**
     * finish current activity
     */
    public void finishActivity() {

        Activity activity = activityStack.lastElement();

        finishActivity(activity);
    }

    /**
     * finish the activities with specified class name
     */
    public void finishActivity(Class<?> cls) {
        List<Activity> activities = new ArrayList<Activity>();
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                // finishActivity(activity);
                activities.add(activity);
            }
        }

        activityStack.removeAll(activities);
        for (Activity activity : activities) {
            finishActivity(activity);
        }
    }

    /**
     * finish all activities in the stack
     */
    public void finishAllActivity() {
        int size = activityStack.size();
        System.out.println("栈的大小是 "+size);
        for (int i = 0; i < size; i++) {
            if (null != activityStack.get(i)) {
                Activity activity = activityStack.get(i);
                if (!activity.isFinishing()) {
                    System.out.println("结束一个activity");
                    activity.finish();
                }
            }
        }
        activityStack.clear();
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }

    public void removeFromStack(Activity activity){
        if (activity != null) {
            activityStack.remove(activity);
            //activity.finish();
            //activity = null;
        }
    }
}
