package com.snapchat.team2.snapchat.ListAdapterDataModel;

/**
 * Created by xu on 2016/10/2.
 */
public class Friend {
    private String initial_letter;
    private String name;
    private int item_type;
    private String user_id;

    public Friend(String initial_letter, String name, int item_type,String user_id) {
        this.initial_letter = initial_letter;
        this.name = name;
        this.item_type = item_type;
        this.user_id=user_id;
    }

    public Friend(){

    }

    public String getInitial_letter() {
        return initial_letter;
    }

    public void setInitial_letter(String initial_letter) {
        this.initial_letter = initial_letter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
