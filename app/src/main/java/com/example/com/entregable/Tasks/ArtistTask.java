package com.example.com.entregable.Tasks;

import android.os.AsyncTask;

import com.example.com.entregable.Model.DAO.AppDatabase;
import com.example.com.entregable.Model.POJO.Artist;
import com.example.com.entregable.View.Fragments.DetalleFragment;

public class ArtistTask extends AsyncTask<Void, Void, Artist> {

    private DetalleFragment fragment;
    private AppDatabase database;
    private String id;

    public ArtistTask(DetalleFragment fragment, String id){
        this.fragment = fragment;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        database = AppDatabase.getInstance(fragment.getContext());
    }

    @Override
    protected Artist doInBackground(Void... voids) {
        return database.artistDao().getArtistByID(id);
    }

    @Override
    protected void onPostExecute(Artist artist) {

        if(artist == null){
            fragment.grabInfoArtist(id);
        }
        else {
            fragment.setInfo(artist);
        }
    }
}