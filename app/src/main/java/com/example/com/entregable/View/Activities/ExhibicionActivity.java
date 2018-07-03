package com.example.com.entregable.View.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.com.entregable.Controller.PaintController;
import com.example.com.entregable.Controller.ResultListener;
import com.example.com.entregable.Model.POJO.Paint;
import com.example.com.entregable.Model.POJO.PaintContainer;
import com.example.com.entregable.R;
import com.example.com.entregable.View.Adapters.AdapterRecyclerPinturas;

public class ExhibicionActivity extends AppCompatActivity implements AdapterRecyclerPinturas.NotificadorCeldaActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibicion);
        recyclerView = findViewById(R.id.ae_rv_pinturas);
        grabInfo();
    }

    private void grabInfo(){
        PaintController controller = new PaintController();
        controller.getPaints(new ResultListener<PaintContainer>() {
            @Override
            public void finish(PaintContainer result) {
                AdapterRecyclerPinturas adapterRecyclerPinturas = new AdapterRecyclerPinturas(result.getPaints(), ExhibicionActivity.this);
                recyclerView.setAdapter(adapterRecyclerPinturas);
                recyclerView.setLayoutManager(new LinearLayoutManager(ExhibicionActivity.this, LinearLayoutManager.VERTICAL, false));
                //recyclerView.setLayoutManager(new GridLayoutManager(ExhibicionActivity.this, 2, GridLayoutManager.VERTICAL, false));
            }
        });
    }

    @Override
    public void notificarPintura(Paint paint) {
        Toast.makeText(this, paint.getName(), Toast.LENGTH_SHORT).show();
    }
}
