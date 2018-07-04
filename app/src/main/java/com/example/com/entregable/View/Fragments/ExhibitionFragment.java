package com.example.com.entregable.View.Fragments;



import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.com.entregable.Controller.ArtistController;
import com.example.com.entregable.Controller.ResultListener;
import com.example.com.entregable.Model.POJO.Artist;
import com.example.com.entregable.Model.POJO.ArtistContainer;
import com.example.com.entregable.Model.POJO.Paint;
import com.example.com.entregable.Model.POJO.PaintContainer;
import com.example.com.entregable.R;
import com.example.com.entregable.Controller.PaintController;
import com.example.com.entregable.Util.Functionality;
import com.example.com.entregable.View.Activities.ExhibicionActivity;
import com.example.com.entregable.View.Adapters.AdapterRecyclerPinturas;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExhibitionFragment extends Fragment implements AdapterRecyclerPinturas.NotificadorCelda{

    private RecyclerView recycler;
    private List<Artist> artistList;
    private NotificadorExhibitionActivity notificadorExhibitionActivity;
    private String nombreSeccion = "Pinturas";
    private ProgressBar progressBar;

    public ExhibitionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notificadorExhibitionActivity = (NotificadorExhibitionActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exhibition, container, false);
        recycler = view.findViewById(R.id.fe_rv_pinturas);
        progressBar = view.findViewById(R.id.fe_pb_progress);
        grabInfo();
        return view;
    }

    private void grabInfo(){
        Functionality.loadProgressbar(true, progressBar);
        PaintController controller = new PaintController();

        controller.getPaints(new ResultListener<PaintContainer>() {
            @Override
            public void finish(PaintContainer result) {
                AdapterRecyclerPinturas adapterRecyclerPinturas = new AdapterRecyclerPinturas(result.getPaints(), ExhibitionFragment.this);
                recycler.setAdapter(adapterRecyclerPinturas);
                recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                //recycler.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

                Functionality.loadProgressbar(false, progressBar);

            }
        });


        ((ExhibicionActivity)getActivity()).getSupportActionBar().setTitle(nombreSeccion);
    }

    @Override
    public void notificarPintura(Paint paint) {
        notificadorExhibitionActivity.notificar(paint);
    }

    public interface NotificadorExhibitionActivity{
        void notificar(Paint paint);
    }
}
