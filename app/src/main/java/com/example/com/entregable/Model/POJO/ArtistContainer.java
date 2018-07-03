package com.example.com.entregable.Model.POJO;

import java.util.List;

public class ArtistContainer {
    private List<Artist> artists;

    public ArtistContainer(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
