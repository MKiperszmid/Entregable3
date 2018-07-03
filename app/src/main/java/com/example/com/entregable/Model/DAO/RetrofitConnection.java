package com.example.com.entregable.Model.DAO;

import com.example.com.entregable.Controller.ResultListener;
import com.example.com.entregable.Model.POJO.PaintContainer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DH on 3/7/2018.
 */

public class RetrofitConnection {
    private Retrofit retrofit;
    private PaintService service;

    public RetrofitConnection(){
        retrofit = new Retrofit.Builder().baseUrl("https://api.myjson.com/bins/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(PaintService.class);
    }

    public void getPaints(final ResultListener<PaintContainer> listener){
        Call<PaintContainer> call = service.getPaints();
        call.enqueue(new Callback<PaintContainer>() {
            @Override
            public void onResponse(Call<PaintContainer> call, Response<PaintContainer> response) {
                listener.finish(response.body());
            }

            @Override
            public void onFailure(Call<PaintContainer> call, Throwable t) {
                listener.finish(null);
            }
        });
    }
}
