package com.snapchat.team2.snapchat.dataJsonModel;

/**
 * Created by xu on 2016/10/15.
 */
public class GetChatResponseModelWithName {
    private String id = null;
    private String  from = null;
    private String name = null;
    private String to = null;
    private String status = null;
    private String content = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
