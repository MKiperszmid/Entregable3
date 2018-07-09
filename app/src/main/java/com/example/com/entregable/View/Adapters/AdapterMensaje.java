package com.example.com.entregable.View.Adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.com.entregable.Model.POJO.Mensaje;
import com.example.com.entregable.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class AdapterMensaje extends RecyclerView.Adapter {
    private List<Mensaje> mensajes;
    private final int MI_MENSAJE = 0;
    private final int TU_MENSAJE = 1;

    public AdapterMensaje(){
        this.mensajes = new ArrayList<>();
    }

    public AdapterMensaje(List<Mensaje> mensajes){
        this.mensajes = mensajes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.celda_mensaje, parent, false);

        switch (viewType){
            case MI_MENSAJE:
                return new MiMensajeViewHolder(view);
            case TU_MENSAJE:
                return new TuMensajeViewHolder(view);
                //No se necesitaria un Default. Pero por si acaso lo dejo.
            default:
                return new TuMensajeViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(mensajes.get(position).getSender().equals(user.getEmail())){
            return MI_MENSAJE;
        }
        return TU_MENSAJE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MensajeViewHolder mensajeViewHolder = (MensajeViewHolder)holder;
        mensajeViewHolder.bindMensaje(mensajes.get(position));
    }

    public void clearMensajes(){
        mensajes.clear();
        notifyDataSetChanged();
    }

    public void addMensaje(Mensaje mensaje){
        mensajes.add(mensaje);
        notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    public abstract class MensajeViewHolder extends RecyclerView.ViewHolder {
        protected TextView texto;
        protected TextView sender;
        protected LinearLayout layout;

        public MensajeViewHolder(View itemView) {
            super(itemView);
            texto = itemView.findViewById(R.id.mensaje);
            sender = itemView.findViewById(R.id.sender);
            layout = itemView.findViewById(R.id.layout);
        }

        public abstract void bindMensaje(Mensaje mensaje);
    }

    public class MiMensajeViewHolder extends MensajeViewHolder {
        public MiMensajeViewHolder(View itemView) {
            super(itemView);
        }
        public void bindMensaje(Mensaje mensaje){
            texto.setBackgroundResource(R.color.colorVerdeAgua);
            sender.setTextColor(itemView.getResources().getColor(R.color.colorVerdeAgua));
            layout.setGravity(Gravity.END);
            sender.setText(mensaje.getSender());
            texto.setText(mensaje.getMensaje());
        }
    }

    public class TuMensajeViewHolder extends MensajeViewHolder {

        public TuMensajeViewHolder(View itemView) {
            super(itemView);
        }

        public void bindMensaje(Mensaje mensaje){
            layout.setGravity(Gravity.START);
            texto.setBackgroundResource(R.color.colorPrimary);
            sender.setTextColor(itemView.getResources().getColor(R.color.colorPrimary));
            sender.setText(mensaje.getSender());
            texto.setText(mensaje.getMensaje());
        }
    }
}
