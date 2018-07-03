package com.example.com.entregable.Controller;

import com.example.com.entregable.Model.DAO.ArtistDao;
import com.example.com.entregable.Model.POJO.Artist;
import com.example.com.entregable.Model.POJO.ArtistContainer;

public class ArtistController {
    private ArtistDao artistDao;

    public ArtistController(){
        this.artistDao = new ArtistDao();
    }

    public void grabArtists(final ResultListener<Artist> listener, String id){
        artistDao.grabArtists(new ResultListener<Artist>() {
            @Override
            public void finish(Artist result) {
                listener.finish(result);
            }
        }, id);
    }
}
