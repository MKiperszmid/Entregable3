package com.example.com.entregable.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.com.entregable.Controller.ArtistController;
import com.example.com.entregable.Controller.ResultListener;
import com.example.com.entregable.Model.POJO.Artist;
import com.example.com.entregable.Model.POJO.Paint;
import com.example.com.entregable.R;
import com.example.com.entregable.Util.Functionality;
import com.example.com.entregable.View.Activities.ExhibicionActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleFragment extends Fragment {

    public static final String PINTURA_KEY = "pintura";
    private TextView tvName;
    private TextView tvNationality;
    private TextView tvInfluenced;
    private ProgressBar progressBar;

    public DetalleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);
        progressBar = view.findViewById(R.id.fd_pb_progress);

        Functionality.loadProgressbar(true, progressBar);

        Bundle bundle = getArguments();

        Paint paint = (Paint)bundle.getSerializable(PINTURA_KEY);

        ((ExhibicionActivity)getActivity()).getSupportActionBar().setTitle(paint.getName());

        grabInfoArtist(paint.getArtistId().toString(), view);

        return view;
    }

    private void grabInfoArtist(String id, final View view){
        ArtistController artistController = new ArtistController();
        artistController.grabArtists(new ResultListener<Artist>() {
            @Override
            public void finish(Artist result) {
                if(result != null){
                    setInfo(view, result);
                }
            }
        }, id);
    }

    private void setInfo(View view, Artist artist){
        tvName = view.findViewById(R.id.fd_tv_nombreArtista);
        tvNationality = view.findViewById(R.id.fd_tv_nationalityArtista);
        tvInfluenced = view.findViewById(R.id.fd_tv_influencedArtista);

        String name = "Artista: " + artist.getName();
        String nationality = "Nationality: " + artist.getNationality();
        String influenced = "Influenced by: " + artist.getInfluenced_by();

        tvName.setText(name);
        tvNationality.setText(nationality);
        tvInfluenced.setText(influenced);
        Functionality.loadProgressbar(false, progressBar);
    }
}
