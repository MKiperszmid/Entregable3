package com.example.com.entregable.Controller;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.com.entregable.Model.DAO.AppDatabase;
import com.example.com.entregable.Model.POJO.Paint;
import com.example.com.entregable.Model.POJO.PaintContainer;
import com.example.com.entregable.R;
import com.example.com.entregable.Util.Functionality;
import com.example.com.entregable.View.Activities.ExhibicionActivity;
import com.example.com.entregable.View.Adapters.AdapterRecyclerPinturas;
import com.example.com.entregable.View.Fragments.ExhibitionFragment;

import java.util.ArrayList;
import java.util.List;

public class PaintTask extends AsyncTask<Void, Void, List<Paint>> {

    private View view;
    private List<Paint> container;
    private AppDatabase database;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private AdapterRecyclerPinturas.NotificadorCelda notificadorCelda;
    private ExhibicionActivity exhibicionActivity;

    public PaintTask(View view, ProgressBar progressBar, RecyclerView recyclerView, AdapterRecyclerPinturas.NotificadorCelda notificadorCelda, ExhibicionActivity exhibicionActivity){
        this.view = view;
        this.progressBar = progressBar;
        this.recyclerView = recyclerView;
        this.notificadorCelda = notificadorCelda;
        this.exhibicionActivity = exhibicionActivity;
        container = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        database = AppDatabase.getInstance(view.getContext());
        recyclerView = view.findViewById(R.id.fe_rv_pinturas);
        progressBar = view.findViewById(R.id.fe_pb_progress);
    }

    @Override
    protected List<Paint> doInBackground(Void... voids) {
        return database.paintDao().getAllPaints();
    }

    @Override
    protected void onPostExecute(List<Paint> paints) {
        container = paints;
        if(container == null || container.size() <= 0){
            grabInfo();
        }
        else{
            loadRecycler();
        }
    }

    private void loadRecycler() {
        AdapterRecyclerPinturas adapterRecyclerPinturas = new AdapterRecyclerPinturas(container, notificadorCelda);
        recyclerView.setAdapter(adapterRecyclerPinturas);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        //recycler.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        Functionality.loadProgressbar(false, progressBar);
    }

    private void grabInfo() {
        Functionality.loadProgressbar(true, progressBar);
        PaintController controller = new PaintController();

        controller.getPaints(new ResultListener<PaintContainer>() {
            @Override
            public void finish(PaintContainer result) {
                try {
                    database.paintDao().insertAllPaints(result.getPaints());
                } catch (IllegalStateException e){
                    Log.d("PAINTTASK-GRABINFO", "Error a la hora de ingresar pinturas en la base de datos.");
                }
                loadRecycler();
            }
        });

        exhibicionActivity.getSupportActionBar().setTitle(ExhibitionFragment.NOMBRE_SECCION);
    }
}
