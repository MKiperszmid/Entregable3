package com.example.com.entregable.Controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.com.entregable.Model.DAO.AppDatabase;
import com.example.com.entregable.Model.POJO.Artist;
import com.example.com.entregable.Model.POJO.Paint;
import com.example.com.entregable.R;
import com.example.com.entregable.Util.Functionality;

public class ArtistTask extends AsyncTask<Void, Void, Artist> {

    private View view;
    private AppDatabase database;
    private String id;
    private TextView tvName, tvNationality, tvInfluenced;
    private ImageView ivImagen;
    private ProgressBar progressBar;
    private Paint paint;
    private Artist artist;

    public ArtistTask(View view, Paint paint, TextView tvName, TextView tvNationality, TextView tvInfluenced, ImageView ivImagen, ProgressBar progressBar){
        this.paint = paint;
        this.view = view;



        this.id = paint.getArtistId().toString();
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        database = AppDatabase.getInstance(view.getContext());
        progressBar = view.findViewById(R.id.fd_pb_progress);
        Functionality.loadProgressbar(true, progressBar);
    }

    @Override
    protected Artist doInBackground(Void... voids) {
        return database.artistDao().getArtistByID(id);
    }

    @Override
    protected void onPostExecute(Artist myArtist) {
        this.artist = myArtist;
        if(artist == null){
            grabInfo();
        }
        else {
            setInfo();
        }
    }

    private void grabInfo(){
        ArtistController artistController = new ArtistController();
        artistController.grabArtists(new ResultListener<Artist>() {
            @Override
            public void finish(Artist result) {
                if(result != null){
                    artist = result;
                    setInfo();
                    try {
                        database.artistDao().insertArtist(result);
                    }
                    catch (IllegalStateException e){
                        //Modo Avion. Entra a artist no precargado. Sale a la exhibicion. Saca modo avion. Esperar 10 segundos.
                        Log.d("ARTISTTASK-GRABINFO", "El usuario se fue de la pantalla del artista, mientras este se estaba ingresando a la base de datos.");
                    }
                }
            }
        }, id);
    }

    private void setInfo(){
        tvName = view.findViewById(R.id.fd_tv_nombreArtista);
        tvNationality = view.findViewById(R.id.fd_tv_nationalityArtista);
        tvInfluenced = view.findViewById(R.id.fd_tv_influencedArtista);
        ivImagen = view.findViewById(R.id.fd_iv_imagen);

        String name = "Artista: " + artist.getName();
        String nationality = "Nationality: " + artist.getNationality();
        String influenced = "Influenced by: " + artist.getInfluenced_by();
        Functionality.cargarImagenStorage(view.getContext(), paint.getImage(), ivImagen);

        tvName.setText(name);
        tvNationality.setText(nationality);
        tvInfluenced.setText(influenced);
        Functionality.loadProgressbar(false, progressBar);
    }
}
