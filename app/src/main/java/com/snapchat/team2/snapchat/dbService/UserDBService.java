package com.snapchat.team2.snapchat.dbService;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snapchat.team2.snapchat.dbHelper.DBManager;
import com.snapchat.team2.snapchat.dbModel.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bm on 27/09/2016.
 */

public class UserDBService {

    private DBManager dbManager;


    public UserDBService(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public User getUserByUserEmail(String userEmail){

        SQLiteDatabase database;
        database=dbManager.openDB();

        Cursor cursor= database.rawQuery("select * from user where user_email=?", new String[]{userEmail});
        User u=new User();
        while (cursor.moveToNext()){
            u=new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
        }

        dbManager.closeDB(database);
        return u;
    }

    public List<User> getFriendsByUserId(String id){
        SQLiteDatabase database;
        database=dbManager.openDB();

        Cursor cursor= database.rawQuery("select friendwith from user where user_id=?",new String[]{id});
        List<User> result=new ArrayList<User>();
        while (cursor.moveToNext()){
            String friend_id_string = cursor.getString(0);
            String[] friend_ids = null;
            friend_ids = friend_id_string.split(",");
            for(String friend_id:friend_ids){
                Cursor c = database.rawQuery("select * from user where user_id=?",new String[]{friend_id});
                while (c.moveToNext()){
                    User u = new User(c.getInt(0),c.getString(1),c.getString(2));
                    result.add(u);
                }


            }
        }

        dbManager.closeDB(database);
        return result;
    }

    public String login(String email,String password){
        int result = 0;

        SQLiteDatabase database;
        database=dbManager.openDB();
        System.out.println("查询的email 是"+ email);
        System.out.println("查询的password 是"+ password);
        Cursor cursor = database.rawQuery("select user_id from user where user_email=? and password=?",new String[]{email,password});
        while(cursor.moveToNext()){
            result = cursor.getInt(0);
            System.out.println("获得了一个user is "+ result);
        }

        if(result == 0){
            return null;
        }
        else{
            return result+"";
        }

    }


    public String[] getClicks(String id){
        SQLiteDatabase database;
        database=dbManager.openDB();
        Cursor cursor= database.rawQuery("select * from clicks where user_id=?",new String[]{id});

        if (cursor.getCount()<=0||cursor==null){
            setClicks(id);
            System.out.println("I am here!!!!!!!!!!");

        }

        String[] result = {"","",""};

        while (cursor.moveToNext()){
            result[0] = cursor.getString(1);
            result[1] = cursor.getString(2);
            result[2] = cursor.getString(3);
        }
        dbManager.closeDB(database);

        return result;
    }

    public void setClicks(String tclicks,String nclicks,String bclicks,String userid){
        SQLiteDatabase database;
        database=dbManager.openDB();


        ContentValues values = new ContentValues();
        values.put("tech_clicks", tclicks);
        values.put("news_clicks", nclicks);
        values.put("bussiness_clicks", bclicks);


        database.update("clicks", values,"user_id=?", new String[] { userid });

        dbManager.closeDB(database);


    }

    public void setClicks(String userid){
        SQLiteDatabase database;
        database=dbManager.openDB();


        ContentValues values = new ContentValues();
        System.out.println("In the set ");
        values.put("user_id", userid);
        values.put("tech_clicks", "2");
        values.put("news_clicks", "2");
        values.put("bussiness_clicks", "2");


        database.insert("clicks", null, values);
        dbManager.closeDB(database);


    }

}
