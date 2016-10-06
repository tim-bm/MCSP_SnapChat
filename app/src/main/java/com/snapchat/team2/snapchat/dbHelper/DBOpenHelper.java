package com.snapchat.team2.snapchat.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bm on 27/09/2016.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static DBOpenHelper instance=null;
    private final static  String DATABASE_NAME="SnapChatDatabase";
    private  final static int DATABASE_VERSION=1;

    //Create Table statements:
    final String CREATE_TABLE_USER="CREATE TABLE \"user\" (" +
            "\"user_id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "\"user_name\"  TEXT NOT NULL," +
            "\"user_email\"  TEXT NOT NULL, " +
            "\"password\" TEXT NOT NULL, "+
            "\"friendwith\" TEXT NOT NULL"+
            ");";


    //initial data
    final String INSERT_USER_ADMIN1="INSERT INTO \"user\" VALUES (1, 'admin', 'admin@snapchat.com','123fdfd','2,3');";
    final String INSERT_USER_ADMIN2="INSERT INTO \"user\" VALUES (2, 'admin2', 'admin2@snapchat.com','123fdfd2','1');";
    final String INSERT_USER_ADMIN3="INSERT INTO \"user\" VALUES (3, 'admin3', 'admin3@snapchat.com','123fdfd3','1');";


    private DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static DBOpenHelper getInstance(Context context){

        if(instance==null){
            System.out.println("db openhelper是空");
            synchronized (DBOpenHelper.class) {
                if (instance == null) {

                    instance = new DBOpenHelper(context);
                }
            }
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //create table

        sqLiteDatabase.execSQL(CREATE_TABLE_USER);


        //insert data
        sqLiteDatabase.execSQL(INSERT_USER_ADMIN1);
        sqLiteDatabase.execSQL(INSERT_USER_ADMIN2);
        sqLiteDatabase.execSQL(INSERT_USER_ADMIN3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
