package com.example.com.entregable.Tasks;

import android.os.AsyncTask;

import com.example.com.entregable.Model.DAO.AppDatabase;
import com.example.com.entregable.Model.POJO.Paint;
import com.example.com.entregable.View.Fragments.ExhibitionFragment;

import java.util.List;

public class PaintTask extends AsyncTask<Void, Void, List<Paint>> {

    private ExhibitionFragment fragment;
    private AppDatabase database;

    public PaintTask(ExhibitionFragment fragment){
        this.fragment = fragment;
    }

    @Override
    protected void onPreExecute() {
        database = AppDatabase.getInstance(fragment.getContext());

    }

    @Override
    protected List<Paint> doInBackground(Void... voids) {
        return database.paintDao().getAllPaints();
    }

    @Override
    protected void onPostExecute(List<Paint> paints) {

        if(paints == null || paints.size() <= 0){
            fragment.grabInfo();
        }
        else {
            fragment.loadRecycler(paints);
        }
    }
}
