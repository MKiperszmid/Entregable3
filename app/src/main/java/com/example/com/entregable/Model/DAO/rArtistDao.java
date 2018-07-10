package com.example.com.entregable.Model.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.com.entregable.Model.POJO.Artist;

import java.util.List;

@Dao
public interface rArtistDao {

    @Query("SELECT * FROM artist")
    List<Artist> getAllArtists();

    @Query("SELECT * FROM artist WHERE artistId LIKE :id LIMIT 1")
    Artist findById(String id);

    @Insert
    void insertAll(Artist... artists);
}
