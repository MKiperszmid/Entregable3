package com.example.com.entregable.View.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.com.entregable.Model.POJO.Paint;
import com.example.com.entregable.R;
import com.example.com.entregable.Util.Functionality;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by DH on 3/7/2018.
 */

public class AdapterRecyclerPinturas extends RecyclerView.Adapter {
    private List<Paint> paints;
    private NotificadorCelda notificadorCelda;

    public AdapterRecyclerPinturas(List<Paint> paints, NotificadorCelda notificadorCelda){
        this.paints = paints;
        this.notificadorCelda = notificadorCelda;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.celda_recycler_pinturas, parent, false);
        return new PinturasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PinturasViewHolder pinturasViewHolder = (PinturasViewHolder)holder;
        pinturasViewHolder.bindPintura(paints.get(position));
    }

    @Override
    public int getItemCount() {
        if(paints == null)
            return 0;
        return paints.size();
    }

    public class PinturasViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public PinturasViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.crp_iv_pinturaImagen);
            textView = itemView.findViewById(R.id.crp_tv_pinturaName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificadorCelda.notificarPintura(paints.get(getAdapterPosition()));
                }
            });
        }

        public void bindPintura(Paint paint){
            textView.setText(paint.getName());
            Functionality.cargarImagenStorage(itemView.getContext(), paint.getImage(), imageView);
        }
    }


    public interface NotificadorCelda {
        void notificarPintura(Paint paint);
    }
}
