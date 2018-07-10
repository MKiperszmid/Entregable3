package com.example.com.entregable.View.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.com.entregable.Model.POJO.Paint;
import com.example.com.entregable.R;
import com.example.com.entregable.View.Fragments.ChatFragment;
import com.example.com.entregable.View.Fragments.DetalleFragment;
import com.example.com.entregable.View.Fragments.ExhibitionFragment;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ExhibicionActivity extends AppCompatActivity implements ExhibitionFragment.NotificadorExhibitionActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibicion);
        drawerLayout = findViewById(R.id.ae_dl_layout);
        navigationView = findViewById(R.id.ae_nv_navigation);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(new NavigationListener());
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadFragment(new ExhibitionFragment());
    }

    private void navigationItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.navmenuPaintings:
                loadFragment(new ExhibitionFragment());
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.navmenuChatroom:
                loadFragment(new ChatFragment());
                drawerLayout.closeDrawer(Gravity.START);
                break;
            case R.id.navmenuLogout:
                logout();
                break;
        }
    }

    @Override
    public void notificar(Paint paint) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetalleFragment.PINTURA_KEY, paint);
        loadFragment(new DetalleFragment(), bundle);
    }

    private void logout(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            onBackPressed();
            overridePendingTransition(R.anim.slide_der_in, R.anim.slide_der_out);
            auth.signOut();
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if(accessToken != null && !accessToken.isExpired()) {
                LoginManager.getInstance().logOut();
            }
        }
    }

    private void loadFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.ae_fl_container, fragment);
        transaction.commit();
    }

    private void loadFragment(Fragment fragment, Bundle bundle){
        FragmentManager manager = getSupportFragmentManager();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.ae_fl_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private class NavigationListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            navigationItemSelected(item);
            return true;
        }
    }

}
