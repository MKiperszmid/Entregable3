package com.example.com.entregable.Controller;

import com.example.com.entregable.Model.DAO.RetrofitConnection;
import com.example.com.entregable.Model.POJO.PaintContainer;

/**
 * Created by DH on 3/7/2018.
 */

public class PaintController {
    private RetrofitConnection retrofitConnection;

    public PaintController(){
        retrofitConnection = new RetrofitConnection();
    }

    public void getPaints(final ResultListener<PaintContainer> listener){
        retrofitConnection.getPaints(new ResultListener<PaintContainer>() {
            @Override
            public void finish(PaintContainer result) {
                listener.finish(result);
            }
        });
    }
}
