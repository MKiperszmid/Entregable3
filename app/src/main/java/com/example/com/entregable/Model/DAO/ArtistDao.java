package com.example.com.entregable.Model.DAO;

import android.arch.persistence.room.Dao;
import android.support.annotation.NonNull;

import com.example.com.entregable.Controller.ResultListener;
import com.example.com.entregable.Model.POJO.Artist;
import com.example.com.entregable.Model.POJO.ArtistContainer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArtistDao {
    private final String ARTISTS_KEY = "artists";
    private List<Artist> artistList;

    public ArtistDao(){
        artistList = new ArrayList<>();
    }

    public void grabArtists(final ResultListener<Artist> listener, final String id){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(ARTISTS_KEY);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            Artist artist = null;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    artist = snapshot.getValue(Artist.class);
                    if(artist.getArtistId().equals(id)) {
                        break;
                    }
                }
                listener.finish(artist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Null?
            }
        });
    }


    public void grabAllArtists(final ResultListener<ArtistContainer> listener){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(ARTISTS_KEY);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            Artist artist = null;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    artist = snapshot.getValue(Artist.class);
                    artistList.add(artist);
                }
                ArtistContainer container = new ArtistContainer(artistList);
                listener.finish(container);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Null?
            }
        });
    }
}