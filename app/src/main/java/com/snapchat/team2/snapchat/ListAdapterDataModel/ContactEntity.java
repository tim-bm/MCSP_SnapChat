package com.snapchat.team2.snapchat.ListAdapterDataModel;

/**
 * Created by xu on 2016/10/15.
 */
public class ContactEntity {
    private String name;
    private String number;

    public ContactEntity(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
