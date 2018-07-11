package com.example.com.entregable.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.com.entregable.Controller.ArtistController;
import com.example.com.entregable.Controller.ArtistTask;
import com.example.com.entregable.Controller.ResultListener;
import com.example.com.entregable.Model.DAO.AppDatabase;
import com.example.com.entregable.Model.POJO.Artist;
import com.example.com.entregable.Model.POJO.ArtistContainer;
import com.example.com.entregable.Model.POJO.Paint;
import com.example.com.entregable.R;
import com.example.com.entregable.Tasks.ArtistTask;
import com.example.com.entregable.Util.Functionality;
import com.example.com.entregable.View.Activities.ExhibicionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleFragment extends Fragment {

    public static final String PINTURA_KEY = "pintura";
    private TextView tvName;
    private TextView tvNationality;
    private TextView tvInfluenced;
    private ProgressBar progressBar;
    private ImageView ivImagen;
    private Paint paint;
    private List<Artist> artistList;
    private View myView;

    public DetalleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);
        artistList = new ArrayList<>();
        //FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();


        Bundle bundle = getArguments();

        paint = (Paint)bundle.getSerializable(PINTURA_KEY);

        ((ExhibicionActivity)getActivity()).getSupportActionBar().setTitle(paint.getName());

       // grabInfoArtist(paint.getArtistId().toString(), view);
        //grabAllArtists();
        myView = view;
        //grabArtist(paint.getArtistId().toString());
        ArtistTask artistTask = new ArtistTask(this, paint.getArtistId().toString());
        artistTask.execute();
        return view;
    }

    private void grabArtist(String id) {
        AppDatabase database = AppDatabase.getInstance(getContext());
        Artist artist = null;

        artist = database.artistDao().getArtistByID(id);

        if(artist == null){
            //Artista no esta en la base de datos
            grabInfoArtist(id);
        }
        else {
            //Artista SI esta en la base de datos
            setInfo(artist);
        }
    }

    private void grabAllArtists(){
        saveAllArtists();
        AppDatabase appDatabase = AppDatabase.getInstance(getContext());
        appDatabase.artistDao().getAllArtists();
    }

    private void saveAllArtists(){
        ArtistController artistController = new ArtistController();
        final AppDatabase appDatabase = AppDatabase.getInstance(getContext());
        artistController.grabAllArtists(new ResultListener<ArtistContainer>() {
            @Override
            public void finish(ArtistContainer result) {
                for(Artist artist : result.getArtists()){
                    appDatabase.artistDao().insertArtist(artist);
                }
            }
        });
    }

    public void grabInfoArtist(String id){
        ArtistController artistController = new ArtistController();
        artistController.grabArtists(new ResultListener<Artist>() {
            @Override
            public void finish(Artist result) {
                if(result != null){
                    setInfo(result);
                    AppDatabase appDatabase = AppDatabase.getInstance(myView.getContext());
                    appDatabase.artistDao().insertArtist(result);
                }
            }
        }, id);
    }

    public void setInfo(Artist artist){
        tvName = myView.findViewById(R.id.fd_tv_nombreArtista);
        tvNationality = myView.findViewById(R.id.fd_tv_nationalityArtista);
        tvInfluenced = myView.findViewById(R.id.fd_tv_influencedArtista);
        ivImagen = myView.findViewById(R.id.fd_iv_imagen);

        String name = "Artista: " + artist.getName();
        String nationality = "Nationality: " + artist.getNationality();
        String influenced = "Influenced by: " + artist.getInfluenced_by();
        Functionality.cargarImagenStorage(myView.getContext(), paint.getImage(), ivImagen);

        tvName.setText(name);
        tvNationality.setText(nationality);
        tvInfluenced.setText(influenced);
        Functionality.loadProgressbar(false, progressBar);
    }
}
