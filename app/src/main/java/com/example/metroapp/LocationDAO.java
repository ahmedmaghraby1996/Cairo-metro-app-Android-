package com.example.metroapp;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDAO {
    @Insert
    long insert(Location location);
    @Query("Select Distinct * From location")
    List<Location> selectalllocation();
}
