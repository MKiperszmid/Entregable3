package com.example.com.entregable.Model.DAO;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.com.entregable.Model.POJO.Artist;

/**
 * Created by DH on 10/7/2018.
 */

@Database(entities = Artist.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

}
