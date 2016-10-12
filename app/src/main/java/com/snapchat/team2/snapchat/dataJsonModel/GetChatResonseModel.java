package com.snapchat.team2.snapchat.dataJsonModel;

/**
 * Created by xu on 2016/10/12.
 */
public class GetChatResonseModel {
    private String id = null;


    private String  from = null;
    private String to = null;
    private String status = null;
    private String content = null;


    public GetChatResonseModel(String id,  String from, String to,String status, String content) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.status = status;
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
