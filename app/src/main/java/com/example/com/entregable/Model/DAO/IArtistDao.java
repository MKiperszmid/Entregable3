package com.example.com.entregable.Model.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.com.entregable.Model.POJO.Artist;

import java.util.List;

/**
 * Created by DH on 10/7/2018.
 */

@Dao
public interface IArtistDao {
    @Query("SELECT * FROM artist")
    List<Artist> getAllArtists();

    @Query("SELECT * FROM artist WHERE artistId = :id")
    Artist getArtistByID(String id);

    @Insert
    void insertArtist(Artist artist);

    @Delete
    void deleteArtist(Artist artist);


}
