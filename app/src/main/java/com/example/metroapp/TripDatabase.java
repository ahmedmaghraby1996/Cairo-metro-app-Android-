package com.example.metroapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Trip.class,Location.class},version = 1)
public abstract class TripDatabase extends RoomDatabase {
    public abstract TripDAO TripDAO();
    public abstract LocationDAO LocationDAO();

    private static TripDatabase ourInstance;

    public static TripDatabase getInstance(Context context) {

        if (ourInstance == null) {

            ourInstance = Room.databaseBuilder(context,

                    TripDatabase.class, "tripss.db")
                    .createFromAsset("databases/tripss.db")
                    .allowMainThreadQueries ()
                    .build();
        }

        return ourInstance;

    }
}
