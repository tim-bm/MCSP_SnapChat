package com.snapchat.team2.snapchat.ListAdapterDataModel;

/**
 * Created by Kun on 10/16/2016.
 */
public class MemoryStoryListItem {

    private String id;
    private String userId;
    private String photoContent;
    private String to;
    private String category;
    private String ifSend;
    private String name;

    public MemoryStoryListItem(String id, String userId, String photoContent, String name, String to, String category, String ifSend) {
        this.id = id;
        this.userId = userId;
        this.photoContent = photoContent;
        this.name = name;
        this.to = to;
        this.category = category;
        this.ifSend = ifSend;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhotoContent() {
        return photoContent;
    }

    public void setPhotoContent(String photoContent) {
        this.photoContent = photoContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIfSend() {
        return ifSend;
    }

    public void setIfSend(String ifSend) {
        this.ifSend = ifSend;
    }

}
