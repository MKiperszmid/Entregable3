package com.example.com.entregable.Model.POJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by DH on 3/7/2018.
 */

@Entity
public class Paint implements Serializable {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "artistId")
    private Integer artistId;
    @ColumnInfo(name = "image")
    private String image;
    @ColumnInfo(name = "name")
    private String name;

    @Ignore
    public Paint(String image, String name, Integer artistId) {
        this.image = image;
        this.name = name;
        this.artistId = artistId;
    }

    public Paint(){

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }
}
