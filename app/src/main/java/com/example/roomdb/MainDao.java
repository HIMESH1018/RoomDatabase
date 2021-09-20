package com.example.roomdb;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao {

    //insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //delete query
    @Delete
    void delete(MainData mainData);

    //delete all
    @Delete
    void reset(List<MainData>mainData);

    //update
    @Query("UPDATE table_room SET text = :sText Where ID = :sID")
    void update(int sID,String sText);

    //Get All data query
    @Query("SELECT * FROM table_room")
    List<MainData> getAll();
}
