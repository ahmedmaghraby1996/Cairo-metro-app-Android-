package com.example.metroapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "location")
public class Location {
    @PrimaryKey(autoGenerate = true)
   public int id;
   public String longtiude;
    public String latitude;
 public    String name;

}
