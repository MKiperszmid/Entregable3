package com.example.com.entregable.View.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.entregable.Model.POJO.Mensaje;
import com.example.com.entregable.R;
import com.example.com.entregable.View.Activities.ExhibicionActivity;
import com.example.com.entregable.View.Activities.MainActivity;
import com.example.com.entregable.View.Adapters.AdapterMensaje;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private TextView btnEnviar;
    private EditText texto;
    private FirebaseDatabase database;
    private List<Mensaje> mensajes;
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private String nombreSeccion = "Chatroom";
    private final String CHATROOMDB = "Messages";
    private AdapterMensaje adapterMensaje;
    private Boolean isPrimeraVez;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ((ExhibicionActivity)getActivity()).getSupportActionBar().setTitle(nombreSeccion);
        texto = view.findViewById(R.id.fc_et_textoMensaje);
        btnEnviar = view.findViewById(R.id.fc_tv_enviarMensaje);
        isPrimeraVez = true;
        recyclerView = view.findViewById(R.id.fc_rv_mensajes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msj = texto.getText().toString();
                if(msj.length() > 0){
                    publicarMensaje(msj);
                    texto.setText("");
                }
            }
        });

        adapterMensaje = new AdapterMensaje();
        recyclerView.setAdapter(adapterMensaje);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child(CHATROOMDB);

        cargarMsjs();

        return view;
    }

    private void cargarMsjs(){

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    mensajes = new ArrayList<>();
                    recyclerView.setAdapter(new AdapterMensaje(mensajes));
                    return;
                }

                mensajes = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Mensaje mensaje = data.getValue(Mensaje.class);
                    mensajes.add(mensaje);
                    adapterMensaje.addMensaje(mensaje);
                }

                if(mensajes.size() > 0){
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }
                isPrimeraVez = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        actualizarMsjs();
    }

    private void actualizarMsjs(){
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(isPrimeraVez) return;
                adapterMensaje.addMensaje(dataSnapshot.getValue(Mensaje.class));
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

/*
    private void cargarMensajes(){
        ref = database.getReference().child(CHATROOMDB);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    mensajes = new ArrayList<>();
                    recyclerView.setAdapter(new AdapterMensaje(mensajes));
                    return;
                }

                mensajes = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Mensaje mensaje = data.getValue(Mensaje.class);
                    mensajes.add(mensaje);
                }

                AdapterMensaje adapterMensaje = new AdapterMensaje(mensajes);
                recyclerView.setAdapter(adapterMensaje);

                if(mensajes.size() > 0){
                    recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "FAILED", Toast.LENGTH_SHORT).show();
            }
        });
    }
*/
    private void publicarMensaje(String mensaje){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ref.push().setValue(new Mensaje(mensaje, user.getEmail()));
    }

}
