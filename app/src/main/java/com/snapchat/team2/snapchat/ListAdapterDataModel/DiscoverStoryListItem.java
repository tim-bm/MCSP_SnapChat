package com.snapchat.team2.snapchat.ListAdapterDataModel;

import android.widget.ImageView;

/**
 * Created by Kun on 10/3/2016.
 */

public class DiscoverStoryListItem {

    private String id;
    private String title;
    private  String text;
    private String image;
    private String categoryId;

    public DiscoverStoryListItem(String id, String title, String text, String image, String categoryId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.image = image;
        this.categoryId = categoryId;
    }

    public DiscoverStoryListItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}