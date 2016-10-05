package com.snapchat.team2.snapchat.dataJsonModel;

/**
 * Created by xu on 2016/10/5.
 */
public class userModel {
    private String id;
    private  String name;
    private String email;
    private String birth;
    private  String password;
    private String friendwith;

    public userModel(String id, String name, String email, String birth, String password, String friendwith) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.password = password;
        this.friendwith = friendwith;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFriendwith() {
        return friendwith;
    }

    public void setFriendwith(String friendwith) {
        this.friendwith = friendwith;
    }
}
