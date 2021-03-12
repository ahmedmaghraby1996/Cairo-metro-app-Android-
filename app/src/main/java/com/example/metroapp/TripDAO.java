package com.example.metroapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TripDAO {
    @Query("Select fromto From trips")
    List<String> selectTrips();
@Insert
    long insert(Trip trip);
@Query("Select details From trips where fromto=:data")
    String selectDetails(String data);

    @Query("Select notes From trips where fromto=:data")
    String selectNotes(String data);
@Query("Select * from trips")
List<Trip> selectall();
@Delete()
   int delete(Trip trip);
}
