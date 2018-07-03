package com.example.com.entregable.Model.DAO;

import com.example.com.entregable.Model.POJO.PaintContainer;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by DH on 3/7/2018.
 */

public interface PaintService {

    @GET("x858r")
    Call<PaintContainer> getPaints();
}
