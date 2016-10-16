package com.snapchat.team2.snapchat.dataJsonModel;

/**
 * Created by xu on 2016/10/16.
 */
public class ImageDataModel {
    private String id;
    private String userId;
    private String photoContent;
    private String to;
    private String category;
    private String ifsend;
    private String name;

    public ImageDataModel(String id, String userId, String photoContent, String to, String category, String ifsend,String name) {
        this.id = id;
        this.userId = userId;
        this.photoContent = photoContent;
        this.to = to;
        this.category = category;
        this.ifsend = ifsend;
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
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

    public String getIfsend() {
        return ifsend;
    }

    public void setIfsend(String ifsend) {
        this.ifsend = ifsend;
    }
}
