package com.example.com.entregable.Model.DAO;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.com.entregable.Model.POJO.Artist;

@Database(entities = {Artist.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public static final String DATABASE_NAME = "entregable3DB";
    private static AppDatabase instance;

    public abstract rArtistDao artistDao();

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class).build();
        }
        return instance;
    }

    public static AppDatabase getFileDatabase(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }

    public static void destroyInstance(){ instance = null; }
}
