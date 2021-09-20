package com.example.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//add database entities
@Database(entities = {MainData.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    //create db instance
    private static  RoomDB db;

    //define dtabase name
    private static String DATABAS_NAME ="Room_Database";

    public synchronized static RoomDB getInstance(Context context){

        //check condition

        if(db == null){
            //when db null
            //insitialize db
            db = Room.databaseBuilder(context.getApplicationContext()
                    ,RoomDB.class,DATABAS_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }

    public abstract MainDao mainDao();

}
