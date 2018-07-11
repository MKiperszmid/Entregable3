package com.example.com.entregable.Tasks;

import android.os.AsyncTask;

import com.example.com.entregable.Model.DAO.AppDatabase;
import com.example.com.entregable.Model.POJO.Paint;
import com.example.com.entregable.View.Fragments.ExhibitionFragment;

import java.util.ArrayList;
import java.util.List;

public class PaintTask extends AsyncTask<Void, Void, List<Paint>> {

    private ExhibitionFragment fragment;
    private List<Paint> paintList;
    private AppDatabase database;

    public PaintTask(ExhibitionFragment fragment){
        this.fragment = fragment;
    }

    @Override
    protected void onPreExecute() {
        paintList = new ArrayList<>();
        database = AppDatabase.getInstance(fragment.getContext());

    }

    @Override
    protected List<Paint> doInBackground(Void... voids) {
        return database.paintDao().getAllPaints();
    }

    @Override
    protected void onPostExecute(List<Paint> paints) {
        paintList = paints;

        if(paintList == null || paintList.size() <= 0){
            fragment.grabInfo();
        }
        else {
            fragment.loadRecycler(paintList);
        }
    }
}
