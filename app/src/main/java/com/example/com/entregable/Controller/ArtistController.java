package com.example.com.entregable.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.com.entregable.Model.POJO.Artist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArtistController {
    private final String ARTISTAS_KEY = "artists";
    private List<Artist> artistas;

    public ArtistController(){
        artistas = new ArrayList<>();
    }

    public void grabArtists(final Context context){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(ARTISTAS_KEY);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())
                    return;

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Log.i("DATA DATASNAP: ", data.getValue().toString());
                    for (DataSnapshot datas : data.getChildren()){
                        Log.i("DATAS DATASNAP: ", datas.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Hubo un error al conectar con la base de datos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
