package com.snapchat.team2.snapchat.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by bm on 27/09/2016.
 */

public class DBManager {

    private static DBOpenHelper dbOpenHelper;
    private static SQLiteDatabase db;

    private static  int mCount;

    private static DBManager instance=null;

    private DBManager(Context context){

        dbOpenHelper= DBOpenHelper.getInstance(context);

    }

    public static synchronized  DBManager getInstance(Context context){
        if(instance==null){
            System.out.println("dbmanager是空");

            instance=new DBManager(context);
        }

        return instance;
    }

    public synchronized  SQLiteDatabase openDB(){
        if(mCount==0){
            db=dbOpenHelper.getWritableDatabase();
        }

        mCount++;
        return db;
    }

    public synchronized void closeDB(SQLiteDatabase database){
        mCount--;
        if(mCount==0){

            database.close();
        }
    }
}
