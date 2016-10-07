package com.snapchat.team2.snapchat.ListAdapterDataModel;

/**
 * Created by Kun on 10/3/2016.
 */

public class StoryListItem {
    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    private String title;
    private  String subTitle;
    private int imageResId;
    private boolean favourite = false;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}