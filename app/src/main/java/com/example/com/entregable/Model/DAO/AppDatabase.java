package com.example.com.entregable.Model.DAO;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.com.entregable.Model.POJO.Artist;
import com.example.com.entregable.Model.POJO.Paint;

/**
 * Created by DH on 10/7/2018.
 */

@Database(entities = {Artist.class, Paint.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public static final String DATABASE_NAME = "Entregable3DB";

    public abstract IArtistDao artistDao();
    public abstract IPaintDao paintDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
