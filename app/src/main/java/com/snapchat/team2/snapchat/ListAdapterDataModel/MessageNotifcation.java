package com.snapchat.team2.snapchat.ListAdapterDataModel;

/**
 * Created by xu on 2016/10/15.
 */
public class MessageNotifcation {
    private String fromID;

    private String content;

    private int itemtype;

    public MessageNotifcation( String fromID,String content, int itemtype) {
        this.fromID = fromID;

        this.content = content;
        this.itemtype = itemtype;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getItemtype() {
        return itemtype;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }
}
