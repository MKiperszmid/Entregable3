package com.example.com.entregable.Util;

import android.view.View;
import android.widget.ProgressBar;

public class Functionality {
    public static void loadProgressbar(Boolean bool, ProgressBar progressBar){
        int visibility = View.INVISIBLE;

        if(bool){
            visibility = View.VISIBLE;
        }
        progressBar.setVisibility(visibility);
        progressBar.setIndeterminate(bool);
    }
}
