package com.snapchat.team2.snapchat.ListAdapterDataModel;

/**
 * Created by xu on 2016/10/10.
 */

public class ChatMessage {
    private String id;
    private String content;
    //item type ,1 means send 2 means accept
    private int itemType;

    public ChatMessage(String content, int itemType) {
        this.content = content;
        this.itemType = itemType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
