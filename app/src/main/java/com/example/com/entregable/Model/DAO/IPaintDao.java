package com.example.com.entregable.Model.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.com.entregable.Model.POJO.Paint;
import com.example.com.entregable.Model.POJO.PaintContainer;

import java.util.List;

/**
 * Created by DH on 10/7/2018.
 */

@Dao
public interface IPaintDao {
    @Query("SELECT * FROM paint")
    List<Paint> getAllPaints();

    @Query("SELECT * FROM paint WHERE artistId = :id")
    Paint getPaintByArtistID(String id);

    @Insert
    void insertAllPaints(List<Paint> paintContainer);

    @Insert
    void insertPaint(Paint paint);

    @Delete
    void deletePaint(Paint paint);
}
