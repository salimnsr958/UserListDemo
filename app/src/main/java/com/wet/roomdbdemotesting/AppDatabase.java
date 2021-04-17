package com.wet.roomdbdemotesting;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {UserEntity.class},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object lock = new Object();
    private static AppDatabase instance;

    private static final String DB_NAME = "demo_user_db";

    public static AppDatabase getInstance(Context context) {
        if (instance==null)
        {
            synchronized (lock)
            {
                instance  = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.DB_NAME).build();
            }
        }
        return instance;
    }

    public abstract UserDao userDao();
}
