package com.snapchat.team2.snapchat.dbService;

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

}
