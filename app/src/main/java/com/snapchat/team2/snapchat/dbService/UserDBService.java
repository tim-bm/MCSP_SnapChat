package com.snapchat.team2.snapchat.dbService;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snapchat.team2.snapchat.dbHelper.DBManager;
import com.snapchat.team2.snapchat.dbModel.User;

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

}
