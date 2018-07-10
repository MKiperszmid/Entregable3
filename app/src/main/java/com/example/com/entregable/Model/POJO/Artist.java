package com.example.com.entregable.Model.POJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Artist {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "artistId")
    private String artistId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "nationality")
    private String nationality;
    @ColumnInfo(name = "influenced_by")
    private String Influenced_by;

    @Ignore
    public Artist(String Influenced_by, String nationality, String name, String artistId) {
        this.Influenced_by = Influenced_by;
        this.artistId = artistId;
        this.name = name;
        this.nationality = nationality;
    }

    public Artist(){

    }

    public String getInfluenced_by() {
        return Influenced_by;
    }

    public void setInfluenced_by(String influenced_by) {
        Influenced_by = influenced_by;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Artist)) return false;
        Artist artist = (Artist) obj;

        return artist.getArtistId().equals(this.getArtistId())
                && artist.getName().equals(this.getName())
                && artist.getNationality().equals(this.getNationality());
    }
}
