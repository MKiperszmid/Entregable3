package com.example.com.entregable.Util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Functionality {
    public static void loadProgressbar(Boolean bool, ProgressBar progressBar){
        int visibility = View.INVISIBLE;

        if(bool){
            visibility = View.VISIBLE;
        }
        progressBar.setVisibility(visibility);
        progressBar.setIndeterminate(bool);
    }

    public static void cargarImagenStorage(Context context, String path, ImageView imageView){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference();
        reference = reference.child(path);
        try{
            Glide.with(context).using(new FirebaseImageLoader()).load(reference).into(imageView);
        }
        catch (Exception e){
            e.printStackTrace();
            //No puede cargar la imagen
        }
    }
}
